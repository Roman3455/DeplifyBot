package com.roman3455.deplifybot.dto.telegram.api.ui;

import com.roman3455.deplifybot.configuration.JacksonConfiguration;
import com.roman3455.deplifybot.dto.telegram.api.ui.button.InlineKeyboardButton;
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

import java.util.List;

import static org.assertj.core.api.BDDAssertions.then;

@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@JsonTest
@Import(JacksonConfiguration.class)
@DisplayName("InlineKeyboardMarkup — JSON serialization & deserialization")
class InlineKeyboardMarkupJsonTest {

    @Autowired
    private JacksonTester<InlineKeyboardMarkup> json;

    private static final String SOURCE = "/fixture/telegram/ui/inline_keyboard_markup/inline_keyboard_markup_full.json";

    private InlineKeyboardMarkup fullPayload;

    @BeforeAll
    void setUp() {
        var urlButton = InlineKeyboardButton.ofUrl("Button", "https://ok");
        var callbackDataButton = InlineKeyboardButton.ofCallbackData("Button", "callback");
        fullPayload = InlineKeyboardMarkup.ofRows(List.of(urlButton), List.of(callbackDataButton));
    }

    @Test
    @DisplayName("Should serialize full payload object into expected JSON fixture")
    void shouldSerializeFullPayload() throws Exception {
        var serialized = json.write(fullPayload);
        then(serialized).isNotNull()
                .isEqualToJson(new ClassPathResource(SOURCE))
                .doesNotHaveJsonPath("$.inlineKeyboard");
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
