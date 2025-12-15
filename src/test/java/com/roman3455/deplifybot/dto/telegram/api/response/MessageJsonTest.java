package com.roman3455.deplifybot.dto.telegram.api.response;

import com.roman3455.deplifybot.configuration.JacksonConfiguration;
import com.roman3455.deplifybot.test_utils.DtoJsonMarshallingTestSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.provider.Arguments;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.stream.Stream;

import static com.roman3455.deplifybot.test_utils.TelegramApiDtoBuilder.validMessageFullPayload;
import static com.roman3455.deplifybot.test_utils.TelegramApiDtoBuilder.validMessageRequiredPayload;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ActiveProfiles("test")
@JsonTest
@Import(JacksonConfiguration.class)
@DisplayName("Message — JSON serialization & deserialization")
class MessageJsonTest extends DtoJsonMarshallingTestSupport<Message> {

    private static final String PATH = "/fixture/telegram/response/message/message_";

    @Autowired
    private JacksonTester<Message> jsonTester;

    @Override
    protected JacksonTester<Message> tester() {
        return jsonTester;
    }

    @Override
    protected Stream<Arguments> provideArguments() {
        return Stream.of(
                Arguments.of(
                        CASE_NAME_FULL_PAYLOAD,
                        validMessageFullPayload(),
                        PATH + "full.json",
                        List.of(
                                "$.messageId",
                                "$.messageThreadId",
                                "$.migrateToChatId",
                                "$.migrateFromChatId",
                                "$.chatShared"
                        )
                ),
                Arguments.of(
                        CASE_NAME_REQUIRED_PAYLOAD,
                        validMessageRequiredPayload(),
                        PATH + "required.json",
                        List.of(
                                "$.message_thread_id",
                                "$.from",
                                "$.text",
                                "$.migrate_to_chatId",
                                "$.migrate_from_chatId",
                                "$.chat_shared"
                        )
                )
        );
    }

    @Test
    @DisplayName("Should return true when payload has field 'text'")
    void shouldReturnTrueWhenPayloadHasFieldText() {
        assertTrue(validMessageFullPayload().hasText());
    }

    @Test
    @DisplayName("Should return false when payload has not field 'text'")
    void shouldReturnFalseWhenPayloadHasNotFieldText() {
        assertFalse(validMessageRequiredPayload().hasText());
    }

}
