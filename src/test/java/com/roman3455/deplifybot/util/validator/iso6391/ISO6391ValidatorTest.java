package com.roman3455.deplifybot.util.validator.iso6391;

import com.roman3455.deplifybot.util.ValidationTestSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Bean Validation of ISO6391Validator")
class ISO6391ValidatorTest extends ValidationTestSupport {

    record TestDto(@ISO6391 String languageCode) {
    }

    @Test
    @DisplayName("Allows null language code")
    void allowsNullLanguageCode() {
        assertValid(new TestDto(null));
    }

    @Test
    @DisplayName("Allows empty language code")
    void allowsEmptyLanguageCode() {
        assertValid(new TestDto(""));
    }

    @Test
    @DisplayName("Accepts valid ISO 639-1 codes regardless of case")
    void acceptsValidLanguageCodes() {
        assertValid(new TestDto("en"));
        assertValid(new TestDto("RU"));
    }

    @Test
    @DisplayName("Rejects invalid language codes")
    void rejectsInvalidLanguageCodes() {
        final String field = "languageCode";
        final String messagePart = "Invalid ISO 639-1 language code";
        assertViolationContains(new TestDto("x"), field, messagePart);
        assertViolationContains(new TestDto("eng"), field, messagePart);
        assertViolationContains(new TestDto("zz"), field, messagePart);
    }

}
