package com.roman3455.deplifybot.exception.telegram;

/**
 * Base class for all Telegram API related exceptions.
 *
 * <p>Thrown when the Telegram Bot API returns an error response.</p>
 */
public abstract class TelegramApiException extends RuntimeException {

    protected TelegramApiException(final String message) {
        super(message);
    }

}
