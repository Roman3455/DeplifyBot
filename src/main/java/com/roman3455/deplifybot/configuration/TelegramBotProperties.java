package com.roman3455.deplifybot.configuration;

import com.roman3455.deplifybot.dto.telegram.api.enums.BotCommandScopeType;
import com.roman3455.deplifybot.dto.telegram.api.enums.UpdateType;
import com.roman3455.deplifybot.util.validator.iso6391.ISO6391;
import jakarta.annotation.Nullable;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Locale;

/**
 * Configuration properties for Telegram bot integration.
 */
@Validated
@ConfigurationProperties(prefix = "telegram.bot.settings")
public record TelegramBotProperties(

        @NotBlank
        @Pattern(regexp = "^https://.+$", message = "{SetWebhookRequest.url.Pattern.message}")
        String webhookUrl,

        @NotBlank
        @Pattern(regexp = "^/.+$", message = "{SetWebhookRequest.path.Pattern.message}")
        String webhookPath,

        @Nullable
        @Min(value = 1)
        @Max(value = MAX_HTTP_CONNECTIONS)
        Integer allowedHttpConnections,

        @Nullable
        List<UpdateType> allowedUpdateTypes,

        @Nullable
        Boolean dropPendingUpdates,

        @NotNull
        BotCommandScopeType setUserCommandMenu,

        @Min(MIN_BYTES_SIZE)
        @Max(MAX_BYTES_SIZE)
        int secretTokenBytesSize,

        @NotEmpty
        List<@Valid LanguageSpec> languageSpecs

) {

    /**
     * The min allowed bytes size for token generation.
     */
    private static final int MIN_BYTES_SIZE = 16;

    /**
     * The max allowed bytes size for token generation.
     */
    private static final int MAX_BYTES_SIZE = 64;

    /**
     * The max allowed http connections to the webhook for update delivery.
     */
    private static final int MAX_HTTP_CONNECTIONS = 100;

    /**
     * Parameters used to register i18n messages in Telegram.
     */
    public record LanguageSpec(

            @NotBlank
            String localeTag,

            @Nullable
            @ISO6391
            String languageCode,

            @NotBlank
            String countryCode

    ) {

        /**
         * @return prepared Locale to use.
         */
        public Locale getLocale() {
            return Locale.forLanguageTag(localeTag);
        }

    }

}
