package com.roman3455.deplifybot.service.telegram;

import com.roman3455.deplifybot.configuration.TelegramBotProperties;
import com.roman3455.deplifybot.service.telegram.impl.TelegramApiTokenServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("TelegramApiTokenService — Token format & behavior test")
class TelegramApiTokenServiceTest {

    private TelegramApiTokenService service;

    @BeforeEach
    void setUp() {
        final int bytesSize = 32;
        final int maxConnections = 40;
        TelegramBotProperties properties = new TelegramBotProperties(
                List.of("message"),
                new TelegramBotProperties.Connections(maxConnections),
                new TelegramBotProperties.Webhook(
                        "https://example.com/webhook",
                        "/telegram/webhook"
                ),
                new TelegramBotProperties.Token(bytesSize)
        );
        service = new TelegramApiTokenServiceImpl(properties);
    }

    @Test
    @DisplayName("Should return URL-safe Base64 token without '=' and remain stable through bean lifecycle")
    void shouldReturnUrlSafeTokenFormatAndStability() {
        final int expectedTokenLength = 43;
        String t1 = service.getToken();
        String t2 = service.getToken();
        assertThat(t1).isNotBlank()
                .isEqualTo(t2)
                .matches("^[A-Za-z0-9_-]+$")
                .doesNotContain("=")
                .hasSize(expectedTokenLength);
    }

    @Test
    @DisplayName("Should return true for exact match and false for null or different values")
    void shouldMatchesBehaviour() {
        String token = service.getToken();
        assertThat(service.matches(token)).isTrue();
        assertThat(service.matches(token + "x")).isFalse();
        assertThat(service.matches(null)).isFalse();
        assertThat(service.matches("")).isFalse();
    }

}
