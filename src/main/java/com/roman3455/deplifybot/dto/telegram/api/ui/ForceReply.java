package com.roman3455.deplifybot.dto.telegram.api.ui;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * DTO representing the Telegram <i>ForceReply</i> reply markup.
 *
 * <p>When a message is sent with {@code ForceReply}, the Telegram client forces the user
 * to respond — the input field is automatically focused, and the reply is explicitly linked
 * to the original message.</p>
 *
 * <p>The {@code forceReply} flag must always be {@code true}. If set to {@code false},
 * an {@link IllegalArgumentException} will be thrown during construction.</p>
 *
 * @param forceReply            required. Must always be {@code true}; required by the Telegram API.
 * @param inputFieldPlaceholder optional. Placeholder text for the Telegram client's input field;
 *                              1–{@value #MAX_PLACEHOLDER_LENGTH} characters.
 * @see <a href="https://core.telegram.org/bots/api#forcereply">Telegram API — ForceReply</a>
 */
public record ForceReply(

        @AssertTrue(message = "{ForceReply.forceReplyIsTrue.AssertTrue}")
        boolean forceReply,

        @Nullable
        @Size(min = 1, max = MAX_PLACEHOLDER_LENGTH)
        String inputFieldPlaceholder

) implements ReplyMarkup {

    /**
     * Maximum allowed length of the {@code inputFieldPlaceholder}.
     */
    public static final int MAX_PLACEHOLDER_LENGTH = 64;

    /**
     * Creates a {@code ForceReply} instance without a placeholder.
     *
     * @return a {@code ForceReply} instance with {@code inputFieldPlaceholder = null}.
     */
    public static ForceReply empty() {
        return new ForceReply(true, null);
    }

    /**
     * Creates a {@code ForceReply} instance with a placeholder.
     *
     * @param inputFieldPlaceholder placeholder text to show in the client's input field;
     *                              must be non-null and within allowed length.
     * @return a {@code ForceReply} instance with the specified placeholder.
     */
    public static ForceReply withPlaceholder(final @NotNull String inputFieldPlaceholder) {
        return new ForceReply(true, inputFieldPlaceholder);
    }

}
