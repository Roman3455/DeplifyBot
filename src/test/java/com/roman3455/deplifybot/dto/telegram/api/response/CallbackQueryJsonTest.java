package com.roman3455.deplifybot.dto.telegram.api.response;

import com.roman3455.deplifybot.configuration.JacksonConfiguration;
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
@DisplayName("CallbackQuery — JSON serialization & deserialization")
class CallbackQueryJsonTest {

    @Autowired
    private JacksonTester<CallbackQuery> json;

    private static final String SOURCE = "/fixture/telegram/response/callback_query/";
    private static final String FULL_JSON = SOURCE + "callback_query_full.json";
    private static final String REQUIRED_ONLY_JSON = SOURCE + "callback_query_required_only.json";

    private CallbackQuery fullPayload;
    private CallbackQuery requiredOnlyPayload;

    @BeforeEach
    void setUp() {
        final String callbackQueryId = "123";
        final long userId = 456L;
        final Instant messageDateTime = Instant.ofEpochSecond(1710248593);
        User user = new User(userId, true, "awesome bot", null, null);
        Chat chat = new Chat(2L, ChatType.SUPERGROUP, null, null, null, null);
        Message message = new Message(1L, null, null, messageDateTime, chat, null, null, null, null);
        fullPayload = new CallbackQuery(callbackQueryId, user, message, "callback");
        requiredOnlyPayload = new CallbackQuery(callbackQueryId, user, null, null);
    }

    @Test
    @DisplayName("Should serialize full payload object into expected JSON fixture")
    void shouldSerializeFullPayload() throws Exception {
        var serialized = json.write(fullPayload);
        then(serialized).isNotNull()
                .isEqualToJson(new ClassPathResource(FULL_JSON));

    }

    @Test
    @DisplayName("Should deserialize full payload JSON fixture into expected object")
    void shouldDeserializeFullPayload() throws Exception {
        var deserialized = json.readObject(new ClassPathResource(FULL_JSON));
        then(deserialized).isNotNull()
                .isEqualTo(fullPayload);
        assertThat(deserialized.hasMessage()).isTrue();
        assertThat(deserialized.hasData()).isTrue();
    }

    @Test
    @DisplayName("Should round-trip full payload JSON fixture")
    void shouldRoundTripFullPayload() throws Exception {
        var deserialized = json.readObject(new ClassPathResource(FULL_JSON));
        var serialized = json.write(deserialized);
        then(serialized).isNotNull()
                .isEqualToJson(new ClassPathResource(FULL_JSON));
    }

    @Test
    @DisplayName("Should serialize required only payload object into expected JSON fixture")
    void shouldSerializeRequiredOnlyPayload() throws Exception {
        var serialized = json.write(requiredOnlyPayload);
        then(serialized).isNotNull()
                .isEqualToJson(new ClassPathResource(REQUIRED_ONLY_JSON));
    }

    @Test
    @DisplayName("Should deserialize required only payload JSON fixture into expected object")
    void shouldDeserializeRequiredOnlyPayload() throws Exception {
        var deserialized = json.readObject(new ClassPathResource(REQUIRED_ONLY_JSON));
        then(deserialized).isNotNull()
                .isEqualTo(requiredOnlyPayload);
        assertThat(deserialized.hasMessage()).isFalse();
        assertThat(deserialized.hasData()).isFalse();
    }

    @Test
    @DisplayName("Should round-trip required only payload object")
    void shouldRoundTripRequiredOnlyPayload() throws Exception {
        var serialized = json.write(requiredOnlyPayload);
        var deserialized = json.parseObject(serialized.getJson());
        then(deserialized).isNotNull()
                .isEqualTo(requiredOnlyPayload);
    }

}
