package com.roman3455.deplifybot.exception.telegram;

/**
 * Thrown when the Telegram API responds with HTTP 403 (Forbidden).
 */
public class TelegramForbiddenException extends TelegramApiException {

    public TelegramForbiddenException(final String message) {
        super(message);
    }

}
