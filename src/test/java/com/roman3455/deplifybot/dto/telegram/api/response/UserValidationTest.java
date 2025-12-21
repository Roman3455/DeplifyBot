package com.roman3455.deplifybot.dto.telegram.api.response;

import com.roman3455.deplifybot.test_utils.DtoValidationTestSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

import static com.roman3455.deplifybot.dto.telegram.api.TelegramApiDtoBuilder.validUserFullPayload;
import static com.roman3455.deplifybot.dto.telegram.api.TelegramApiDtoBuilder.validUserRequiredPayload;
import static com.roman3455.deplifybot.dto.telegram.api.TelegramApiDtoBuilder.invalidUserWithNullId;
import static com.roman3455.deplifybot.dto.telegram.api.TelegramApiDtoBuilder.invalidUserWithNullFirstName;
import static com.roman3455.deplifybot.dto.telegram.api.TelegramApiDtoBuilder.invalidUserWithUnknownLanguageCode;
import static com.roman3455.deplifybot.dto.telegram.api.TelegramApiDtoBuilder.invalidUserLanguageCodeMoreThenTwoChars;
import static com.roman3455.deplifybot.dto.telegram.api.TelegramApiDtoBuilder.invalidUserWithLanguageCodeLessThenTwoChars;

@DisplayName("User - DTO validation")
class UserValidationTest extends DtoValidationTestSupport<User> {

    private static final String LANGUAGE_CODE_FIELD = "languageCode";

    @Override
    protected Stream<Arguments> provideInvalidArguments() {
        return Stream.of(
                Arguments.of(
                        "field 'id' is null (@NotNull)",
                        invalidUserWithNullId(),
                        "id",
                        MESSAGE_TEMPLATE_NOT_NULL
                ),
                Arguments.of(
                        "field 'firstName' is null (@NotNull)",
                        invalidUserWithNullFirstName(),
                        "firstName",
                        MESSAGE_TEMPLATE_NOT_NULL
                ),
                Arguments.of(
                        "field 'languageCode' has unknown 2 chars value (@ISO6391)",
                        invalidUserWithUnknownLanguageCode(),
                        LANGUAGE_CODE_FIELD,
                        MESSAGE_TEMPLATE_ISO6391
                ),
                Arguments.of(
                        "field 'languageCode' has more then 2 chars length (@ISO6391)",
                        invalidUserLanguageCodeMoreThenTwoChars(),
                        LANGUAGE_CODE_FIELD,
                        MESSAGE_TEMPLATE_ISO6391
                ),
                Arguments.of(
                        "field 'languageCode' has less then 2 chars length (@ISO6391)",
                        invalidUserWithLanguageCodeLessThenTwoChars(),
                        LANGUAGE_CODE_FIELD,
                        MESSAGE_TEMPLATE_ISO6391
                )
        );
    }

    @Override
    protected Stream<Arguments> provideValidArguments() {
        return Stream.of(
                Arguments.of(CASE_NAME_FULL_PAYLOAD, validUserFullPayload()),
                Arguments.of(CASE_NAME_REQUIRED_PAYLOAD, validUserRequiredPayload())
        );
    }

}
