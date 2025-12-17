package com.roman3455.deplifybot.dto.telegram.api.ui;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * {@inheritDoc}
 *
 * <p>Forces the user to reply to a specific message.</p>
 *
 * @param forceReply            required. Must always be {@code true}.
 * @param inputFieldPlaceholder optional. Placeholder for the input field.
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
