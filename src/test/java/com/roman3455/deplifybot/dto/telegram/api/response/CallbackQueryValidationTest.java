package com.roman3455.deplifybot.dto.telegram.api.response;

import com.roman3455.deplifybot.dto.telegram.api.enums.ChatType;
import com.roman3455.deplifybot.util.ValidationTestSupport;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Instant;

@DisplayName("CallbackQuery - bean validation")
class CallbackQueryValidationTest extends ValidationTestSupport {

    private User user;

    @BeforeEach
    void setUp() {
        final long userId = 456L;
        user = new User(userId, true, "awesome bot", null, null);
    }

    @Test
    @DisplayName("Should pass validation for valid full payload")
    void shouldPassValidationFullPayload() {
        final Instant messageDateTime = Instant.ofEpochSecond(1710248593);
        Chat chat = new Chat(2L, ChatType.SUPERGROUP, null, null, null, null);
        Message message = new Message(1L, null, null, messageDateTime, chat, null, null, null, null);
        var valid = new CallbackQuery("123", user, message, "callback");
        assertValid(valid);
    }

    @Test
    @DisplayName("Should pass validation for valid required payload")
    void shouldPassValidationRequiredFieldsPayload() {
        var valid = new CallbackQuery("123", user, null, null);
        assertValid(valid);
    }

    @Test
    @DisplayName("Should fail validation when field 'id' is null (@NotNull)")
    void shouldFailValidationIdNullConstraint() {
        final String field = "id";
        final String messageTemplate = "{jakarta.validation.constraints.NotNull.message}";
        var invalid = new CallbackQuery(null, user, null, null);
        assertViolationContains(invalid, field, messageTemplate);
    }

    @Test
    @DisplayName("Should fail validation when field 'from' is null (@NotNull)")
    void shouldFailValidationFromNullConstraint() {
        final String field = "from";
        final String messageTemplate = "{jakarta.validation.constraints.NotNull.message}";
        var invalid = new CallbackQuery("23", null, null, null);
        assertViolationContains(invalid, field, messageTemplate);
    }

}
