package com.roman3455.deplifybot.dto.telegram.api.request;

import com.roman3455.deplifybot.util.ValidationTestSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Bean Validation of BotShortDescriptionRequest")
class BotShortDescriptionRequestValidationTest extends ValidationTestSupport {

    @Test
    @DisplayName("Valid payload passes validation")
    void validPayload() {
        assertValid(
                new BotShortDescriptionRequest("Short description", "en")
        );
    }

    @Test
    @DisplayName("Field 'shortDescription' @Size: Value above max should fail")
    void shortDescriptionConstraintAboveMax() {
        final int outOfBoundLength = 121;
        assertViolationContains(
                new BotShortDescriptionRequest("x".repeat(outOfBoundLength), null),
                "shortDescription", "Allowed 'shortDescription' length must be at most 120 characters."
        );
    }

    @Test
    @DisplayName("Field 'languageCode' @ISO6391: Unexisted language code should fail")
    void languageCodeConstraintUnexisted() {
        assertViolationContains(
                new BotShortDescriptionRequest(null, "xx"),
                "languageCode", "Allowed ISO 639-1 'languageCode' length must be exactly 2 characters."
        );
    }

    @Test
    @DisplayName("Field 'languageCode' @ISO6391: Invalid language code length should fail")
    void languageCodeConstraintInvalid() {
        assertViolationContains(
                new BotShortDescriptionRequest(null, "eng"),
                "languageCode", "Allowed ISO 639-1 'languageCode' length must be exactly 2 characters."
        );
    }

    @Test
    @DisplayName("Fields 'shortDescription' and 'languageCode' @AssertTrue: Both null value should fail")
    void invalidWhenBothNull() {
        assertViolationContains(
                new BotShortDescriptionRequest(null, null),
                "anyProvided", "Either 'shortDescription' or 'languageCode' must be provided."
        );
    }

}
