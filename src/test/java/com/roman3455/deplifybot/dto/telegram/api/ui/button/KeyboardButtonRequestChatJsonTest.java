package com.roman3455.deplifybot.dto.telegram.api.ui.button;

import com.roman3455.deplifybot.configuration.JacksonConfiguration;
import com.roman3455.deplifybot.dto.telegram.api.response.ChatAdministratorRights;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.BDDAssertions.then;

@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@JsonTest
@Import(JacksonConfiguration.class)
@DisplayName("KeyboardButtonRequest — JSON serialization & deserialization")
class KeyboardButtonRequestChatJsonTest {

    @Autowired
    private JacksonTester<KeyboardButtonRequestChat> json;

    private static final String SOURCE = "/fixture/telegram/ui/keyboard_button_request_chat/";
    private static final String FULL_JSON = SOURCE + "keyboard_button_request_chat_full.json";
    private static final String REQUIRED_ONLY_JSON = SOURCE + "keyboard_button_request_chat_required_only.json";

    private KeyboardButtonRequestChat fullPayload;
    private KeyboardButtonRequestChat requiredOnlyPayload;

    @BeforeAll
    void setUp() {
        var chatAdministratorRights = new ChatAdministratorRights(
                true,
                true,
                true,
                false,
                false,
                false,
                false,
                false,
                true,
                true,
                true,
                null,
                null,
                null,
                null,
                null
        );
        fullPayload = new KeyboardButtonRequestChat(
                1L,
                true,
                chatAdministratorRights,
                chatAdministratorRights,
                true,
                true
        );
        requiredOnlyPayload = new KeyboardButtonRequestChat(
                1L,
                true,
                null,
                null,
                null,
                null
        );
    }

    @Test
    @DisplayName("Should serialize full payload object into expected JSON fixture")
    void shouldSerializeFullPayload() throws Exception {
        var serialized = json.write(fullPayload);
        then(serialized).isNotNull()
                .isEqualToJson(new ClassPathResource(FULL_JSON))
                .doesNotHaveJsonPath("$.requestId")
                .doesNotHaveJsonPath("$.chatIsChannel")
                .doesNotHaveJsonPath("$.userAdministratorRights")
                .doesNotHaveJsonPath("$.botAdministratorRights")
                .doesNotHaveJsonPath("$.botIsMember")
                .doesNotHaveJsonPath("$.requestTitle");
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
