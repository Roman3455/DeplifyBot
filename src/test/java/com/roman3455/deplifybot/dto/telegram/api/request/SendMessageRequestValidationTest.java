package com.roman3455.deplifybot.dto.telegram.api.request;

import com.roman3455.deplifybot.dto.telegram.api.enums.ParseModeType;
import com.roman3455.deplifybot.util.ValidationTestSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("SendMessageRequest - bean validation")
class SendMessageRequestValidationTest extends ValidationTestSupport {

    private static final String NOT_BLANK_MESSAGE_TEMPLATE = "{jakarta.validation.constraints.NotBlank.message}";
    private static final String CHAT_ID = "123";
    private static final String TEXT = "message";

    @Test
    @DisplayName("Should pass validation for valid full payload")
    void shouldPassValidationFullPayload() {
        final long messageThreadId = -13435667;
        SendMessageRequest valid = new SendMessageRequest(
                CHAT_ID,
                messageThreadId,
                TEXT,
                ParseModeType.HTML,
                true,
                true
        );
        assertValid(valid);
    }

    @Test
    @DisplayName("Should pass validation for valid required payload")
    void shouldPassValidationRequiredFieldsPayload() {
        SendMessageRequest valid = new SendMessageRequest(
                CHAT_ID,
                null,
                TEXT,
                null,
                null,
                null
        );
        assertValid(valid);
    }

    @Test
    @DisplayName("Should fail validation when field 'chatId' is null (@NotNull)")
    void shouldFailValidationChatIdNullConstraint() {
        final String field = "chatId";
        final String messageTemplate = "{jakarta.validation.constraints.NotNull.message}";
        var invalid = new SendMessageRequest(
                null,
                null,
                TEXT,
                null,
                null,
                null
        );
        assertViolationContains(invalid, field, messageTemplate);
    }

    @Test
    @DisplayName("Should fail validation when field 'text' is null (@NotBlank)")
    void shouldFailValidationTextNullConstraint() {
        final String field = "text";
        var invalid = new SendMessageRequest(
                CHAT_ID,
                null,
                null,
                null,
                null,
                null
        );
        assertViolationContains(invalid, field, NOT_BLANK_MESSAGE_TEMPLATE);
    }

    @Test
    @DisplayName("Should fail validation when field 'text' is blank (@NotBlank)")
    void shouldFailValidationTextBlankConstraint() {
        final String field = "text";
        var invalid = new SendMessageRequest(
                CHAT_ID,
                null,
                "   ",
                null,
                null,
                null
        );
        assertViolationContains(invalid, field, NOT_BLANK_MESSAGE_TEMPLATE);
    }

    @Test
    @DisplayName("Should fail validation when field 'text' has value above max (@Size)")
    void shouldFailValidationTextAboveMaxConstraint() {
        final String field = "text";
        final String messageTemplate = "{Size.max.message}";
        final int outOfBoundChars = 4097;
        var invalid = new SendMessageRequest(
                CHAT_ID,
                null,
                "x".repeat(outOfBoundChars),
                null,
                null,
                null
        );
        assertViolationContains(invalid, field, messageTemplate);
    }

}
