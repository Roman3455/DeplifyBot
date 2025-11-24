package com.roman3455.deplifybot.dto.telegram.api.ui.button;

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
@DisplayName("InlineKeyboardButton — JSON serialization & deserialization")
class InlineKeyboardButtonJsonTest {

    @Autowired
    private JacksonTester<InlineKeyboardButton> json;

    private static final String SOURCE = "/fixture/telegram/ui/inline_keyboard_button/inline_keyboard_button_";
    private static final String CALLBACK_DATA_ONLY_JSON = SOURCE + "callback_data_only.json";
    private static final String COPY_TEXT_ONLY_JSON = SOURCE + "copy_text_only.json";
    private static final String URL_ONLY_JSON = SOURCE + "url_only.json";

    private InlineKeyboardButton callbackDataPayload;
    private InlineKeyboardButton copyTextPayload;
    private InlineKeyboardButton urlPayload;

    @BeforeAll
    void setUp() {
        var buttonLabel = "Button";
        callbackDataPayload = new InlineKeyboardButton(buttonLabel, null, "callback", null);
        var copyTextButton = new CopyTextButton("Text to copy");
        copyTextPayload = new InlineKeyboardButton(buttonLabel, null, null, copyTextButton);
        urlPayload = new InlineKeyboardButton(buttonLabel, "https://ok", null, null);
    }

    @Test
    @DisplayName("Should serialize callbackData only payload object into expected JSON fixture")
    void shouldSerializeCallbackDataOnlyPayload() throws Exception {
        var serialized = json.write(callbackDataPayload);
        then(serialized).isNotNull()
                .isEqualToJson(new ClassPathResource(CALLBACK_DATA_ONLY_JSON))
                .doesNotHaveJsonPath("$.callbackData");
    }

    @Test
    @DisplayName("Should deserialize callbackData only payload JSON fixture into expected object")
    void shouldDeserializeCallbackDataOnlyPayload() throws Exception {
        var deserialized = json.readObject(new ClassPathResource(CALLBACK_DATA_ONLY_JSON));
        then(deserialized).isNotNull()
                .isEqualTo(callbackDataPayload);
    }

    @Test
    @DisplayName("Should round-trip message only payload object")
    void shouldRoundTripCallbackDataOnlyPayload() throws Exception {
        var serialized = json.write(callbackDataPayload);
        var deserialized = json.parseObject(serialized.getJson());
        then(deserialized).isNotNull()
                .isEqualTo(callbackDataPayload);
    }

    @Test
    @DisplayName("Should serialize copyText only payload object into expected JSON fixture")
    void shouldSerializeCopyTextOnlyPayload() throws Exception {
        var serialized = json.write(copyTextPayload);
        then(serialized).isNotNull()
                .isEqualToJson(new ClassPathResource(COPY_TEXT_ONLY_JSON))
                .doesNotHaveJsonPath("$.copyText");
    }

    @Test
    @DisplayName("Should deserialize copyText only payload JSON fixture into expected object")
    void shouldDeserializeCopyTextOnlyPayload() throws Exception {
        var deserialized = json.readObject(new ClassPathResource(COPY_TEXT_ONLY_JSON));
        then(deserialized).isNotNull()
                .isEqualTo(copyTextPayload);
    }

    @Test
    @DisplayName("Should round-trip copyText only payload object")
    void shouldRoundTripCopyTextOnlyPayload() throws Exception {
        var serialized = json.write(copyTextPayload);
        var deserialized = json.parseObject(serialized.getJson());
        then(deserialized).isNotNull()
                .isEqualTo(copyTextPayload);
    }

    @Test
    @DisplayName("Should serialize url only payload object into expected JSON fixture")
    void shouldSerializeUrlOnlyPayload() throws Exception {
        var serialized = json.write(urlPayload);
        then(serialized).isNotNull()
                .isEqualToJson(new ClassPathResource(URL_ONLY_JSON));
    }

    @Test
    @DisplayName("Should deserialize url only payload JSON fixture into expected object")
    void shouldDeserializeUrlOnlyPayload() throws Exception {
        var deserialized = json.readObject(new ClassPathResource(URL_ONLY_JSON));
        then(deserialized).isNotNull()
                .isEqualTo(urlPayload);
    }

    @Test
    @DisplayName("Should round-trip url only payload object")
    void shouldRoundTripUrlOnlyPayload() throws Exception {
        var serialized = json.write(urlPayload);
        var deserialized = json.parseObject(serialized.getJson());
        then(deserialized).isNotNull()
                .isEqualTo(urlPayload);
    }

}
