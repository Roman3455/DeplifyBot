package com.roman3455.deplifybot.dto.telegram.api.response;

import com.roman3455.deplifybot.test_utils.DtoValidationTestSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

import static com.roman3455.deplifybot.test_utils.TelegramApiDtoBuilder.validChatMemberFullPayload;
import static com.roman3455.deplifybot.test_utils.TelegramApiDtoBuilder.invalidChatMemberWithNullStatus;
import static com.roman3455.deplifybot.test_utils.TelegramApiDtoBuilder.invalidChatMemberWithNullUser;
import static com.roman3455.deplifybot.test_utils.TelegramApiDtoBuilder.invalidChatMemberWithInvalidUser;

@DisplayName("ChatMember - DTO validation")
class ChatMemberValidationTest extends DtoValidationTestSupport<ChatMember> {

    @Override
    protected Stream<Arguments> provideInvalidArguments() {
        return Stream.of(
                Arguments.of(
                        "field 'status' is null (@NotNull)",
                        invalidChatMemberWithNullStatus(),
                        "status",
                        MESSAGE_TEMPLATE_NOT_NULL
                ),
                Arguments.of(
                        "field 'user' is null (@NotNull)",
                        invalidChatMemberWithNullUser(),
                        "user",
                        MESSAGE_TEMPLATE_NOT_NULL
                ),
                Arguments.of(
                        "field 'user' has invalid value (@Valid)",
                        invalidChatMemberWithInvalidUser(),
                        "user.id",
                        MESSAGE_TEMPLATE_NOT_NULL
                )
        );
    }

    @Override
    protected Stream<Arguments> provideValidArguments() {
        return Stream.of(
                Arguments.of(CASE_NAME_FULL_PAYLOAD, validChatMemberFullPayload())
        );
    }

}
