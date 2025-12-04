package com.roman3455.deplifybot.dto.telegram.api.response;

import com.roman3455.deplifybot.configuration.JacksonConfiguration;
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
@DisplayName("ChatAdministratorRights — JSON serialization & deserialization")
class ChatAdministratorRightsJsonTest {

    @Autowired
    private JacksonTester<ChatAdministratorRights> json;

    private static final String SOURCE = "/fixture/telegram/response/chat_administrator_rights/";
    private static final String FULL_JSON = SOURCE + "chat_administrator_rights_full.json";
    private static final String REQUIRED_ONLY_JSON = SOURCE + "chat_administrator_rights_required_only.json";

    private ChatAdministratorRights fullPayload;
    private ChatAdministratorRights requiredOnlyPayload;

    @BeforeAll
    void setUp() {
        fullPayload = new ChatAdministratorRights(
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
                true,
                true,
                true,
                false,
                false
        );
        requiredOnlyPayload = new ChatAdministratorRights(
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
    }

    @Test
    @DisplayName("Should serialize full payload object into expected JSON fixture")
    void shouldSerializeFullPayload() throws Exception {
        var serialized = json.write(fullPayload);
        then(serialized).isNotNull()
                .isEqualToJson(new ClassPathResource(FULL_JSON))
                .doesNotHaveJsonPath("$.isAnonymous")
                .doesNotHaveJsonPath("$.canManageChat")
                .doesNotHaveJsonPath("$.canDeleteMessages")
                .doesNotHaveJsonPath("$.canManageVideoChats")
                .doesNotHaveJsonPath("$.canRestrictMembers")
                .doesNotHaveJsonPath("$.canPromoteMembers")
                .doesNotHaveJsonPath("$.canChangeInfo")
                .doesNotHaveJsonPath("$.canInviteUsers")
                .doesNotHaveJsonPath("$.canPostStories")
                .doesNotHaveJsonPath("$.canEditStories")
                .doesNotHaveJsonPath("$.canDeleteStories")
                .doesNotHaveJsonPath("$.canPostMessages")
                .doesNotHaveJsonPath("$.canEditMessages")
                .doesNotHaveJsonPath("$.canPinMessages")
                .doesNotHaveJsonPath("$.canManageTopics")
                .doesNotHaveJsonPath("$.canManageDirectMessages");
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
