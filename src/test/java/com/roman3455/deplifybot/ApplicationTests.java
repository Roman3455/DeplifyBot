package com.roman3455.deplifybot;

import com.roman3455.deplifybot.client.TelegramClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
class ApplicationTests {

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private TelegramClient telegramClient;

    @Test
    void contextLoads() {
        assertThat(applicationContext).isNotNull();
    }

    @Test
    void  contextLoadsTelegramClient() {
        assertThat(telegramClient).isNotNull();
    }

    @Test
    void contextLoadsJackson2ObjectMapperBuilderCustomizerBean() {
        var customizers = applicationContext.getBeansOfType(Jackson2ObjectMapperBuilder.class);
        assertThat(customizers).isNotEmpty();
    }

}
