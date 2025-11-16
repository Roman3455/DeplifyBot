package com.roman3455.deplifybot.dto.telegram.api.response;

import com.roman3455.deplifybot.dto.telegram.api.enums.ChatType;
import jakarta.validation.constraints.NotNull;
import org.springframework.lang.Nullable;

/**
 * DTO represents a chat object from the Telegram Bot API.
 *
 * <p>A chat can represent a private conversation, a group, a supergroup, or a channel. Depending on the
 * chat type, certain fields may be present or absent. For example, private chats include the user's first
 * name, while group and channel chats may have a title and a public username.</p>
 *
 * @param id        required. Unique identifier for this chat.
 * @param type      required. Type of the chat (private, group, supergroup, or channel).
 * @param title     optional. Chat title, present for group chats, supergroups, and channels.
 * @param username  optional. Public username of the chat, without the leading {@code @}. Present only if
 *                  the chat has one (channels, supergroups, some groups, and some users).
 * @param firstName optional. First name of the other party in a private chat. Present only for private chats.
 * @param isForum   optional. Indicates whether the chat is a forum supergroup where topics are enabled.
 *                  Present only for supergroups with forum mode turned on.
 * @see <a href="https://core.telegram.org/bots/api#chat">Telegram API — Chat</a>
 */
public record Chat(

        @NotNull
        Long id,

        @NotNull
        ChatType type,

        @Nullable
        String title,

        @Nullable
        String username,

        @Nullable
        String firstName,

        @Nullable
        Boolean isForum

) {
}
