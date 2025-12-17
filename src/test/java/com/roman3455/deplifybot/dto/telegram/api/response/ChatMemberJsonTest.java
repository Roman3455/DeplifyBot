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

import static com.roman3455.deplifybot.dto.telegram.api.TelegramApiDtoBuilder.validChatMemberFullPayload;

@ActiveProfiles("test")
@JsonTest
@Import(JacksonConfiguration.class)
@DisplayName("ChatMember — JSON serialization & deserialization")
class ChatMemberJsonTest extends DtoJsonMarshallingTestSupport<ChatMember> {

    private static final String PATH = "/fixture/telegram/response/chat_member/chat_member_";

    @Autowired
    private JacksonTester<ChatMember> jsonTester;

    @Override
    protected JacksonTester<ChatMember> tester() {
        return jsonTester;
    }

    @Override
    protected Stream<Arguments> provideArguments() {
        return Stream.of(
                Arguments.of(
                        CASE_NAME_FULL_PAYLOAD,
                        validChatMemberFullPayload(),
                        PATH + "full.json",
                        List.of()
                )
        );
    }

}
