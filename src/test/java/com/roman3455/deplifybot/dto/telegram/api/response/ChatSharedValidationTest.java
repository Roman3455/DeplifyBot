package com.roman3455.deplifybot.dto.telegram.api.response;

import com.roman3455.deplifybot.test_utils.DtoValidationTestSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

import static com.roman3455.deplifybot.dto.telegram.api.TelegramApiDtoBuilder.validChatSharedFullPayload;
import static com.roman3455.deplifybot.dto.telegram.api.TelegramApiDtoBuilder.validChatSharedRequiredPayload;
import static com.roman3455.deplifybot.dto.telegram.api.TelegramApiDtoBuilder.invalidChatSharedWithNullRequestId;
import static com.roman3455.deplifybot.dto.telegram.api.TelegramApiDtoBuilder.invalidChatSharedWithNullChatId;

@DisplayName("ChatShared - DTO validation")
class ChatSharedValidationTest extends DtoValidationTestSupport<ChatShared> {

    @Override
    protected Stream<Arguments> provideInvalidArguments() {
        return Stream.of(
                Arguments.of(
                        "field 'requestId' is null (@NotNull)",
                        invalidChatSharedWithNullRequestId(),
                        "requestId",
                        MESSAGE_TEMPLATE_NOT_NULL
                ),
                Arguments.of(
                        "field 'chatId' is null (@NotNull)",
                        invalidChatSharedWithNullChatId(),
                        "chatId",
                        MESSAGE_TEMPLATE_NOT_NULL
                )
        );
    }

    @Override
    protected Stream<Arguments> provideValidArguments() {
        return Stream.of(
                Arguments.of(CASE_NAME_FULL_PAYLOAD, validChatSharedFullPayload()),
                Arguments.of(CASE_NAME_REQUIRED_PAYLOAD, validChatSharedRequiredPayload())
        );
    }

}
