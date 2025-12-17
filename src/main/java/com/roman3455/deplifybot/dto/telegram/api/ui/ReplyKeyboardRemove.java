package com.roman3455.deplifybot.dto.telegram.api.ui;

import jakarta.validation.constraints.AssertTrue;

/**
 * {@inheritDoc}
 *
 * <p>Instructs the Telegram client to remove the current reply keyboard.</p>
 *
 * @param removeKeyboard required always {@code true}. Indicates that the keyboard should be removed.
 * @see <a href="https://core.telegram.org/bots/api#replykeyboardremove">Telegram API — ReplyKeyboardRemove</a>
 */
public record ReplyKeyboardRemove(

        @AssertTrue(message = "{ReplyKeyboardRemove.removeKeyboardIsTrue.AssertTrue}")
        boolean removeKeyboard

) implements ReplyMarkup {
}
