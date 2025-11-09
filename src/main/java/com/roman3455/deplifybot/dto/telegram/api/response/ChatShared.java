package com.roman3455.deplifybot.dto.telegram.api.response;

import jakarta.validation.constraints.NotNull;
import org.springframework.lang.Nullable;

/**
 * DTO represents a shared chat object received via the chat request feature of the Telegram Bot API.
 *
 * <p>This object is delivered when a user selects and shares a chat with the bot in response to a button
 * configured with {@code request_chat}. It contains identifiers that allow the bot to reference and interact
 * with the shared chat, as well as optional metadata such as a title or username.</p>
 *
 * @param requestId required. Identifier of the request that triggered the chat selection. Matches the value
 *                  defined when the bot requested chat sharing.
 * @param chatId    required. Unique identifier of the shared chat.
 * @param title     optional. The title of the chat (e.g., group or channel name). Present for non-private chats.
 * @param username  optional. The public username of the chat, without the leading {@code @}. Present only if
 *                  the chat has a public handle.
 * @see <a href="https://core.telegram.org/bots/api#chatshared">Telegram API — ChatShared</a>
 */
public record ChatShared(

        @NotNull
        Long requestId,
        @NotNull
        Long chatId,
        @Nullable
        String title,
        @Nullable
        String username

) {
}
