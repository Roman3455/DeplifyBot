package com.roman3455.deplifybot.dto.telegram.api.response;

import com.roman3455.deplifybot.test_utils.DtoValidationTestSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

import static com.roman3455.deplifybot.test_utils.TelegramApiDtoBuilder.validUpdateMessagePayload;
import static com.roman3455.deplifybot.test_utils.TelegramApiDtoBuilder.validUpdateCallbackQueryPayload;
import static com.roman3455.deplifybot.test_utils.TelegramApiDtoBuilder.validUpdateMyChatMemberPayload;
import static com.roman3455.deplifybot.test_utils.TelegramApiDtoBuilder.invalidUpdateWithNullUpdateId;
import static com.roman3455.deplifybot.test_utils.TelegramApiDtoBuilder.invalidUpdateWithNegativeUpdateId;
import static com.roman3455.deplifybot.test_utils.TelegramApiDtoBuilder.invalidUpdateWithInvalidMessage;
import static com.roman3455.deplifybot.test_utils.TelegramApiDtoBuilder.invalidUpdateWithInvalidCallbackQuery;
import static com.roman3455.deplifybot.test_utils.TelegramApiDtoBuilder.invalidUpdateWithInvalidMyChatMember;
import static com.roman3455.deplifybot.test_utils.TelegramApiDtoBuilder.invalidUpdateWithNullOptionalFields;
import static com.roman3455.deplifybot.test_utils.TelegramApiDtoBuilder.invalidUpdateWithAllOptionalFieldsPresent;

@DisplayName("Update - DTO validation")
class UpdateValidationTest extends DtoValidationTestSupport<Update> {

    private static final String UPDATE_ID_FIELD = "updateId";
    private static final String ANY_PROVIDED_FIELD = "anyProvided";
    private static final String MESSAGE_TEMPLATE_ASSERT_TRUE = "{Update.isAnyProvided.AssertTrue}";

    @Override
    protected Stream<Arguments> provideInvalidArguments() {
        return Stream.of(
                Arguments.of(
                        "field 'updateId' is null (@NotNull)",
                        invalidUpdateWithNullUpdateId(),
                        UPDATE_ID_FIELD,
                        MESSAGE_TEMPLATE_NOT_NULL
                ),
                Arguments.of(
                        "field 'updateId' has negative value (@Positive)",
                        invalidUpdateWithNegativeUpdateId(),
                        UPDATE_ID_FIELD,
                        MESSAGE_TEMPLATE_POSITIVE
                ),
                Arguments.of(
                        "field 'message' has invalid value (@Valid)",
                        invalidUpdateWithInvalidMessage(),
                        "message.messageId",
                        MESSAGE_TEMPLATE_NOT_NULL
                ),
                Arguments.of(
                        "field 'callbackQuery' has invalid value (@Valid)",
                        invalidUpdateWithInvalidCallbackQuery(),
                        "callbackQuery.id",
                        MESSAGE_TEMPLATE_NOT_NULL
                ),
                Arguments.of(
                        "field 'myChatMember' has invalid value (@Valid)",
                        invalidUpdateWithInvalidMyChatMember(),
                        "myChatMember.date",
                        MESSAGE_TEMPLATE_NOT_NULL
                ),
                Arguments.of(
                        "optional fields not present (@AssertTrue)",
                        invalidUpdateWithNullOptionalFields(),
                        ANY_PROVIDED_FIELD,
                        MESSAGE_TEMPLATE_ASSERT_TRUE
                ),
                Arguments.of(
                        "more then one optional field present (@AssertTrue)",
                        invalidUpdateWithAllOptionalFieldsPresent(),
                        ANY_PROVIDED_FIELD,
                        MESSAGE_TEMPLATE_ASSERT_TRUE
                )
        );
    }

    @Override
    protected Stream<Arguments> provideValidArguments() {
        return Stream.of(
                Arguments.of("message payload", validUpdateMessagePayload()),
                Arguments.of("callbackQuery payload", validUpdateCallbackQueryPayload()),
                Arguments.of("myChatMember payload", validUpdateMyChatMemberPayload())
        );
    }

}
