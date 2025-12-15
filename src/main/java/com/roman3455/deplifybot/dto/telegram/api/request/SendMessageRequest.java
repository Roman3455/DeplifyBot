package com.roman3455.deplifybot.dto.telegram.api.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.roman3455.deplifybot.dto.telegram.api.enums.ParseModeType;
import com.roman3455.deplifybot.dto.telegram.api.ui.ReplyMarkup;
import jakarta.annotation.Nullable;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * DTO represents a payload for the Telegram Bot API {@code sendMessage} method.
 *
 * <p>This request is used to send text messages to a specific chat. It supports formatting
 * options, message threading in topics, and additional delivery settings.</p>
 *
 * @param chatId              required. Unique identifier for the target chat. Can be a numeric chat ID or channel
 *                            username (e.g. {@code "@channel"}).
 * @param messageThreadId     optional. Unique identifier of a message thread (topic) within a forum supergroup.
 *                            When {@code null}, the message is sent into the main chat stream.
 * @param text                required. Text content of the message. Must not exceed {@value MAX_TEXT_LENGTH}
 *                            characters.
 * @param parseMode           optional. Formatting mode determining how the {@code text} is interpreted,
 *                            such as {@code MarkdownV2} or {@code HTML}. When {@code null}, the message is treated as
 *                            plain text.
 * @param disableNotification optional. When {@code true}, sends the message silently — users receive
 *                            a notification without sound.
 * @param protectContent      optional. When {@code true}, prevents the message content from being
 *                            forwarded or saved by the user (content protection).
 * @param replyMarkup         optional. An inline keyboard, custom reply keyboard, instructions to remove a reply
 *                            keyboard or to force a reply from the user.
 * @see <a href="https://core.telegram.org/bots/api#sendmessage">Telegram API — sendMessage</a>.
 */
public record SendMessageRequest(

        @NotNull
        Long chatId,

        @Nullable
        Long messageThreadId,

        @NotBlank
        @Size(max = MAX_TEXT_LENGTH, message = "{Size.max.message}")
        String text,

        @Nullable
        ParseModeType parseMode,

        @Nullable
        Boolean disableNotification,

        @Nullable
        Boolean protectContent,

        @Nullable
        @JsonProperty(access = JsonProperty.Access.READ_ONLY)
        @Valid
        ReplyMarkup replyMarkup

) {

    /**
     * The max allowed text length for sending message.
     */
    private static final int MAX_TEXT_LENGTH = 4096;

}
