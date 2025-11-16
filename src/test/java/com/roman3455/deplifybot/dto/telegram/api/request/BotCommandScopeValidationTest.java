package com.roman3455.deplifybot.dto.telegram.api.request;

import com.roman3455.deplifybot.dto.telegram.api.enums.BotCommandScopeType;
import com.roman3455.deplifybot.util.ValidationTestSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("BotCommandScope - bean validation")
class BotCommandScopeValidationTest extends ValidationTestSupport {

    private static final long CHAT_ID = 123L;

    @Test
    @DisplayName("Should pass validation for valid full payload")
    void shouldPassValidationFullPayload() {
        var valid = new BotCommandScope(BotCommandScopeType.CHAT, CHAT_ID);
        assertValid(valid);
    }

    @Test
    @DisplayName("Should pass validation for valid required payload")
    void shouldPassValidationRequiredFieldsPayload() {
        var valid = new BotCommandScope(BotCommandScopeType.DEFAULT, null);
        assertValid(valid);
    }

    @Test
    @DisplayName("Should fail validation when field 'type' is null (@NotNull)")
    void shouldFailValidationTypeNullConstraint() {
        final String field = "type";
        final String messageTemplate = "{jakarta.validation.constraints.NotNull.message}";
        var invalidWithAllNullFields = new BotCommandScope(null, null);
        assertViolationContains(invalidWithAllNullFields, field, messageTemplate);
        var invalidWithNonNullChatId = new BotCommandScope(null, CHAT_ID);
        assertViolationContains(invalidWithNonNullChatId, field, messageTemplate);
    }

    @Test
    @DisplayName("Should fail validation when field 'type' and 'chatId' has invalid values (@AssertTrue)")
    void shouldFailValidationTypeAndChatIdAssertConstraint() {
        final String field = "chatIdConsistentWithType";
        final String messageTemplate = "{BotCommandScope.isChatIdConsistentWithType.AssertTrue}";
        var invalidChatTypeWithNullChatId = new BotCommandScope(BotCommandScopeType.CHAT, null);
        assertViolationContains(invalidChatTypeWithNullChatId, field, messageTemplate);
        var invalidDefaultTypeWithChatId = new BotCommandScope(BotCommandScopeType.DEFAULT, CHAT_ID);
        assertViolationContains(invalidDefaultTypeWithChatId, field, messageTemplate);
    }

}
