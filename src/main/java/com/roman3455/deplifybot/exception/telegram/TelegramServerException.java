package com.roman3455.deplifybot.exception.telegram;

/**
 * Thrown when the Telegram API responds with HTTP 500 (Internal Server Error)
 * or other server-side error codes.
 */
public class TelegramServerException extends TelegramApiException {

    public TelegramServerException(final String message) {
        super(message);
    }

}
