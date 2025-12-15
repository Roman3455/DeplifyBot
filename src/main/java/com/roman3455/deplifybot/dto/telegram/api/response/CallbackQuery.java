package com.roman3455.deplifybot.dto.telegram.api.response;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.lang.Nullable;

/**
 * DTO represents an incoming callback query from the Telegram Bot API.
 *
 * <p>Callback queries are sent to the bot when a user presses an inline button attached to a message.
 * Each callback query carries a unique {@code id} and information about the user who triggered it,
 * along with either the original message or an identifier for an inline message.</p>
 *
 * @param id      required. Unique identifier for this query.
 * @param from    required. The {@link User} who triggered the callback. Contains information about the
 *                user and their account.
 * @param message optional. The {@link Message} associated with the callback. Present only if the button
 *                was attached to a standard chat message.
 * @param data    optional. The callback data payload specified when the button was created.
 * @see <a href="https://core.telegram.org/bots/api#callbackquery">Telegram API — CallbackQuery</a>
 */
public record CallbackQuery(

        @NotNull
        String id,

        @NotNull
        @Valid
        User from,

        @Nullable
        @Valid
        Message message,

        @Nullable
        String data

) {

    /**
     * Check if callback query contains message.
     *
     * @return {@code true} if {@link #message} is not {@code null}.
     */
    public boolean hasMessage() {
        return message != null;
    }

    /**
     * Check if callback query contains data.
     *
     * @return {@code true} if {@link #data} is not {@code null}.
     */
    public boolean hasData() {
        return data != null;
    }

}
