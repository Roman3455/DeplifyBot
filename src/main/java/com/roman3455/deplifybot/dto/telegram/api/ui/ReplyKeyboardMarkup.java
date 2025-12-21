package com.roman3455.deplifybot.dto.telegram.api.ui;

import com.roman3455.deplifybot.dto.telegram.api.ui.button.KeyboardButton;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.util.Arrays;
import java.util.List;

/**
 * {@inheritDoc}
 *
 * <p>Defines a custom reply keyboard that replaces the system keyboard.</p>
 *
 * @param keyboard              required. Array of button rows, each represented by an Array of {@link KeyboardButton}.
 * @param isPersistent          optional. The keyboard remains visible after use. Defaults to {@code false}, in which
 *                              case the custom keyboard can be hidden and opened with a keyboard icon.
 * @param resizeKeyboard        optional. Resize the keyboard for optimal fit. Defaults to {@code false}, in which case
 *                              the custom keyboard is always of the same height as the app's standard keyboard.
 * @param oneTimeKeyboard       optional. Hides the keyboard after first use.
 * @param inputFieldPlaceholder optional. The placeholder to be shown in the input field when the keyboard is active.
 * @see <a href="https://core.telegram.org/bots/api#replykeyboardmarkup">Telegram API — ReplyKeyboardMarkup</a>
 */
public record ReplyKeyboardMarkup(

        @NotEmpty
        List<@NotEmpty List<@Valid KeyboardButton>> keyboard,

        @Nullable
        Boolean isPersistent,

        @Nullable
        Boolean resizeKeyboard,

        @Nullable
        Boolean oneTimeKeyboard,

        @Nullable
        @Size(min = 1, max = MAX_PLACEHOLDER_LENGTH)
        String inputFieldPlaceholder

) implements ReplyMarkup {

    /**
     * Maximum allowed length for {@code inputFieldPlaceholder}.
     */
    public static final int MAX_PLACEHOLDER_LENGTH = 64;

    /**
     * Creates a persistent and resized keyboard with the given rows.
     *
     * <p>Equivalent to:</p>
     * <pre>
     * isPersistent      = true
     * resizeKeyboard     = true
     * oneTimeKeyboard    = false
     * inputPlaceholder   = null
     * </pre>
     *
     * @param keyboards rows of {@link KeyboardButton}.
     * @return configured {@link ReplyKeyboardMarkup}
     */
    @SafeVarargs
    public static ReplyKeyboardMarkup ofResizedPersistentKeyboard(@Nonnull final List<KeyboardButton>... keyboards) {
        return ofRows(
                true,
                true,
                false,
                null,
                keyboards
        );
    }

    /**
     * Creates a {@link ReplyKeyboardMarkup} with explicit configuration flags and rows.
     *
     * @return new {@link ReplyKeyboardMarkup}
     */
    @SafeVarargs
    public static ReplyKeyboardMarkup ofRows(
            final Boolean isPersistent,
            final Boolean resizeKeyboard,
            final Boolean oneTimeKeyboard,
            final String inputFieldPlaceholder,
            @Nonnull final List<KeyboardButton>... keyboards
    ) {
        List<List<KeyboardButton>> rows = Arrays.stream(keyboards)
                .map(List::copyOf)
                .toList();
        return new ReplyKeyboardMarkup(
                rows,
                isPersistent,
                resizeKeyboard,
                oneTimeKeyboard,
                inputFieldPlaceholder
        );
    }

}
