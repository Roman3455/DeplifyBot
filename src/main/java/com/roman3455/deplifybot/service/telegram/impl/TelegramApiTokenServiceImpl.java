package com.roman3455.deplifybot.service.telegram.impl;

import com.roman3455.deplifybot.configuration.TelegramBotProperties;
import com.roman3455.deplifybot.service.telegram.TelegramApiTokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Base64;

/**
 * Default implementation of {@link TelegramApiTokenService} that generates a cryptographically secure
 * Telegram Bot API secret token at application startup.
 *
 * <p>The token is created using random bytes, encoded in URL-safe Base64 format without padding. It is
 * intended for use in webhook authentication via the {@code "X-Telegram-Bot-Api-Secret-Token"} header.</p>
 */
@Service
public class TelegramApiTokenServiceImpl implements TelegramApiTokenService {

    private static final Logger LOG = LoggerFactory.getLogger(TelegramApiTokenServiceImpl.class);

    private final TelegramBotProperties botProperties;

    private String token;

    /**
     * Creates a new instance with a randomly generated Telegram API secret token.
     *
     * <p>The token is generated once per application startup and logged for verification
     * purposes (without exposing the actual value).</p>
     */
    public TelegramApiTokenServiceImpl(final TelegramBotProperties botProperties) {
        this.botProperties = botProperties;
        generateToken();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getToken() {
        return token;
    }

    /**
     * {@inheritDoc}
     */
    @Override
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
        int bytesSize = botProperties.token().bytesSize();
        byte[] randomBytes = new byte[bytesSize];
        new SecureRandom().nextBytes(randomBytes);
        this.token = Base64.getUrlEncoder().withoutPadding().encodeToString(randomBytes);
        LOG.info("Telegram bot API token successfully generated");
    }

}
