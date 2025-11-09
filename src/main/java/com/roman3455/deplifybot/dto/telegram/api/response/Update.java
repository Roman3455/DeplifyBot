package com.roman3455.deplifybot.dto.telegram.api.response;

import jakarta.validation.Valid;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.lang.Nullable;

/**
 * DTO represents an incoming update from the Telegram Bot API.
 *
 * <p>Each update carries a unique {@code updateId} and may contain one of several optional payloads depending on
 * the event type. At most one of the optional parameters can be present in any given update.</p>
 *
 * @param updateId      required. The update's unique identifier. It allows you to ignore repeated updates or to
 *                      restore the correct update sequence.
 * @param message       optional. New incoming message of any kind - text, photo, sticker, etc.
 * @param callbackQuery optional. New incoming callback query.
 * @param myChatMember  optional. The bot's chat member status was updated in a chat. For private chats, this
 *                      update is received only when the bot is blocked or unblocked by the user.
 * @see <a href="https://core.telegram.org/bots/api#update">Telegram API — Update</a>
 */
public record Update(

        @NotNull
        @Positive
        long updateId,
        @Nullable
        @Valid Message message,
        @Nullable
        @Valid CallbackQuery callbackQuery,
        @Nullable
        @Valid ChatMemberUpdated myChatMember

) {

    /**
     * Check if update contains message.
     *
     * @return {@code true} if {@link #message} is not {@code null}.
     */
    public boolean hasMessage() {
        return this.message != null;
    }

    /**
     * Check if update contains callback query.
     *
     * @return {@code true} if {@link #callbackQuery} is not {@code null}.
     */
    public boolean hasCallbackQuery() {
        return this.callbackQuery != null;
    }

    /**
     * Check if update contains my chat member.
     *
     * @return {@code true} if {@link #myChatMember} is not {@code null}.
     */
    public boolean hasMyChatMember() {
        return this.myChatMember != null;
    }

    /**
     * Cross-field constraint: require at most one of the optional field to be provided.
     *
     * <p>Violation will be reported on the synthetic property named after this method.</p>
     *
     * @return {@code true} if only one of the optional is not {@code null}.
     */
    @AssertTrue(message = "{Update.isAnyProvided.AssertTrue}")
    private boolean isAnyProvided() {
        int filledOptionalFields = 0;
        if (message != null) {
            filledOptionalFields++;
        }
        if (callbackQuery != null) {
            filledOptionalFields++;
        }
        if (myChatMember != null) {
            filledOptionalFields++;
        }
        return filledOptionalFields == 1;
    }

}
