package com.roman3455.deplifybot.dto.telegram.api.ui.button;

import com.roman3455.deplifybot.test_utils.DtoValidationTestSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

import static com.roman3455.deplifybot.dto.telegram.api.TelegramApiDtoBuilder.invalidKeyboardButtonWithBlankText;
import static com.roman3455.deplifybot.dto.telegram.api.TelegramApiDtoBuilder.invalidKeyboardButtonWithInvalidRequestChat;
import static com.roman3455.deplifybot.dto.telegram.api.TelegramApiDtoBuilder.validKeyboardButtonFullPayload;
import static com.roman3455.deplifybot.dto.telegram.api.TelegramApiDtoBuilder.validKeyboardButtonRequiredPayload;

@DisplayName("KeyboardButton - DTO validation")
class KeyboardButtonValidationTest extends DtoValidationTestSupport<KeyboardButton> {

    @Override
    protected Stream<Arguments> provideInvalidArguments() {
        return Stream.of(
                Arguments.of(
                        "field 'text' is blank (@NotBlank)",
                        invalidKeyboardButtonWithBlankText(),
                        "text",
                        MESSAGE_TEMPLATE_NOT_BLANK
                ),
                Arguments.of(
                        "field 'requestChat' has invalid value (@Valid)",
                        invalidKeyboardButtonWithInvalidRequestChat(),
                        "requestChat.requestId",
                        MESSAGE_TEMPLATE_NOT_NULL
                )
        );
    }

    @Override
    protected Stream<Arguments> provideValidArguments() {
        return Stream.of(
                Arguments.of(CASE_NAME_FULL_PAYLOAD, validKeyboardButtonFullPayload()),
                Arguments.of(CASE_NAME_REQUIRED_PAYLOAD, validKeyboardButtonRequiredPayload())
        );
    }

}
