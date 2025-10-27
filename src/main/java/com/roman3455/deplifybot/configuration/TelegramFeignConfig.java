package com.roman3455.deplifybot.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.roman3455.deplifybot.exception.TelegramErrorDecoder;
import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Customizing Feign behavior when interacting with the Telegram API.
 * <p>Registers a custom {@link ErrorDecoder} that maps Telegram HTTP error responses to structured
 * {@link com.roman3455.deplifybot.exception.telegram.TelegramApiException} types via {@link TelegramErrorDecoder}.
 * This ensures consistent error handling across all Telegram Feign clients.
 */
@Configuration
public class TelegramFeignConfig {

    /**
     * Provides a {@link TelegramErrorDecoder} bean for Feign.
     *
     * @param objectMapper the shared {@link ObjectMapper} used to parse Telegram API error responses.
     * @return a configured {@link ErrorDecoder} instance.
     */
    @Bean
    ErrorDecoder telegramErrorDecoder(final ObjectMapper objectMapper) {
        return new TelegramErrorDecoder(objectMapper);
    }

}
