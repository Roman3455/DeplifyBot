package com.roman3455.deplifybot.dto.telegram.api.ui.button;

import com.roman3455.deplifybot.util.ValidationTestSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("CopyTextButton - bean validation")
class CopyTextButtonValidationTest extends ValidationTestSupport {

    @Test
    @DisplayName("Should pass validation for valid full payload")
    void shouldPassValidationFullPayload() {
        var valid = new CopyTextButton("Text to copy");
        assertValid(valid);
    }

    @Test
    @DisplayName("Should fail validation when field 'text' has value above max (@Size)")
    void shouldFailValidationTextAboveMaxConstraint() {
        final int outOfBoundLength = 257;
        final String field = "text";
        final String messageTemplate = "{Size.max.message}";
        var invalid = new CopyTextButton("x".repeat(outOfBoundLength));
        assertViolationContains(invalid, field, messageTemplate);
    }

}
