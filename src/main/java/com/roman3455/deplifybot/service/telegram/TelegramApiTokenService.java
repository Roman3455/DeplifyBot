package com.roman3455.deplifybot.service.telegram;

/**
 * Provides access to the Telegram Bot API secret token used for webhook authentication.
 *
 * <p>The token is generated once during application startup and used to verify that
 * incoming webhook requests originate from Telegram.</p>
 */
public interface TelegramApiTokenService {

    /**
     * Returns the currently active Telegram Bot API secret token.
     *
     * @return the secret token string used for webhook validation.
     */
    String getToken();

    /**
     * Compares the provided candidate token against the stored secret token using a constant-time
     * comparison to prevent timing attacks.
     *
     * @param candidate the token received from the incoming request header
     *                  {@code "X-Telegram-Bot-Api-Secret-Token"}.
     * @return {@code true} if the tokens match; {@code false} otherwise.
     */
    boolean matches(String candidate);

}
