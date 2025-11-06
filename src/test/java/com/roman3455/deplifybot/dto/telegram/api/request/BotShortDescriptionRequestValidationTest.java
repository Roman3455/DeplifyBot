package com.roman3455.deplifybot.dto.telegram.api.request;

import com.roman3455.deplifybot.util.ValidationTestSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Bean Validation of BotShortDescriptionRequest")
class BotShortDescriptionRequestValidationTest extends ValidationTestSupport {

    @Test
    @DisplayName("Valid payload passes validation")
    void validPayload() {
        var valid = new BotShortDescriptionRequest("Short description", "en");
        assertValid(valid);
    }

    @Test
    @DisplayName("Field 'shortDescription' @Size: Value above max should fail")
    void shortDescriptionConstraintAboveMax() {
        final int outOfBoundLength = 121;
        final String field = "shortDescription";
        final String messageTemplate = "{Size.max.message}";
        var invalid = new BotShortDescriptionRequest("x".repeat(outOfBoundLength), null);
        assertViolationContains(invalid, field, messageTemplate);
    }

    @Test
    @DisplayName("Field 'languageCode' @ISO6391: Unexisted language code should fail")
    void languageCodeConstraintUnexisted() {
        final String field = "languageCode";
        final String messageTemplate = "{ISO6391.languageCode.message}";
        var invalid = new BotShortDescriptionRequest(null, "xx");
        assertViolationContains(invalid, field, messageTemplate);
    }

    @Test
    @DisplayName("Field 'languageCode' @ISO6391: Invalid language code length should fail")
    void languageCodeConstraintInvalid() {
        final String field = "languageCode";
        final String messageTemplate = "{ISO6391.languageCode.message}";
        var invalid = new BotShortDescriptionRequest(null, "eng");
        assertViolationContains(invalid, field, messageTemplate);
    }

    @Test
    @DisplayName("Fields 'shortDescription' and 'languageCode' @AssertTrue: Both null value should fail")
    void invalidWhenBothNull() {
        final String field = "anyProvided";
        final String messageTemplate = "{BotShortDescriptionRequest.isAnyProvided.AssertTrue}";
        var invalid = new BotShortDescriptionRequest(null, null);
        assertViolationContains(invalid, field, messageTemplate);
    }

}
