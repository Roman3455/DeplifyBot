package com.roman3455.deplifybot.dto.telegram.api.ui.button;

import com.roman3455.deplifybot.test_utils.DtoValidationTestSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

import static com.roman3455.deplifybot.dto.telegram.api.TelegramApiDtoBuilder.validCopyTextButtonFullPayload;
import static com.roman3455.deplifybot.dto.telegram.api.TelegramApiDtoBuilder.invalidCopyTextButtonWithBlankText;
import static com.roman3455.deplifybot.dto.telegram.api.TelegramApiDtoBuilder.invalidCopyTextButtonWithSizeAboveMaxText;

@DisplayName("CopyTextButton - DTO validation")
class CopyTextButtonValidationTest extends DtoValidationTestSupport<CopyTextButton> {

    private static final String TEXT_FIELD = "text";

    @Override
    protected Stream<Arguments> provideInvalidArguments() {
        return Stream.of(
                Arguments.of(
                        "field 'text' has blank value (@NotBlank)",
                        invalidCopyTextButtonWithBlankText(),
                        TEXT_FIELD,
                        MESSAGE_TEMPLATE_NOT_BLANK
                ),
                Arguments.of(
                        "field 'text' has value above max (@Size)",
                        invalidCopyTextButtonWithSizeAboveMaxText(),
                        TEXT_FIELD,
                        MESSAGE_TEMPLATE_MAX_SIZE
                )
        );
    }

    @Override
    protected Stream<Arguments> provideValidArguments() {
        return Stream.of(Arguments.of(CASE_NAME_FULL_PAYLOAD, validCopyTextButtonFullPayload()));
    }

}
