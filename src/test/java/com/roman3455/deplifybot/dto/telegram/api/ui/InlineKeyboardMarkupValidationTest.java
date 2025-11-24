package com.roman3455.deplifybot.dto.telegram.api.ui;

import com.roman3455.deplifybot.dto.telegram.api.ui.button.InlineKeyboardButton;
import com.roman3455.deplifybot.util.ValidationTestSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

@DisplayName("InlineKeyboardMarkup - bean validation")
class InlineKeyboardMarkupValidationTest extends ValidationTestSupport {

    @Test
    @DisplayName("Should pass validation for valid full payload")
    void shouldPassValidationFullPayload() {
        var urlButton = InlineKeyboardButton.ofUrl("Button", "https://ok");
        var callbackDataButton = InlineKeyboardButton.ofCallbackData("Button", "callback");
        var valid = InlineKeyboardMarkup.ofRows(List.of(urlButton), List.of(callbackDataButton));
        assertValid(valid);
    }

    @Test
    @DisplayName("Should fail validation when field 'inlineKeyboard' has empty <List> (@NotEmpty)")
    void shouldFailValidationCommandsEmptyConstraint() {
        final String field = "inlineKeyboard";
        final String messageTemplate = "{jakarta.validation.constraints.NotEmpty.message}";
        var invalid = new InlineKeyboardMarkup(List.of());
        assertViolationContains(invalid, field, messageTemplate);
    }

    @Test
    @DisplayName("Should fail validation when field 'inlineKeyboard' has nested empty <List> (@NotEmpty)")
    void shouldFailValidationCommandsNestedEmptyConstraint() {
        final String field = "inlineKeyboard[0].<list element>";
        final String messageTemplate = "{jakarta.validation.constraints.NotEmpty.message}";
        var invalid = new InlineKeyboardMarkup(List.of(List.of()));
        assertViolationContains(invalid, field, messageTemplate);
    }
}
