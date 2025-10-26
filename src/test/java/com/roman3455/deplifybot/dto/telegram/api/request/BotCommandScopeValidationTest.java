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
        assertValid(
                new BotCommandScope(BotCommandScopeType.CHAT, CHAT_ID)
        );
    }

    @Test
    @DisplayName("Valid required payload passes validation")
    void validPayloadRequiredFields() {
        assertValid(
                new BotCommandScope(BotCommandScopeType.DEFAULT, null)
        );
    }

    @Test
    @DisplayName("Field 'type': @NotNull should fail")
    void typeConstraint() {
        final String field = "type";
        final String messagePart = "Field 'type' is required.";
        assertViolationContains(
                new BotCommandScope(null, null),
                field, messagePart
        );
        assertViolationContains(
                new BotCommandScope(null, CHAT_ID),
                field, messagePart
        );
    }

    @Test
    @DisplayName("Fields 'type' and 'chatId' @AssertTrue: Invalid values should fail")
    void chatIdAssertConstraint() {
        final String field = "chatIdConsistentWithType";
        final String messagePart = "'chatId' must be provided if 'type' is 'CHAT', and must be null otherwise.";
        assertViolationContains(
                new BotCommandScope(BotCommandScopeType.CHAT, null),
                field, messagePart
        );
        assertViolationContains(
                new BotCommandScope(BotCommandScopeType.DEFAULT, CHAT_ID),
                field, messagePart
        );
    }

}
