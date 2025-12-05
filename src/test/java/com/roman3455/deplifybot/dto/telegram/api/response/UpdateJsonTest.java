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

import static com.roman3455.deplifybot.test_utils.TelegramApiDtoBuilder.validUpdateMessagePayload;
import static com.roman3455.deplifybot.test_utils.TelegramApiDtoBuilder.validUpdateCallbackQueryPayload;
import static com.roman3455.deplifybot.test_utils.TelegramApiDtoBuilder.validUpdateMyChatMemberPayload;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ActiveProfiles("test")
@JsonTest
@Import(JacksonConfiguration.class)
@DisplayName("Update — JSON serialization & deserialization")
class UpdateJsonTest extends DtoJsonMarshallingTestSupport<Update> {

    private static final String PATH = "/fixture/telegram/response/update/update_";

    @Autowired
    private JacksonTester<Update> jsonTester;

    @Override
    protected JacksonTester<Update> tester() {
        return jsonTester;
    }

    @Override
    protected Stream<Arguments> provideArguments() {
        return Stream.of(
                Arguments.of(
                        "message payload",
                        validUpdateMessagePayload(),
                        PATH + "message.json",
                        List.of("$.updateId", "$.callback_query", "$.my_chat_member")
                ),
                Arguments.of(
                        "callback query payload",
                        validUpdateCallbackQueryPayload(),
                        PATH + "callback_query.json",
                        List.of("$.updateId", "$.message", "$.callbackQuery", "$.my_chat_member")
                ),
                Arguments.of(
                        "my chat member payload",
                        validUpdateMyChatMemberPayload(),
                        PATH + "my_chat_member.json",
                        List.of("$.updateId", "$.message", "$.callback_query", "$.myChatMember")
                )
        );
    }

    @Test
    @DisplayName("Should return true when payload has field 'message'")
    void shouldReturnTrueWhenPayloadHasFieldMessage() {
        assertTrue(validUpdateMessagePayload().hasMessage());
    }

    @Test
    @DisplayName("Should return false when payload has not field 'message'")
    void shouldReturnFalseWhenPayloadHasNoFieldMessage() {
        assertFalse(validUpdateCallbackQueryPayload().hasMessage());
    }

    @Test
    @DisplayName("Should return true when payload has field 'callbackQuery'")
    void shouldReturnTrueWhenPayloadHasFieldCallbackQuery() {
        assertTrue(validUpdateCallbackQueryPayload().hasCallbackQuery());
    }

    @Test
    @DisplayName("Should return false when payload has not field 'callbackQuery'")
    void shouldReturnFalseWhenPayloadHasNoFieldCallbackQuery() {
        assertFalse(validUpdateMessagePayload().hasCallbackQuery());
    }

    @Test
    @DisplayName("Should return true when payload has field 'myChatMember'")
    void shouldReturnTrueWhenPayloadHasFieldMyChatMember() {
        assertTrue(validUpdateMyChatMemberPayload().hasMyChatMember());
    }

    @Test
    @DisplayName("Should return false when payload has not field 'myChatMember'")
    void shouldReturnFalseWhenPayloadHasNoFieldMyChatMember() {
        assertFalse(validUpdateMessagePayload().hasMyChatMember());
    }

}
