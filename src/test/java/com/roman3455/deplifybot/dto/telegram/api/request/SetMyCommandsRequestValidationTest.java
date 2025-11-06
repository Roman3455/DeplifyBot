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

import static org.assertj.core.api.BDDAssertions.thenThrownBy;

@DisplayName("Bean Validation of SetMyCommandsRequest")
class SetMyCommandsRequestValidationTest extends ValidationTestSupport {

    private static final ResourceBundle BUNDLE = ResourceBundle
            .getBundle("i18n/messages", LocaleContextHolder.getLocale());

    @Test
    @DisplayName("Valid full payload passes validation")
    void validPayloadAllFields() {
        var valid = new SetMyCommandsRequest(
                List.of(new MyCommand("c", "d")),
                new BotCommandScope(BotCommandScopeType.DEFAULT, null),
                "en"
        );
        assertValid(valid);
    }

    @Test
    @DisplayName("Field 'commands' @NotEmpty: Empty list should fail")
    void commandsConstraintEmpty() {
        final String field = "commands";
        final String messageTemplate = "{jakarta.validation.constraints.NotEmpty.message}";
        var invalid = new SetMyCommandsRequest(List.of(), null, null);
        assertViolationContains(invalid, field, messageTemplate);
    }

    @Test
    @DisplayName("Field 'commands' @Size: Value above max should fail")
    void commandsConstraintAboveMax() {
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
    @DisplayName("Field 'commands' @Valid: Invalid MyCommand should fail")
    void commandsConstraintInvalidCommand() {
        final String field = "commands[0].command";
        final String messageTemplate = "{jakarta.validation.constraints.NotBlank.message}";
        var invalidCommands = List.of(new MyCommand("  ", "description"));
        var invalid = new SetMyCommandsRequest(invalidCommands, null, null);
        assertViolationContains(invalid, field, messageTemplate);
    }

    @Test
    @DisplayName("Field 'scope' @Valid: Invalid BotCommandScope should fail")
    void scopeConstraintInvalidBotCommandScope() {
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
    @DisplayName("Field 'languageCode' @ISO6391: Unexisted language code should fail")
    void languageCodeConstraintUnexisted() {
        final String field = "languageCode";
        final String messageTemplate = "{ISO6391.languageCode.message}";
        var invalid = new SetMyCommandsRequest(
                List.of(new MyCommand("c", "d")),
                null,
                "xx"
        );
        assertViolationContains(invalid, field, messageTemplate);
    }

    @Test
    @DisplayName("Field 'languageCode' @ISO6391: Invalid language code length should fail")
    void languageCodeConstraintInvalid() {
        final String field = "languageCode";
        final String messageTemplate = "{ISO6391.languageCode.message}";
        var invalid = new SetMyCommandsRequest(
                List.of(new MyCommand("c", "d")),
                null,
                "eng"
        );
        assertViolationContains(invalid, field, messageTemplate);
    }

    @Test
    @DisplayName("Field 'commands' @NotEmpty: Null value should throw IllegalArgumentException")
    void commandsConstraintNullValue() {
        final String field = "commands";
        thenThrownBy(() -> new SetMyCommandsRequest(null, null, null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(BUNDLE.getString("IllegalArgumentException.field.NotNull.bundle").formatted(field));
    }

}
