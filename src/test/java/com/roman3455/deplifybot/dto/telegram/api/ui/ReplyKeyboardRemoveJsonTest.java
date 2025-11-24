package com.roman3455.deplifybot.dto.telegram.api.ui;

import com.roman3455.deplifybot.configuration.JacksonConfiguration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.BDDAssertions.then;

@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@JsonTest
@Import(JacksonConfiguration.class)
@DisplayName("ReplyKeyboardRemove — JSON serialization & deserialization")
class ReplyKeyboardRemoveJsonTest {

    @Autowired
    private JacksonTester<ReplyKeyboardRemove> json;

    private static final String SOURCE = "/fixture/telegram/ui/reply_keyboard_remove/reply_keyboard_remove_full.json";

    private ReplyKeyboardRemove fullPayload;

    @BeforeAll
    void setUp() {
        fullPayload = new ReplyKeyboardRemove(true);
    }

    @Test
    @DisplayName("Should serialize full payload object into expected JSON fixture")
    void shouldSerializeFullPayload() throws Exception {
        var serialized = json.write(fullPayload);
        then(serialized).isNotNull()
                .isEqualToJson(new ClassPathResource(SOURCE))
                .doesNotHaveJsonPath("$.removeKeyboard");
    }

    @Test
    @DisplayName("Should deserialize full payload JSON fixture into expected object")
    void shouldDeserializeFullPayload() throws Exception {
        var deserialized = json.readObject(new ClassPathResource(SOURCE));
        then(deserialized).isNotNull()
                .isEqualTo(fullPayload);
    }

    @Test
    @DisplayName("Should round-trip full payload JSON fixture")
    void shouldRoundTripFullPayload() throws Exception {
        var deserialized = json.readObject(new ClassPathResource(SOURCE));
        var serialized = json.write(deserialized);
        then(serialized).isNotNull()
                .isEqualToJson(new ClassPathResource(SOURCE));
    }

}
