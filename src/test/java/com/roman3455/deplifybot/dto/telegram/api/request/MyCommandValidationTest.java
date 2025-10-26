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
        assertValid(new MyCommand(COMMAND, DESCRIPTION));
    }

    @Test
    @DisplayName("Field 'command' @NotBlank: Null value should fail")
    void commandConstraintsNullValue() {
        assertViolationContains(
                new MyCommand(null, DESCRIPTION),
                "command", "Field 'command' is required."
        );
    }

    @Test
    @DisplayName("Field 'command' @NotBlank: Blank value should fail")
    void commandConstraintsBlankValue() {
        assertViolationContains(
                new MyCommand("", DESCRIPTION),
                "command", "Field 'command' is required."
        );
    }

    @Test
    @DisplayName("Field 'command' @Size: Value above max should fail")
    void commandConstraintsAboveMax() {
        final int outOfBoundChars = 33;
        assertViolationContains(
                new MyCommand("x".repeat(outOfBoundChars), DESCRIPTION),
                "command", "Allowed 'command' length must be at most 32 characters."
        );
    }

    @Test
    @DisplayName("Field 'command' @Pattern: Pattern mismatch should fail")
    void commandConstraintMismatchPattern() {
        assertViolationContains(
                new MyCommand("/" + COMMAND, DESCRIPTION),
                "command", "Only characters 'a-z', '0-9', '_' are allowed."
        );
    }

    @Test
    @DisplayName("Field 'description' @NotBlank: Null value should fail")
    void descriptionConstraintsNullValue() {
        assertViolationContains(
                new MyCommand(COMMAND, null),
                "description", "Field 'description' is required."
        );
    }

    @Test
    @DisplayName("Field 'description' @NotBlank: Blank value should fail")
    void descriptionConstraintsBlankValue() {
        assertViolationContains(
                new MyCommand(COMMAND, "  "),
                "description", "Field 'description' is required."
        );
    }

    @Test
    @DisplayName("Field 'description' @Size: Value above max should fail")
    void descriptionConstraintAboveMax() {
        final int outOfBoundChars = 257;
        assertViolationContains(
                new MyCommand(COMMAND, "x".repeat(outOfBoundChars)),
                "description", "Allowed 'description' length must be at most 256 characters."
        );
    }

}
