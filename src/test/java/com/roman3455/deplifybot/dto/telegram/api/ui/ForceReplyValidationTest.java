package com.roman3455.deplifybot.dto.telegram.api.ui;

import com.roman3455.deplifybot.test_utils.DtoValidationTestSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

import static com.roman3455.deplifybot.dto.telegram.api.TelegramApiDtoBuilder.invalidForceReplyWithFalseForceReply;
import static com.roman3455.deplifybot.dto.telegram.api.TelegramApiDtoBuilder.invalidForceReplyWithSizeAboveMaxPlaceholder;
import static com.roman3455.deplifybot.dto.telegram.api.TelegramApiDtoBuilder.invalidForceReplyWithSizeBelowMinPlaceholder;
import static com.roman3455.deplifybot.dto.telegram.api.TelegramApiDtoBuilder.validForceReplyFullPayload;
import static com.roman3455.deplifybot.dto.telegram.api.TelegramApiDtoBuilder.validForceReplyRequiredPayload;

@DisplayName("ForceReply - DTO validation")
class ForceReplyValidationTest extends DtoValidationTestSupport<ForceReply> {

    private static final String PLACEHOLDER_FIELD = "inputFieldPlaceholder";

    @Override
    protected Stream<Arguments> provideInvalidArguments() {
        return Stream.of(
                Arguments.of(
                        "field 'forceReply' is false (@AssertTrue)",
                        invalidForceReplyWithFalseForceReply(),
                        "forceReply",
                        "{ForceReply.forceReplyIsTrue.AssertTrue}"
                ),
                Arguments.of(
                        "field 'inputFieldPlaceholder' has value below min (@Size)",
                        invalidForceReplyWithSizeBelowMinPlaceholder(),
                        PLACEHOLDER_FIELD,
                        MESSAGE_TEMPLATE_SIZE
                ),
                Arguments.of(
                        "field 'inputFieldPlaceholder' has value above max (@Size)",
                        invalidForceReplyWithSizeAboveMaxPlaceholder(),
                        PLACEHOLDER_FIELD,
                        MESSAGE_TEMPLATE_SIZE
                )
        );
    }

    @Override
    protected Stream<Arguments> provideValidArguments() {
        return Stream.of(
                Arguments.of(CASE_NAME_FULL_PAYLOAD, validForceReplyFullPayload()),
                Arguments.of(CASE_NAME_REQUIRED_PAYLOAD, validForceReplyRequiredPayload())
        );
    }

}
