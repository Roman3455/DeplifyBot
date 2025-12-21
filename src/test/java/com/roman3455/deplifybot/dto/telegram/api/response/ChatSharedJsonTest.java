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

import static com.roman3455.deplifybot.dto.telegram.api.TelegramApiDtoBuilder.validChatSharedFullPayload;
import static com.roman3455.deplifybot.dto.telegram.api.TelegramApiDtoBuilder.validChatSharedRequiredPayload;

@ActiveProfiles("test")
@JsonTest
@Import(JacksonConfiguration.class)
@DisplayName("ChatShared — JSON serialization & deserialization")
class ChatSharedJsonTest extends DtoJsonMarshallingTestSupport<ChatShared> {

    private static final String PATH = "/fixture/telegram/response/chat_shared/chat_shared_";

    @Autowired
    private JacksonTester<ChatShared> jsonTester;

    @Override
    protected JacksonTester<ChatShared> tester() {
        return jsonTester;
    }

    @Override
    protected Stream<Arguments> provideArguments() {
        return Stream.of(
                Arguments.of(
                        CASE_NAME_FULL_PAYLOAD,
                        validChatSharedFullPayload(),
                        PATH + "full.json",
                        List.of("$.requestId", "$.chatId")
                ),
                Arguments.of(
                        CASE_NAME_REQUIRED_PAYLOAD,
                        validChatSharedRequiredPayload(),
                        PATH + "required.json",
                        List.of("$.title", "$.username")
                )

        );
    }

}
