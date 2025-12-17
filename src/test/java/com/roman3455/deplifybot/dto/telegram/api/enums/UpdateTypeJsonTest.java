package com.roman3455.deplifybot.dto.telegram.api.enums;

import com.roman3455.deplifybot.configuration.JacksonConfiguration;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.TestInstance;
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
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@JsonTest
@Import(JacksonConfiguration.class)
@DisplayName("UpdateType — JSON serialization")
class UpdateTypeJsonTest {

    @Autowired
    private JacksonTester<Envelope> envelopeJson;

    private static final String SOURCE = "/fixture/telegram/enums/update_type/update_type_";
    private static final String MESSAGE_JSON = SOURCE + "message.json";
    private static final String EDITED_MESSAGE_JSON = SOURCE + "edited_message.json";
    private static final String CALLBACK_QUERY_JSON = SOURCE + "callback_query.json";
    private static final String MY_CHAT_MEMBER_JSON = SOURCE + "my_chat_member.json";

    private record Envelope(UpdateType type) {
    }

    @ParameterizedTest(name = "[{index}] {0}")
    @MethodSource("enumSerializableValues")
    @DisplayName("Should serialize enum to string value using @JsonValue")
    @SuppressWarnings("unused")
    void shouldSerializeEnumAsStringValue(
            final String caseName,
            final UpdateType type,
            final String fixturePath
    ) throws Exception {
        var given = new Envelope(type);
        var serialized = envelopeJson.write(given);
        then(serialized).isNotNull()
                .isEqualToJson(new ClassPathResource(fixturePath))
                .extractingJsonPathStringValue("$.type")
                .isEqualTo(type.getValue());
    }

    static Stream<Arguments> enumSerializableValues() {
        return Stream.of(
                Arguments.of(
                        "message",
                        UpdateType.MESSAGE,
                        MESSAGE_JSON
                ),
                Arguments.of(
                        "edited_message",
                        UpdateType.EDITED_MESSAGE,
                        EDITED_MESSAGE_JSON
                ),
                Arguments.of(
                        "callback_query",
                        UpdateType.CALLBACK_QUERY,
                        CALLBACK_QUERY_JSON
                ),
                Arguments.of(
                        "my_chat_member",
                        UpdateType.MY_CHAT_MEMBER,
                        MY_CHAT_MEMBER_JSON
                )
        );
    }

}
