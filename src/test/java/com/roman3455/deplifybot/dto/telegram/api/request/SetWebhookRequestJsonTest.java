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
    @DisplayName("Serializes full payload with all fields present")
    void serializeFullPayload() throws Exception {
        var actual = json.write(fullPayload);
        then(actual).isNotNull();
        then(actual).isEqualToJson(new ClassPathResource(FULL_JSON));
        then(actual).doesNotHaveJsonPath("$.maxConnections");
        then(actual).doesNotHaveJsonPath("$.allowedUpdates");
        then(actual).doesNotHaveJsonPath("$.dropPendingUpdates");
        then(actual).doesNotHaveJsonPath("$.secretToken");
    }

    @Test
    @DisplayName("Deserializes full payload fixture into populated fields")
    void deserializeFullPayload() throws Exception {
        var actual = json.readObject(FULL_JSON);
        then(actual).isNotNull();
        then(actual).isEqualTo(fullPayload);
    }

    @Test
    @DisplayName("Serializes payload with 'url' only (omitting optional fields)")
    void serializeUrlOnlyPayload() throws Exception {
        var actual = json.write(urlOnlyPayload);
        then(actual).isNotNull();
        then(actual).isEqualToJson(new ClassPathResource(URL_ONLY_JSON));
    }

    @Test
    @DisplayName("Deserializes payload with 'url' only")
    void deserializeUrlOnlyPayload() throws Exception {
        var actual = json.readObject(URL_ONLY_JSON);
        then(actual).isNotNull();
        then(actual).isEqualTo(urlOnlyPayload);
    }

}
