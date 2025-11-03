package com.roman3455.deplifybot.configuration;

import com.roman3455.deplifybot.dto.telegram.api.enums.BotCommandScopeType;
import com.roman3455.deplifybot.dto.telegram.api.request.BotCommandScope;
import com.roman3455.deplifybot.dto.telegram.api.request.BotDescriptionRequest;
import com.roman3455.deplifybot.dto.telegram.api.request.BotShortDescriptionRequest;
import com.roman3455.deplifybot.dto.telegram.api.request.MyCommand;
import com.roman3455.deplifybot.dto.telegram.api.request.SetMyCommandsRequest;
import com.roman3455.deplifybot.dto.telegram.api.request.SetWebhookRequest;
import com.roman3455.deplifybot.dto.telegram.api.response.ResponseBody;
import com.roman3455.deplifybot.exception.business.BotInitializationException;
import com.roman3455.deplifybot.exception.telegram.TelegramApiException;
import com.roman3455.deplifybot.service.telegram.TelegramApiTokenService;
import com.roman3455.deplifybot.service.telegram.TelegramClientService;
import com.roman3455.deplifybot.service.telegram.command.CommandType;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.MessageSource;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.function.Function;

/**
 * Initializes Telegram bot metadata and webhook on application startup.
 *
 * <p>Responsibilities:</p>
 * <ul>
 *   <li>Set localized <b>description</b> and <b>short description</b>.</li>
 *   <li>Set localized <b>commands</b> for private chats.</li>
 *   <li>Set <b>webhook</b>.</li>
 * </ul>
 *
 * <p><b>Error handling:</b> transport/HTTP errors are mapped to {@link TelegramApiException} or {@link FeignException}
 * and wrapped into {@link BotInitializationException}. Domain errors (HTTP 200 but {@code ok=false} or
 * {@code result=false}) are detected via {@link #assertOk(ResponseBody, String, String)} and also fail
 * initialization.</p>
 *
 * <p><b>Localization:</b> DEFAULT entry uses {@link Locale#ROOT} to read base bundle (ensure
 * {@code MessageSource#setFallbackToSystemLocale(false)} so the base file is used as default).</p>
 */
@ConditionalOnProperty(value = "telegram.bot.init.enabled", havingValue = "true", matchIfMissing = true)
@Component
public class BotInitializer {

    private static final Logger LOG = LoggerFactory.getLogger(BotInitializer.class);

    /**
     * Supported locales for initialization:
     * <ul>
     *   <li>DEFAULT: base bundle via {@link Locale#ROOT} (sent without {@code language_code}).</li>
     *   <li>EN: {@code language_code="en"}.</li>
     *   <li>RU: {@code language_code="ru"}.</li>
     * </ul>
     */
    private static final List<LanguageSpec> LANGUAGE_SPECS = List.of(
            new LanguageSpec(Locale.ROOT, null, "DEFAULT"),
            new LanguageSpec(Locale.forLanguageTag("en"), "en", "EN"),
            new LanguageSpec(Locale.forLanguageTag("ru"), "ru", "RU")
    );

    private final MessageSource messageSource;
    private final TelegramClientService clientService;
    private final TelegramApiTokenService tokenService;

    private final String botUrl;
    private final int maxConnections;

    /**
     * Value object describing one locale variant to be applied to Telegram API.
     *
     * @param locale       Java locale used to resolve messages.
     * @param languageCode Telegram {@code language_code} (nullable for default).
     * @param label        Short label used in logs.
     */
    private record LanguageSpec(Locale locale, String languageCode, String label) {
    }

    public BotInitializer(
            final MessageSource messageSource,
            final TelegramClientService clientService,
            final TelegramApiTokenService tokenService,
            @Value("${telegram.bot.webhook.url}") final String webhookUrl,
            @Value("${telegram.bot.webhook.path}") final String webhookPath,
            @Value("${telegram.bot.connections.value}") final int maxConnections
    ) {
        this.messageSource = messageSource;
        this.clientService = clientService;
        this.tokenService = tokenService;
        this.botUrl = webhookUrl + webhookPath;
        this.maxConnections = maxConnections;
    }

    /**
     * Entry point invoked when the application is ready.
     *
     * @throws BotInitializationException if any setup step fails.
     */
    @EventListener(ApplicationReadyEvent.class)
    public void botInit() {
        setBotDescription();
        LOG.info("All bot descriptions successfully set");
        setBotShortDescription();
        LOG.info("All bot short descriptions successfully set");
        setBotCommands();
        LOG.info("All bot commands successfully set");
        setBotWebhook();
        LOG.info("Bot webhook successfully set");
    }

