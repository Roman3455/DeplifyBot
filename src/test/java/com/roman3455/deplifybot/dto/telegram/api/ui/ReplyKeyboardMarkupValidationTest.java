package com.roman3455.deplifybot.dto.telegram.api.ui;

import com.roman3455.deplifybot.test_utils.DtoValidationTestSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

import static com.roman3455.deplifybot.test_utils.TelegramApiDtoBuilder.invalidReplyKeyboardMarkupWithEmptyKeyboard;
import static com.roman3455.deplifybot.test_utils.TelegramApiDtoBuilder.invalidReplyKeyboardMarkupWithInvalidKeyboard;
import static com.roman3455.deplifybot.test_utils.TelegramApiDtoBuilder.invalidReplyKeyboardMarkupWithNestedEmptyKeyboard;
import static com.roman3455.deplifybot.test_utils.TelegramApiDtoBuilder.invalidReplyKeyboardMarkupWithSizeAboveMaxPlaceholder;
import static com.roman3455.deplifybot.test_utils.TelegramApiDtoBuilder.invalidReplyKeyboardMarkupWithSizeBelowMinPlaceholder;
import static com.roman3455.deplifybot.test_utils.TelegramApiDtoBuilder.validReplyKeyboardMarkupFullPayload;
import static com.roman3455.deplifybot.test_utils.TelegramApiDtoBuilder.validReplyKeyboardMarkupRequiredPayload;

@DisplayName("ReplyKeyboardMarkup - DTO validation")
class ReplyKeyboardMarkupValidationTest extends DtoValidationTestSupport<ReplyKeyboardMarkup> {

    private static final String PLACEHOLDER_FIELD = "inputFieldPlaceholder";

    @Override
    protected Stream<Arguments> provideInvalidArguments() {
        return Stream.of(
                Arguments.of(
                        "field 'keyboard' has empty <List> (@NotEmpty)",
                        invalidReplyKeyboardMarkupWithEmptyKeyboard(),
                        "keyboard",
                        MESSAGE_TEMPLATE_NOT_EMPTY
                ),
                Arguments.of(
                        "field 'keyboard' has nested empty <List> (@NotEmpty)",
                        invalidReplyKeyboardMarkupWithNestedEmptyKeyboard(),
                        "keyboard[0].<list element>",
                        MESSAGE_TEMPLATE_NOT_EMPTY
                ),
                Arguments.of(
                        "field 'keyboard' has invalid value (@Valid)",
                        invalidReplyKeyboardMarkupWithInvalidKeyboard(),
                        "keyboard[0].<list element>[0].text",
                        MESSAGE_TEMPLATE_NOT_BLANK
                ),
                Arguments.of(
                        "field 'inputFieldPlaceholder' has value below min (@Size)",
                        invalidReplyKeyboardMarkupWithSizeBelowMinPlaceholder(),
                        PLACEHOLDER_FIELD,
                        MESSAGE_TEMPLATE_SIZE
                ),
                Arguments.of(
                        "field 'inputFieldPlaceholder' has value above max (@Size)",
                        invalidReplyKeyboardMarkupWithSizeAboveMaxPlaceholder(),
                        PLACEHOLDER_FIELD,
                        MESSAGE_TEMPLATE_SIZE
                )
        );
    }

    @Override
    protected Stream<Arguments> provideValidArguments() {
        return Stream.of(
                Arguments.of(CASE_NAME_FULL_PAYLOAD, validReplyKeyboardMarkupFullPayload()),
                Arguments.of(CASE_NAME_REQUIRED_PAYLOAD, validReplyKeyboardMarkupRequiredPayload())
        );
    }

}
