package com.roman3455.deplifybot.dto.telegram.api.ui.button;

import com.roman3455.deplifybot.util.ValidationTestSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("KeyboardButton - bean validation")
class KeyboardButtonValidationTest extends ValidationTestSupport {

    @Test
    @DisplayName("Should pass validation for valid full payload")
    void shouldPassValidationFullPayload() {
        var requestChat = new KeyboardButtonRequestChat(
                1L,
                true,
                null,
                null,
                null,
                null
        );
        var valid = new KeyboardButton("Label", requestChat);
        assertValid(valid);
    }

    @Test
    @DisplayName("Should pass validation for valid required only payload")
    void shouldPassValidationRequiredOnlyPayload() {
        var valid = new KeyboardButton("Label", null);
        assertValid(valid);
    }

    @Test
    @DisplayName("Should fail validation when field 'text' is null (@NotNull)")
    void shouldFailValidationWhenFieldTextIdIsNull() {
        final String field = "text";
        final String messageTemplate = "{jakarta.validation.constraints.NotNull.message}";
        var invalid = new KeyboardButton(null, null);
        assertViolationContains(invalid, field, messageTemplate);
    }

}