    /**
     * Sets localized bot description.
     */
    private void setBotDescription() {
        final String messageCode = "bot.description";
        final String fallback = "Description not set";
        setForLocales("description", clientService::setMyDescription, spec -> {
            String message = messageSource.getMessage(messageCode, null, fallback, spec.locale());
            return new BotDescriptionRequest(message, spec.languageCode());
        });
    }

    /**
     * Sets localized short description.
     */
    private void setBotShortDescription() {
        final String messageCode = "bot.short-description";
        final String fallback = "Short description not set";
        setForLocales("short description", clientService::setMyShortDescription, spec -> {
            String message = messageSource.getMessage(messageCode, null, fallback, spec.locale());
            return new BotShortDescriptionRequest(message, spec.languageCode());
        });
    }

    /**
     * Sets localized commands for private chats.
     */
    private void setBotCommands() {
        var chatScope = new BotCommandScope(BotCommandScopeType.ALL_PRIVATE_CHATS, null);
        setForLocales("bot commands", clientService::setMyCommands, spec -> {
            List<MyCommand> commands = Arrays.stream(CommandType.values())
                    .map(type -> new MyCommand(
                            type.getNameWithoutSlash(),
                            messageSource.getMessage(
                                    type.getMessageCode(),
                                    null,
                                    "<missing " + type.getMessageCode() + ">",
                                    spec.locale()
                            ))
                    ).toList();
            return new SetMyCommandsRequest(commands, chatScope, spec.languageCode());
        });
    }

    /**
     * Registers webhook with Telegram.
     *
     * @throws BotInitializationException on transport or domain error.
     */
    private void setBotWebhook() {
        var request = new SetWebhookRequest(botUrl, maxConnections, null, true, tokenService.getToken());
        ResponseBody<Boolean> responseBody;
        try {
            responseBody = clientService.setWebhook(request);
        } catch (TelegramApiException | FeignException e) {
            throw new BotInitializationException("Transport error while setting bot webhook", e);
        }
        assertOk(responseBody, "webhook", "N/A");
    }

    /**
     * Generic helper that:
     * <ol>
     *   <li>Builds a request per locale.</li>
     *   <li>Calls Telegram API.</li>
     *   <li>Validates domain success via {@link #assertOk(ResponseBody, String, String)}.</li>
     * </ol>
     *
     * @param actionLabel     label for logs/exceptions (e.g., {@code "description"}).
     * @param apiCall         function to perform API call (transport errors are caught and wrapped).
     * @param requestSupplier supplier to build per-locale request.
     * @param <TReq>          request type (e.g., {@link BotDescriptionRequest}).
     * @throws BotInitializationException on transport or domain error.
     */
    private <TReq> void setForLocales(
            final String actionLabel,
            final Function<TReq, ResponseBody<Boolean>> apiCall,
            final Function<LanguageSpec, TReq> requestSupplier
    ) {
        LANGUAGE_SPECS.forEach(spec -> {
            TReq request = requestSupplier.apply(spec);
            ResponseBody<Boolean> response;
            try {
                response = apiCall.apply(request);

            } catch (TelegramApiException | FeignException e) {
                throw new BotInitializationException(
                        "Transport error while setting bot %s for %s locale".formatted(actionLabel, spec.label()), e
                );
            }
            assertOk(response, actionLabel, spec.label());
        });
    }

    /**
     * Validates Telegram Bot API domain success.
     *
     * <p>Throws if response is null, {@code ok=false}, or {@code result=false}.</p>
     *
     * @param response API response body
     * @param action   action label (e.g., {@code "description"})
     * @param label    locale label for logs (e.g., {@code "EN"})
     * @throws BotInitializationException if domain success criteria are not met
     */
    private void assertOk(final ResponseBody<Boolean> response, final String action, final String label) {
        if (response == null || !response.ok() || Boolean.FALSE.equals(response.result())) {
            String desc = (response != null && response.description() != null)
                    ? response.description() : "no response";
            throw new BotInitializationException(
                    "Domain error while setting bot %s for %s locale: %s".formatted(action, label, desc)
            );
        } else {
            LOG.info("Bot {} successfully set for {} locale", action, label);
        }
    }

}
