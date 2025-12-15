package com.roman3455.deplifybot.dto.telegram.api.response;

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

import static com.roman3455.deplifybot.test_utils.TelegramApiDtoBuilder.validChatFullPayload;
import static com.roman3455.deplifybot.test_utils.TelegramApiDtoBuilder.validChatRequiredPayload;

@ActiveProfiles("test")
@JsonTest
@Import(JacksonConfiguration.class)
@DisplayName("Chat — JSON serialization & deserialization")
class ChatJsonTest extends DtoJsonMarshallingTestSupport<Chat> {

    private static final String PATH = "/fixture/telegram/response/chat/chat_";

    @Autowired
    private JacksonTester<Chat> jsonTester;

    @Override
    protected JacksonTester<Chat> tester() {
        return jsonTester;
    }

    @Override
    protected Stream<Arguments> provideArguments() {
        return Stream.of(
                Arguments.of(
                        CASE_NAME_FULL_PAYLOAD,
                        validChatFullPayload(),
                        PATH + "full.json",
                        List.of("$.firstName", "$.isForum")
                ),
                Arguments.of(
                        CASE_NAME_REQUIRED_PAYLOAD,
                        validChatRequiredPayload(),
                        PATH + "required.json",
                        List.of("$.title", "$.username", "$.first_name", "$.is_forum")
                )
        );
    }

}
