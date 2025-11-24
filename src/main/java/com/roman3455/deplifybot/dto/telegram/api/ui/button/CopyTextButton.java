package com.roman3455.deplifybot.dto.telegram.api.ui.button;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * DTO represents a button with text to be copied in a Telegram bot interface.
 *
 * @param text required. The text to be copied to the clipboard. It must be a non-null string with a maximum length
 *             of {@value #MAX_TEXT_LENGTH} characters.
 * @see <a href="https://core.telegram.org/bots/api#copytextbutton">Telegram API — CopyTextButton</a>
 */
public record CopyTextButton(

        @NotNull
        @Size(max = MAX_TEXT_LENGTH, message = "{Size.max.message}")
        String text

) {

    /**
     * The max allowed length of the {@code text} field.
     */
    public static final int MAX_TEXT_LENGTH = 256;

}
