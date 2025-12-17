package com.roman3455.deplifybot.dto.telegram.api.request;

import com.roman3455.deplifybot.test_utils.DtoValidationTestSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

import static com.roman3455.deplifybot.dto.telegram.api.TelegramApiDtoBuilder.validMyCommandFullPayload;
import static com.roman3455.deplifybot.dto.telegram.api.TelegramApiDtoBuilder.invalidMyCommandWithBlankCommand;
import static com.roman3455.deplifybot.dto.telegram.api.TelegramApiDtoBuilder.invalidMyCommandWithSizeAboveMaxCommand;
import static com.roman3455.deplifybot.dto.telegram.api.TelegramApiDtoBuilder.invalidMyCommandWithMismatchCommandPattern;
import static com.roman3455.deplifybot.dto.telegram.api.TelegramApiDtoBuilder.invalidMyCommandWithBlankDescription;
import static com.roman3455.deplifybot.dto.telegram.api.TelegramApiDtoBuilder.invalidMyCommandWithSizeAboveMaxDescription;

@DisplayName("MyCommand - DTO validation")
class MyCommandValidationTest extends DtoValidationTestSupport<MyCommand> {

    private static final String COMMAND_FIELD = "command";
    private static final String DESCRIPTION_FIELD = "description";

    @Override
    protected Stream<Arguments> provideInvalidArguments() {
        return Stream.of(
                Arguments.of(
                        "field 'command' is blank (@NotBlank)",
                        invalidMyCommandWithBlankCommand(),
                        COMMAND_FIELD,
                        MESSAGE_TEMPLATE_NOT_BLANK
                ),
                Arguments.of(
                        "field 'command' has value above max (@Size)",
                        invalidMyCommandWithSizeAboveMaxCommand(),
                        COMMAND_FIELD,
                        MESSAGE_TEMPLATE_MAX_SIZE
                ),
                Arguments.of(
                        "field 'command' mismatch pattern (@Pattern)",
                        invalidMyCommandWithMismatchCommandPattern(),
                        COMMAND_FIELD,
                        "{MyCommand.command.Pattern.message}"
                ),
                Arguments.of(
                        "field 'description' is blank (@NotBlank)",
                        invalidMyCommandWithBlankDescription(),
                        DESCRIPTION_FIELD,
                        MESSAGE_TEMPLATE_NOT_BLANK
                ),
                Arguments.of(
                        "field 'description' has value above max (@Size)",
                        invalidMyCommandWithSizeAboveMaxDescription(),
                        DESCRIPTION_FIELD,
                        MESSAGE_TEMPLATE_MAX_SIZE
                )
        );
    }

    @Override
    protected Stream<Arguments> provideValidArguments() {
        return Stream.of(
                Arguments.of(CASE_NAME_FULL_PAYLOAD, validMyCommandFullPayload())
        );
    }

}
