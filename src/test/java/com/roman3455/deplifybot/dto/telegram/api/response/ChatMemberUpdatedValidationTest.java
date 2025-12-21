package com.roman3455.deplifybot.dto.telegram.api.response;

import com.roman3455.deplifybot.test_utils.DtoValidationTestSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

import static com.roman3455.deplifybot.dto.telegram.api.TelegramApiDtoBuilder.validChatMemberUpdatedFullPayload;
import static com.roman3455.deplifybot.dto.telegram.api.TelegramApiDtoBuilder.invalidChatMemberUpdatedWithNullChat;
import static com.roman3455.deplifybot.dto.telegram.api.TelegramApiDtoBuilder.invalidChatMemberUpdatedWithInvalidChat;
import static com.roman3455.deplifybot.dto.telegram.api.TelegramApiDtoBuilder.invalidChatMemberUpdatedWithNullFrom;
import static com.roman3455.deplifybot.dto.telegram.api.TelegramApiDtoBuilder.invalidChatMemberUpdatedWithInvalidFrom;
import static com.roman3455.deplifybot.dto.telegram.api.TelegramApiDtoBuilder.invalidChatMemberUpdatedWithNullDate;
import static com.roman3455.deplifybot.dto.telegram.api.TelegramApiDtoBuilder.invalidChatMemberUpdatedWithNullOldChatMember;
import static com.roman3455.deplifybot.dto.telegram.api.TelegramApiDtoBuilder
        .invalidChatMemberUpdatedWithInvalidOldChatMember;
import static com.roman3455.deplifybot.dto.telegram.api.TelegramApiDtoBuilder.invalidChatMemberUpdatedWithNullNewChatMember;
import static com.roman3455.deplifybot.dto.telegram.api.TelegramApiDtoBuilder
        .invalidChatMemberUpdatedWithInvalidNewChatMember;

@DisplayName("ChatMemberUpdated - DTO validation")
class ChatMemberUpdatedValidationTest extends DtoValidationTestSupport<ChatMemberUpdated> {

    @Override
    protected Stream<Arguments> provideInvalidArguments() {
        return Stream.of(
                Arguments.of(
                        "field 'chat' is null (@NotNull)",
                        invalidChatMemberUpdatedWithNullChat(),
                        "chat",
                        MESSAGE_TEMPLATE_NOT_NULL
                ),
                Arguments.of(
                        "field 'chat' has invalid value (@Valid)",
                        invalidChatMemberUpdatedWithInvalidChat(),
                        "chat.id",
                        MESSAGE_TEMPLATE_NOT_NULL
                ),
                Arguments.of(
                        "field 'from' is null (@NotNull)",
                        invalidChatMemberUpdatedWithNullFrom(),
                        "from",
                        MESSAGE_TEMPLATE_NOT_NULL
                ),
                Arguments.of(
                        "field 'from' has invalid value (@Valid)",
                        invalidChatMemberUpdatedWithInvalidFrom(),
                        "from.id",
                        MESSAGE_TEMPLATE_NOT_NULL
                ),
                Arguments.of(
                        "field 'date' is null (@NotNull)",
                        invalidChatMemberUpdatedWithNullDate(),
                        "date",
                        MESSAGE_TEMPLATE_NOT_NULL
                ),
                Arguments.of(
                        "field 'oldChatMember' is null (@NotNull)",
                        invalidChatMemberUpdatedWithNullOldChatMember(),
                        "oldChatMember",
                        MESSAGE_TEMPLATE_NOT_NULL
                ),
                Arguments.of(
                        "field 'oldChatMember' has invalid value (@Valid)",
                        invalidChatMemberUpdatedWithInvalidOldChatMember(),
                        "oldChatMember.user",
                        MESSAGE_TEMPLATE_NOT_NULL
                ),
                Arguments.of(
                        "field 'newChatMember' is null (@NotNull)",
                        invalidChatMemberUpdatedWithNullNewChatMember(),
                        "newChatMember",
                        MESSAGE_TEMPLATE_NOT_NULL
                ),
                Arguments.of(
                        "field 'newChatMember' has invalid value (@Valid)",
                        invalidChatMemberUpdatedWithInvalidNewChatMember(),
                        "newChatMember.user",
                        MESSAGE_TEMPLATE_NOT_NULL
                )
        );
    }

    @Override
    protected Stream<Arguments> provideValidArguments() {
        return Stream.of(
                Arguments.of(CASE_NAME_FULL_PAYLOAD, validChatMemberUpdatedFullPayload())
        );
    }

}
