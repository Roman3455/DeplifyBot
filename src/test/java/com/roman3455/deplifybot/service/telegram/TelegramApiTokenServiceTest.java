package com.roman3455.deplifybot.service.telegram;

import com.roman3455.deplifybot.service.telegram.impl.TelegramApiTokenServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = {TelegramApiTokenServiceTest.class})
@Import(TelegramApiTokenServiceImpl.class)
@DisplayName("TelegramApiTokenService — Token format & behavior test")
class TelegramApiTokenServiceTest {

    @Autowired
    private TelegramApiTokenService service;

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
