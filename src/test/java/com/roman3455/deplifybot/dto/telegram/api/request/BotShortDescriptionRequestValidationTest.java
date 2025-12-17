package com.roman3455.deplifybot.dto.telegram.api.request;

import com.roman3455.deplifybot.test_utils.DtoValidationTestSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

import static com.roman3455.deplifybot.dto.telegram.api.TelegramApiDtoBuilder.validBotShortDescriptionRequestFullPayload;
import static com.roman3455.deplifybot.dto.telegram.api.TelegramApiDtoBuilder
        .invalidBotShortDescriptionRequestWithDescriptionAboveMax;
import static com.roman3455.deplifybot.dto.telegram.api.TelegramApiDtoBuilder
        .invalidBotShortDescriptionRequestWithUnknownLanguageCode;
import static com.roman3455.deplifybot.dto.telegram.api.TelegramApiDtoBuilder
        .invalidBotShortDescriptionRequestWithLanguageCodeMoreThenTwoChars;
import static com.roman3455.deplifybot.dto.telegram.api.TelegramApiDtoBuilder
        .invalidBotShortDescriptionRequestWithLanguageCodeLessThenTwoChars;
import static com.roman3455.deplifybot.dto.telegram.api.TelegramApiDtoBuilder
        .invalidBotShortDescriptionRequestWithNullDescriptionAndLanguageCode;

@DisplayName("BotShortDescriptionRequest - DTO validation")
class BotShortDescriptionRequestValidationTest extends DtoValidationTestSupport<BotShortDescriptionRequest> {

    private static final String LANGUAGE_CODE_FIELD = "languageCode";

    @Override
    protected Stream<Arguments> provideInvalidArguments() {
        return Stream.of(
                Arguments.of(
                        "field 'shortDescription' has value above max (@Size)",
                        invalidBotShortDescriptionRequestWithDescriptionAboveMax(),
                        "shortDescription",
                        MESSAGE_TEMPLATE_MAX_SIZE
                ),
                Arguments.of(
                        "field 'languageCode' has unknown 2 chars value (@ISO6391)",
                        invalidBotShortDescriptionRequestWithUnknownLanguageCode(),
                        LANGUAGE_CODE_FIELD,
                        MESSAGE_TEMPLATE_ISO6391
                ),
                Arguments.of(
                        "field 'languageCode' has more then 2 chars (@ISO6391)",
                        invalidBotShortDescriptionRequestWithLanguageCodeMoreThenTwoChars(),
                        LANGUAGE_CODE_FIELD,
                        MESSAGE_TEMPLATE_ISO6391
                ),
                Arguments.of(
                        "field 'languageCode' has less then 2 chars (@ISO6391)",
                        invalidBotShortDescriptionRequestWithLanguageCodeLessThenTwoChars(),
                        LANGUAGE_CODE_FIELD,
                        MESSAGE_TEMPLATE_ISO6391
                ),
                Arguments.of(
                        "both fields 'shortDescription' and 'languageCode' are null (@AssertTrue)",
                        invalidBotShortDescriptionRequestWithNullDescriptionAndLanguageCode(),
                        "anyProvided",
                        "{BotShortDescriptionRequest.isAnyProvided.AssertTrue}"
                )
        );
    }

    @Override
    protected Stream<Arguments> provideValidArguments() {
        return Stream.of(
                Arguments.of(CASE_NAME_FULL_PAYLOAD, validBotShortDescriptionRequestFullPayload())
        );
    }

}
