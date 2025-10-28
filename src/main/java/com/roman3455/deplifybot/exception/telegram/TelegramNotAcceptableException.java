package com.roman3455.deplifybot.exception.telegram;

/**
 * Thrown when the Telegram API responds with HTTP 406 (Not Acceptable).
 */
public class TelegramNotAcceptableException extends TelegramApiException {

    public TelegramNotAcceptableException(final String message) {
        super(message);
    }

}
