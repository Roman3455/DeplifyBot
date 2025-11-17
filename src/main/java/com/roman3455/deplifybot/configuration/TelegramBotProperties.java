package com.roman3455.deplifybot.configuration;

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

/**
 * Configuration properties for Telegram bot integration.
 *
 * <p>Bound to the {@code telegram.bot.*} prefix.</p>
 */
@Validated
@ConfigurationProperties(prefix = "telegram.bot")
public record TelegramBotProperties(

        @NotEmpty
        List<@NotBlank String> allowedUpdateTypes,

        @NotNull
        @Valid Connections connections,

        @NotNull
        @Valid Webhook webhook,

        @NotNull
        @Valid Token token

) {

    /**
     * Webhook connection limits.
     */
    public record Connections(

            @Min(MIN_CONNECTIONS)
            @Max(MAX_CONNECTIONS)
            int value

    ) {
        private static final int MAX_CONNECTIONS = 100;
        private static final int MIN_CONNECTIONS = 1;
    }

    /**
     * Webhook URL and path used to register the bot endpoint in Telegram.
     */
    public record Webhook(

            @NotBlank
            @Pattern(regexp = "^https://.+$", message = "{SetWebhookRequest.url.Pattern.message}")
            String url,

            @NotBlank
            String path

    ) {
    }

    /**
     * Parameters used to generate the Telegram API secret token.
     */
    public record Token(

            @Min(MIN_BYTES)
            @Max(MAX_BYTES)
            int bytesSize

    ) {
        private static final int MAX_BYTES = 64;
        private static final int MIN_BYTES = 16;
    }

}
