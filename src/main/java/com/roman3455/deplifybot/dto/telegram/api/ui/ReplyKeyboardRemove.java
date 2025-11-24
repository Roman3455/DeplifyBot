package com.roman3455.deplifybot.dto.telegram.api.ui;

import jakarta.validation.constraints.AssertTrue;

/**
 * DTO representing the {@code ReplyKeyboardRemove} reply markup for Telegram bot interface.
 *
 * <p>When {@code ReplyKeyboardRemove} is used, it instructs the Telegram client to remove the custom reply
 * keyboard from the user's interface after the message is sent. This action is commonly used after a bot
 * prompts a user for a response, ensuring the keyboard does not persist unnecessarily.</p>
 *
 * @param removeKeyboard  required always {@code true}. Indicates that the keyboard should be removed.
 * @see <a href="https://core.telegram.org/bots/api#replykeyboardremove">Telegram API — ReplyKeyboardRemove</a>
 */
public record ReplyKeyboardRemove(

        @AssertTrue(message = "{ReplyKeyboardRemove.removeKeyboardIsTrue.AssertTrue}")
        boolean removeKeyboard

) implements ReplyMarkup {
}
