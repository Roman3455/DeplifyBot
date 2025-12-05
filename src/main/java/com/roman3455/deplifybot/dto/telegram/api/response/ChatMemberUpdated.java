package com.roman3455.deplifybot.dto.telegram.api.response;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.time.Instant;

/**
 * DTO represents an update about a user's or the bot's chat member status.
 *
 * <p>This object is delivered when the membership status of a user (including the bot) changes within a chat.
 * Such changes may include joining, leaving, being promoted or demoted, or being restricted. The update contains
 * both the previous and the new membership state, allowing the application to determine what exactly has changed.</p>
 *
 * @param chat          required. The chat in which the member status was updated.
 * @param from          required. The user who performed the action that caused the change (e.g., an admin who
 *                      promoted or restricted another user).
 * @param date          required. The timestamp when the change occurred.
 * @param oldChatMember required. The previous chat member state before the update.
 * @param newChatMember required. The new chat member state after the update.
 * @see <a href="https://core.telegram.org/bots/api#chatmemberupdated">Telegram API — ChatMemberUpdated</a>
 */
public record ChatMemberUpdated(

        @NotNull
        @Valid
        Chat chat,

        @NotNull
        @Valid
        User from,

        @NotNull
        Instant date,

        @NotNull
        @Valid
        ChatMember oldChatMember,

        @NotNull
        @Valid
        ChatMember newChatMember

) {
}
