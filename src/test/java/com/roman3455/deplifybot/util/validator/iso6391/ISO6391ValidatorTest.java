package com.roman3455.deplifybot.util.validator.iso6391;

import com.roman3455.deplifybot.util.ValidationTestSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Bean Validation of ISO6391Validator")
class ISO6391ValidatorTest extends ValidationTestSupport {

    private record TestDto(@ISO6391 String languageCode) {
    }

    @Test
    @DisplayName("Should allow null language code")
    void shouldAllowNullLanguageCode() {
        assertValid(new TestDto(null));
    }

    @Test
    @DisplayName("Should allow empty language code")
    void shouldAllowEmptyLanguageCode() {
        assertValid(new TestDto(""));
    }

    @Test
    @DisplayName("Should accept valid ISO 639-1 codes regardless of case")
    void shouldAcceptValidLanguageCodes() {
        assertValid(new TestDto("en"));
        assertValid(new TestDto("RU"));
    }

    @Test
    @DisplayName("Should reject invalid language codes")
    void shouldRejectInvalidLanguageCodes() {
        final String field = "languageCode";
        final String messagePart = "Invalid ISO 639-1 language code";
        assertViolationContains(new TestDto("x"), field, messagePart);
        assertViolationContains(new TestDto("eng"), field, messagePart);
        assertViolationContains(new TestDto("zz"), field, messagePart);
    }

}
