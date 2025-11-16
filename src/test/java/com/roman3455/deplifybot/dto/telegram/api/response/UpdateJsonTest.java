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

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.BDDAssertions.then;

@JsonTest
@Import(JacksonConfiguration.class)
@DisplayName("Update — JSON serialization & deserialization")
class UpdateJsonTest {

    @Autowired
    private JacksonTester<Update> json;

    private static final String SOURCE = "/fixture/telegram/response/update/";
    private static final String MESSAGE_ONLY_JSON = SOURCE + "update_message_only.json";
    private static final String CALLBACK_QUERY_ONLY_JSON = SOURCE + "update_callback_query_only.json";
    private static final String MY_CHAT_MEMBER_JSON = SOURCE + "update_my_chat_member_only.json";

    private Update messageOnlyPayload;
    private Update callbackQueryOnlyPayload;
    private Update myChatMemberOnlyPayload;

    @BeforeEach
    void setUp() {
        final long updateId = 123L;
        final Instant dateTime = Instant.ofEpochSecond(1710248593);
        Chat chat = new Chat(2L, ChatType.SUPERGROUP, null, null, null, null);
        Message message = new Message(1L, null, null, dateTime, chat, null, null, null, null);
        messageOnlyPayload = new Update(updateId, message, null, null);
        final long userId = 456L;
        User user = new User(userId, true, "awesome bot", null, null);
        CallbackQuery callbackQuery = new CallbackQuery("123", user, null, null);
        callbackQueryOnlyPayload = new Update(updateId, null, callbackQuery, null);
        ChatMember oldChatMember = new ChatMember(ChatMemberStatusType.ADMINISTRATOR, user);
        ChatMember newChatMember = new ChatMember(ChatMemberStatusType.MEMBER, user);
        ChatMemberUpdated myChatMember = new ChatMemberUpdated(chat, user, dateTime, oldChatMember, newChatMember);
        myChatMemberOnlyPayload = new Update(updateId, null, null, myChatMember);
    }

    @Test
    @DisplayName("Should serialize message only payload object into expected JSON fixture")
    void shouldSerializeMessageOnlyPayload() throws Exception {
        var serialized = json.write(messageOnlyPayload);
        then(serialized).isNotNull()
                .isEqualToJson(new ClassPathResource(MESSAGE_ONLY_JSON))
                .doesNotHaveJsonPath("$.updateId");
    }

    @Test
    @DisplayName("Should deserialize message only payload JSON fixture into expected object")
    void shouldDeserializeMessageOnlyPayload() throws Exception {
        var deserialized = json.readObject(new ClassPathResource(MESSAGE_ONLY_JSON));
        then(deserialized).isNotNull()
                .isEqualTo(messageOnlyPayload);
        assertThat(deserialized.hasMessage()).isTrue();
        assertThat(deserialized.hasCallbackQuery()).isFalse();
        assertThat(deserialized.hasMyChatMember()).isFalse();
    }

    @Test
    @DisplayName("Should round-trip message only payload object")
    void shouldRoundTripMessageOnlyPayload() throws Exception {
        var serialized = json.write(messageOnlyPayload);
        var deserialized = json.parseObject(serialized.getJson());
        then(deserialized).isNotNull()
                .isEqualTo(messageOnlyPayload);
    }

    @Test
    @DisplayName("Should serialize callback query only payload object into expected JSON fixture")
    void shouldSerializeCallbackQueryOnlyPayload() throws Exception {
        var serialized = json.write(callbackQueryOnlyPayload);
        then(serialized).isNotNull()
                .isEqualToJson(new ClassPathResource(CALLBACK_QUERY_ONLY_JSON))
                .doesNotHaveJsonPath("$.callbackQuery");
    }

    @Test
    @DisplayName("Should deserialize callback query only payload JSON fixture into expected object")
    void shouldDeserializeCallbackQueryOnlyPayload() throws Exception {
        var deserialized = json.readObject(new ClassPathResource(CALLBACK_QUERY_ONLY_JSON));
        then(deserialized).isNotNull()
                .isEqualTo(callbackQueryOnlyPayload);
        assertThat(deserialized.hasMessage()).isFalse();
        assertThat(deserialized.hasCallbackQuery()).isTrue();
        assertThat(deserialized.hasMyChatMember()).isFalse();
    }

    @Test
    @DisplayName("Should round-trip callback query only payload object")
    void shouldRoundTripCallbackQueryOnlyPayload() throws Exception {
        var serialized = json.write(callbackQueryOnlyPayload);
        var deserialized = json.parseObject(serialized.getJson());
        then(deserialized).isNotNull()
                .isEqualTo(callbackQueryOnlyPayload);
    }

    @Test
    @DisplayName("Should serialize my chat member only payload object into expected JSON fixture")
    void shouldSerializeMyChatMemberOnlyPayload() throws Exception {
        var serialized = json.write(myChatMemberOnlyPayload);
        then(serialized).isNotNull()
                .isEqualToJson(new ClassPathResource(MY_CHAT_MEMBER_JSON))
                .doesNotHaveJsonPath("$.myChatMember");
    }

    @Test
    @DisplayName("Should deserialize my chat member only payload JSON fixture into expected object")
    void shouldDeserializeMyChatMemberOnlyPayload() throws Exception {
        var deserialized = json.readObject(new ClassPathResource(MY_CHAT_MEMBER_JSON));
        then(deserialized).isNotNull()
                .isEqualTo(myChatMemberOnlyPayload);
        assertThat(deserialized.hasMessage()).isFalse();
        assertThat(deserialized.hasCallbackQuery()).isFalse();
        assertThat(deserialized.hasMyChatMember()).isTrue();
    }

    @Test
    @DisplayName("Should round-trip my chat member only payload object")
    void shouldRoundTripMyChatMemberOnlyPayload() throws Exception {
        var serialized = json.write(myChatMemberOnlyPayload);
        var deserialized = json.parseObject(serialized.getJson());
        then(deserialized).isNotNull()
                .isEqualTo(myChatMemberOnlyPayload);
    }
}
