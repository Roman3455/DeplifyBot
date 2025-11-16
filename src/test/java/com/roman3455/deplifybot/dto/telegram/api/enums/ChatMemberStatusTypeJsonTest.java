package com.roman3455.deplifybot.dto.telegram.api.enums;

import com.roman3455.deplifybot.configuration.JacksonConfiguration;
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
@DisplayName("ChatMemberStatusType — JSON serialization & deserialization")
class ChatMemberStatusTypeJsonTest {

    @Autowired
    private JacksonTester<Envelope> envelopeJson;

    private static final String SOURCE = "/fixture/telegram/enums/chat_member_status_type/";
    private static final String LEFT_JSON = SOURCE + "chat_member_status_type_left_value.json";
    private static final String UNKNOWN_JSON = SOURCE + "chat_member_status_type_unknown_value.json";

    private record Envelope(ChatMemberStatusType status) {
    }

    @Test
    @DisplayName("Should serialize enum to string value using @JsonValue")
    void shouldSerializeEnumAsStringValue() throws Exception {
        var given = new Envelope(ChatMemberStatusType.LEFT);
        var serialized = envelopeJson.write(given);
        then(serialized).isNotNull()
                .isEqualToJson(new ClassPathResource(LEFT_JSON))
                .extractingJsonPathStringValue("status")
                .isEqualTo(ChatMemberStatusType.LEFT.getValue());
    }

    @Test
    @DisplayName("Should deserialize string value to enum using @JsonCreator")
    void shouldDeserializeEnumAsStringValue() throws Exception {
        var deserialized = envelopeJson.readObject(new ClassPathResource(LEFT_JSON));
        then(deserialized).isNotNull();
        then(deserialized.status()).isEqualTo(ChatMemberStatusType.LEFT);
    }

    @Test
    @DisplayName("Should deserialize unknown value and serialize it back as 'unknown'")
    void shouldDeserializeUnknownValueAndSerialize() throws Exception {
        var deserialized = envelopeJson.readObject(new ClassPathResource(UNKNOWN_JSON));
        then(deserialized).isNotNull();
        then(deserialized.status()).isEqualTo(ChatMemberStatusType.UNKNOWN);
        var serialized = envelopeJson.write(deserialized);
        then(serialized).extractingJsonPathStringValue("status")
                .isEqualTo(ChatMemberStatusType.UNKNOWN.getValue());
    }

}
