package com.roman3455.deplifybot;

import com.roman3455.deplifybot.client.TelegramClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

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

}
