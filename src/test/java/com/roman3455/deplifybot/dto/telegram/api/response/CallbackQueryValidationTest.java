package com.roman3455.deplifybot.dto.telegram.api.response;

import com.roman3455.deplifybot.test_utils.DtoValidationTestSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

import static com.roman3455.deplifybot.test_utils.TelegramApiDtoBuilder.validCallbackQueryFullPayload;
import static com.roman3455.deplifybot.test_utils.TelegramApiDtoBuilder.validCallbackQueryRequiredPayload;
import static com.roman3455.deplifybot.test_utils.TelegramApiDtoBuilder.invalidCallbackQueryWithNullId;
import static com.roman3455.deplifybot.test_utils.TelegramApiDtoBuilder.invalidCallbackQueryWithNullUser;
import static com.roman3455.deplifybot.test_utils.TelegramApiDtoBuilder.invalidCallbackQueryWithInvalidUser;
import static com.roman3455.deplifybot.test_utils.TelegramApiDtoBuilder.invalidCallbackQueryWithInvalidMessage;

@DisplayName("CallbackQuery - DTO validation")
class CallbackQueryValidationTest extends DtoValidationTestSupport<CallbackQuery> {

    @Override
    protected Stream<Arguments> provideInvalidArguments() {
        return Stream.of(
                Arguments.of(
                        "field 'id' is null (@NotNull)",
                        invalidCallbackQueryWithNullId(),
                        "id",
                        MESSAGE_TEMPLATE_NOT_NULL
                ),
                Arguments.of(
                        "field 'from' is null (@NotNull)",
                        invalidCallbackQueryWithNullUser(),
                        "from",
                        MESSAGE_TEMPLATE_NOT_NULL
                ),
                Arguments.of(
                        "field 'from' has invalid value (@Valid)",
                        invalidCallbackQueryWithInvalidUser(),
                        "from.id",
                        MESSAGE_TEMPLATE_NOT_NULL
                ),
                Arguments.of(
                        "field 'message' has invalid value (@Valid)",
                        invalidCallbackQueryWithInvalidMessage(),
                        "message.messageId",
                        MESSAGE_TEMPLATE_NOT_NULL
                )
        );
    }

    @Override
    protected Stream<Arguments> provideValidArguments() {
        return Stream.of(
                Arguments.of(CASE_NAME_FULL_PAYLOAD, validCallbackQueryFullPayload()),
                Arguments.of(CASE_NAME_REQUIRED_PAYLOAD, validCallbackQueryRequiredPayload())
        );
    }

}
