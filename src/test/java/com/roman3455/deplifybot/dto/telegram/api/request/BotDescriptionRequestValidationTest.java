package com.roman3455.deplifybot.dto.telegram.api.request;

import com.roman3455.deplifybot.util.ValidationTestSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Bean Validation of BotDescriptionRequest")
class BotDescriptionRequestValidationTest extends ValidationTestSupport {

    @Test
    @DisplayName("Valid payload passes validation")
    void validPayload() {
        assertValid(
                new BotDescriptionRequest("Description", "en")
        );
    }

    @Test
    @DisplayName("Field 'description' @Size: Value above max should fail")
    void descriptionConstraintAboveMax() {
        final int outOfBoundLength = 513;
        assertViolationContains(
                new BotDescriptionRequest("x".repeat(outOfBoundLength), null),
                "description", "Allowed 'description' length must be at most 512 characters."
        );
    }

    @Test
    @DisplayName("Field 'languageCode' @ISO6391: Unexisted language code should fail")
    void languageCodeConstraintUnexisted() {
        assertViolationContains(
                new BotDescriptionRequest(null, "xx"),
                "languageCode", "Allowed ISO 639-1 'languageCode' length must be exactly 2 characters."
        );
    }

    @Test
    @DisplayName("Field 'languageCode' @ISO6391: Invalid language code length should fail")
    void languageCodeConstraintInvalid() {
        assertViolationContains(
                new BotDescriptionRequest(null, "eng"),
                "languageCode", "Allowed ISO 639-1 'languageCode' length must be exactly 2 characters."
        );
    }

    @Test
    @DisplayName("Fields 'description' and 'languageCode' @AssertTrue: Both null value should fail")
    void invalidWhenBothNull() {
        assertViolationContains(
                new BotDescriptionRequest(null, null),
                "anyProvided", "Either 'description' or 'languageCode' must be provided."
        );
    }

}
