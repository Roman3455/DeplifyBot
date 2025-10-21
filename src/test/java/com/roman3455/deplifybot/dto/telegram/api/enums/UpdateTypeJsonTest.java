package com.roman3455.deplifybot.dto.telegram.api.enums;

import com.roman3455.deplifybot.configuration.JacksonConfiguration;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.context.annotation.Import;

import static org.assertj.core.api.BDDAssertions.then;


@JsonTest
@Import(JacksonConfiguration.class)
@DisplayName("UpdateType JSON serialization/deserialization")
class UpdateTypeJsonTest {

    @Autowired
    private JacksonTester<Envelope> envelopeJson;

    private static final String SOURCE = "/fixture/telegram/enums/update_type/";

    private record Envelope(UpdateType type) {
    }

    @Test
    @DisplayName("Should serialize enum to string value using @JsonValue")
    void shouldSerializeEnumAsStringValue() throws Exception {
        var given = new Envelope(UpdateType.MESSAGE);
        var actual = envelopeJson.write(given);

        then(actual)
                .extractingJsonPathValue("$.type")
                .isEqualTo(UpdateType.MESSAGE.getValue());
    }

    @Test
    @DisplayName("Should deserialize string value to enum using @JsonCreator")
    void shouldDeserializeStringToEnum() throws Exception {
        var given = envelopeJson.readObject(SOURCE + "update_type_message_envelop.json");

        then(given).isNotNull();
        then(given.type).isEqualTo(UpdateType.MESSAGE);
    }

    @Test
    @DisplayName("Should deserialize unknown value and serialize it back as 'unknown'")
    void shouldRoundTripUnknownValue() throws Exception {
        var serialized = envelopeJson.readObject(SOURCE + "update_type_unknown_envelop.json");
        var deserialized = envelopeJson.write(serialized);

        then(serialized).isNotNull();
        then(serialized.type).isEqualTo(UpdateType.UNKNOWN);
        then(deserialized)
                .extractingJsonPathStringValue("$.type")
                .isEqualTo(UpdateType.UNKNOWN.getValue());
    }

}
