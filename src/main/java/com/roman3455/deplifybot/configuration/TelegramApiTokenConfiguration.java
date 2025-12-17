package com.roman3455.deplifybot.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Base64;

/**
 * Provides access to a cryptographically secure Telegram Bot API secret token used for webhook authentication.
 *
 * <p>The token is generated once during application startup and used to verify that incoming webhook requests
 * originate from Telegram via the {@code "X-Telegram-Bot-Api-Secret-Token"} header. The token is created using
 * random bytes, encoded in URL-safe Base64 format without padding.</p>
 */
@Component
public class TelegramApiTokenConfiguration {

    private static final Logger LOG = LoggerFactory.getLogger(TelegramApiTokenConfiguration.class);

    private final TelegramBotProperties botProperties;

    private String token;

    /**
     * Creates a new instance with a randomly generated Telegram API secret token.
     *
     * <p>The token is generated once per application startup and logged for verification
     * purposes (without exposing the actual value).</p>
     */
    public TelegramApiTokenConfiguration(final TelegramBotProperties botProperties) {
        this.botProperties = botProperties;
        generateToken();
    }

    /**
     * Returns the currently active Telegram Bot API secret token.
     *
     * @return the secret token string used for webhook validation.
     */
    public String getToken() {
        return token;
    }

    /**
     * Compares the provided candidate token against the stored secret token using a constant-time
     * comparison to prevent timing attacks.
     *
     * @param candidate the token received from the incoming request header
     *                  {@code "X-Telegram-Bot-Api-Secret-Token"}.
     * @return {@code true} if the tokens match; {@code false} otherwise.
     */
    public boolean matches(final String candidate) {
        if (candidate == null) {
            LOG.warn("Received null Telegram API token");
            return false;
        }
        byte[] candidateBytes = candidate.getBytes(StandardCharsets.UTF_8);
        byte[] tokenBytes = token.getBytes(StandardCharsets.UTF_8);
        return MessageDigest.isEqual(tokenBytes, candidateBytes);
    }

    private void generateToken() {
        byte[] randomBytes = new byte[botProperties.secretTokenBytesSize()];
        new SecureRandom().nextBytes(randomBytes);
        this.token = Base64.getUrlEncoder().withoutPadding().encodeToString(randomBytes);
        LOG.info("Telegram bot API token successfully generated");
    }

}
