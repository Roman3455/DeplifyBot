package com.roman3455.deplifybot.exception.business;

/**
 * Thrown when the Telegram bot initialization process fails.
 *
 * <p>This exception indicates that a configuration or setup step during application startup (e.g., setting
 * descriptions, commands, or webhook) could not be completed successfully.</p>
 *
 * <p>Typical causes include:</p>
 * <ul>
 *   <li>Transport-level issues (wrapped {@link com.roman3455.deplifybot.exception.telegram.TelegramApiException}
 *       or {@link feign.FeignException}).</li>
 *   <li>Domain-level failures (Telegram API returned {@code ok=false} or {@code result=false}).</li>
 * </ul>
 *
 * <p>This is an unchecked exception because initialization errors are considered fatal
 * and should prevent the application from starting successfully.</p>
 */
public class BotInitializationException extends RuntimeException {

    /**
     * Creates a new {@code BotInitializationException} with the specified detail message.
     *
     * @param message the detail message describing the cause of failure.
     */
    public BotInitializationException(final String message) {
        super(message);
    }

    /**
     * Creates a new {@code BotInitializationException} with the specified detail message and cause.
     *
     * @param message the detail message describing the cause of failure.
     * @param cause   the underlying exception that triggered the initialization failure.
     */
    public BotInitializationException(final String message, final Throwable cause) {
        super(message, cause);
    }

}
