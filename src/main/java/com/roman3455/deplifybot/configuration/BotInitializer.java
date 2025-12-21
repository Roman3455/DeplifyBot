package com.roman3455.deplifybot.configuration;

import com.roman3455.deplifybot.dto.telegram.api.enums.BotCommandScopeType;
import com.roman3455.deplifybot.dto.telegram.api.enums.UpdateType;
import com.roman3455.deplifybot.dto.telegram.api.request.BotCommandScope;
import com.roman3455.deplifybot.dto.telegram.api.request.BotDescriptionRequest;
import com.roman3455.deplifybot.dto.telegram.api.request.BotShortDescriptionRequest;
import com.roman3455.deplifybot.dto.telegram.api.request.MyCommand;
import com.roman3455.deplifybot.dto.telegram.api.request.SetMyCommandsRequest;
import com.roman3455.deplifybot.dto.telegram.api.request.SetWebhookRequest;
import com.roman3455.deplifybot.dto.telegram.api.response.ResponseBody;
import com.roman3455.deplifybot.exception.business.BotInitializationException;
import com.roman3455.deplifybot.exception.telegram.TelegramApiException;
import com.roman3455.deplifybot.service.telegram.TelegramClientService;
import com.roman3455.deplifybot.service.telegram.command.CommandType;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.MessageSource;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

/**
 * Initializes Telegram bot metadata and webhook on application startup.
 *
 * <p>Responsibilities:
 * <ul>
 *   <li>Set localized description.</li>
 *   <li>Set localized short description.</li>
 *   <li>Set localized commands.</li>
 *   <li>Set <b>webhook</b>.</li>
 * </ul></p>
 *
 * <p>Error handling: transport/HTTP errors wrapped into {@link BotInitializationException}.
 * Domain errors (HTTP 200 but {@code ok=false} or {@code result=false}) are detected via
 * {@link #assertOk(ResponseBody, String, String)} and also fail initialization.</p>
 */
@ConditionalOnProperty(value = "telegram.bot.init.enabled", havingValue = "true", matchIfMissing = true)
@Component
public class BotInitializer {

    private static final Logger LOG = LoggerFactory.getLogger(BotInitializer.class);

    private final MessageSource msgSource;
    private final TelegramApiTokenConfiguration tokenConfig;
    private final TelegramBotProperties botProperties;
    private final TelegramClientService clientService;

    private final List<TelegramBotProperties.LanguageSpec> languageSpecs;

    public BotInitializer(
            final MessageSource msgSource,
            final TelegramApiTokenConfiguration tokenConfig,
            final TelegramBotProperties botProperties,
            final TelegramClientService clientService
    ) {
        this.msgSource = msgSource;
        this.tokenConfig = tokenConfig;
        this.botProperties = botProperties;
        this.clientService = clientService;
        this.languageSpecs = List.copyOf(botProperties.languageSpecs());
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
        String messageCode = "bot.description";
        setLocalisedProperty(messageCode, clientService::setMyDescription, spec -> {
            String message = msgSource.getMessage(messageCode, null, messageCode, spec.getLocale());
            return new BotDescriptionRequest(message, spec.languageCode());
        });
    }

    /**
     * Sets localized short description.
     */
    private void setBotShortDescription() {
        String messageCode = "bot.short-description";
        setLocalisedProperty(messageCode, clientService::setMyShortDescription, spec -> {
            String message = msgSource.getMessage(messageCode, null, messageCode, spec.getLocale());
            return new BotShortDescriptionRequest(message, spec.languageCode());
        });
    }

    /**
     * Sets localized commands for configured chat.
     */
    private void setBotCommands() {
        var chatType = botProperties.setUserCommandMenu();
        var userMenuScope = new BotCommandScope(
                chatType.equals(BotCommandScopeType.CHAT) ? BotCommandScopeType.DEFAULT : chatType,
                null
        );
        String actonLabel = "bot commands for %s".formatted(userMenuScope.type().getValue());
        List<CommandType> commandTypes = Arrays.asList(CommandType.values());
        setLocalisedProperty(actonLabel, clientService::setMyCommands, spec -> {
            List<MyCommand> localizedCommands = commandTypes.stream()
                    .map(t -> new MyCommand(
                                    t.getNameWithoutSlash(),
                                    msgSource.getMessage(t.getMessageCode(), null, t.getMessageCode(), spec.getLocale())
                            )
                    ).toList();
            return new SetMyCommandsRequest(localizedCommands, userMenuScope, spec.languageCode());
        });
    }

    /**
     * Registers webhook with Telegram.
     *
     * @throws BotInitializationException on transport or domain error.
     */
    private void setBotWebhook() {
        final int defaultHttpConnections = 40;
        String url = botProperties.webhookUrl() + botProperties.webhookPath();
        final int connections = botProperties.allowedHttpConnections() == null
                ? defaultHttpConnections
                : botProperties.allowedHttpConnections();
        HashSet<UpdateType> configuredUpdateTypes = botProperties.allowedUpdateTypes() == null
                ? new HashSet<>()
                : new HashSet<>(botProperties.allowedUpdateTypes());
        List<UpdateType> allowedUpdates = null;
        if (!configuredUpdateTypes.isEmpty()) {
            allowedUpdates = Stream.of(UpdateType.values())
                    .filter(configuredUpdateTypes::contains)
                    .toList();
        }
        SetWebhookRequest request = new SetWebhookRequest(
                url,
                connections,
                allowedUpdates,
                true,
                tokenConfig.getToken()
        );
        ResponseBody<Boolean> response;
        try {
            response = clientService.setWebhook(request);
        } catch (TelegramApiException | FeignException e) {
            throw new BotInitializationException("Transport error while setting bot webhook. Cause: ", e);
        }
        assertOk(response, "webhook", "");
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
     * @param <T>             request type (e.g., {@link BotDescriptionRequest}).
     * @throws BotInitializationException on transport or domain error.
     */
    private <T> void setLocalisedProperty(
            final String actionLabel,
            final Function<T, ResponseBody<Boolean>> apiCall,
            final Function<TelegramBotProperties.LanguageSpec, T> requestSupplier
    ) {
        languageSpecs.forEach(spec -> {
            T request = requestSupplier.apply(spec);
            ResponseBody<Boolean> response;
            try {
                response = apiCall.apply(request);
            } catch (TelegramApiException | FeignException e) {
                String exceptionTemplate = "Transport error while setting bot %s for %s locale. Cause: ";
                throw new BotInitializationException(exceptionTemplate.formatted(actionLabel, spec.countryCode()), e);
            }
            assertOk(response, actionLabel, spec.countryCode());
        });
    }

    /**
     * Validates Telegram Bot API domain success.
     *
     * <p>Throws if response is null, {@code ok=false}, or {@code result=false}.</p>
     *
     * @param response API response body.
     * @param action   action label (e.g., {@code "description"}).
     * @param label    locale label for logs (e.g., {@code "EN"}).
     * @throws BotInitializationException if domain success criteria are not met.
     */
    private void assertOk(final ResponseBody<Boolean> response, final String action, final String label) {
        boolean isNotSuccessful = response == null || !response.ok() || !Boolean.TRUE.equals(response.result());
        if (isNotSuccessful) {
            boolean hasCause = response != null && response.description() != null;
            String desc = hasCause ? response.description() : "response information is missing";
            String exceptionTemplate = "Domain error while setting bot %s for %s locale. Cause: %s";
            throw new BotInitializationException(exceptionTemplate.formatted(action, label, desc));
        } else {
            if (!label.isBlank()) {
                LOG.info("{} successfully set for {} locale", action, label);
            }
        }
    }

}
