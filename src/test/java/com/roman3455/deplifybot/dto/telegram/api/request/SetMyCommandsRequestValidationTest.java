package com.roman3455.deplifybot.dto.telegram.api.request;

import com.roman3455.deplifybot.test_utils.DtoValidationTestSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

import static com.roman3455.deplifybot.dto.telegram.api.TelegramApiDtoBuilder.validSetMyCommandsRequestFullPayload;
import static com.roman3455.deplifybot.dto.telegram.api.TelegramApiDtoBuilder.validSetMyCommandsRequestRequiredPayload;
import static com.roman3455.deplifybot.dto.telegram.api.TelegramApiDtoBuilder
        .invalidSetMyCommandsRequestWithEmptyCommandsList;
import static com.roman3455.deplifybot.dto.telegram.api.TelegramApiDtoBuilder
        .invalidSetMyCommandsRequestWithSizeAboveMaxCommandsList;
import static com.roman3455.deplifybot.dto.telegram.api.TelegramApiDtoBuilder
        .invalidSetMyCommandsRequestWithInvalidMyCommands;
import static com.roman3455.deplifybot.dto.telegram.api.TelegramApiDtoBuilder.invalidSetMyCommandsRequestWithInvalidScope;
import static com.roman3455.deplifybot.dto.telegram.api.TelegramApiDtoBuilder
        .invalidSetMyCommandsRequestWithUnknownLanguageCode;
import static com.roman3455.deplifybot.dto.telegram.api.TelegramApiDtoBuilder
        .invalidSetMyCommandsRequestWithLanguageCodeMoreThenTwoChars;
import static com.roman3455.deplifybot.dto.telegram.api.TelegramApiDtoBuilder
        .invalidSetMyCommandsRequestWithLanguageCodeLessThenTwoChars;

@DisplayName("SetMyCommandsRequest - DTO validation")
class SetMyCommandsRequestValidationTest extends DtoValidationTestSupport<SetMyCommandsRequest> {

    private static final String COMMANDS_FIELD = "commands";
    private static final String LANGUAGE_CODE_FIELD = "languageCode";

    @Override
    protected Stream<Arguments> provideInvalidArguments() {
        return Stream.of(
                Arguments.of(
                        "field 'commands' has empty <List> (@NotEmpty)",
                        invalidSetMyCommandsRequestWithEmptyCommandsList(),
                        COMMANDS_FIELD,
                        MESSAGE_TEMPLATE_NOT_EMPTY
                ),
                Arguments.of(
                        "field 'commands' has value above max (@Size)",
                        invalidSetMyCommandsRequestWithSizeAboveMaxCommandsList(),
                        COMMANDS_FIELD,
                        MESSAGE_TEMPLATE_MAX_SIZE
                ),
                Arguments.of(
                        "field 'commands' has invalid object (@Valid)",
                        invalidSetMyCommandsRequestWithInvalidMyCommands(),
                        "commands[0].command",
                        MESSAGE_TEMPLATE_NOT_BLANK
                ),
                Arguments.of(
                        "field 'scope' has invalid object (@Valid)",
                        invalidSetMyCommandsRequestWithInvalidScope(),
                        "scope.type",
                        MESSAGE_TEMPLATE_NOT_NULL
                ),
                Arguments.of(
                        "field 'languageCode' has unknown 2 chars value (@ISO6391)",
                        invalidSetMyCommandsRequestWithUnknownLanguageCode(),
                        LANGUAGE_CODE_FIELD,
                        MESSAGE_TEMPLATE_ISO6391
                ),
                Arguments.of(
                        "field 'languageCode' has more then 2 chars (@ISO6391)",
                        invalidSetMyCommandsRequestWithLanguageCodeMoreThenTwoChars(),
                        LANGUAGE_CODE_FIELD,
                        MESSAGE_TEMPLATE_ISO6391
                ),
                Arguments.of(
                        "field 'languageCode' has less then 2 chars (@ISO6391)",
                        invalidSetMyCommandsRequestWithLanguageCodeLessThenTwoChars(),
                        LANGUAGE_CODE_FIELD,
                        MESSAGE_TEMPLATE_ISO6391
                )
        );
    }

    @Override
    protected Stream<Arguments> provideValidArguments() {
        return Stream.of(
                Arguments.of(CASE_NAME_FULL_PAYLOAD, validSetMyCommandsRequestFullPayload()),
                Arguments.of(CASE_NAME_REQUIRED_PAYLOAD, validSetMyCommandsRequestRequiredPayload())
        );
    }

}
