package com.roman3455.deplifybot.dto.telegram.api.request;

import com.roman3455.deplifybot.configuration.JacksonConfiguration;
import com.roman3455.deplifybot.dto.telegram.api.enums.BotCommandScopeType;
import org.junit.jupiter.api.BeforeEach;
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
@DisplayName("BotCommandScope — JSON serialization & deserialization")
class BotCommandScopeJsonTest {

    @Autowired
    private JacksonTester<BotCommandScope> json;

    private static final String SOURCE = "/fixture/telegram/request/bot_command_scope/";
    private static final String FULL_JSON = SOURCE + "bot_command_scope_full.json";
    private static final String TYPE_ONLY_JSON = SOURCE + "bot_command_scope_type_only.json";

    private BotCommandScope fullPayload;
    private BotCommandScope typeOnlyPayload;

    @BeforeEach
    void setUp() {
        final long chatId = 123L;
        fullPayload = new BotCommandScope(BotCommandScopeType.CHAT, chatId);
        typeOnlyPayload = new BotCommandScope(BotCommandScopeType.ALL_GROUP_CHATS, null);
    }

    @Test
    @DisplayName("Should serialize full payload object into expected JSON fixture")
    void shouldSerializeFullPayload() throws Exception {
        var serialized = json.write(fullPayload);
        then(serialized).isNotNull()
                .isEqualToJson(new ClassPathResource(FULL_JSON))
                .doesNotHaveJsonPath("$.chatId");
    }

    @Test
    @DisplayName("Should deserialize full payload JSON fixture into expected object")
    void shouldDeserializeFullPayload() throws Exception {
        var deserialized = json.readObject(new ClassPathResource(FULL_JSON));
        then(deserialized).isNotNull()
                .isEqualTo(fullPayload);
    }

    @Test
    @DisplayName("Should round-trip full payload JSON fixture")
    void shouldRoundTripFullPayload() throws Exception {
        var deserialized = json.readObject(new ClassPathResource(FULL_JSON));
        var serialized = json.write(deserialized);
        then(serialized).isNotNull()
                .isEqualToJson(new ClassPathResource(FULL_JSON));
    }

    @Test
    @DisplayName("Should serialize 'type'-only payload object into expected JSON fixture")
    void shouldSerializeTypeOnlyPayload() throws Exception {
        var serialized = json.write(typeOnlyPayload);
        then(serialized).isNotNull()
                .isEqualToJson(new ClassPathResource(TYPE_ONLY_JSON))
                .doesNotHaveJsonPath("$.chat_id");
    }

    @Test
    @DisplayName("Should deserialize 'type'-only payload JSON fixture into expected object")
    void shouldDeserializeTypeOnlyPayload() throws Exception {
        var deserialized = json.readObject(new ClassPathResource(TYPE_ONLY_JSON));
        then(deserialized).isNotNull()
                .isEqualTo(typeOnlyPayload);
    }

    @Test
    @DisplayName("Should round-trip 'type'-only payload object")
    void shouldRoundTripTypeOnlyPayload() throws Exception {
        var serialized = json.write(typeOnlyPayload);
        var deserialized = json.parseObject(serialized.getJson());
        then(deserialized).isNotNull()
                .isEqualTo(typeOnlyPayload);
    }

}
