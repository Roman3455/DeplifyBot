package com.roman3455.deplifybot.dto.telegram.api.response;

import com.roman3455.deplifybot.util.ValidationTestSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("User - bean validation")
class UserValidationTest extends ValidationTestSupport {

    @Test
    @DisplayName("Should pass validation for valid full payload")
    void shouldPassValidationFullPayload() {
        var valid = new User(1L, false, "John", "@Doe", "en");
        assertValid(valid);
    }

    @Test
    @DisplayName("Should pass validation for valid required payload")
    void shouldPassValidationRequiredFieldsPayload() {
        var valid = new User(1L, true, "ESBot", null, null);
        assertValid(valid);
    }

    @Test
    @DisplayName("Should fail validation when field 'id' is null (@NotNull)")
    void shouldFailValidationIdNullConstraint() {
        final String field = "id";
        final String messageTemplate = "{jakarta.validation.constraints.NotNull.message}";
        var invalid = new User(null, true, "ESBot", null, null);
        assertViolationContains(invalid, field, messageTemplate);
    }

    @Test
    @DisplayName("Should fail validation when field 'firstName' is null (@NotNull)")
    void shouldFailValidationFirstNameNullConstraint() {
        final String field = "firstName";
        final String messageTemplate = "{jakarta.validation.constraints.NotNull.message}";
        var invalid = new User(1L, true, null, null, null);
        assertViolationContains(invalid, field, messageTemplate);
    }

    @Test
    @DisplayName("Should fail validation when field 'languageCode' has unknown 2 chars value (@ISO6391)")
    void shouldFailValidationLanguageCodeUnexistedConstraint() {
        final String field = "languageCode";
        final String messageTemplate = "{ISO6391.languageCode.message}";
        var invalid = new User(1L, true, "ESBot", null, "xx");
        assertViolationContains(invalid, field, messageTemplate);
    }

    @Test
    @DisplayName("Should fail validation when field 'languageCode' has invalid length (@ISO6391)")
    void shouldFailValidationLanguageCodeInvalidConstraint() {
        final String field = "languageCode";
        final String messageTemplate = "{ISO6391.languageCode.message}";
        var invalid = new User(1L, true, "ESBot", null, "eng");
        assertViolationContains(invalid, field, messageTemplate);
    }

}
