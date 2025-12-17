package com.roman3455.deplifybot.dto.telegram.api.request;

import com.roman3455.deplifybot.test_utils.DtoValidationTestSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

import static com.roman3455.deplifybot.dto.telegram.api.TelegramApiDtoBuilder.validBotCommandScopeFullPayload;
import static com.roman3455.deplifybot.dto.telegram.api.TelegramApiDtoBuilder.validBotCommandScopeRequiredPayload;
import static com.roman3455.deplifybot.dto.telegram.api.TelegramApiDtoBuilder.invalidBotCommandScopeWithNullType;
import static com.roman3455.deplifybot.dto.telegram.api.TelegramApiDtoBuilder.invalidBotCommandScopeWithNullTypeAndChatId;
import static com.roman3455.deplifybot.dto.telegram.api.TelegramApiDtoBuilder
        .invalidBotCommandScopeWithChatIdNotPresentAndTypeIsChat;
import static com.roman3455.deplifybot.dto.telegram.api.TelegramApiDtoBuilder
        .invalidBotCommandScopeWithChatIdPresentAndTypeNotChat;

@DisplayName("BotCommandScope - DTO validation")
class BotCommandScopeValidationTest extends DtoValidationTestSupport<BotCommandScope> {

    private static final String TYPE_FIELD = "type";
    private static final String ASSERT_FIELD = "chatIdConsistentWithType";
    private static final String ASSERT_TRUE_MESSAGE = "{BotCommandScope.isChatIdConsistentWithType.AssertTrue}";

    @Override
    protected Stream<Arguments> provideInvalidArguments() {
        return Stream.of(
                Arguments.of(
                        "field 'type' is null (@NotNull)",
                        invalidBotCommandScopeWithNullType(),
                        TYPE_FIELD,
                        MESSAGE_TEMPLATE_NOT_NULL
                ),
                Arguments.of(
                        "field 'type' and 'chatId' is null (@NotNull)",
                        invalidBotCommandScopeWithNullTypeAndChatId(),
                        TYPE_FIELD,
                        MESSAGE_TEMPLATE_NOT_NULL
                ),
                Arguments.of(
                        "field 'chatId' is not present when 'type' is 'CHAT' (@AssertTrue)'",
                        invalidBotCommandScopeWithChatIdNotPresentAndTypeIsChat(),
                        ASSERT_FIELD,
                        ASSERT_TRUE_MESSAGE
                ),
                Arguments.of(
                        "field 'chatId' is present when 'type' is not 'CHAT' (@AssertTrue)'",
                        invalidBotCommandScopeWithChatIdPresentAndTypeNotChat(),
                        ASSERT_FIELD,
                        ASSERT_TRUE_MESSAGE
                )
        );
    }

    @Override
    protected Stream<Arguments> provideValidArguments() {
        return Stream.of(
                Arguments.of(CASE_NAME_FULL_PAYLOAD, validBotCommandScopeFullPayload()),
                Arguments.of(CASE_NAME_REQUIRED_PAYLOAD, validBotCommandScopeRequiredPayload())
        );
    }

}
