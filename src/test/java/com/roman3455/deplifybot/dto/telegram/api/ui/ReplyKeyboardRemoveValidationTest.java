package com.roman3455.deplifybot.dto.telegram.api.ui;

import com.roman3455.deplifybot.util.ValidationTestSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("ReplyKeyboardRemove - bean validation")
class ReplyKeyboardRemoveValidationTest extends ValidationTestSupport {

    @Test
    @DisplayName("Should pass validation for valid full payload")
    void shouldPassValidationFullPayload() {
        var valid = new ReplyKeyboardRemove(true);
        assertValid(valid);
    }

    @Test
    @DisplayName("Should fail validation when field 'removeKeyboard' is false (@AssertTrue)")
    void shouldFailValidationWhenFieldRemoveKeyboardIsFalse() {
        final String field = "removeKeyboard";
        final String messageTemplate = "{ReplyKeyboardRemove.removeKeyboardIsTrue.AssertTrue}";
        var invalid = new ReplyKeyboardRemove(false);
        assertViolationContains(invalid, field, messageTemplate);
    }

}
