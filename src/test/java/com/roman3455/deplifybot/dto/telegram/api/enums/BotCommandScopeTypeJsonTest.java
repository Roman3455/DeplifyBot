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
@DisplayName("BotCommandScopeType — JSON serialization & deserialization")
class BotCommandScopeTypeJsonTest {

    @Autowired
    private JacksonTester<Envelope> envelopeJson;

    private static final String SOURCE = "/fixture/telegram/enums/bot_command_scope_type/bot_command_scope_type_";
    private static final String DEFAULT_JSON = SOURCE + "default.json";
    private static final String ALL_PRIVATE_CHATS_JSON = SOURCE + "all_private_chats.json";
    private static final String ALL_GROUP_CHATS_JSON = SOURCE + "all_group_chats.json";
    private static final String ALL_CHAT_ADMINISTRATORS_JSON = SOURCE + "all_chat_administrators.json";
    private static final String CHAT_JSON = SOURCE + "chat.json";
    private static final String UNKNOWN_JSON = SOURCE + "unknown.json";

    private record Envelope(BotCommandScopeType scope) {
    }

    @ParameterizedTest(name = "[{index}] {0}")
    @MethodSource("enumSerializableAndDeserializableValues")
    @DisplayName("Should serialize enum to string value using @JsonValue")
    @SuppressWarnings("unused")
    void shouldSerializeEnumAsStringValue(
            final String caseName,
            final BotCommandScopeType type,
            final String fixturePath
    ) throws Exception {
        var given = new Envelope(type);
        var serialized = envelopeJson.write(given);
        then(serialized).isNotNull()
                .isEqualToJson(new ClassPathResource(fixturePath))
                .extractingJsonPathStringValue("$.scope")
                .isEqualTo(type.getValue());
    }

    @ParameterizedTest(name = "[{index}] {0}")
    @MethodSource("enumSerializableAndDeserializableValues")
    @DisplayName("Should deserialize string value to enum using @JsonCreator")
    @SuppressWarnings("unused")
    void shouldDeserializeStringToEnum(
            final String caseName,
            final BotCommandScopeType type,
            final String fixturePath
    ) throws IOException {
        var deserialized = envelopeJson.readObject(new ClassPathResource(fixturePath));
        then(deserialized).isNotNull();
        then(deserialized.scope()).isEqualTo(type);
    }

    static Stream<Arguments> enumSerializableAndDeserializableValues() {
        return Stream.of(
                Arguments.of(
                        "default",
                        BotCommandScopeType.DEFAULT,
                        DEFAULT_JSON
                ),
                Arguments.of(
                        "all_private_chats",
                        BotCommandScopeType.ALL_PRIVATE_CHATS,
                        ALL_PRIVATE_CHATS_JSON
                ),
                Arguments.of(
                        "all_group_chats",
                        BotCommandScopeType.ALL_GROUP_CHATS,
                        ALL_GROUP_CHATS_JSON
                ),
                Arguments.of(
                        "all_chat_administrators",
                        BotCommandScopeType.ALL_CHAT_ADMINISTRATORS,
                        ALL_CHAT_ADMINISTRATORS_JSON
                ),
                Arguments.of(
                        "chat",
                        BotCommandScopeType.CHAT,
                        CHAT_JSON
                )
        );
    }

    @Test
    @DisplayName("Should deserialize unknown value and serialize it back as 'unknown'")
    void shouldRoundTripUnknownValue() throws Exception {
        var deserialized = envelopeJson.readObject(new ClassPathResource(UNKNOWN_JSON));
        then(deserialized).isNotNull();
        then(deserialized.scope()).isEqualTo(BotCommandScopeType.UNKNOWN);
        var serialized = envelopeJson.write(deserialized);
        then(serialized).extractingJsonPathStringValue("$.scope")
                .isEqualTo(BotCommandScopeType.UNKNOWN.getValue());
    }

}
