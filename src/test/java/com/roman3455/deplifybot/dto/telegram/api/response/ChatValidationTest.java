package com.roman3455.deplifybot.dto.telegram.api.response;

import com.roman3455.deplifybot.test_utils.DtoValidationTestSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

import static com.roman3455.deplifybot.test_utils.TelegramApiDtoBuilder.validChatFullPayload;
import static com.roman3455.deplifybot.test_utils.TelegramApiDtoBuilder.validChatRequiredPayload;
import static com.roman3455.deplifybot.test_utils.TelegramApiDtoBuilder.invalidChatWithNullId;
import static com.roman3455.deplifybot.test_utils.TelegramApiDtoBuilder.invalidChatWithNullType;

@DisplayName("Chat - DTO validation")
class ChatValidationTest extends DtoValidationTestSupport<Chat> {

    @Override
    protected Stream<Arguments> provideInvalidArguments() {
        return Stream.of(
                Arguments.of(
                        "field 'id' is null (@NotNull)",
                        invalidChatWithNullId(),
                        "id",
                        MESSAGE_TEMPLATE_NOT_NULL
                ),
                Arguments.of(
                        "field 'type' is null (@NotNull)",
                        invalidChatWithNullType(),
                        "type",
                        MESSAGE_TEMPLATE_NOT_NULL
                )
        );
    }

    @Override
    protected Stream<Arguments> provideValidArguments() {
        return Stream.of(
                Arguments.of(CASE_NAME_FULL_PAYLOAD, validChatFullPayload()),
                Arguments.of(CASE_NAME_REQUIRED_PAYLOAD, validChatRequiredPayload())
        );
    }

}
