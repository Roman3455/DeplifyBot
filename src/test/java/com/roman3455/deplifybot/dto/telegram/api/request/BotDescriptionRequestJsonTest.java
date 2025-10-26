package com.roman3455.deplifybot.dto.telegram.api.request;

import com.roman3455.deplifybot.configuration.JacksonConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.ClassPathResource;

import static org.assertj.core.api.BDDAssertions.then;

@JsonTest
@Import(JacksonConfiguration.class)
@DisplayName("BotDescriptionRequest — JSON serialization & deserialization")
class BotDescriptionRequestJsonTest {

    @Autowired
    private JacksonTester<BotDescriptionRequest> json;

    private static final String SOURCE = "/fixture/telegram/request/bot_description_request/";
    private static final String FULL_JSON = SOURCE + "bot_description_request_full.json";
    private static final String LANGUAGE_ONLY_JSON = SOURCE + "bot_description_request_language_only.json";
    private static final String DESCRIPTION_ONLY_JSON = SOURCE + "bot_description_request_description_only.json";

    private BotDescriptionRequest fullPayload;
    private BotDescriptionRequest descriptionOnlyPayload;
    private BotDescriptionRequest languageOnlyPayload;

    @BeforeEach
    void setUp() {
        fullPayload = new BotDescriptionRequest("This is a description", "en");
        descriptionOnlyPayload = new BotDescriptionRequest("Description only", null);
        languageOnlyPayload = new BotDescriptionRequest(null, "ru");
    }

    @Test
    @DisplayName("Serializes full payload with both fields present")
    void serializeFullPayload() throws Exception {
        var actual = json.write(fullPayload);
        then(actual).isNotNull();
        then(actual).isEqualToJson(new ClassPathResource(FULL_JSON));
        then(actual).doesNotHaveJsonPath("$.languageCode");
    }

    @Test
    @DisplayName("Deserializes full payload fixture into populated fields")
    void deserializeFullPayload() throws Exception {
        var actual = json.readObject(FULL_JSON);
        then(actual).isNotNull();
        then(actual).isEqualTo(fullPayload);
    }

    @Test
    @DisplayName("Serializes payload with 'description' only (omitting optional fields)")
    void serializeDescriptionOnlyPayload() throws Exception {
        var actual = json.write(descriptionOnlyPayload);
        then(actual).isNotNull();
        then(actual).isEqualToJson(new ClassPathResource(DESCRIPTION_ONLY_JSON));
    }

    @Test
    @DisplayName("Deserializes payload with 'description' only")
    void deserializeDescriptionOnlyPayload() throws Exception {
        var actual = json.readObject(DESCRIPTION_ONLY_JSON);
        then(actual).isNotNull();
        then(actual).isEqualTo(descriptionOnlyPayload);
    }

    @Test
    @DisplayName("Serializes payload with 'languageCode' only (omitting optional fields)")
    void serializeLanguageOnlyPayload() throws Exception {
        var actual = json.write(languageOnlyPayload);
        then(actual).isNotNull();
        then(actual).isEqualToJson(new ClassPathResource(LANGUAGE_ONLY_JSON));
    }

    @Test
    @DisplayName("Deserializes payload with 'languageCode' only")
    void deserializeLanguageOnlyPayload() throws Exception {
        var actual = json.readObject(LANGUAGE_ONLY_JSON);
        then(actual).isNotNull();
        then(actual).isEqualTo(languageOnlyPayload);
    }

}
