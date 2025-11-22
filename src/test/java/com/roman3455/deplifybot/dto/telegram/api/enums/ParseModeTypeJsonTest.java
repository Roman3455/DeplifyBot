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

import java.util.stream.Stream;

import static org.assertj.core.api.BDDAssertions.then;

@ActiveProfiles("test")
@JsonTest
@Import(JacksonConfiguration.class)
@DisplayName("ParseModeType — JSON serialization & deserialization")
class ParseModeTypeJsonTest {

    @Autowired
    private JacksonTester<Envelope> envelopeJson;

    private static final String SOURCE = "/fixture/telegram/enums/parse_mode_type/parse_mode_type_";
    private static final String HTML_JSON = SOURCE + "HTML.json";
    private static final String MARKDOWN_V2_JSON = SOURCE + "MarkdownV2.json";
    private static final String PLAIN_JSON = SOURCE + "plain.json";
    private static final String UNKNOWN_JSON = SOURCE + "unknown.json";

    private record Envelope(ParseModeType parseMode) {
    }

    @ParameterizedTest(name = "[{index}] {0}")
    @MethodSource("enumSerializableAndDeserializableValues")
    @DisplayName("Should serialize enum to string value using @JsonValue")
    void shouldSerializeEnumAsStringValue(
            final String caseName,
            final ParseModeType parseMode,
            final String fixturePath
    ) throws Exception {
        var given = new Envelope(parseMode);
        var serialized = envelopeJson.write(given);
        then(serialized).isNotNull()
                .isEqualToJson(new ClassPathResource(fixturePath))
                .doesNotHaveJsonPath("$.parseMode")
                .extractingJsonPathStringValue("$.parse_mode")
                .isEqualTo(parseMode.getValue());
    }

    @ParameterizedTest(name = "[{index}] {0}")
    @MethodSource("enumSerializableAndDeserializableValues")
    @DisplayName("Should deserialize string value to enum using @JsonCreator")
    void shouldDeserializeStringToEnum(
            final String caseName,
            final ParseModeType parseMode,
            final String fixturePath
    ) throws Exception {
        var deserialized = envelopeJson.readObject(new ClassPathResource(fixturePath));
        then(deserialized).isNotNull();
        then(deserialized.parseMode()).isEqualTo(parseMode);
    }

    static Stream<Arguments> enumSerializableAndDeserializableValues() {
        return Stream.of(
                Arguments.of(
                        "MarkdownV2",
                        ParseModeType.MARKDOWN_V2,
                        MARKDOWN_V2_JSON
                ),
                Arguments.of(
                        "HTML",
                        ParseModeType.HTML,
                        HTML_JSON
                ),
                Arguments.of(
                        "Plain",
                        ParseModeType.PLAIN,
                        PLAIN_JSON
                )
        );
    }

    @Test
    @DisplayName("Should deserialize unknown value and serialize it back as 'unknown'")
    void shouldDeserializeUnknownValueAndSerialize() throws Exception {
        var deserialized = envelopeJson.readObject(new ClassPathResource(UNKNOWN_JSON));
        then(deserialized).isNotNull();
        then(deserialized.parseMode()).isEqualTo(ParseModeType.UNKNOWN);
        var serialized = envelopeJson.write(deserialized);
        then(serialized).extractingJsonPathStringValue("$.parse_mode")
                .isEqualTo(ChatType.UNKNOWN.getValue());
    }

}
