package com.roman3455.deplifybot.exception.telegram;

/**
 * Thrown when the Telegram API responds with HTTP 401 (Unauthorized).
 */
public class TelegramUnauthorizedException extends TelegramApiException {

    public TelegramUnauthorizedException(final String message) {
        super(message);
    }

}
