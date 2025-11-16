package com.roman3455.deplifybot.dto.telegram.api.request;

import com.roman3455.deplifybot.configuration.JacksonConfiguration;
import com.roman3455.deplifybot.dto.telegram.api.enums.UpdateType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.ClassPathResource;

import java.util.List;

import static org.assertj.core.api.BDDAssertions.then;

@JsonTest
@Import(JacksonConfiguration.class)
@DisplayName("SetWebhookRequest — JSON serialization & deserialization")
class SetWebhookRequestJsonTest {

    @Autowired
    private JacksonTester<SetWebhookRequest> json;

    private static final String SOURCE = "/fixture/telegram/request/set_webhook_request/";
    private static final String FULL_JSON = SOURCE + "set_webhook_request_full.json";
    private static final String URL_ONLY_JSON = SOURCE + "set_webhook_request_url_only.json";

    private SetWebhookRequest fullPayload;
    private SetWebhookRequest urlOnlyPayload;

    @BeforeEach
    void setUp() {
        final int httpConnections = 80;
        final String url = "https://example.com/webhook";
        fullPayload = new SetWebhookRequest(
                url,
                httpConnections,
                List.of(UpdateType.MESSAGE, UpdateType.CALLBACK_QUERY),
                true,
                "_Token-123"
        );
        urlOnlyPayload = new SetWebhookRequest(
                url,
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
                .doesNotHaveJsonPath("$.maxConnections")
                .doesNotHaveJsonPath("$.allowedUpdates")
                .doesNotHaveJsonPath("$.dropPendingUpdates")
                .doesNotHaveJsonPath("$.secretToken");
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
    @DisplayName("Should serialize 'url'-only payload object into expected JSON fixture")
    void shouldSerializeUrlOnlyPayload() throws Exception {
        var serialized = json.write(urlOnlyPayload);
        then(serialized).isNotNull()
                .isEqualToJson(new ClassPathResource(URL_ONLY_JSON));
    }

    @Test
    @DisplayName("Should deserialize 'url'-only payload JSON fixture into expected object")
    void shouldDeserializeUrlOnlyPayload() throws Exception {
        var deserialized = json.readObject(new ClassPathResource(URL_ONLY_JSON));
        then(deserialized).isNotNull()
                .isEqualTo(urlOnlyPayload);
    }

    @Test
    @DisplayName("Should round-trip 'url'-only payload object")
    void shouldRoundTripUrlOnlyPayload() throws Exception {
        var serialized = json.write(urlOnlyPayload);
        var deserialized = json.parseObject(serialized.getJson());
        then(deserialized).isNotNull()
                .isEqualTo(urlOnlyPayload);
    }

}
