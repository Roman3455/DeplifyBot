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
    @DisplayName("Should serialize full payload object into expected JSON fixture")
    void shouldSerializeFullPayload() throws Exception {
        var serialized = json.write(fullPayload);
        then(serialized).isNotNull()
                .isEqualToJson(new ClassPathResource(FULL_JSON))
                .doesNotHaveJsonPath("$.languageCode");
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
    @DisplayName("Should serialize 'commands'-only payload object into expected JSON fixture")
    void shouldSerializeCommandsOnlyPayload() throws Exception {
        var serialized = json.write(commandsOnlyPayload);
        then(serialized).isNotNull()
                .isEqualToJson(new ClassPathResource(COMMANDS_ONLY_JSON));
    }

    @Test
    @DisplayName("Should deserialize 'commands'-only payload JSON fixture into expected object")
    void shouldDeserializeCommandsOnlyPayload() throws Exception {
        var deserialized = json.readObject(new ClassPathResource(COMMANDS_ONLY_JSON));
        then(deserialized).isNotNull()
                .isEqualTo(commandsOnlyPayload);
    }

    @Test
    @DisplayName("Should round-trip 'commands'-only payload object")
    void shouldRoundTripCommandsOnlyPayload() throws Exception {
        var serialized = json.write(commandsOnlyPayload);
        var deserialized = json.parseObject(serialized.getJson());
        then(deserialized).isNotNull()
                .isEqualTo(commandsOnlyPayload);
    }

}
