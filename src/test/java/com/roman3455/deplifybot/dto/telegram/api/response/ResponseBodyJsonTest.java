package com.roman3455.deplifybot.dto.telegram.api.response;

import com.roman3455.deplifybot.configuration.JacksonConfiguration;
import com.roman3455.deplifybot.test_utils.DtoJsonMarshallingTestSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.provider.Arguments;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.stream.Stream;

import static com.roman3455.deplifybot.test_utils.TelegramApiDtoBuilder.validResponseBodySuccessPayload;
import static com.roman3455.deplifybot.test_utils.TelegramApiDtoBuilder.validResponseBodyTooManyRequestsPayload;
import static com.roman3455.deplifybot.test_utils.TelegramApiDtoBuilder.validResponseBodyBadRequestPayload;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ActiveProfiles("test")
@JsonTest
@Import(JacksonConfiguration.class)
@DisplayName("ResponseBody — JSON serialization & deserialization")
class ResponseBodyJsonTest extends DtoJsonMarshallingTestSupport<ResponseBody<?>> {

    private static final String PATH = "/fixture/telegram/response/response_body/response_body_";

    @Autowired
    private JacksonTester<ResponseBody<?>> jsonTester;

    @Override
    protected JacksonTester<ResponseBody<?>> tester() {
        return jsonTester;
    }

    @Override
    protected Stream<Arguments> provideArguments() {
        return Stream.of(
                Arguments.of(
                        "success payload",
                        validResponseBodySuccessPayload(),
                        PATH + "success.json",
                        List.of("$.error_code", "$.description", "$.parameters")
                ),
                Arguments.of(
                        "too many requests payload",
                        validResponseBodyTooManyRequestsPayload(),
                        PATH + "too_many_requests_with_retry.json",
                        List.of("$.result", "$.parameters.migrate_to_chat_id")
                ),
                Arguments.of(
                        "bad request payload",
                        validResponseBodyBadRequestPayload(),
                        PATH + "bad_request_with_migrate.json",
                        List.of("$.result", "$.parameters.retry_after")
                )
        );
    }

    @Test
    @DisplayName("Should return true when payload has error information")
    void shouldReturnTrueWhenPayloadHasErrorInformation() {
        assertTrue(validResponseBodyBadRequestPayload().isError());
    }

    @Test
    @DisplayName("Should return false when payload has not error information")
    void shouldReturnFalseWhenPayloadHasNotErrorInformation() {
        assertFalse(validResponseBodySuccessPayload().isError());
    }

    @Test
    @DisplayName("Should return true when payload has response parameters")
    void shouldReturnTrueWhenPayloadHasResponseParameters() {
        assertTrue(validResponseBodyBadRequestPayload().hasParameters());
    }

    @Test
    @DisplayName("Should return false when payload has not response parameters")
    void shouldReturnFalseWhenPayloadHasNotResponseParameters() {
        assertFalse(validResponseBodySuccessPayload().hasParameters());
    }

}
