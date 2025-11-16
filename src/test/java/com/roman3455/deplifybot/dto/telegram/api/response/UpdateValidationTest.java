package com.roman3455.deplifybot.dto.telegram.api.response;

import com.roman3455.deplifybot.dto.telegram.api.enums.ChatMemberStatusType;
import com.roman3455.deplifybot.dto.telegram.api.enums.ChatType;
import com.roman3455.deplifybot.util.ValidationTestSupport;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Instant;

@DisplayName("Update - bean validation")
class UpdateValidationTest extends ValidationTestSupport {

    private static final long UPDATE_ID = 123L;
    private static final Instant DATE_TIME = Instant.ofEpochSecond(1710248593);

    private Message message;
    private CallbackQuery callbackQuery;
    private ChatMemberUpdated myChatMember;

    @BeforeEach
    void setUp() {
        Chat chat = new Chat(2L, ChatType.SUPERGROUP, null, null, null, null);
        User user = new User(1L, true, "awesome bot", null, null);
        ChatMember oldChatMember = new ChatMember(ChatMemberStatusType.ADMINISTRATOR, user);
        ChatMember newChatMember = new ChatMember(ChatMemberStatusType.MEMBER, user);
        message = new Message(1L, null, null, DATE_TIME, chat, null, null, null, null);
        callbackQuery = new CallbackQuery("123", user, null, null);
        myChatMember = new ChatMemberUpdated(chat, user, DATE_TIME, oldChatMember, newChatMember);
    }

    @Test
    @DisplayName("Should pass validation for valid Message payload")
    void shouldPassValidationMessagePayload() {
        var valid = new Update(UPDATE_ID, message, null, null);
        assertValid(valid);
    }

    @Test
    @DisplayName("Should pass validation for valid CallbackQuery payload")
    void shouldPassValidationCallbackQueryPayload() {
        var valid = new Update(UPDATE_ID, null, callbackQuery, null);
        assertValid(valid);
    }

    @Test
    @DisplayName("Should pass validation for valid ChatMemberUpdated payload")
    void shouldPassValidationChatMemberUpdatedPayload() {
        var valid = new Update(UPDATE_ID, null, null, myChatMember);
        assertValid(valid);
    }

    @Test
    @DisplayName("Should fail validation when field 'updateId' is null (@NotNull)")
    void shouldFailValidationUpdateIdNullConstraint() {
        final String field = "updateId";
        final String messageTemplate = "{jakarta.validation.constraints.NotNull.message}";
        var invalid = new Update(null, message, null, null);
        assertViolationContains(invalid, field, messageTemplate);
    }

    @Test
    @DisplayName("Should fail validation when field 'updateId' has negative value (@Positive)")
    void shouldFailValidationUpdateIdNegativeConstraint() {
        final String field = "updateId";
        final String messageTemplate = "{jakarta.validation.constraints.Positive.message}";
        var invalid = new Update(-1L, null, callbackQuery, null);
        assertViolationContains(invalid, field, messageTemplate);
    }

    @Test
    @DisplayName("Should fail validation when optional fields not present (@AssertTrue)")
    void shouldFailValidationOptionalFieldsNotPresentAssertConstraint() {
        final String field = "anyProvided";
        final String messageTemplate = "{Update.isAnyProvided.AssertTrue}";
        var invalid = new Update(UPDATE_ID, null, null, null);
        assertViolationContains(invalid, field, messageTemplate);
    }

    @Test
    @DisplayName("Should fail validation when present more then one optional field (@AssertTrue)")
    void shouldFailValidationOptionalFieldsMoreThenOneAssertConstraint() {
        final String field = "anyProvided";
        final String messageTemplate = "{Update.isAnyProvided.AssertTrue}";
        var invalid = new Update(UPDATE_ID, message, null, myChatMember);
        assertViolationContains(invalid, field, messageTemplate);
    }

}
