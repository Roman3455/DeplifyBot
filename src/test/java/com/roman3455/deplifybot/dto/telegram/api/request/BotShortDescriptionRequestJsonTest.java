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
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.BDDAssertions.then;

@ActiveProfiles("test")
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
    @DisplayName("Should serialize full payload object into expected JSON fixture")
    void shouldSerializeFullPayload() throws Exception {
        var serialized = json.write(fullPayload);
        then(serialized).isNotNull()
                .isEqualToJson(new ClassPathResource(FULL_JSON))
                .doesNotHaveJsonPath("$.shortDescription")
                .doesNotHaveJsonPath("$.languageCode");
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

    @Test
    @DisplayName("Should serialize 'shortDescription'-only payload object into expected JSON fixture")
    void shouldSerializeShortOnlyPayload() throws Exception {
        var serialized = json.write(shortOnlyPayload);
        then(serialized).isNotNull()
                .isEqualToJson(new ClassPathResource(SHORT_ONLY_JSON));
    }

    @Test
    @DisplayName("Should deserialize 'shortDescription'-only payload JSON fixture into expected object")
    void shouldDeserializeShortOnlyPayload() throws Exception {
        var deserialized = json.readObject(new ClassPathResource(SHORT_ONLY_JSON));
        then(deserialized).isNotNull()
                .isEqualTo(shortOnlyPayload);
    }

    @Test
    @DisplayName("Should serialize 'languageCode'-only payload object into expected JSON fixture")
    void shouldSerializeLanguageOnlyPayload() throws Exception {
        var serialized = json.write(languageOnlyPayload);
        then(serialized).isNotNull()
                .isEqualToJson(new ClassPathResource(LANGUAGE_ONLY_JSON));
    }

    @Test
    @DisplayName("Should deserialize 'languageCode'-only payload JSON fixture into expected object")
    void shouldDeserializeLanguageOnlyPayload() throws Exception {
        var deserialized = json.readObject(new ClassPathResource(LANGUAGE_ONLY_JSON));
        then(deserialized).isNotNull()
                .isEqualTo(languageOnlyPayload);
    }

    @Test
    @DisplayName("Should round-trip 'languageCode'-only payload object")
    void shouldRoundTripLanguageCodeOnlyPayload() throws Exception {
        var serialized = json.write(languageOnlyPayload);
        var deserialized = json.parseObject(serialized.getJson());
        then(deserialized).isNotNull()
                .isEqualTo(languageOnlyPayload);
    }

}
