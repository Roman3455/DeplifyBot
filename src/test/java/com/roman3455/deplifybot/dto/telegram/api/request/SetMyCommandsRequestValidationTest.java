package com.roman3455.deplifybot.dto.telegram.api.request;

import com.roman3455.deplifybot.dto.telegram.api.enums.BotCommandScopeType;
import com.roman3455.deplifybot.util.ValidationTestSupport;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.i18n.LocaleContextHolder;

import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.IntStream;

@DisplayName("SetMyCommandsRequest - bean validation")
class SetMyCommandsRequestValidationTest extends ValidationTestSupport {

    @Test
    @DisplayName("Should pass validation for valid full payload")
    void shouldPassValidationFullPayload() {
        var valid = new SetMyCommandsRequest(
                List.of(new MyCommand("c", "d")),
                new BotCommandScope(BotCommandScopeType.DEFAULT, null),
                "en"
        );
        assertValid(valid);
    }

    @Test
    @DisplayName("Should fail validation when field 'commands' has empty <List> (@NotEmpty)")
    void shouldFailValidationCommandsEmptyConstraint() {
        final String field = "commands";
        final String messageTemplate = "{jakarta.validation.constraints.NotEmpty.message}";
        var invalid = new SetMyCommandsRequest(List.of(), null, null);
        assertViolationContains(invalid, field, messageTemplate);
    }

    @Test
    @DisplayName("Should fail validation when field 'commands' has value above max (@Size)")
    void shouldFailValidationCommandsAboveMaxConstraint() {
        final int outOfBoundValue = 101;
        final String field = "commands";
        final String messageTemplate = "{Size.max.message}";
        var commandList = IntStream.range(0, outOfBoundValue)
                .mapToObj(i -> new MyCommand(
                        RandomStringUtils.insecure().nextAlphabetic(2).toLowerCase(),
                        RandomStringUtils.insecure().nextAlphabetic(2).toLowerCase())
                )
                .toList();
        var invalid = new SetMyCommandsRequest(commandList, null, null);
        assertViolationContains(invalid, field, messageTemplate);
    }

    @Test
    @DisplayName("Should fail validation when field 'commands' has invalid object (@Valid)")
    void shouldFailValidationCommandsInvalidCommandConstraint() {
        final String field = "commands[0].command";
        final String messageTemplate = "{jakarta.validation.constraints.NotBlank.message}";
        var invalidCommands = List.of(new MyCommand("  ", "description"));
        var invalid = new SetMyCommandsRequest(invalidCommands, null, null);
        assertViolationContains(invalid, field, messageTemplate);
    }

    @Test
    @DisplayName("Should fail validation when field 'scope' has invalid object (@Valid)")
    void shouldFailValidationScopeInvalidBotCommandScopeConstraint() {
        final String field = "scope.type";
        final String messageTemplate = "{jakarta.validation.constraints.NotNull.message}";
        var invalidBotCommandScope = new BotCommandScope(null, null);
        var invalid = new SetMyCommandsRequest(
                List.of(new MyCommand("c", "d")),
                invalidBotCommandScope,
                null
        );
        assertViolationContains(invalid, field, messageTemplate);
    }

    @Test
    @DisplayName("Should fail validation when field 'languageCode' has unknown 2 chars value (@ISO6391)")
    void shouldFailValidationLanguageCodeUnexistedConstraint() {
        final String field = "languageCode";
        final String messageTemplate = "{ISO6391.validation.constraints.message}";
        var invalid = new SetMyCommandsRequest(
                List.of(new MyCommand("c", "d")),
                null,
                "xx"
        );
        assertViolationContains(invalid, field, messageTemplate);
    }

    @Test
    @DisplayName("Should fail validation when field 'languageCode' has invalid length (@ISO6391)")
    void shouldFailValidationLanguageCodeInvalidConstraint() {
        final String field = "languageCode";
        final String messageTemplate = "{ISO6391.validation.constraints.message}";
        var invalid = new SetMyCommandsRequest(
                List.of(new MyCommand("c", "d")),
                null,
                "eng"
        );
        assertViolationContains(invalid, field, messageTemplate);
    }

}
