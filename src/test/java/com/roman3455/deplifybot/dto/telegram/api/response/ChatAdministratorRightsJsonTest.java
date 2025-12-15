package com.roman3455.deplifybot.dto.telegram.api.response;

import com.roman3455.deplifybot.configuration.JacksonConfiguration;
import com.roman3455.deplifybot.test_utils.DtoJsonMarshallingTestSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.provider.Arguments;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.stream.Stream;

import static com.roman3455.deplifybot.test_utils.TelegramApiDtoBuilder.validChatAdministratorRightsFullPayload;
import static com.roman3455.deplifybot.test_utils.TelegramApiDtoBuilder.validChatAdministratorRightsRequiredPayload;

@ActiveProfiles("test")
@JsonTest
@Import(JacksonConfiguration.class)
@DisplayName("ChatAdministratorRights — JSON serialization & deserialization")
class ChatAdministratorRightsJsonTest extends DtoJsonMarshallingTestSupport<ChatAdministratorRights> {

    private static final String PATH =
            "/fixture/telegram/response/chat_administrator_rights/chat_administrator_rights_";

    @Autowired
    private JacksonTester<ChatAdministratorRights> jsonTester;

    @Override
    protected JacksonTester<ChatAdministratorRights> tester() {
        return jsonTester;
    }

    @Override
    protected Stream<Arguments> provideArguments() {
        return Stream.of(
                Arguments.of(
                        CASE_NAME_FULL_PAYLOAD,
                        validChatAdministratorRightsFullPayload(),
                        PATH + "full.json",
                        List.of(
                                "$.isAnonymous",
                                "$.canManageChat",
                                "$.canDeleteMessages",
                                "$.canManageVideoChats",
                                "$.canRestrictMembers",
                                "$.canPromoteMembers",
                                "$.canChangeInfo",
                                "$.canInviteUsers",
                                "$.canPostStories",
                                "$.canEditStories",
                                "$.canDeleteStories",
                                "$.canPostMessages",
                                "$.canEditMessages",
                                "$.canPinMessages",
                                "$.canManageTopics",
                                "$.canManageDirectMessages"
                        )
                ),
                Arguments.of(
                        CASE_NAME_REQUIRED_PAYLOAD,
                        validChatAdministratorRightsRequiredPayload(),
                        PATH + "required.json",
                        List.of(
                                "$.can_post_messages",
                                "$.can_edit_messages",
                                "$.can_pin_messages",
                                "$.can_manage_topics",
                                "$.can_manage_direct_messages"
                        )
                )
        );
    }

}
