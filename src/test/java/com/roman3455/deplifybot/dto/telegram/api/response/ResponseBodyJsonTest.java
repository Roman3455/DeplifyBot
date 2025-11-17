package com.roman3455.deplifybot.dto.telegram.api.response;

import com.roman3455.deplifybot.configuration.JacksonConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.ActiveProfiles;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.BDDAssertions.then;

@ActiveProfiles("test")
@JsonTest
@Import(JacksonConfiguration.class)
@DisplayName("ResponseBody — JSON serialization & deserialization")
class ResponseBodyJsonTest {

    @Autowired
    private JacksonTester<ResponseBody<?>> json;

    private static final String SOURCE = "/fixture/telegram/response/response_body/";
    private static final String SUCCESS = SOURCE + "response_body_success.json";
    private static final String ERROR_429 = SOURCE + "response_body_error_429_with_retry.json";
    private static final String ERROR_404 = SOURCE + "response_body_error_404_with_migrate.json";

    private ResponseBody<Map<String, Object>> successPayload;
    private ResponseBody<Map<String, Object>> error429Payload;
    private ResponseBody<Map<String, Object>> error404Payload;

    @BeforeEach
    void setUp() {
        successPayload = new ResponseBody<>(
                true,
                Map.of("message_id", 1, "text", "hello"),
                null,
                null,
                null
        );
        final int tooManyRequests = 429;
        error429Payload = new ResponseBody<>(
                false,
                null,
                tooManyRequests,
                "Too Many Requests: retry after 1",
                new ResponseBody.ResponseParameters(null, 1)
        );
        final int badRequest = 400;
        final long chatId = -1001987654321L;
        error404Payload = new ResponseBody<>(
                false,
                null,
                badRequest,
                "Bad Request: group chat was upgraded to a supergroup chat",
                new ResponseBody.ResponseParameters(chatId, null)
        );
    }

    @Test
    @DisplayName("Should serialize success payload object into expected JSON fixture")
    void shouldSerializeSuccessPayload() throws Exception {
        var serialized = json.write(successPayload);
        then(serialized).isNotNull()
                .isEqualToJson(new ClassPathResource(SUCCESS));
    }

    @Test
    @DisplayName("Should deserialize success payload JSON fixture into expected object")
    void shouldDeserializeSuccessPayload() throws Exception {
        var deserialized = json.readObject(new ClassPathResource(SUCCESS));
        then(deserialized).isNotNull()
                .isEqualTo(successPayload);
        assertThat(deserialized.isError()).isFalse();
        assertThat(deserialized.hasParameters()).isFalse();
    }

    @Test
    @DisplayName("Should round-trip success payload JSON fixture")
    void shouldRoundTripSuccessPayload() throws Exception {
        var deserialized = json.readObject(new ClassPathResource(SUCCESS));
        var serialized = json.write(deserialized);
        then(serialized).isNotNull()
                .isEqualToJson(new ClassPathResource(SUCCESS));
    }

    @Test
    @DisplayName("Should serialize error 429 payload object with 'retryAfter' into expected JSON fixture")
    void shouldSerializeError429Payload() throws Exception {
        var serialized = json.write(error429Payload);
        then(serialized).isNotNull()
                .isEqualToJson(new ClassPathResource(ERROR_429))
                .doesNotHaveJsonPath("$.errorCode")
                .doesNotHaveJsonPath("$.parameters.retryAfter");
    }

    @Test
    @DisplayName("Should deserialize error 429 payload JSON fixture into expected object")
    void shouldDeserializeError429Payload() throws Exception {
        var deserialized = json.readObject(new ClassPathResource(ERROR_429));
        then(deserialized).isNotNull()
                .isEqualTo(error429Payload);
        assertThat(deserialized.isError()).isTrue();
        assertThat(deserialized.hasParameters()).isTrue();
    }

    @Test
    @DisplayName("Should serialize error 404 payload object with 'migrateToChatId' into expected JSON fixture")
    void shouldSerializeError404Payload() throws Exception {
        var serialized = json.write(error404Payload);
        then(serialized).isNotNull()
                .isEqualToJson(new ClassPathResource(ERROR_404))
                .doesNotHaveJsonPath("$.parameters.migrateToChatId");
    }

    @Test
    @DisplayName("Should deserialize error 404 payload JSON fixture into expected object")
    void shouldDeserializeError404Payload() throws Exception {
        var deserialized = json.readObject(new ClassPathResource(ERROR_404));
        then(deserialized).isNotNull()
                .isEqualTo(error404Payload);
        assertThat(deserialized.isError()).isTrue();
        assertThat(deserialized.hasParameters()).isTrue();
    }

    @Test
    @DisplayName("Should round-trip error 404 payload object")
    void shouldRoundTripError404OnlyPayload() throws Exception {
        var serialized = json.write(error404Payload);
        var deserialized = json.parseObject(serialized.getJson());
        then(deserialized).isNotNull()
                .isEqualTo(error404Payload);
    }

}
