package com.roman3455.deplifybot.dto.telegram.api.response;

import com.roman3455.deplifybot.dto.telegram.api.enums.ChatType;
import com.roman3455.deplifybot.util.ValidationTestSupport;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Instant;

@DisplayName("Message - bean validation")
class MessageValidationTest extends ValidationTestSupport {

    private static final String MESSAGE_TEMPLATE = "{jakarta.validation.constraints.NotNull.message}";
    private static final Instant DATE = Instant.ofEpochSecond(1710248593);

    private Chat chat;

    @BeforeEach
    void setUp() {
        chat = new Chat(1L, ChatType.PRIVATE, null, null, null, null);
    }

    @Test
    @DisplayName("Should pass validation for valid full payload")
    void shouldPassValidationFullPayload() {
        User from = new User(1L, false, "John", null, null);
        ChatShared chatShared = new ChatShared(1L, 2L, null, null);
        var valid = new Message(1L, 2L, from, DATE, chat, "Text", 1L, 2L, chatShared);
        assertValid(valid);
    }

    @Test
    @DisplayName("Should pass validation for valid required payload")
    void shouldPassValidationRequiredFieldsPayload() {
        var valid = new Message(1L, null, null, DATE, chat, null, null, null, null);
        assertValid(valid);
    }

    @Test
    @DisplayName("Should fail validation when field 'messageId' is null (@NotNull)")
    void shouldFailValidationMessageIdNullConstraint() {
        final String field = "messageId";
        var invalid = new Message(null, null, null, DATE, chat, null, null, null, null);
        assertViolationContains(invalid, field, MESSAGE_TEMPLATE);
    }

    @Test
    @DisplayName("Should fail validation when field 'date' is null (@NotNull)")
    void shouldFailValidationDateNullConstraint() {
        final String field = "date";
        var invalid = new Message(1L, null, null, null, chat, null, null, null, null);
        assertViolationContains(invalid, field, MESSAGE_TEMPLATE);
    }

    @Test
    @DisplayName("Should fail validation when field 'chat' is null (@NotNull)")
    void shouldFailValidationChatNullConstraint() {
        final String field = "chat";
        var invalid = new Message(1L, null, null, DATE, null, null, null, null, null);
        assertViolationContains(invalid, field, MESSAGE_TEMPLATE);
    }

}
