package com.roman3455.deplifybot.dto.telegram.api.response;

import com.roman3455.deplifybot.dto.telegram.api.enums.ChatMemberStatusType;
import com.roman3455.deplifybot.util.ValidationTestSupport;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("ChatMember - bean validation")
class ChatMemberValidationTest extends ValidationTestSupport {

    private User user;

    @BeforeEach
    void setUp() {
        user = new User(1L, false, "John", null, null);
    }

    @Test
    @DisplayName("Should pass validation for valid full payload")
    void shouldPassValidationFullPayload() {
        var valid = new ChatMember(ChatMemberStatusType.LEFT, user);
        assertValid(valid);
    }

    @Test
    @DisplayName("Should fail validation when field 'status' is null (@NotNull)")
    void shouldFailValidationStatusNullConstraint() {
        final String field = "status";
        final String messageTemplate = "{jakarta.validation.constraints.NotNull.message}";
        var invalid = new ChatMember(null, user);
        assertViolationContains(invalid, field, messageTemplate);
    }

    @Test
    @DisplayName("Should fail validation when field 'user' is null (@NotNull)")
    void shouldFailValidationUserNullConstraint() {
        final String field = "user";
        final String messageTemplate = "{jakarta.validation.constraints.NotNull.message}";
        var invalid = new ChatMember(ChatMemberStatusType.MEMBER, null);
        assertViolationContains(invalid, field, messageTemplate);
    }

}
