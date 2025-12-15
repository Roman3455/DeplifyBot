package com.roman3455.deplifybot.dto.telegram.api.response;

import com.roman3455.deplifybot.test_utils.DtoValidationTestSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

import static com.roman3455.deplifybot.test_utils.TelegramApiDtoBuilder.validMessageFullPayload;
import static com.roman3455.deplifybot.test_utils.TelegramApiDtoBuilder.validMessageRequiredPayload;
import static com.roman3455.deplifybot.test_utils.TelegramApiDtoBuilder.invalidMessageWithNullMessageId;
import static com.roman3455.deplifybot.test_utils.TelegramApiDtoBuilder.invalidMessageWithInvalidUser;
import static com.roman3455.deplifybot.test_utils.TelegramApiDtoBuilder.invalidMessageWithNullDate;
import static com.roman3455.deplifybot.test_utils.TelegramApiDtoBuilder.invalidMessageWithNullChat;
import static com.roman3455.deplifybot.test_utils.TelegramApiDtoBuilder.invalidMessageWithInvalidChat;
import static com.roman3455.deplifybot.test_utils.TelegramApiDtoBuilder.invalidMessageWithInvalidChatShared;

@DisplayName("Message - DTO validation")
class MessageValidationTest extends DtoValidationTestSupport<Message> {

    @Override
    protected Stream<Arguments> provideInvalidArguments() {
        return Stream.of(
                Arguments.of(
                        "field 'messageId' is null (@NotNull)",
                        invalidMessageWithNullMessageId(),
                        "messageId",
                        MESSAGE_TEMPLATE_NOT_NULL
                ),
                Arguments.of(
                        "field 'from' has invalid value (@Valid)",
                        invalidMessageWithInvalidUser(),
                        "from.id",
                        MESSAGE_TEMPLATE_NOT_NULL
                ),
                Arguments.of(
                        "field 'date' is null (@NotNull)",
                        invalidMessageWithNullDate(),
                        "date",
                        MESSAGE_TEMPLATE_NOT_NULL
                ),
                Arguments.of(
                        "field 'chat' is null (@NotNull)",
                        invalidMessageWithNullChat(),
                        "chat",
                        MESSAGE_TEMPLATE_NOT_NULL
                ),
                Arguments.of(
                        "field 'chat' has invalid value (@Valid)",
                        invalidMessageWithInvalidChat(),
                        "chat.id",
                        MESSAGE_TEMPLATE_NOT_NULL
                ),
                Arguments.of(
                        "field 'chatShared' has invalid value (@Valid)",
                        invalidMessageWithInvalidChatShared(),
                        "chatShared.chatId",
                        MESSAGE_TEMPLATE_NOT_NULL
                )
        );
    }

    @Override
    protected Stream<Arguments> provideValidArguments() {
        return Stream.of(
                Arguments.of(CASE_NAME_FULL_PAYLOAD, validMessageFullPayload()),
                Arguments.of(CASE_NAME_REQUIRED_PAYLOAD, validMessageRequiredPayload())
        );
    }

}
