package com.roman3455.deplifybot.dto.telegram.api.request;

import com.roman3455.deplifybot.configuration.JacksonConfiguration;
import com.roman3455.deplifybot.dto.telegram.api.enums.ParseModeType;
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
@DisplayName("SendMessageRequest — JSON serialization & deserialization")
class SendMessageRequestJsonTest {

    @Autowired
    private JacksonTester<SendMessageRequest> json;

    private static final String SOURCE = "/fixture/telegram/request/send_message_request/";
    private static final String FULL_JSON = SOURCE + "send_message_request_full.json";
    private static final String REQUIRED_ONLY_JSON = SOURCE + "send_message_request_required_only.json";

    private SendMessageRequest fullPayload;
    private SendMessageRequest requiredOnlyPayload;

    @BeforeEach
    void setUp() {
        final long messageThreadId = -13435667;
        fullPayload = new SendMessageRequest(
                "123",
                messageThreadId,
                "message",
                ParseModeType.HTML,
                true,
                true
        );
        requiredOnlyPayload = new SendMessageRequest(
                "123",
                null,
                "message",
                null,
                null,
                null
        );
    }

    @Test
    @DisplayName("Should serialize full payload object into expected JSON fixture")
    void shouldSerializeFullPayload() throws Exception {
        var serialized = json.write(fullPayload);
        then(serialized).isNotNull()
                .isEqualToJson(new ClassPathResource(FULL_JSON))
                .doesNotHaveJsonPath("$.chatId")
                .doesNotHaveJsonPath("$.parseMode")
                .doesNotHaveJsonPath("$.disableNotification")
                .doesNotHaveJsonPath("$.protectContent");
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
    @DisplayName("Should serialize required only payload object into expected JSON fixture")
    void shouldSerializeRequiredOnlyPayload() throws Exception {
        var serialized = json.write(requiredOnlyPayload);
        then(serialized).isNotNull()
                .isEqualToJson(new ClassPathResource(REQUIRED_ONLY_JSON));
    }

    @Test
    @DisplayName("Should deserialize required only payload JSON fixture into expected object")
    void shouldDeserializeRequiredOnlyPayload() throws Exception {
        var deserialized = json.readObject(new ClassPathResource(REQUIRED_ONLY_JSON));
        then(deserialized).isNotNull()
                .isEqualTo(requiredOnlyPayload);
    }

    @Test
    @DisplayName("Should round-trip required only payload object")
    void shouldRoundTripRequiredOnlyPayload() throws Exception {
        var serialized = json.write(requiredOnlyPayload);
        var deserialized = json.parseObject(serialized.getJson());
        then(deserialized).isNotNull()
                .isEqualTo(requiredOnlyPayload);
    }

}
