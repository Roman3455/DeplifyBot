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

import java.util.List;

import static org.assertj.core.api.BDDAssertions.then;

@JsonTest
@Import(JacksonConfiguration.class)
@DisplayName("SetMyCommandsRequest — JSON serialization & deserialization")
class SetMyCommandsRequestJsonTest {

    @Autowired
    private JacksonTester<SetMyCommandsRequest> json;

    private static final String SOURCE = "/fixture/telegram/request/set_my_commands_request/";
    private static final String FULL_JSON = SOURCE + "set_my_commands_request_full.json";
    private static final String COMMANDS_ONLY_JSON = SOURCE + "set_my_commands_request_commands_only.json";

    private SetMyCommandsRequest fullPayload;
    private SetMyCommandsRequest commandsOnlyPayload;

    @BeforeEach
    void setUp() {
        fullPayload = new SetMyCommandsRequest(
                List.of(new MyCommand("start", "Init command")),
                new BotCommandScope(BotCommandScopeType.DEFAULT, null),
                "en"
        );
        commandsOnlyPayload = new SetMyCommandsRequest(
                List.of(new MyCommand("start", "Init command")),
                null, null
        );
    }

    @Test
    @DisplayName("Serializes full payload with all fields present")
    void serializeFullPayload() throws Exception {
        var actual = json.write(fullPayload);
        then(actual).isNotNull();
        then(actual).isEqualToJson(new ClassPathResource(FULL_JSON));
        then(actual).doesNotHaveJsonPath("$.languageCode");
    }

    @Test
    @DisplayName("Deserializes full payload fixture into populated fields")
    void deserializeFullPayload() throws Exception {
        var actual = json.readObject(FULL_JSON);
        then(actual).isNotNull();
        then(actual).isEqualTo(fullPayload);
    }

    @Test
    @DisplayName("Serializes payload with 'commands' only (omitting optional fields)")
    void serializeCommandsOnlyPayload() throws Exception {
        var actual = json.write(commandsOnlyPayload);
        then(actual).isNotNull();
        then(actual).isEqualToJson(new ClassPathResource(COMMANDS_ONLY_JSON));
    }

    @Test
    @DisplayName("Deserializes payload with 'commands' only")
    void deserializeCommandsOnlyPayload() throws Exception {
        var actual = json.readObject(COMMANDS_ONLY_JSON);
        then(actual).isNotNull();
        then(actual).isEqualTo(commandsOnlyPayload);
    }

}
