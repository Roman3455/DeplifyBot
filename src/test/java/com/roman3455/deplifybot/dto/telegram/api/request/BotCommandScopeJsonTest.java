package com.roman3455.deplifybot.dto.telegram.api.request;

import com.roman3455.deplifybot.configuration.JacksonConfiguration;
import com.roman3455.deplifybot.test_utils.DtoJsonMarshallingTestSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.provider.Arguments;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.stream.Stream;

import static com.roman3455.deplifybot.dto.telegram.api.TelegramApiDtoBuilder.validBotCommandScopeFullPayload;
import static com.roman3455.deplifybot.dto.telegram.api.TelegramApiDtoBuilder.validBotCommandScopeRequiredPayload;

@ActiveProfiles("test")
@JsonTest
@Import(JacksonConfiguration.class)
@DisplayName("BotCommandScope — JSON serialization & deserialization")
class BotCommandScopeJsonTest extends DtoJsonMarshallingTestSupport<BotCommandScope> {

    private static final String PATH = "/fixture/telegram/request/bot_command_scope/bot_command_scope_";

    @Autowired
    private JacksonTester<BotCommandScope> jsonTester;

    @Override
    protected JacksonTester<BotCommandScope> tester() {
        return jsonTester;
    }

    @Override
    protected Stream<Arguments> provideArguments() {
        return Stream.of(
                Arguments.of(
                        CASE_NAME_FULL_PAYLOAD,
                        validBotCommandScopeFullPayload(),
                        PATH + "full.json",
                        List.of("$.chatId")
                ),
                Arguments.of(
                        CASE_NAME_REQUIRED_PAYLOAD,
                        validBotCommandScopeRequiredPayload(),
                        PATH + "required.json",
                        List.of("$.chat_id")
                )
        );
    }

}
