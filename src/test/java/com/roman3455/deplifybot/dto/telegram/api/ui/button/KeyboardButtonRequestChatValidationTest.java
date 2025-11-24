package com.roman3455.deplifybot.dto.telegram.api.ui.button;

import com.roman3455.deplifybot.dto.telegram.api.response.ChatAdministratorRights;
import com.roman3455.deplifybot.util.ValidationTestSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("KeyboardButtonRequestChat - bean validation")
class KeyboardButtonRequestChatValidationTest extends ValidationTestSupport {

    @Test
    @DisplayName("Should pass validation for valid full payload")
    void shouldPassValidationFullPayload() {
        var chatAdministratorRights = new ChatAdministratorRights(
                true,
                true,
                true,
                false,
                false,
                false,
                false,
                false,
                true,
                true,
                true,
                null,
                null,
                null,
                null,
                null
        );
        var valid = new KeyboardButtonRequestChat(
                1L,
                true,
                chatAdministratorRights,
                chatAdministratorRights,
                true,
                true
        );
        assertValid(valid);
    }

    @Test
    @DisplayName("Should pass validation for valid required only payload")
    void shouldPassValidationRequiredOnlyPayload() {
        var valid = new KeyboardButtonRequestChat(
                1L,
                true,
                null,
                null,
                null,
                null
        );
        assertValid(valid);
    }

    @Test
    @DisplayName("Should fail validation when field 'requestId' is null (@NotNull)")
    void shouldFailValidationWhenFieldRequestIdIsNull() {
        final String field = "requestId";
        final String messageTemplate = "{jakarta.validation.constraints.NotNull.message}";
        var invalid = new KeyboardButtonRequestChat(
                null,
                true,
                null,
                null,
                null,
                null
        );
        assertViolationContains(invalid, field, messageTemplate);
    }

}
