package com.roman3455.deplifybot.dto.telegram.api.response;

import com.roman3455.deplifybot.dto.telegram.api.enums.ChatMemberStatusType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

/**
 * DTO represents a chat member object from the Telegram Bot API.
 *
 * <p>This object describes a user's current status within a chat. The status may reflect whether the user is
 * the chat creator, an administrator, a regular member, or restricted in some way. Combined with
 * {@link ChatMemberUpdated}, it allows the application to determine what permissions or actions are available
 * to the user in that chat.</p>
 *
 * @param status required. The role or membership state of the user in the chat.
 * @param user   required. The user to whom this membership record applies.
 * @see <a href="https://core.telegram.org/bots/api#chatmember">Telegram API — ChatMember</a>
 * @see ChatMemberStatusType
 */
public record ChatMember(

        @NotNull
        ChatMemberStatusType status,

        @NotNull
        @Valid User user

) {
}
