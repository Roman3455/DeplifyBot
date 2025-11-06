package com.roman3455.deplifybot.dto.telegram.api.request;

import com.roman3455.deplifybot.dto.telegram.api.enums.BotCommandScopeType;
import com.roman3455.deplifybot.util.ValidationTestSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Bean Validation of BotCommandScope")
class BotCommandScopeValidationTest extends ValidationTestSupport {

    private static final long CHAT_ID = 123L;

    @Test
    @DisplayName("Valid full payload passes validation")
    void validPayloadAllFields() {
        var valid = new BotCommandScope(BotCommandScopeType.CHAT, CHAT_ID);
        assertValid(valid);
    }

    @Test
    @DisplayName("Valid required payload passes validation")
    void validPayloadRequiredFields() {
        var valid = new BotCommandScope(BotCommandScopeType.DEFAULT, null);
        assertValid(valid);
    }

    @Test
    @DisplayName("Field 'type': @NotNull should fail")
    void typeConstraint() {
        final String field = "type";
        final String messageTemplate = "{jakarta.validation.constraints.NotNull.message}";
        var invalidWithAllNullFields = new BotCommandScope(null, null);
        assertViolationContains(invalidWithAllNullFields, field, messageTemplate);
        var invalidWithNonNullChatId = new BotCommandScope(null, CHAT_ID);
        assertViolationContains(invalidWithNonNullChatId, field, messageTemplate);
    }

    @Test
    @DisplayName("Fields 'type' and 'chatId' @AssertTrue: Invalid values should fail")
    void chatIdAssertConstraint() {
        final String field = "chatIdConsistentWithType";
        final String messageTemplate = "{BotCommandScope.isChatIdConsistentWithType.AssertTrue}";
        var invalidChatTypeWithNullChatId = new BotCommandScope(BotCommandScopeType.CHAT, null);
        assertViolationContains(invalidChatTypeWithNullChatId, field, messageTemplate);
        var invalidDefaultTypeWithChatId = new BotCommandScope(BotCommandScopeType.DEFAULT, CHAT_ID);
        assertViolationContains(invalidDefaultTypeWithChatId, field, messageTemplate);
    }

}
