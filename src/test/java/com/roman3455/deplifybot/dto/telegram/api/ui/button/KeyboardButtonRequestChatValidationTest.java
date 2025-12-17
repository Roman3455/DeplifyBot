package com.roman3455.deplifybot.dto.telegram.api.ui.button;

import com.roman3455.deplifybot.test_utils.DtoValidationTestSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

import static com.roman3455.deplifybot.dto.telegram.api.TelegramApiDtoBuilder
        .invalidKeyboardButtonRequestChatWithInvalidBotAdministratorRights;
import static com.roman3455.deplifybot.dto.telegram.api.TelegramApiDtoBuilder
        .invalidKeyboardButtonRequestChatWithInvalidUserAdministratorRights;
import static com.roman3455.deplifybot.dto.telegram.api.TelegramApiDtoBuilder
        .invalidKeyboardButtonRequestChatWithNullRequestId;
import static com.roman3455.deplifybot.dto.telegram.api.TelegramApiDtoBuilder.validKeyboardButtonRequestChatFullPayload;
import static com.roman3455.deplifybot.dto.telegram.api.TelegramApiDtoBuilder.validKeyboardButtonRequestChatRequiredPayload;

@DisplayName("KeyboardButtonRequestChat - bean validation")
class KeyboardButtonRequestChatValidationTest extends DtoValidationTestSupport<KeyboardButtonRequestChat> {

    @Override
    protected Stream<Arguments> provideInvalidArguments() {
        return Stream.of(
                Arguments.of(
                        "field 'requestId' is null (@NotNull)",
                        invalidKeyboardButtonRequestChatWithNullRequestId(),
                        "requestId",
                        MESSAGE_TEMPLATE_NOT_NULL
                ),
                Arguments.of(
                        "field 'userAdministratorRights' has invalid value (@Valid)",
                        invalidKeyboardButtonRequestChatWithInvalidUserAdministratorRights(),
                        "userAdministratorRights.canChangeInfo",
                        MESSAGE_TEMPLATE_NOT_NULL
                ),
                Arguments.of(
                        "field 'botAdministratorRights' has invalid value (@Valid)",
                        invalidKeyboardButtonRequestChatWithInvalidBotAdministratorRights(),
                        "botAdministratorRights.canChangeInfo",
                        MESSAGE_TEMPLATE_NOT_NULL
                )
        );
    }

    @Override
    protected Stream<Arguments> provideValidArguments() {
        return Stream.of(
                Arguments.of(CASE_NAME_FULL_PAYLOAD, validKeyboardButtonRequestChatFullPayload()),
                Arguments.of(CASE_NAME_REQUIRED_PAYLOAD, validKeyboardButtonRequestChatRequiredPayload())
        );
    }

}
