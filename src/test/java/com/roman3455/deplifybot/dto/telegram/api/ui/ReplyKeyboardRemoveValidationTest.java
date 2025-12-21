package com.roman3455.deplifybot.dto.telegram.api.ui;

import com.roman3455.deplifybot.test_utils.DtoValidationTestSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

import static com.roman3455.deplifybot.dto.telegram.api.TelegramApiDtoBuilder.invalidReplyKeyboardRemoveWithFalseValue;
import static com.roman3455.deplifybot.dto.telegram.api.TelegramApiDtoBuilder.validReplyKeyboardRemoveFullPayload;

@DisplayName("ReplyKeyboardRemove - DTO validation")
class ReplyKeyboardRemoveValidationTest extends DtoValidationTestSupport<ReplyKeyboardRemove> {

    @Override
    protected Stream<Arguments> provideInvalidArguments() {
        return Stream.of(
                Arguments.of(
                        "field 'removeKeyboard' is false (@AssertTrue)",
                        invalidReplyKeyboardRemoveWithFalseValue(),
                        "removeKeyboard",
                        "{ReplyKeyboardRemove.removeKeyboardIsTrue.AssertTrue}"
                )
        );
    }

    @Override
    protected Stream<Arguments> provideValidArguments() {
        return Stream.of(
                Arguments.of(CASE_NAME_FULL_PAYLOAD, validReplyKeyboardRemoveFullPayload())
        );
    }

}
