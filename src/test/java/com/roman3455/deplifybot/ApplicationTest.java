package com.roman3455.deplifybot;

import com.roman3455.deplifybot.client.TelegramClient;
import com.roman3455.deplifybot.configuration.TelegramBotProperties;
import com.roman3455.deplifybot.configuration.TelegramFeignConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
@DisplayName("Application context bootstrapping")
class ApplicationTest {

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private TelegramClient telegramClient;

    @Autowired
    private TelegramFeignConfig telegramFeignConfig;

    @Autowired
    private TelegramBotProperties telegramBotProperties;

    @Test
    @DisplayName("Should load application context")
    void shouldLoadApplicationContext() {
        assertThat(applicationContext).isNotNull();
    }

    @Test
    @DisplayName("Should load TelegramClient bean")
    void shouldLoadTelegramClientBean() {
        assertThat(telegramClient).isNotNull();
    }

    @Test
    @DisplayName("Should load TelegramFeignConfig bean")
    void shouldLoadTelegramFeignConfigBean() {
        assertThat(telegramFeignConfig).isNotNull();
    }

    @Test
    @DisplayName("Should load Jackson2ObjectMapperBuilder customizer bean")
    void shouldLoadJackson2ObjectMapperBuilderCustomizerBean() {
        var customizers = applicationContext.getBeansOfType(Jackson2ObjectMapperBuilder.class);
        assertThat(customizers).isNotEmpty();
    }

    @Test
    @DisplayName("Should load TelegramBotProperties bean")
    void shouldLoadTelegramBotPropertiesBean() {
        assertThat(telegramBotProperties).isNotNull();
    }

}
