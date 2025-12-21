package com.roman3455.deplifybot.dto.telegram.api.request;

import com.roman3455.deplifybot.test_utils.DtoValidationTestSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

import static com.roman3455.deplifybot.dto.telegram.api.TelegramApiDtoBuilder.validBotDescriptionRequestFullPayload;
import static com.roman3455.deplifybot.dto.telegram.api.TelegramApiDtoBuilder
        .invalidBotDescriptionRequestWithDescriptionAboveMax;
import static com.roman3455.deplifybot.dto.telegram.api.TelegramApiDtoBuilder
        .invalidBotDescriptionRequestWithUnknownLanguageCode;
import static com.roman3455.deplifybot.dto.telegram.api.TelegramApiDtoBuilder
        .invalidBotDescriptionRequestWithLanguageCodeMoreThenTwoChars;
import static com.roman3455.deplifybot.dto.telegram.api.TelegramApiDtoBuilder
        .invalidBotDescriptionRequestWithLanguageCodeLessThenTwoChars;
import static com.roman3455.deplifybot.dto.telegram.api.TelegramApiDtoBuilder
        .invalidBotDescriptionRequestWithNullDescriptionAndLanguageCode;

@DisplayName("BotDescriptionRequest - DTO validation")
class BotDescriptionRequestValidationTest extends DtoValidationTestSupport<BotDescriptionRequest> {

    private static final String LANGUAGE_CODE_FIELD = "languageCode";

    @Override
    protected Stream<Arguments> provideInvalidArguments() {
        return Stream.of(
                Arguments.of(
                        "field 'description' has value above max (@Size)",
                        invalidBotDescriptionRequestWithDescriptionAboveMax(),
                        "description",
                        MESSAGE_TEMPLATE_MAX_SIZE
                ),
                Arguments.of(
                        "field 'languageCode' has unknown 2 chars value (@ISO6391)",
                        invalidBotDescriptionRequestWithUnknownLanguageCode(),
                        LANGUAGE_CODE_FIELD,
                        MESSAGE_TEMPLATE_ISO6391
                ),
                Arguments.of(
                        "field 'languageCode' has more then 2 chars (@ISO6391)",
                        invalidBotDescriptionRequestWithLanguageCodeMoreThenTwoChars(),
                        LANGUAGE_CODE_FIELD,
                        MESSAGE_TEMPLATE_ISO6391
                ),
                Arguments.of(
                        "field 'languageCode' has less then 2 chars (@ISO6391)",
                        invalidBotDescriptionRequestWithLanguageCodeLessThenTwoChars(),
                        LANGUAGE_CODE_FIELD,
                        MESSAGE_TEMPLATE_ISO6391
                ),
                Arguments.of(
                        "both fields 'description' and 'languageCode' are null (@AssertTrue)",
                        invalidBotDescriptionRequestWithNullDescriptionAndLanguageCode(),
                        "anyProvided",
                        "{BotDescriptionRequest.isAnyProvided.AssertTrue}"
                )
        );
    }

    @Override
    protected Stream<Arguments> provideValidArguments() {
        return Stream.of(
                Arguments.of(CASE_NAME_FULL_PAYLOAD, validBotDescriptionRequestFullPayload())
        );
    }

}
