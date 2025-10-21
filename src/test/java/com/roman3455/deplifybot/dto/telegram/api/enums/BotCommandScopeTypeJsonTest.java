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
@DisplayName("BotCommandScopeType JSON serialization/deserialization")
class BotCommandScopeTypeJsonTest {

    @Autowired
    private JacksonTester<Envelope> envelopeJson;

    private static final String SOURCE = "/fixture/telegram/enums/bot_command_scope_type/";

    private record Envelope(BotCommandScopeType scope) {
    }

    @Test
    @DisplayName("Should serialize enum to string value using @JsonValue")
    void shouldSerializeEnumAsStringValue() throws Exception {
        var given = new Envelope(BotCommandScopeType.DEFAULT);
        var actual = envelopeJson.write(given);

        then(actual)
                .extractingJsonPathStringValue("$.scope")
                .isEqualTo(BotCommandScopeType.DEFAULT.getValue());
    }

    @Test
    @DisplayName("Should deserialize string value to enum using @JsonCreator")
    void shouldDeserializeStringToEnum() throws Exception {
        var given = envelopeJson.readObject(SOURCE + "bot_command_scope_type_default_envelop.json");

        then(given).isNotNull();
        then(given.scope()).isEqualTo(BotCommandScopeType.DEFAULT);
    }

    @Test
    @DisplayName("Should deserialize unknown value and serialize it back as 'unknown'")
    void shouldRoundTripUnknownValue() throws Exception {
        var serialized = envelopeJson.readObject(SOURCE + "bot_command_scope_type_unknown_envelop.json");
        var deserialized = envelopeJson.write(serialized);

        then(serialized).isNotNull();
        then(serialized.scope()).isEqualTo(BotCommandScopeType.UNKNOWN);
        then(deserialized)
                .extractingJsonPathStringValue("$.scope")
                .isEqualTo(BotCommandScopeType.UNKNOWN.getValue());
    }

}
