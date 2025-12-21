package com.roman3455.deplifybot.dto.telegram.api.response;

import com.roman3455.deplifybot.test_utils.DtoValidationTestSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

import static com.roman3455.deplifybot.dto.telegram.api.TelegramApiDtoBuilder.validChatAdministratorRightsFullPayload;
import static com.roman3455.deplifybot.dto.telegram.api.TelegramApiDtoBuilder.validChatAdministratorRightsRequiredPayload;
import static com.roman3455.deplifybot.dto.telegram.api.TelegramApiDtoBuilder
        .invalidChatAdministratorRightsWithNullIsAnonymous;
import static com.roman3455.deplifybot.dto.telegram.api.TelegramApiDtoBuilder
        .invalidChatAdministratorRightsWithNullCanManageChat;
import static com.roman3455.deplifybot.dto.telegram.api.TelegramApiDtoBuilder
        .invalidChatAdministratorRightsWithNullCanDeleteMessages;
import static com.roman3455.deplifybot.dto.telegram.api.TelegramApiDtoBuilder
        .invalidChatAdministratorRightsWithNullManageVideoChats;
import static com.roman3455.deplifybot.dto.telegram.api.TelegramApiDtoBuilder
        .invalidChatAdministratorRightsWithNullCanRestrictMembers;
import static com.roman3455.deplifybot.dto.telegram.api.TelegramApiDtoBuilder
        .invalidChatAdministratorRightsWithNullCanPromoteMembers;
import static com.roman3455.deplifybot.dto.telegram.api.TelegramApiDtoBuilder
        .invalidChatAdministratorRightsWithNullCanChangeInfo;
import static com.roman3455.deplifybot.dto.telegram.api.TelegramApiDtoBuilder
        .invalidChatAdministratorRightsWithNullCanInviteUsers;
import static com.roman3455.deplifybot.dto.telegram.api.TelegramApiDtoBuilder
        .invalidChatAdministratorRightsWithNullCanPostStories;
import static com.roman3455.deplifybot.dto.telegram.api.TelegramApiDtoBuilder
        .invalidChatAdministratorRightsWithNullCanEditStories;
import static com.roman3455.deplifybot.dto.telegram.api.TelegramApiDtoBuilder
        .invalidChatAdministratorRightsWithNullCanDeleteStories;

@DisplayName("ChatAdministratorRights - DTO validation")
class ChatAdministratorRightsValidationTest extends DtoValidationTestSupport<ChatAdministratorRights> {

    @Override
    protected Stream<Arguments> provideInvalidArguments() {
        return Stream.of(
                Arguments.of(
                        "field 'isAnonymous' is null (@NotNull)",
                        invalidChatAdministratorRightsWithNullIsAnonymous(),
                        "isAnonymous",
                        MESSAGE_TEMPLATE_NOT_NULL
                ),
                Arguments.of(
                        "field 'canManageChat' is null (@NotNull)",
                        invalidChatAdministratorRightsWithNullCanManageChat(),
                        "canManageChat",
                        MESSAGE_TEMPLATE_NOT_NULL
                ),
                Arguments.of(
                        "field 'canDeleteMessages' is null (@NotNull)",
                        invalidChatAdministratorRightsWithNullCanDeleteMessages(),
                        "canDeleteMessages",
                        MESSAGE_TEMPLATE_NOT_NULL
                ),
                Arguments.of(
                        "field 'canManageVideoChats' is null (@NotNull)",
                        invalidChatAdministratorRightsWithNullManageVideoChats(),
                        "canManageVideoChats",
                        MESSAGE_TEMPLATE_NOT_NULL
                ),
                Arguments.of(
                        "field 'canRestrictMembers' is null (@NotNull)",
                        invalidChatAdministratorRightsWithNullCanRestrictMembers(),
                        "canRestrictMembers",
                        MESSAGE_TEMPLATE_NOT_NULL
                ),
                Arguments.of(
                        "field 'canPromoteMembers' is null (@NotNull)",
                        invalidChatAdministratorRightsWithNullCanPromoteMembers(),
                        "canPromoteMembers",
                        MESSAGE_TEMPLATE_NOT_NULL
                ),
                Arguments.of(
                        "field 'canChangeInfo' is null (@NotNull)",
                        invalidChatAdministratorRightsWithNullCanChangeInfo(),
                        "canChangeInfo",
                        MESSAGE_TEMPLATE_NOT_NULL
                ),
                Arguments.of(
                        "field 'canInviteUsers' is null (@NotNull)",
                        invalidChatAdministratorRightsWithNullCanInviteUsers(),
                        "canInviteUsers",
                        MESSAGE_TEMPLATE_NOT_NULL
                ),
                Arguments.of(
                        "field 'canPostStories' is null (@NotNull)",
                        invalidChatAdministratorRightsWithNullCanPostStories(),
                        "canPostStories",
                        MESSAGE_TEMPLATE_NOT_NULL
                ),
                Arguments.of(
                        "field 'canEditStories' is null (@NotNull)",
                        invalidChatAdministratorRightsWithNullCanEditStories(),
                        "canEditStories",
                        MESSAGE_TEMPLATE_NOT_NULL
                ),
                Arguments.of(
                        "field 'canDeleteStories' is null (@NotNull)",
                        invalidChatAdministratorRightsWithNullCanDeleteStories(),
                        "canDeleteStories",
                        MESSAGE_TEMPLATE_NOT_NULL
                )
        );
    }

    @Override
    protected Stream<Arguments> provideValidArguments() {
        return Stream.of(
                Arguments.of(CASE_NAME_FULL_PAYLOAD, validChatAdministratorRightsFullPayload()),
                Arguments.of(CASE_NAME_REQUIRED_PAYLOAD, validChatAdministratorRightsRequiredPayload())
        );
    }

}
