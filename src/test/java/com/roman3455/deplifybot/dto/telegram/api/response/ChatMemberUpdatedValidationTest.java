package com.roman3455.deplifybot.dto.telegram.api.response;

import com.roman3455.deplifybot.dto.telegram.api.enums.ChatMemberStatusType;
import com.roman3455.deplifybot.dto.telegram.api.enums.ChatType;
import com.roman3455.deplifybot.util.ValidationTestSupport;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Instant;

@DisplayName("ChatMemberUpdated - bean validation")
class ChatMemberUpdatedValidationTest extends ValidationTestSupport {

    private static final String MESSAGE_TEMPLATE = "{jakarta.validation.constraints.NotNull.message}";
    private static final Instant DATE = Instant.ofEpochSecond(1710248593);

    private Chat chat;
    private User from;
    private ChatMember oldChatMember;
    private ChatMember newChatMember;

    @BeforeEach
    void setUp() {
        chat = new Chat(1L, ChatType.PRIVATE, null, null, null, null);
        from = new User(2L, false, "John Doe", null, null);
        oldChatMember = new ChatMember(ChatMemberStatusType.MEMBER, from);
        newChatMember = new ChatMember(ChatMemberStatusType.LEFT, from);
    }

    @Test
    @DisplayName("Should pass validation for valid full payload")
    void shouldPassValidationFullPayload() {
        var valid = new ChatMemberUpdated(chat, from, DATE, oldChatMember, newChatMember);
        assertValid(valid);
    }

    @Test
    @DisplayName("Should fail validation when field 'chat' is null (@NotNull)")
    void shouldFailValidationChatNullConstraint() {
        final String field = "chat";
        var invalid = new ChatMemberUpdated(null, from, DATE, oldChatMember, newChatMember);
        assertViolationContains(invalid, field, MESSAGE_TEMPLATE);
    }

    @Test
    @DisplayName("Should fail validation when field 'from' is null (@NotNull)")
    void shouldFailValidationFromNullConstraint() {
        final String field = "from";
        var invalid = new ChatMemberUpdated(chat, null, DATE, oldChatMember, newChatMember);
        assertViolationContains(invalid, field, MESSAGE_TEMPLATE);
    }

    @Test
    @DisplayName("Should fail validation when field 'date' is null (@NotNull)")
    void shouldFailValidationDateNullConstraint() {
        final String field = "date";
        var invalid = new ChatMemberUpdated(chat, from, null, oldChatMember, newChatMember);
        assertViolationContains(invalid, field, MESSAGE_TEMPLATE);
    }

    @Test
    @DisplayName("Should fail validation when field 'oldChatMember' is null (@NotNull)")
    void shouldFailValidationOldChatMemberNullConstraint() {
        final String field = "oldChatMember";
        var invalid = new ChatMemberUpdated(chat, from, DATE, null, newChatMember);
        assertViolationContains(invalid, field, MESSAGE_TEMPLATE);
    }

    @Test
    @DisplayName("Should fail validation when field 'newChatMember' is null (@NotNull)")
    void shouldFailValidationNewChatMemberNullConstraint() {
        final String field = "newChatMember";
        var invalid = new ChatMemberUpdated(chat, from, DATE, oldChatMember, null);
        assertViolationContains(invalid, field, MESSAGE_TEMPLATE);
    }

}
