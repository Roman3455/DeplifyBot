package com.roman3455.deplifybot.dto.telegram.api.enums;

import com.roman3455.deplifybot.configuration.JacksonConfiguration;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.ActiveProfiles;

import java.io.IOException;

import static org.assertj.core.api.BDDAssertions.then;

@ActiveProfiles("test")
@JsonTest
@Import(JacksonConfiguration.class)
@DisplayName("ChatType — JSON serialization & deserialization")
class ChatTypeJsonTest {

    @Autowired
    private JacksonTester<Envelope> envelopeJson;

    private static final String SOURCE = "/fixture/telegram/enums/chat_type/";
    private static final String PRIVATE_JSON = SOURCE + "chat_type_private_value.json";
    private static final String UNKNOWN_JSON = SOURCE + "chat_type_unknown_value.json";

    private record Envelope(ChatType type) {
    }

    @Test
    @DisplayName("Should serialize enum to string value using @JsonValue")
    void shouldSerializeEnvelope() throws IOException {
        var given = new Envelope(ChatType.PRIVATE);
        var serialized = envelopeJson.write(given);
        then(serialized).isNotNull()
                .isEqualToJson(new ClassPathResource(PRIVATE_JSON))
                .extractingJsonPathStringValue("$.type")
                .isEqualTo(ChatType.PRIVATE.getValue());
    }

    @Test
    @DisplayName("Should deserialize string value to enum using @JsonCreator")
    void shouldDeserializeEnumAsStringValue() throws Exception {
        var deserialized = envelopeJson.readObject(new ClassPathResource(PRIVATE_JSON));
        then(deserialized).isNotNull();
        then(deserialized.type()).isEqualTo(ChatType.PRIVATE);
    }

    @Test
    @DisplayName("Should deserialize unknown value and serialize it back as 'unknown'")
    void shouldDeserializeUnknownValueAndSerialize() throws Exception {
        var deserialized = envelopeJson.readObject(new ClassPathResource(UNKNOWN_JSON));
        then(deserialized).isNotNull();
        then(deserialized.type()).isEqualTo(ChatType.UNKNOWN);
        var serialized = envelopeJson.write(deserialized);
        then(serialized).extractingJsonPathStringValue("$.type")
                .isEqualTo(ChatType.UNKNOWN.getValue());
    }

}
