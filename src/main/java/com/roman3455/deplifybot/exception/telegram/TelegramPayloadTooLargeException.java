package com.roman3455.deplifybot.exception.telegram;

/**
 * Thrown when the Telegram API responds with HTTP 413 (Payload Too Large).
 */
public class TelegramPayloadTooLargeException extends TelegramApiException {

    public TelegramPayloadTooLargeException(final String message) {
        super(message);
    }

}
