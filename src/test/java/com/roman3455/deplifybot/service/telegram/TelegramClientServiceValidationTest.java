package com.roman3455.deplifybot.service.telegram;

import com.roman3455.deplifybot.client.TelegramClient;
import com.roman3455.deplifybot.dto.telegram.api.request.BotDescriptionRequest;
import com.roman3455.deplifybot.dto.telegram.api.request.BotShortDescriptionRequest;
import com.roman3455.deplifybot.dto.telegram.api.request.SetMyCommandsRequest;
import com.roman3455.deplifybot.dto.telegram.api.request.SetWebhookRequest;
import com.roman3455.deplifybot.service.telegram.impl.TelegramClientServiceImpl;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verifyNoInteractions;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TelegramClientServiceValidationTest.Config.class)
@DisplayName("TelegramClientService — method validation")
class TelegramClientServiceValidationTest {

    @Configuration
    static class Config {

        @Bean
        MethodValidationPostProcessor methodValidationPostProcessor() {
            return new MethodValidationPostProcessor();
        }

        @Bean
        TelegramClient telegramClient() {
            return org.mockito.Mockito.mock(TelegramClient.class);
        }

        @Bean
        TelegramClientService service(final TelegramClient client) {
            return new TelegramClientServiceImpl(client);
        }

    }

    @Autowired
    private TelegramClientService service;

    @Autowired
    private TelegramClient client;

    @Test
    @DisplayName("setMyDescription(null) -> ConstraintViolationException; client is not called")
    void setMyDescriptionNullThrowsViolation() {
        assertThatThrownBy(() -> service.setMyDescription(null))
                .isInstanceOf(ConstraintViolationException.class);
        verifyNoInteractions(client);
    }

    @Test
    @DisplayName("setMyShortDescription(null) -> ConstraintViolationException; client is not called")
    void setMyShortDescriptionNullThrowsViolation() {
        assertThatThrownBy(() -> service.setMyShortDescription(null))
                .isInstanceOf(ConstraintViolationException.class);
        verifyNoInteractions(client);
    }

    @Test
    @DisplayName("setMyCommands(null) -> ConstraintViolationException; client is not called")
    void setMyCommandsNullThrowsViolation() {
        assertThatThrownBy(() -> service.setMyCommands(null))
                .isInstanceOf(ConstraintViolationException.class);
        verifyNoInteractions(client);
    }

    @Test
    @DisplayName("setWebhook(null) -> ConstraintViolationException; client is not called")
    void setWebhookNullThrowsViolation() {
        assertThatThrownBy(() -> service.setWebhook(null))
                .isInstanceOf(ConstraintViolationException.class);
        verifyNoInteractions(client);
    }

    @Test
    @DisplayName("setMyDescription: ConstraintViolationException (violates @ISO6391); client is not called")
    void setMyDescriptionInvalidLanguageCodeThrowsViolation() {
        var invalid = new BotDescriptionRequest("desc", "eng");
        assertThatThrownBy(() -> service.setMyDescription(invalid))
                .isInstanceOf(ConstraintViolationException.class)
                .hasMessageContaining("'languageCode' length must be exactly 2 characters.");
    }

    @Test
    @DisplayName("setMyShortDescription: ConstraintViolationException (violates @AssertTrue); client is not called")
    void setMyShortDescriptionNullFieldsThrowsViolation() {
        var invalid = new BotShortDescriptionRequest(null, null);
        assertThatThrownBy(() -> service.setMyShortDescription(invalid))
                .isInstanceOf(ConstraintViolationException.class)
                .hasMessageContaining("'shortDescription' or 'languageCode' must be provided.");
    }

    @Test
    @DisplayName("setMyCommands: ConstraintViolationException (violates @Size); client is not called")
    void setMyCommandsEmptyListThrowsViolation() {
        var invalid = new SetMyCommandsRequest(List.of(), null, null);
        assertThatThrownBy(() -> service.setMyCommands(invalid))
                .isInstanceOf(ConstraintViolationException.class)
                .hasMessageContaining("Field 'commands' is required and cannot be empty.");
        verifyNoInteractions(client);
    }

    @Test
    @DisplayName("setWebhook: ConstraintViolationException (violates @Pattern); client is not called")
    void setWebhookMismatchUlrThrowsViolation() {
        var invalid = new SetWebhookRequest("http://ex.ru", null, null, null, null);
        assertThatThrownBy(() -> service.setWebhook(invalid))
                .isInstanceOf(ConstraintViolationException.class)
                .hasMessageContaining("must start with 'https://'");
    }
}
