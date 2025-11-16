package com.roman3455.deplifybot.dto.telegram.api.response;

import com.roman3455.deplifybot.util.ValidationTestSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("ChatShared - bean validation")
class ChatSharedValidationTest extends ValidationTestSupport {

    @Test
    @DisplayName("Should pass validation for valid full payload")
    void shouldPassValidationFullPayload() {
        var valid = new ChatShared(1L, 2L, "Title", "@username");
        assertValid(valid);
    }

    @Test
    @DisplayName("Should pass validation for valid required payload")
    void shouldPassValidationRequiredFieldsPayload() {
        var valid = new ChatShared(1L, 2L, null, null);
        assertValid(valid);
    }

    @Test
    @DisplayName("Should fail validation when field 'requestId' is null (@NotNull)")
    void shouldFailValidationRequestIdNullConstraint() {
        final String field = "requestId";
        final String messageTemplate = "{jakarta.validation.constraints.NotNull.message}";
        var invalid = new ChatShared(null, 2L, null, null);
        assertViolationContains(invalid, field, messageTemplate);
    }

    @Test
    @DisplayName("Should fail validation when field 'chatId' is null (@NotNull)")
    void shouldFailValidationChatIdNullConstraint() {
        final String field = "chatId";
        final String messageTemplate = "{jakarta.validation.constraints.NotNull.message}";
        var invalid = new ChatShared(1L, null, null, null);
        assertViolationContains(invalid, field, messageTemplate);
    }

}
