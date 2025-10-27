package com.roman3455.deplifybot.exception.telegram;

/**
 * Thrown when the Telegram API responds with HTTP 429 (Too Many Requests).
 *
 * <p>Contains a {@code retryAfter} value indicating how long to wait before retrying.</p>
 */
public class TelegramTooManyRequestsException extends TelegramApiException {

    private final long retryAfter;

    public TelegramTooManyRequestsException(final String message, final long retryAfter) {
        super(message);
        this.retryAfter = retryAfter;
    }

    /**
     * @return number of seconds to wait before retrying the request
     */
    public long getRetryAfter() {
        return retryAfter;
    }

}
