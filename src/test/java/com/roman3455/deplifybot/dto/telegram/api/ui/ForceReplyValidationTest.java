package com.roman3455.deplifybot.dto.telegram.api.ui;

import com.roman3455.deplifybot.util.ValidationTestSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("ForceReply - bean validation")
class ForceReplyValidationTest extends ValidationTestSupport {

    @Test
    @DisplayName("Should pass validation for valid full payload")
    void shouldPassValidationFullPayload() {
        var valid = new ForceReply(true, "Input text");
        assertValid(valid);
        assertValid(ForceReply.withPlaceholder("Input text"));
    }

    @Test
    @DisplayName("Should pass validation for valid required only payload")
    void shouldPassValidationRequiredOnlyPayload() {
        var valid = new ForceReply(true, null);
        assertValid(valid);
        assertValid(ForceReply.empty());
    }

    @Test
    @DisplayName("Should fail validation when field 'forceReply' is false (@AssertTrue)")
    void shouldFailValidationWhenFieldForceReplyIsFalse() {
        final String field = "forceReply";
        final String messageTemplate = "{ForceReply.forceReplyIsTrue.AssertTrue}";
        var invalid = new ForceReply(false, null);
        assertViolationContains(invalid, field, messageTemplate);
    }

    @Test
    @DisplayName("Should fail validation when field 'inputFieldPlaceholder' has value above max (@Size)")
    void shouldFailValidationInputFieldPlaceholderAboveMaxConstraint() {
        final int outOfBoundLength = 65;
        final String field = "inputFieldPlaceholder";
        final String messageTemplate = "{jakarta.validation.constraints.Size.message}";
        var invalid = new ForceReply(true, "x".repeat(outOfBoundLength));
        assertViolationContains(invalid, field, messageTemplate);
    }

    @Test
    @DisplayName("Should fail validation when field 'inputFieldPlaceholder' has value below min (@Size)")
    void shouldFailValidationInputFieldPlaceholderBelowMinConstraint() {
        final String field = "inputFieldPlaceholder";
        final String messageTemplate = "{jakarta.validation.constraints.Size.message}";
        var invalid = new ForceReply(true, "");
        assertViolationContains(invalid, field, messageTemplate);
    }

}
