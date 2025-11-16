package com.roman3455.deplifybot.dto.telegram.api.response;

import com.roman3455.deplifybot.dto.telegram.api.enums.ChatType;
import com.roman3455.deplifybot.util.ValidationTestSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Chat - bean validation")
class ChatValidationTest extends ValidationTestSupport {

    @Test
    @DisplayName("Should pass validation for valid full payload")
    void shouldPassValidationFullPayload() {
        var valid = new Chat(1L, ChatType.PRIVATE, "Title", "@name", "John", false);
        assertValid(valid);
    }

    @Test
    @DisplayName("Should pass validation for valid required payload")
    void shouldPassValidationRequiredFieldsPayload() {
        var valid = new Chat(1L, ChatType.PRIVATE, null, null, null, null);
        assertValid(valid);
    }

    @Test
    @DisplayName("Should fail validation when field 'id' is null (@NotNull)")
    void shouldFailValidationIdNullConstraint() {
        final String field = "id";
        final String messageTemplate = "{jakarta.validation.constraints.NotNull.message}";
        var invalid = new Chat(null, ChatType.PRIVATE, null, null, null, null);
        assertViolationContains(invalid, field, messageTemplate);
    }

    @Test
    @DisplayName("Should fail validation when field 'type' is null (@NotNull)")
    void shouldFailValidationTypeNullConstraint() {
        final String field = "type";
        final String messageTemplate = "{jakarta.validation.constraints.NotNull.message}";
        var invalid = new Chat(2L, null, null, null, null, null);
        assertViolationContains(invalid, field, messageTemplate);
    }

}
