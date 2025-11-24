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
@DisplayName("CopyTextButton — JSON serialization & deserialization")
class CopyTextButtonJsonTest {

    @Autowired
    private JacksonTester<CopyTextButton> json;

    private static final String SOURCE = "/fixture/telegram/ui/copy_text_button/";
    private static final String FULL_JSON = SOURCE + "copy_text_button_full.json";

    private CopyTextButton fullPayload;

    @BeforeAll
    void setUp() {
        fullPayload = new CopyTextButton("Text to be copied");
    }

    @Test
    @DisplayName("Should serialize full payload object into expected JSON fixture")
    void shouldSerializeFullPayload() throws Exception {
        var serialized = json.write(fullPayload);
        then(serialized).isNotNull()
                .isEqualToJson(new ClassPathResource(FULL_JSON));
    }

    @Test
    @DisplayName("Should deserialize full payload JSON fixture into expected object")
    void shouldDeserializeFullPayload() throws Exception {
        var deserialized = json.readObject(new ClassPathResource(FULL_JSON));
        then(deserialized).isNotNull()
                .isEqualTo(fullPayload);
    }

    @Test
    @DisplayName("Should round-trip full payload JSON fixture")
    void shouldRoundTripFullPayload() throws Exception {
        var deserialized = json.readObject(new ClassPathResource(FULL_JSON));
        var serialized = json.write(deserialized);
        then(serialized).isNotNull()
                .isEqualToJson(new ClassPathResource(FULL_JSON));
    }

}
