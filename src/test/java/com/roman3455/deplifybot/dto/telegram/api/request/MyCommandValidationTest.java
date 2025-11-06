package com.roman3455.deplifybot.dto.telegram.api.request;

import com.roman3455.deplifybot.util.ValidationTestSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Bean Validation of MyCommand")
class MyCommandValidationTest extends ValidationTestSupport {

    private static final String COMMAND = "start";
    private static final String DESCRIPTION = "init command";

    @Test
    @DisplayName("Valid full payload passes validation")
    void validPayloadAllFields() {
        var valid = new MyCommand(COMMAND, DESCRIPTION);
        assertValid(valid);
    }

    @Test
    @DisplayName("Field 'command' @NotBlank: Null value should fail")
    void commandConstraintsNullValue() {
        final String field = "command";
        final String messageTemplate = "{jakarta.validation.constraints.NotBlank.message}";
        var invalid = new MyCommand(null, DESCRIPTION);
        assertViolationContains(invalid, field, messageTemplate);
    }

    @Test
    @DisplayName("Field 'command' @NotBlank: Blank value should fail")
    void commandConstraintsBlankValue() {
        final String field = "command";
        final String messageTemplate = "{jakarta.validation.constraints.NotBlank.message}";
        var invalid = new MyCommand("", DESCRIPTION);
        assertViolationContains(invalid, field, messageTemplate);
    }

    @Test
    @DisplayName("Field 'command' @Size: Value above max should fail")
    void commandConstraintsAboveMax() {
        final int outOfBoundChars = 33;
        final String field = "command";
        final String messageTemplate = "{Size.max.message}";
        var invalid = new MyCommand("x".repeat(outOfBoundChars), DESCRIPTION);
        assertViolationContains(invalid, field, messageTemplate);
    }

    @Test
    @DisplayName("Field 'command' @Pattern: Pattern mismatch should fail")
    void commandConstraintMismatchPattern() {
        final String field = "command";
        final String messageTemplate = "{MyCommand.command.Pattern.message}";
        var invalid = new MyCommand("/" + COMMAND, DESCRIPTION);
        assertViolationContains(invalid, field, messageTemplate);
    }

    @Test
    @DisplayName("Field 'description' @NotBlank: Null value should fail")
    void descriptionConstraintsNullValue() {
        final String field = "description";
        final String messageTemplate = "{jakarta.validation.constraints.NotBlank.message}";
        var invalid = new MyCommand(COMMAND, null);
        assertViolationContains(invalid, field, messageTemplate);
    }

    @Test
    @DisplayName("Field 'description' @NotBlank: Blank value should fail")
    void descriptionConstraintsBlankValue() {
        final String field = "description";
        final String messageTemplate = "{jakarta.validation.constraints.NotBlank.message}";
        var invalid = new MyCommand(COMMAND, "  ");
        assertViolationContains(invalid, field, messageTemplate);
    }

    @Test
    @DisplayName("Field 'description' @Size: Value above max should fail")
    void descriptionConstraintAboveMax() {
        final int outOfBoundChars = 257;
        final String field = "description";
        final String messageTemplate = "{Size.max.message}";
        var invalid = new MyCommand(COMMAND, "x".repeat(outOfBoundChars));
        assertViolationContains(invalid, field, messageTemplate);
    }

}
