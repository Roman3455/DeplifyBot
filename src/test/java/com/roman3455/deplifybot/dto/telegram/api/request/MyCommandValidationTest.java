package com.roman3455.deplifybot.dto.telegram.api.request;

import com.roman3455.deplifybot.util.ValidationTestSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("MyCommand - bean validation")
class MyCommandValidationTest extends ValidationTestSupport {

    private static final String COMMAND = "start";
    private static final String DESCRIPTION = "init command";

    @Test
    @DisplayName("Should pass validation for valid full payload")
    void shouldPassValidationFullPayload() {
        var valid = new MyCommand(COMMAND, DESCRIPTION);
        assertValid(valid);
    }

    @Test
    @DisplayName("Should fail validation when field 'command' is null (@NotBlank)")
    void shouldFailValidationCommandNullConstraint() {
        final String field = "command";
        final String messageTemplate = "{jakarta.validation.constraints.NotBlank.message}";
        var invalid = new MyCommand(null, DESCRIPTION);
        assertViolationContains(invalid, field, messageTemplate);
    }

    @Test
    @DisplayName("Should fail validation when field 'command' is blank (@NotBlank)")
    void shouldFailValidationCommandBlankConstraint() {
        final String field = "command";
        final String messageTemplate = "{jakarta.validation.constraints.NotBlank.message}";
        var invalid = new MyCommand("", DESCRIPTION);
        assertViolationContains(invalid, field, messageTemplate);
    }

    @Test
    @DisplayName("Should fail validation when field 'command' has value above max (@Size)")
    void shouldFailValidationCommandAboveMaxConstraint() {
        final int outOfBoundChars = 33;
        final String field = "command";
        final String messageTemplate = "{Size.max.message}";
        var invalid = new MyCommand("x".repeat(outOfBoundChars), DESCRIPTION);
        assertViolationContains(invalid, field, messageTemplate);
    }

    @Test
    @DisplayName("Should fail validation when field 'command' mismatch pattern (@Pattern)")
    void shouldFailValidationCommandMismatchPatternConstraint() {
        final String field = "command";
        final String messageTemplate = "{MyCommand.command.Pattern.message}";
        var invalid = new MyCommand("/" + COMMAND, DESCRIPTION);
        assertViolationContains(invalid, field, messageTemplate);
    }

    @Test
    @DisplayName("Should fail validation when field 'description' is null (@NotBlank)")
    void shouldFailValidationDescriptionNullConstraint() {
        final String field = "description";
        final String messageTemplate = "{jakarta.validation.constraints.NotBlank.message}";
        var invalid = new MyCommand(COMMAND, null);
        assertViolationContains(invalid, field, messageTemplate);
    }

    @Test
    @DisplayName("Should fail validation when field 'description' is blank (@NotBlank)")
    void shouldFailValidationDescriptionBlankConstraint() {
        final String field = "description";
        final String messageTemplate = "{jakarta.validation.constraints.NotBlank.message}";
        var invalid = new MyCommand(COMMAND, "  ");
        assertViolationContains(invalid, field, messageTemplate);
    }

    @Test
    @DisplayName("Should fail validation when field 'description' has value above max (@Size)")
    void shouldFailValidationDescriptionAboveMaxConstraint() {
        final int outOfBoundChars = 257;
        final String field = "description";
        final String messageTemplate = "{Size.max.message}";
        var invalid = new MyCommand(COMMAND, "x".repeat(outOfBoundChars));
        assertViolationContains(invalid, field, messageTemplate);
    }

}
