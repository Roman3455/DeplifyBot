package com.roman3455.deplifybot.dto.telegram.api.enums;

import com.roman3455.deplifybot.configuration.JacksonConfiguration;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.ActiveProfiles;

import java.io.IOException;
import java.util.stream.Stream;

import static org.assertj.core.api.BDDAssertions.then;

@ActiveProfiles("test")
@JsonTest
@Import(JacksonConfiguration.class)
@DisplayName("ChatType — JSON serialization & deserialization")
class ChatTypeJsonTest {

    @Autowired
    private JacksonTester<Envelope> envelopeJson;

    private static final String SOURCE = "/fixture/telegram/enums/chat_type/chat_type_";
    private static final String PRIVATE_JSON = SOURCE + "private.json";
    private static final String GROUP_JSON = SOURCE + "group.json";
    private static final String SUPERGROUP_JSON = SOURCE + "supergroup.json";
    private static final String CHANNEL_JSON = SOURCE + "channel.json";
    private static final String UNKNOWN_JSON = SOURCE + "unknown.json";

    private record Envelope(ChatType type) {
    }

    @ParameterizedTest(name = "[{index}] {0}")
    @MethodSource("enumSerializableAndDeserializableValues")
    @DisplayName("Should serialize enum to string value using @JsonValue")
    void shouldSerializeEnumAsStringValue(
            final String caseName,
            final ChatType type,
            final String fixturePath
    ) throws Exception {
        var given = new Envelope(type);
        var serialized = envelopeJson.write(given);
        then(serialized).isNotNull()
                .isEqualToJson(new ClassPathResource(fixturePath))
                .extractingJsonPathStringValue("$.type")
                .isEqualTo(type.getValue());
    }

    @ParameterizedTest(name = "[{index}] {0}")
    @MethodSource("enumSerializableAndDeserializableValues")
    @DisplayName("Should deserialize string value to enum using @JsonCreator")
    void shouldDeserializeStringToEnum(
            final String caseName,
            final ChatType type,
            final String fixturePath
    ) throws IOException {
        var deserialized = envelopeJson.readObject(new ClassPathResource(fixturePath));
        then(deserialized).isNotNull();
        then(deserialized.type()).isEqualTo(type);
    }

    static Stream<Arguments> enumSerializableAndDeserializableValues() {
        return Stream.of(
                Arguments.of(
                        "private",
                        ChatType.PRIVATE,
                        PRIVATE_JSON
                ),
                Arguments.of(
                        "group",
                        ChatType.GROUP,
                        GROUP_JSON
                ),
                Arguments.of(
                        "supergroup",
                        ChatType.SUPERGROUP,
                        SUPERGROUP_JSON
                ),
                Arguments.of(
                        "channel",
                        ChatType.CHANNEL,
                        CHANNEL_JSON
                )
        );
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
