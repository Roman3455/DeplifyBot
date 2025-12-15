package com.roman3455.deplifybot.dto.telegram.api.ui.button;

import com.roman3455.deplifybot.test_utils.DtoValidationTestSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

import static com.roman3455.deplifybot.test_utils.TelegramApiDtoBuilder.validInlineKeyboardButtonCallbackDataPayload;
import static com.roman3455.deplifybot.test_utils.TelegramApiDtoBuilder.validInlineKeyboardButtonCopyTextPayload;
import static com.roman3455.deplifybot.test_utils.TelegramApiDtoBuilder.validInlineKeyboardButtonUrlPayload;
import static com.roman3455.deplifybot.test_utils.TelegramApiDtoBuilder.invalidInlineKeyboardButtonWithBlankText;
import static com.roman3455.deplifybot.test_utils.TelegramApiDtoBuilder.invalidInlineKeyboardButtonWithMismatchUrl;
import static com.roman3455.deplifybot.test_utils.TelegramApiDtoBuilder
        .invalidInlineKeyboardButtonWithBytesLengthBelowMinCallbackData;
import static com.roman3455.deplifybot.test_utils.TelegramApiDtoBuilder
        .invalidInlineKeyboardButtonWithBytesLengthAboveMaxCallbackData;
import static com.roman3455.deplifybot.test_utils.TelegramApiDtoBuilder
        .invalidInlineKeyboardButtonWithInvalidCopyTextButton;
import static com.roman3455.deplifybot.test_utils.TelegramApiDtoBuilder
        .invalidInlineKeyboardButtonWithNullOptionalFields;
import static com.roman3455.deplifybot.test_utils.TelegramApiDtoBuilder
        .invalidInlineKeyboardButtonWithAllOptionalFieldsPresent;

@DisplayName("InlineKeyboardButton - DTO validation")
class InlineKeyboardButtonValidationTest extends DtoValidationTestSupport<InlineKeyboardButton> {

    private static final String CALLBACK_DATA_FIELD = "callbackData";
    private static final String ASSERT_TRUE_FIELD = "anyProvided";
    private static final String MESSAGE_TEMPLATE_BYTES_LENGTH = "{BytesLength.validation.constraints.message}";
    private static final String MESSAGE_TEMPLATE_ASSERT_TRUE = "{InlineKeyboardButton.isAnyProvided.AssertTrue}";

    @Override
    protected Stream<Arguments> provideInvalidArguments() {
        return Stream.of(
                Arguments.of(
                        "field 'text' is blank (@NotBlank)",
                        invalidInlineKeyboardButtonWithBlankText(),
                        "text",
                        MESSAGE_TEMPLATE_NOT_BLANK
                ),
                Arguments.of(
                        "field 'url' mismatch pattern (@Pattern)",
                        invalidInlineKeyboardButtonWithMismatchUrl(),
                        "url",
                        "{InlineKeyboardButton.url.Pattern.message}"
                ),
                Arguments.of(
                        "field 'callbackData' bytes length below min value (@BytesLength)",
                        invalidInlineKeyboardButtonWithBytesLengthBelowMinCallbackData(),
                        CALLBACK_DATA_FIELD,
                        MESSAGE_TEMPLATE_BYTES_LENGTH
                ),
                Arguments.of(
                        "field 'callbackData' bytes length above max value (@BytesLength)",
                        invalidInlineKeyboardButtonWithBytesLengthAboveMaxCallbackData(),
                        CALLBACK_DATA_FIELD,
                        MESSAGE_TEMPLATE_BYTES_LENGTH
                ),
                Arguments.of(
                        "field 'copyText' has invalid CopyTextButton (@Valid)",
                        invalidInlineKeyboardButtonWithInvalidCopyTextButton(),
                        "copyText.text",
                        MESSAGE_TEMPLATE_NOT_BLANK
                ),
                Arguments.of(
                        "optional fields not present (@AssertTrue)",
                        invalidInlineKeyboardButtonWithNullOptionalFields(),
                        ASSERT_TRUE_FIELD,
                        MESSAGE_TEMPLATE_ASSERT_TRUE
                ),
                Arguments.of(
                        "more then one optional fields are present (@AssertTrue)",
                        invalidInlineKeyboardButtonWithAllOptionalFieldsPresent(),
                        ASSERT_TRUE_FIELD,
                        MESSAGE_TEMPLATE_ASSERT_TRUE
                )
        );
    }

    @Override
    protected Stream<Arguments> provideValidArguments() {
        return Stream.of(
                Arguments.of("callbackData payload", validInlineKeyboardButtonCallbackDataPayload()),
                Arguments.of("copyText payload", validInlineKeyboardButtonCopyTextPayload()),
                Arguments.of("url payload", validInlineKeyboardButtonUrlPayload())
        );
    }

}
