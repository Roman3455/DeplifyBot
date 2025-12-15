package com.roman3455.deplifybot.dto.telegram.api.ui;

import com.roman3455.deplifybot.test_utils.DtoValidationTestSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

import static com.roman3455.deplifybot.test_utils.TelegramApiDtoBuilder.invalidInlineKeyboardMarkupWithEmptyInlineKeyboard;
import static com.roman3455.deplifybot.test_utils.TelegramApiDtoBuilder.invalidInlineKeyboardMarkupWithNestedEmptyInlineKeyboard;
import static com.roman3455.deplifybot.test_utils.TelegramApiDtoBuilder.validInlineKeyboardMarkupFullPayload;
import static com.roman3455.deplifybot.test_utils.TelegramApiDtoBuilder.validInlineKeyboardMarkupWithInvalidInlineKeyboardButton;

@DisplayName("InlineKeyboardMarkup - DTO validation")
class InlineKeyboardMarkupValidationTest extends DtoValidationTestSupport<InlineKeyboardMarkup> {

    @Override
    protected Stream<Arguments> provideInvalidArguments() {
        return Stream.of(
                Arguments.of(
                        "field 'inlineKeyboard' has empty <List> (@NotEmpty)",
                        invalidInlineKeyboardMarkupWithEmptyInlineKeyboard(),
                        "inlineKeyboard",
                        MESSAGE_TEMPLATE_NOT_EMPTY
                ),
                Arguments.of(
                        "field 'inlineKeyboard' has nested empty <List> (@NotEmpty)",
                        invalidInlineKeyboardMarkupWithNestedEmptyInlineKeyboard(),
                        "inlineKeyboard[0].<list element>",
                        MESSAGE_TEMPLATE_NOT_EMPTY
                ),
                Arguments.of(
                        "field 'inlineKeyboard' has invalid value (@Valid)",
                        validInlineKeyboardMarkupWithInvalidInlineKeyboardButton(),
                        "inlineKeyboard[0].<list element>[0].text",
                        MESSAGE_TEMPLATE_NOT_BLANK
                )
        );
    }

    @Override
    protected Stream<Arguments> provideValidArguments() {
        return Stream.of(
                Arguments.of(CASE_NAME_FULL_PAYLOAD, validInlineKeyboardMarkupFullPayload())
        );
    }

}
