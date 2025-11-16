package com.roman3455.deplifybot.dto.telegram.api.request;

import com.roman3455.deplifybot.dto.telegram.api.enums.UpdateType;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.springframework.lang.Nullable;

import java.util.List;

/**
 * DTO represents a Telegram bot's webhook properties.
 *
 * @param url                required. HTTPS URL to send updates to.
 * @param maxConnections     optional. The maximum allowed number of simultaneous HTTPS connections to the webhook for
 *                           update delivery, 1-100 {@value #MAX_HTTP_CONNECTIONS}. Defaults to 40.
 * @param allowedUpdates     optional. A list of the update types you want your bot to receive. Specify an empty list to
 *                           receive all update types except {@code chat_member}, {@code message_reaction}, and
 *                           {@code message_reaction_count} (default). If not specified, the previous setting will be
 *                           used.
 * @param dropPendingUpdates optional. Pass {@code True} to drop all pending updates.
 * @param secretToken        optional. A secret token to be sent in a header {@code X-Telegram-Bot-Api-Secret-Token} in
 *                           every webhook request, 1-256 characters {@value #MAX_TOKEN_LENGTH}. Only characters A-Z,
 *                           a-z, 0-9, _ and - are allowed.
 * @see <a href="https://core.telegram.org/bots/api#update">Telegram API — Update</a>
 * @see <a href="https://core.telegram.org/bots/api#setwebhook">Telegram API — SetWebhook</a>
 */
public record SetWebhookRequest(

        @NotBlank
        @Pattern(regexp = "^https://.+$", message = "{SetWebhookRequest.url.Pattern.message}")
        String url,

        @Nullable
        @Min(value = 1, message = "{Size.min.message}")
        @Max(value = MAX_HTTP_CONNECTIONS, message = "{Size.max.message}")
        Integer maxConnections,

        @Nullable
        List<UpdateType> allowedUpdates,

        @Nullable
        Boolean dropPendingUpdates,

        @Nullable
        @Pattern(regexp = "^[A-Za-z0-9_-]+$", message = "{SetWebhookRequest.secretToken.Pattern.message}")
        @Size(min = 1, max = MAX_TOKEN_LENGTH)
        String secretToken

) {

    /**
     * The max allowed http connections to the webhook for update delivery.
     */
    private static final int MAX_HTTP_CONNECTIONS = 100;

    /**
     * The max allowed length of the {@code secretToken} field.
     */
    private static final int MAX_TOKEN_LENGTH = 256;

}
