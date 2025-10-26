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
@DisplayName("BotShortDescriptionRequest — JSON serialization & deserialization")
class BotShortDescriptionRequestJsonTest {

    @Autowired
    private JacksonTester<BotShortDescriptionRequest> json;

    private static final String SOURCE = "/fixture/telegram/request/bot_short_description_request/";
    private static final String FULL_JSON = SOURCE + "bot_short_description_request_full.json";
    private static final String LANGUAGE_ONLY_JSON = SOURCE + "bot_short_description_request_language_only.json";
    private static final String SHORT_ONLY_JSON = SOURCE + "bot_short_description_request_short_only.json";

    private BotShortDescriptionRequest fullPayload;
    private BotShortDescriptionRequest shortOnlyPayload;
    private BotShortDescriptionRequest languageOnlyPayload;

    @BeforeEach
    void setUp() {
        fullPayload = new BotShortDescriptionRequest("This is a short description", "en");
        shortOnlyPayload = new BotShortDescriptionRequest("Description only", null);
        languageOnlyPayload = new BotShortDescriptionRequest(null, "ru");
    }

    @Test
    @DisplayName("Serializes full payload with both fields present")
    void serializeFullPayload() throws Exception {
        var actual = json.write(fullPayload);
        then(actual).isNotNull();
        then(actual).isEqualToJson(new ClassPathResource(FULL_JSON));
        then(actual).doesNotHaveJsonPath("$.shortDescription");
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
    @DisplayName("Serializes payload with 'shortDescription' only (omitting optional fields)")
    void serializeShortOnlyPayload() throws Exception {
        var actual = json.write(shortOnlyPayload);
        then(actual).isNotNull();
        then(actual).isEqualToJson(new ClassPathResource(SHORT_ONLY_JSON));
    }

    @Test
    @DisplayName("Deserializes payload with 'shortDescription' only")
    void deserializeShortOnlyPayload() throws Exception {
        var actual = json.readObject(SHORT_ONLY_JSON);
        then(actual).isNotNull();
        then(actual).isEqualTo(shortOnlyPayload);
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
