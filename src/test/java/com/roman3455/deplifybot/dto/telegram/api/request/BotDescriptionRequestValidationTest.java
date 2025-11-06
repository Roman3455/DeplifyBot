package com.roman3455.deplifybot.dto.telegram.api.request;

import com.roman3455.deplifybot.util.ValidationTestSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Bean Validation of BotDescriptionRequest")
class BotDescriptionRequestValidationTest extends ValidationTestSupport {

    @Test
    @DisplayName("Valid payload passes validation")
    void validPayload() {
        var valid = new BotDescriptionRequest("Description", "en");
        assertValid(valid);
    }

    @Test
    @DisplayName("Field 'description' @Size: Value above max should fail")
    void descriptionConstraintAboveMax() {
        final int outOfBoundLength = 513;
        final String field = "description";
        final String messageTemplate = "{Size.max.message}";
        var invalid = new BotDescriptionRequest("x".repeat(outOfBoundLength), null);
        assertViolationContains(invalid, field, messageTemplate);
    }

    @Test
    @DisplayName("Field 'languageCode' @ISO6391: Unexisted language code should fail")
    void languageCodeConstraintUnexisted() {
        final String field = "languageCode";
        final String messageTemplate = "{ISO6391.languageCode.message}";
        var invalid = new BotDescriptionRequest(null, "xx");
        assertViolationContains(invalid, field, messageTemplate);
    }

    @Test
    @DisplayName("Field 'languageCode' @ISO6391: Invalid language code length should fail")
    void languageCodeConstraintInvalid() {
        final String field = "languageCode";
        final String messageTemplate = "{ISO6391.languageCode.message}";
        var invalid = new BotDescriptionRequest(null, "eng");
        assertViolationContains(invalid, field, messageTemplate);
    }

    @Test
    @DisplayName("Fields 'description' and 'languageCode' @AssertTrue: Both null value should fail")
    void invalidWhenBothNull() {
        final String field = "anyProvided";
        final String messageTemplate = "{BotDescriptionRequest.isAnyProvided.AssertTrue}";
        var invalid = new BotDescriptionRequest(null, null);
        assertViolationContains(invalid, field, messageTemplate);
    }

}
