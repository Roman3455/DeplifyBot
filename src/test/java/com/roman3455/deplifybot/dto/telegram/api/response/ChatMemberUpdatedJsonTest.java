package com.roman3455.deplifybot.dto.telegram.api.response;

import com.roman3455.deplifybot.configuration.JacksonConfiguration;
import com.roman3455.deplifybot.dto.telegram.api.enums.ChatMemberStatusType;
import com.roman3455.deplifybot.dto.telegram.api.enums.ChatType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.ClassPathResource;

import java.time.Instant;

import static org.assertj.core.api.BDDAssertions.then;

@JsonTest
@Import(JacksonConfiguration.class)
@DisplayName("ChatMemberUpdated — JSON serialization & deserialization")
class ChatMemberUpdatedJsonTest {

    @Autowired
    private JacksonTester<ChatMemberUpdated> json;

    private static final String SOURCE = "/fixture/telegram/response/chat_member_updated/";
    private static final String FULL_JSON = SOURCE + "chat_member_updated_full.json";

    private ChatMemberUpdated fullPayload;

    @BeforeEach
    void setUp() {
        final Instant dateTime = Instant.ofEpochSecond(1710248593);
        final long chatId = 123L;
        final long userId = 456L;
        var chat = new Chat(chatId, ChatType.SUPERGROUP, null, null, null, null);
        var user = new User(userId, false, "John", null, null);
        var oldChatMember = new ChatMember(ChatMemberStatusType.ADMINISTRATOR, user);
        var newChatMember = new ChatMember(ChatMemberStatusType.MEMBER, user);
        fullPayload = new ChatMemberUpdated(chat, user, dateTime, oldChatMember, newChatMember);
    }

    @Test
    @DisplayName("Should serialize full payload object into expected JSON fixture")
    void shouldSerializeFullPayload() throws Exception {
        var serialized = json.write(fullPayload);
        then(serialized).isNotNull()
                .isEqualToJson(new ClassPathResource(FULL_JSON))
                .doesNotHaveJsonPath("$.oldChatMember")
                .doesNotHaveJsonPath("$.newChatMember");
    }

    @Test
    @DisplayName("Should deserialize full payload JSON fixture into expected object")
    void shouldDeserializeFullPayload() throws Exception {
        var deserialized = json.readObject(new ClassPathResource(FULL_JSON));
        then(deserialized).isNotNull()
                .isEqualTo(fullPayload);
    }

    @Test
    @DisplayName("Should round-trip full payload JSON fixture")
    void shouldRoundTripFullPayload() throws Exception {
        var deserialized = json.readObject(new ClassPathResource(FULL_JSON));
        var serialized = json.write(deserialized);
        then(serialized).isNotNull()
                .isEqualToJson(new ClassPathResource(FULL_JSON));
    }

}
