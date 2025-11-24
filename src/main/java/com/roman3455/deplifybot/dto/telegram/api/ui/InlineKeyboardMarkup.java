package com.roman3455.deplifybot.dto.telegram.api.ui;

import com.roman3455.deplifybot.dto.telegram.api.ui.button.InlineKeyboardButton;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;

import java.util.Arrays;
import java.util.List;

/**
 * DTO representing an inline keyboard markup for a Telegram bot interface.
 *
 * <p>The {@code InlineKeyboardMarkup} class defines an inline keyboard that can be displayed in a Telegram bot message.
 * The keyboard consists of a list of rows, where each row contains a list of {@link InlineKeyboardButton} objects.
 * When a user clicks a button, a callback or action is triggered, based on the configuration of the buttons.</p>
 *
 * @param inlineKeyboard required. A list of rows, where each row is a list of inline keyboard buttons.
 * @see <a href="https://core.telegram.org/bots/api#inlinekeyboardmarkup">Telegram API — InlineKeyboardMarkup</a>
 */
public record InlineKeyboardMarkup(

        @NotEmpty
        List<@NotEmpty List<@Valid InlineKeyboardButton>> inlineKeyboard

) implements ReplyMarkup {

    /**
     * Factory method to create an {@code InlineKeyboardMarkup} instance with multiple rows of inline keyboard buttons.
     *
     * <p>This method allows you to create an inline keyboard with any number of rows, each containing a list of
     * {@link InlineKeyboardButton} objects.</p>
     *
     * @param inlineKeyboards varargs parameter representing rows of inline keyboard buttons.
     * @return a new {@code InlineKeyboardMarkup} instance with the provided rows of buttons.
     */
    @SafeVarargs
    public static InlineKeyboardMarkup ofRows(@NotEmpty final List<@Valid InlineKeyboardButton>... inlineKeyboards) {
        List<List<InlineKeyboardButton>> rows = Arrays.stream(inlineKeyboards)
                .map(List::copyOf)
                .toList();
        return new InlineKeyboardMarkup(rows);
    }

}
