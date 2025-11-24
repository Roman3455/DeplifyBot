package com.roman3455.deplifybot.dto.telegram.api.ui.button;

import jakarta.annotation.Nullable;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

/**
 * DTO representing a button on a custom keyboard for a Telegram bot interface.
 *
 * <p>The {@code KeyboardButton} class defines a button that can be included in a custom keyboard. Each button has
 * a text label that will be displayed on the button itself. When the user presses the button, the text is sent as
 * a message. Optionally, the button can also request a chat from the user via {@link KeyboardButtonRequestChat}.
 * If a chat request is not needed, the {@code requestChat} field can be left as {@code null}.</p>
 *
 * @param text        required. The text displayed on the button. When the button is pressed, this text is sent as
 *                    a message.
 * @param requestChat optional. {@link KeyboardButtonRequestChat} representing a request to the user for a chat.
 * @see <a href="https://core.telegram.org/bots/api#keyboardbutton">Telegram API — KeyboardButton</a>
 */
public record KeyboardButton(

        @NotNull
        String text,

        @Nullable
        @Valid KeyboardButtonRequestChat requestChat

) {

    /**
     * Factory method to create a {@code KeyboardButton} with just text and no chat request.
     *
     * @param text the text to be displayed on the button (must not be null). This text will be sent as a message
     *             when the button is pressed.
     * @return a {@code KeyboardButton} with the specified text and no chat request.
     */
    public static KeyboardButton ofText(@NotNull final String text) {
        return new KeyboardButton(text, null);
    }

}
