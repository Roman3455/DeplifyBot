package com.roman3455.deplifybot.exception.telegram;

/**
 * Thrown when the Telegram API responds with HTTP 400 (Bad Request).
 */
public class TelegramBadRequestException extends TelegramApiException {

    public TelegramBadRequestException(final String message) {
        super(message);
    }

}
