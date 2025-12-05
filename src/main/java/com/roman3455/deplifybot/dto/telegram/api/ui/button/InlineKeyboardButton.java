package com.roman3455.deplifybot.dto.telegram.api.ui.button;

import com.roman3455.deplifybot.util.validator.bytes_length.BytesLength;
import jakarta.annotation.Nullable;
import jakarta.validation.Valid;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * DTO representing a button in an inline keyboard for the Telegram bot interface.
 *
 * <p>The {@code InlineKeyboardButton} allows the creation of a button with various actions,
 * including opening a URL, sending a callback data, or copying text to the clipboard. Only one of these
 * actions can be provided at a time (i.e., either {@code url}, {@code callbackData}, or {@code copyText}).</p>
 *
 * @param text         required. The text of the button to be displayed in the Telegram interface.
 * @param url          optional. The URL that the button opens when clicked (must be a valid HTTP or Telegram URL).
 * @param callbackData optional. The callback data sent when the button is pressed.
 * @param copyText     optional. The {@link CopyTextButton} representing the action of copying text to the clipboard.
 * @see <a href="https://core.telegram.org/bots/api#inlinekeyboardbutton">Telegram API — InlineKeyboardButton</a>
 */
public record InlineKeyboardButton(

        @NotBlank
        String text,

        @Nullable
        @Pattern(regexp = "^(https?|tg)://.+", message = "{InlineKeyboardButton.url.Pattern.message}")
        String url,

        @Nullable
        @BytesLength(min = 1, max = MAX_CALLBACK_DATA_LENGTH)
        String callbackData,

        @Nullable
        @Valid
        CopyTextButton copyText

) {

    /**
     * Maximum allowed length of the {@code callbackData} field.
     */
    public static final int MAX_CALLBACK_DATA_LENGTH = 64;

    /**
     * Cross-field constraint: Ensures that at least one optional field ({@code url}, {@code callbackData},
     * {@code copyText}) is provided, but not more than one.
     *
     * <p>Violation will be reported on the synthetic property named after this method.</p>
     *
     * @return {@code true} if exactly one of the optional fields is non-null, otherwise {@code false}.
     */
    @SuppressWarnings("unused")
    @AssertTrue(message = "{InlineKeyboardButton.isAnyProvided.AssertTrue}")
    private boolean isAnyProvided() {
        List<Object> optionalFields = Arrays.asList(url, callbackData, copyText);
        long filledOptionalFields = optionalFields.stream().filter(Objects::nonNull).count();
        return filledOptionalFields == 1;
    }

    /**
     * Factory method to create an {@code InlineKeyboardButton} with a URL.
     *
     * @param text required. The text to be displayed on the button.
     * @param url  required. The URL to open when the button is clicked (must be a valid HTTP or Telegram URL).
     * @return an {@code InlineKeyboardButton} with the specified {@code url}.
     */
    public static InlineKeyboardButton ofUrl(@NotNull final String text, @NotNull final String url) {
        return new InlineKeyboardButton(text, url, null, null);
    }

    /**
     * Factory method to create an {@code InlineKeyboardButton} with callback data.
     *
     * @param text         required. The text to be displayed on the button.
     * @param callbackData required. The callback data to be sent when the button is pressed.
     * @return an {@code InlineKeyboardButton} with the specified {@code callbackData}.
     */
    public static InlineKeyboardButton ofCallbackData(@NotNull final String text, @NotNull final String callbackData) {
        return new InlineKeyboardButton(text, null, callbackData, null);
    }

    /**
     * Factory method to create an {@code InlineKeyboardButton} that copies text to the clipboard.
     *
     * @param text     required. The text to be displayed on the button.
     * @param copyText required. The text to be copied to the clipboard when the button is clicked.
     * @return an {@code InlineKeyboardButton} with the specified {@code copyText}.
     */
    public static InlineKeyboardButton ofCopyText(@NotNull final String text, @NotNull final String copyText) {
        return new InlineKeyboardButton(text, null, null, new CopyTextButton(copyText));
    }

}
