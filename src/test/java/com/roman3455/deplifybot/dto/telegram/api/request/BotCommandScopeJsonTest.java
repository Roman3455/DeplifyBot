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
    @DisplayName("Serializes full payload with both fields present")
    void serializeFullPayload() throws Exception {
        var actual = json.write(fullPayload);
        then(actual).isNotNull();
        then(actual).isEqualToJson(new ClassPathResource(FULL_JSON));
        then(actual).doesNotHaveJsonPath("$.chatId");
    }

    @Test
    @DisplayName("Deserializes full payload fixture into populated fields")
    void deserializeFullPayload() throws Exception {
        var actual = json.readObject(FULL_JSON);
        then(actual).isNotNull();
        then(actual).isEqualTo(fullPayload);
    }

    @Test
    @DisplayName("Serializes payload with 'type' only (omitting optional field)")
    void serializeTypeOnlyPayload() throws Exception {
        var actual = json.write(typeOnlyPayload);
        then(actual).isNotNull();
        then(actual).isEqualToJson(new ClassPathResource(TYPE_ONLY_JSON));
    }

    @Test
    @DisplayName("Deserializes payload with 'type' only")
    void deserializeTypeOnlyPayload() throws Exception {
        var actual = json.readObject(TYPE_ONLY_JSON);
        then(actual).isNotNull();
        then(actual).isEqualTo(typeOnlyPayload);
    }

}
