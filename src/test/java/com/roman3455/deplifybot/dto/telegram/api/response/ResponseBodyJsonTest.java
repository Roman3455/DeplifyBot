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

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.BDDAssertions.then;

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
    @DisplayName("Serializes success payload")
    void serializeSuccessPayload() throws Exception {
        var actual = json.write(successPayload);
        then(actual).isNotNull();
        then(actual).isEqualToJson(new ClassPathResource(SUCCESS));
    }

    @Test
    @DisplayName("Deserializes success payload into populated fields")
    void deserializeSuccessPayload() throws Exception {
        var actual = json.readObject(SUCCESS);
        then(actual).isNotNull();
        then(actual).isEqualTo(successPayload);
        assertThat(actual.isError()).isFalse();
        assertThat(actual.hasParameters()).isFalse();
    }

    @Test
    @DisplayName("Serializes error 429 payload with 'retryAfter' parameter")
    void serializeError429Payload() throws Exception {
        var actual = json.write(error429Payload);
        then(actual).isNotNull();
        then(actual).isEqualToJson(new ClassPathResource(ERROR_429));
        then(actual).doesNotHaveJsonPath("$.errorCode");
    }

    @Test
    @DisplayName("Deserializes error 429 payload into populated fields")
    void deserializeError429Payload() throws Exception {
        var actual = json.readObject(ERROR_429);
        then(actual).isNotNull();
        then(actual).isEqualTo(error429Payload);
        assertThat(actual.isError()).isTrue();
        assertThat(actual.hasParameters()).isTrue();
    }

    @Test
    @DisplayName("Serializes error 404 payload with 'migrateToChatId' parameter")
    void serializeError404Payload() throws Exception {
        var actual = json.write(error404Payload);
        then(actual).isNotNull();
        then(actual).isEqualToJson(new ClassPathResource(ERROR_404));
    }

    @Test
    @DisplayName("Deserializes error 404 payload into populated fields")
    void deserializeError404Payload() throws Exception {
        var actual = json.readObject(ERROR_404);
        then(actual).isNotNull();
        then(actual).isEqualTo(error404Payload);
        assertThat(actual.isError()).isTrue();
        assertThat(actual.hasParameters()).isTrue();
    }

}
