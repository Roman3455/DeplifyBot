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

import static com.roman3455.deplifybot.dto.telegram.api.TelegramApiDtoBuilder.validChatMemberUpdatedFullPayload;

@ActiveProfiles("test")
@JsonTest
@Import(JacksonConfiguration.class)
@DisplayName("ChatMemberUpdated — JSON serialization & deserialization")
class ChatMemberUpdatedJsonTest extends DtoJsonMarshallingTestSupport<ChatMemberUpdated> {

    private static final String PATH = "/fixture/telegram/response/chat_member_updated/chat_member_updated_";

    @Autowired
    private JacksonTester<ChatMemberUpdated> jsonTester;

    @Override
    protected JacksonTester<ChatMemberUpdated> tester() {
        return jsonTester;
    }

    @Override
    protected Stream<Arguments> provideArguments() {
        return Stream.of(
                Arguments.of(
                        CASE_NAME_FULL_PAYLOAD,
                        validChatMemberUpdatedFullPayload(),
                        PATH + "full.json",
                        List.of("$.oldChatMember", "$.newChatMember")
                )
        );
    }

}
