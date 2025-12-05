package com.roman3455.deplifybot.dto.telegram.api.request;

import com.roman3455.deplifybot.test_utils.DtoValidationTestSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

import static com.roman3455.deplifybot.test_utils.TelegramApiDtoBuilder.validSendMessageRequestFullPayload;
import static com.roman3455.deplifybot.test_utils.TelegramApiDtoBuilder.validSendMessageRequestRequiredPayload;
import static com.roman3455.deplifybot.test_utils.TelegramApiDtoBuilder.invalidSendMessageRequestWithNullChatId;
import static com.roman3455.deplifybot.test_utils.TelegramApiDtoBuilder.invalidSendMessageRequestWithBlankText;
import static com.roman3455.deplifybot.test_utils.TelegramApiDtoBuilder.invalidSendMessageRequestWithSizeAboveMaxText;
import static com.roman3455.deplifybot.test_utils.TelegramApiDtoBuilder.invalidSendMessageRequestWithInvalidReplyMarkup;

@DisplayName("SendMessageRequest - DTO validation")
class SendMessageRequestValidationTest extends DtoValidationTestSupport<SendMessageRequest> {

    private static final String TEXT_FIELD = "text";

    @Override
    protected Stream<Arguments> provideInvalidArguments() {
        return Stream.of(
                Arguments.of(
                        "field 'chatId' is null (@NotNull)",
                        invalidSendMessageRequestWithNullChatId(),
                        "chatId",
                        MESSAGE_TEMPLATE_NOT_NULL
                ),
                Arguments.of(
                        "field 'text' is blank (@NotBlank)",
                        invalidSendMessageRequestWithBlankText(),
                        TEXT_FIELD,
                        MESSAGE_TEMPLATE_NOT_BLANK
                ),
                Arguments.of(
                        "field 'text' has value above max (@Size)",
                        invalidSendMessageRequestWithSizeAboveMaxText(),
                        TEXT_FIELD,
                        MESSAGE_TEMPLATE_MAX_SIZE
                ),
                Arguments.of(
                        "field 'replyMarkup' has invalid ReplyMarkup (@Valid)",
                        invalidSendMessageRequestWithInvalidReplyMarkup(),
                        "replyMarkup.removeKeyboard",
                        "{ReplyKeyboardRemove.removeKeyboardIsTrue.AssertTrue}"
                )
        );
    }

    @Override
    protected Stream<Arguments> provideValidArguments() {
        return Stream.of(
                Arguments.of(CASE_NAME_FULL_PAYLOAD, validSendMessageRequestFullPayload()),
                Arguments.of(CASE_NAME_REQUIRED_PAYLOAD, validSendMessageRequestRequiredPayload())
        );
    }

}
