package com.roman3455.deplifybot.configuration;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Base64;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("TelegramApiTokenConfiguration — token format & behavior test")
class TelegramApiTokenConfigurationTest {

    private static final int TOKEN_BYTES_LENGTH = 32;

    private final TelegramBotProperties botProperties = mock(TelegramBotProperties.class);

    private TelegramApiTokenConfiguration tokenConfiguration;

    @BeforeAll
    void setUp() {
        when(botProperties.secretTokenBytesSize()).thenReturn(TOKEN_BYTES_LENGTH);
        tokenConfiguration = new TelegramApiTokenConfiguration(botProperties);
    }

    @Test
    @DisplayName("Should generate not blank token and matches pattern '^[A-Za-z0-9_-]+$'")
    void shouldGenerateNotNullTokenWithExpectedLength() {
        String token = tokenConfiguration.getToken();
        assertThat(token).isNotBlank()
                .matches("^[A-Za-z0-9_-]+$");
    }

    @Test
    @DisplayName("Should generate token with expected UTF-8 bytes length")
    void shouldGenerateTokenWithExpectedLength() {
        String token = tokenConfiguration.getToken();
        byte[] tokenBytes = Base64.getUrlDecoder().decode(token);
        assertThat(tokenBytes.length).isEqualTo(TOKEN_BYTES_LENGTH);
    }

    @Test
    @DisplayName("Should return true when generated token matches itself")
    void generatedTokenShouldMatchesItSelf() {
        String token1 = tokenConfiguration.getToken();
        String token2 = tokenConfiguration.getToken();
        assertThat(token1).isEqualTo(token2);
        assertTrue(tokenConfiguration.matches(token2));
    }

    @Test
    @DisplayName("Should return false when generated token not match invalid token")
    void generatedTokenShouldNotMatchInvalidToken() {
        String invalidToken = tokenConfiguration.getToken() + "x";
        assertFalse(tokenConfiguration.matches(invalidToken));
        assertFalse(tokenConfiguration.matches(null));
        assertFalse(tokenConfiguration.matches(""));
    }

    @Test
    @DisplayName("Should generate token called once")
    void shouldGenerateTokenOnlyOnce() {
        verify(botProperties).secretTokenBytesSize();
    }

}
