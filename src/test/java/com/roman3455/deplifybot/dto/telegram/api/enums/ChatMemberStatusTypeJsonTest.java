package com.roman3455.deplifybot.dto.telegram.api.enums;

import com.roman3455.deplifybot.configuration.JacksonConfiguration;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
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

import java.io.IOException;
import java.util.stream.Stream;

import static org.assertj.core.api.BDDAssertions.then;

@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@JsonTest
@Import(JacksonConfiguration.class)
@DisplayName("ChatMemberStatusType — JSON serialization & deserialization")
class ChatMemberStatusTypeJsonTest {

    @Autowired
    private JacksonTester<Envelope> envelopeJson;

    private static final String SOURCE = "/fixture/telegram/enums/chat_member_status_type/chat_member_status_type_";
    private static final String ADMINISTRATOR_JSON = SOURCE + "administrator.json";
    private static final String CREATOR_JSON = SOURCE + "creator.json";
    private static final String KICKED_JSON = SOURCE + "kicked.json";
    private static final String LEFT_JSON = SOURCE + "left.json";
    private static final String MEMBER_JSON = SOURCE + "member.json";
    private static final String RESTRICTED_JSON = SOURCE + "restricted.json";
    private static final String UNKNOWN_JSON = SOURCE + "unknown.json";

    private record Envelope(ChatMemberStatusType status) {
    }

    @ParameterizedTest(name = "[{index}] {0}")
    @MethodSource("enumSerializableAndDeserializableValues")
    @DisplayName("Should serialize enum to string value using @JsonValue")
    @SuppressWarnings("unused")
    void shouldSerializeEnumAsStringValue(
            final String caseName,
            final ChatMemberStatusType status,
            final String fixturePath
    ) throws Exception {
        var given = new Envelope(status);
        var serialized = envelopeJson.write(given);
        then(serialized).isNotNull()
                .isEqualToJson(new ClassPathResource(fixturePath))
                .extractingJsonPathStringValue("$.status")
                .isEqualTo(status.getValue());
    }

    @ParameterizedTest(name = "[{index}] {0}")
    @MethodSource("enumSerializableAndDeserializableValues")
    @DisplayName("Should deserialize string value to enum using @JsonCreator")
    @SuppressWarnings("unused")
    void shouldDeserializeStringToEnum(
            final String caseName,
            final ChatMemberStatusType status,
            final String fixturePath
    ) throws IOException {
        var deserialized = envelopeJson.readObject(new ClassPathResource(fixturePath));
        then(deserialized).isNotNull();
        then(deserialized.status()).isEqualTo(status);
    }

    static Stream<Arguments> enumSerializableAndDeserializableValues() {
        return Stream.of(
                Arguments.of(
                        "administrator",
                        ChatMemberStatusType.ADMINISTRATOR,
                        ADMINISTRATOR_JSON
                ),
                Arguments.of(
                        "creator",
                        ChatMemberStatusType.CREATOR,
                        CREATOR_JSON
                ),
                Arguments.of(
                        "kicked",
                        ChatMemberStatusType.KICKED,
                        KICKED_JSON
                ),
                Arguments.of(
                        "left",
                        ChatMemberStatusType.LEFT,
                        LEFT_JSON
                ),
                Arguments.of(
                        "member",
                        ChatMemberStatusType.MEMBER,
                        MEMBER_JSON
                ),
                Arguments.of(
                        "restricted",
                        ChatMemberStatusType.RESTRICTED,
                        RESTRICTED_JSON
                )
        );
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
