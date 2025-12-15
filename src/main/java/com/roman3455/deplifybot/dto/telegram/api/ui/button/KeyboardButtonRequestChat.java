package com.roman3455.deplifybot.dto.telegram.api.ui.button;

import com.roman3455.deplifybot.dto.telegram.api.response.ChatAdministratorRights;
import jakarta.annotation.Nullable;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

/**
 * DTO representing the request for a chat in a keyboard button, triggered by a Telegram bot.
 *
 * <p>The {@code KeyboardButtonRequestChat} class defines the necessary fields to request a chat from a user
 * in response to a keyboard button. This request can provide details about the chat's type, the administrator
 * rights of both the user and the bot, as well as whether the bot is a member of the chat.</p>
 *
 * @param requestId               required. The unique identifier for this chat request.
 * @param chatIsChannel           required. Indicates whether the requested chat is a channel (true) or a group or
 *                                a supergroup chat (false).
 * @param userAdministratorRights optional. {@link ChatAdministratorRights} of the user in the requested chat.
 * @param botAdministratorRights  optional. {@link ChatAdministratorRights} of the bot in the requested chat.
 * @param botIsMember             optional. Indicates whether the bot is a member of the requested chat
 *                                (null if unknown).
 * @param requestTitle            optional. Title of the requested chat, which can be used as part of the request.
 * @see <a href="https://core.telegram.org/bots/api#keyboardbuttonrequestchat">
 * Telegram API — KeyboardButtonRequestChat</a>
 */
public record KeyboardButtonRequestChat(

        @NotNull
        Long requestId,

        boolean chatIsChannel,

        @Nullable
        @Valid
        ChatAdministratorRights userAdministratorRights,

        @Nullable
        @Valid
        ChatAdministratorRights botAdministratorRights,

        @Nullable
        Boolean botIsMember,

        @Nullable
        Boolean requestTitle

) {
}
