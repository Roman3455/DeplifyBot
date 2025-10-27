package com.roman3455.deplifybot.exception.telegram;

/**
 * Thrown when the Telegram API responds with HTTP 404 (Not Found).
 */
public class TelegramNotFoundException extends TelegramApiException {

    public TelegramNotFoundException(final String message) {
        super(message);
    }

}
