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

import static org.assertj.core.api.BDDAssertions.then;


@ActiveProfiles("test")
@JsonTest
@Import(JacksonConfiguration.class)
@DisplayName("UpdateType — JSON serialization & deserialization")
class UpdateTypeJsonTest {

    @Autowired
    private JacksonTester<Envelope> json;

    private static final String SOURCE = "/fixture/telegram/enums/update_type/";
    private static final String MESSAGE_JSON = SOURCE + "update_type_message_value.json";
    private static final String UNKNOWN_JSON = SOURCE + "update_type_unknown_value.json";

    private record Envelope(UpdateType type) {
    }

    @Test
    @DisplayName("Should serialize enum to string value using @JsonValue")
    void shouldSerializeEnumAsStringValue() throws Exception {
        var given = new Envelope(UpdateType.MESSAGE);
        var serialized = json.write(given);
        then(serialized).isNotNull()
                .isEqualToJson(new ClassPathResource(MESSAGE_JSON))
                .extractingJsonPathValue("$.type")
                .isEqualTo(UpdateType.MESSAGE.getValue());
    }

    @Test
    @DisplayName("Should deserialize string value to enum using @JsonCreator")
    void shouldDeserializeStringToEnum() throws Exception {
        var deserialized = json.readObject(new ClassPathResource(MESSAGE_JSON));
        then(deserialized).isNotNull();
        then(deserialized.type()).isEqualTo(UpdateType.MESSAGE);
    }

    @Test
    @DisplayName("Should deserialize unknown value and serialize it back as 'unknown'")
    void shouldRoundTripUnknownValue() throws Exception {
        var deserialized = json.readObject(new ClassPathResource(UNKNOWN_JSON));
        then(deserialized).isNotNull();
        then(deserialized.type()).isEqualTo(UpdateType.UNKNOWN);
        var serialized = json.write(deserialized);
        then(serialized).extractingJsonPathStringValue("$.type")
                .isEqualTo(UpdateType.UNKNOWN.getValue());
    }

}
