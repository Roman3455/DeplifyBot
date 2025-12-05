package com.roman3455.deplifybot.dto.telegram.api.response;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.lang.Nullable;

import java.time.Instant;

/**
 * DTO represents an incoming message from the Telegram Bot API.
 *
 * <p>A message may contain text or other types of content. Depending on the source and context,
 * it may also include thread information (for topics), sender data, chat metadata, and migration
 * identifiers when groups are converted to supergroups.</p>
 *
 * @param messageId         required. Unique identifier for this message within the chat.
 * @param messageThreadId   optional. Identifier of the thread the message belongs to. Present only if the
 *                          chat supports message threading (e.g. forum topics).
 * @param from              optional. Sender of the message. For outgoing messages sent by the bot itself,
 *                          this field may be missing.
 * @param date              required. Timestamp when the message was sent.
 * @param chat              required. The chat where the message belongs.
 * @param text              optional. Text content of the message. Present only if the message contains text.
 * @param migrateToChatId   optional. Indicates that the chat has been migrated to a new supergroup with the
 *                          provided identifier. Present in service messages only.
 * @param migrateFromChatId optional. Indicates that the current message was sent in a chat that migrated
 *                          from another chat identifier. Present in service messages only.
 * @param chatShared        optional. Contains information about a chat shared via the chat request feature.
 * @see <a href="https://core.telegram.org/bots/api#message">Telegram API — Message</a>
 */
public record Message(

        @NotNull
        Long messageId,

        @Nullable
        Long messageThreadId,

        @Nullable
        @Valid
        User from,

        @NotNull
        Instant date,

        @NotNull
        @Valid
        Chat chat,

        @Nullable
        String text,

        @Nullable
        Long migrateToChatId,

        @Nullable
        Long migrateFromChatId,

        @Nullable
        @Valid
        ChatShared chatShared

) {

    /**
     * Check if the message contains non-empty text content.
     *
     * @return {@code true} if {@link #text} is not {@code null} and not blank.
     */
    public boolean hasText() {
        return this.text != null && !this.text.isBlank();
    }

}
