package com.roman3455.deplifybot.dto.telegram.api.request;

import com.roman3455.deplifybot.dto.telegram.api.enums.BotCommandScopeType;
import com.roman3455.deplifybot.util.ValidationTestSupport;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.IntStream;

import static org.assertj.core.api.BDDAssertions.thenThrownBy;

@DisplayName("Bean Validation of SetMyCommandsRequest")
class SetMyCommandsRequestValidationTest extends ValidationTestSupport {

    @Test
    @DisplayName("Valid full payload passes validation")
    void validPayloadAllFields() {
        var req = new SetMyCommandsRequest(
                List.of(
                        new MyCommand("start", "Start command"),
                        new MyCommand("main", "Main menu")
                ),
                new BotCommandScope(BotCommandScopeType.DEFAULT, null),
                "en"
        );
        assertValid(req);
    }

    @Test
    @DisplayName("Field 'commands' @NotEmpty: Empty list should fail")
    void commandsConstraintEmpty() {
        var req = new SetMyCommandsRequest(List.of(), null, null);
        assertViolationContains(req, "commands", "Field 'commands' is required and cannot be empty.");
    }

    @Test
    @DisplayName("Field 'commands' @NotEmpty: Null value should throw IllegalArgumentException")
    void commandsConstraintNullValue() {
        thenThrownBy(() -> new SetMyCommandsRequest(null, null, null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Field 'commands' cannot be null");
    }

    @Test
    @DisplayName("Field 'commands' @Size: Value above max should fail")
    void commandsConstraintAboveMax() {
        final int outOfBoundValue = 101;
        var commandList = IntStream.range(0, outOfBoundValue)
                .mapToObj(i -> new MyCommand(
                        RandomStringUtils.insecure().nextAlphabetic(2).toLowerCase(),
                        RandomStringUtils.insecure().nextAlphabetic(2).toLowerCase())
                )
                .toList();
        var req = new SetMyCommandsRequest(commandList, null, null);
        assertViolationContains(req, "commands", "At most 100 commands allowed.");
    }

    @Test
    @DisplayName("Field 'commands' @Valid: Invalid MyCommand should fail")
    void commandsConstraintInvalidCommand() {
        var invalidCommands = List.of(new MyCommand("  ", "description"));
        var req = new SetMyCommandsRequest(invalidCommands, null, null);
        assertViolationContains(req, "commands[0].command", "Field 'command' is required.");
    }

    @Test
    @DisplayName("Field 'scope' @Valid: Invalid BotCommandScope should fail")
    void scopeConstraintInvalidBotCommandScope() {
        var invalidBotCommandScope = new BotCommandScope(null, null);
        var req = new SetMyCommandsRequest(
                List.of(new MyCommand("c", "d")),
                invalidBotCommandScope,
                null
        );
        assertViolationContains(req, "scope.type", "Field 'type' is required.");
    }

    @Test
    @DisplayName("Field 'languageCode' @ISO6391: Unexisted language code should fail")
    void languageCodeConstraintUnexisted() {
        var req = new SetMyCommandsRequest(
                List.of(new MyCommand("c", "d")),
                null,
                "xx"
        );
        assertViolationContains(req,
                "languageCode", "Allowed ISO 639-1 'languageCode' length must be exactly 2 characters."
        );
    }

    @Test
    @DisplayName("Field 'languageCode' @ISO6391: Invalid language code length should fail")
    void languageCodeConstraintInvalid() {
        var req = new SetMyCommandsRequest(
                List.of(new MyCommand("c", "d")),
                null,
                "eng"
        );
        assertViolationContains(req,
                "languageCode", "Allowed ISO 639-1 'languageCode' length must be exactly 2 characters."
        );
    }

}
