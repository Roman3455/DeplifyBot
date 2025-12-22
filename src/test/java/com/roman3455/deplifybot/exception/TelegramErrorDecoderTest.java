package com.roman3455.deplifybot.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.roman3455.deplifybot.configuration.JacksonConfiguration;
import com.roman3455.deplifybot.exception.telegram.TelegramBadRequestException;
import com.roman3455.deplifybot.exception.telegram.TelegramForbiddenException;
import com.roman3455.deplifybot.exception.telegram.TelegramNotAcceptableException;
import com.roman3455.deplifybot.exception.telegram.TelegramNotFoundException;
import com.roman3455.deplifybot.exception.telegram.TelegramPayloadTooLargeException;
import com.roman3455.deplifybot.exception.telegram.TelegramServerException;
import com.roman3455.deplifybot.exception.telegram.TelegramTooManyRequestsException;
import com.roman3455.deplifybot.exception.telegram.TelegramUnauthorizedException;
import feign.FeignException;
import feign.Request;
import feign.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("TelegramErrorDecoder — unit tests")
class TelegramErrorDecoderTest {

    private static final int BAD_REQUEST = 400;
    private static final int UNAUTHORIZED = 401;
    private static final int FORBIDDEN = 403;
    private static final int NOT_FOUND = 404;
    private static final int NOT_ACCEPTABLE = 406;
    private static final int PAYLOAD_TOO_LARGE = 413;
    private static final int IM_A_TEAPOT = 418;
    private static final int ENHANCE_YOUR_CALM = 420;
    private static final int TOO_MANY_REQUESTS = 429;
    private static final int INTERNAL_SERVER_ERROR = 500;
    private static final int BAD_GATEWAY = 502;
    private static final int SERVICE_UNAVAILABLE = 503;
    private static final int GATEWAY_TIMEOUT = 504;

    private final TelegramErrorDecoder decoder = new TelegramErrorDecoder(objectMapper());

    private ObjectMapper objectMapper() {
        var builder = new Jackson2ObjectMapperBuilder();
        new JacksonConfiguration().jackson2ObjectMapperBuilderCustomizer().customize(builder);
        return builder.build();
    }

    @ParameterizedTest(name = "[{index}] {0}")
    @MethodSource("decodeCases")
    @SuppressWarnings("unused")
    @DisplayName("Should decode Telegram API error by HTTP status")
    void shouldDecodeByStatus(
            final String caseName,
            final int status,
            final String bodyJson,
            final Class<? extends Exception> expectedType,
            final String expectedMessageContains,
            final Long expectedRetryAfterOrNull
    ) {
        Exception ex = decode(status, bodyJson);
        assertThat(ex).isInstanceOf(expectedType);
        if (expectedMessageContains != null) {
            assertThat(ex).hasMessageContaining(expectedMessageContains);
        }
        if (expectedRetryAfterOrNull != null) {
            assertThat(ex)
                    .isInstanceOf(TelegramTooManyRequestsException.class);
            TelegramTooManyRequestsException t = (TelegramTooManyRequestsException) ex;
            assertThat(t.getRetryAfter()).isEqualTo(expectedRetryAfterOrNull);
        }
    }

    static Stream<Arguments> decodeCases() {
        String base = """
                {"ok":false,"error_code":%d,"description":"%s"}
                """;

        String tooManyWithRetry = """
                {"ok":false,"error_code":429,"description":"Too Many Requests",
                 "parameters":{"retry_after":1}}
                """;

        return Stream.of(
                Arguments.of(
                        "400 -> TelegramBadRequestException",
                        BAD_REQUEST,
                        base.formatted(BAD_REQUEST, "bad request"),
                        TelegramBadRequestException.class,
                        "bad request",
                        null
                ),
                Arguments.of(
                        "401 -> TelegramUnauthorizedException",
                        UNAUTHORIZED,
                        base.formatted(UNAUTHORIZED, "unauthorized"),
                        TelegramUnauthorizedException.class,
                        "unauthorized",
                        null
                ),
                Arguments.of(
                        "403 -> TelegramForbiddenException",
                        FORBIDDEN,
                        base.formatted(FORBIDDEN, "forbidden"),
                        TelegramForbiddenException.class,
                        "forbidden",
                        null
                ),
                Arguments.of(
                        "404 -> TelegramNotFoundException",
                        NOT_FOUND,
                        base.formatted(NOT_FOUND, "not found"),
                        TelegramNotFoundException.class,
                        "not found",
                        null
                ),
                Arguments.of(
                        "406 -> TelegramNotAcceptableException",
                        NOT_ACCEPTABLE,
                        base.formatted(NOT_ACCEPTABLE, "not acceptable"),
                        TelegramNotAcceptableException.class,
                        "not acceptable",
                        null
                ),
                Arguments.of(
                        "413 -> TelegramPayloadTooLargeException",
                        PAYLOAD_TOO_LARGE,
                        base.formatted(PAYLOAD_TOO_LARGE, "too large"),
                        TelegramPayloadTooLargeException.class,
                        "too large",
                        null
                ),
                Arguments.of(
                        "429 -> TelegramTooManyRequestsException (retry_after parsed)",
                        TOO_MANY_REQUESTS,
                        tooManyWithRetry,
                        TelegramTooManyRequestsException.class,
                        "Too Many Requests",
                        1L
                ),
                Arguments.of(
                        "420 -> TelegramTooManyRequestsException (retry_after default 0)",
                        ENHANCE_YOUR_CALM,
                        base.formatted(ENHANCE_YOUR_CALM, "Enhance your calm"),
                        TelegramTooManyRequestsException.class,
                        "Enhance your calm",
                        0L
                ),
                Arguments.of(
                        "500 -> TelegramServerException",
                        INTERNAL_SERVER_ERROR,
                        base.formatted(INTERNAL_SERVER_ERROR, "server error"),
                        TelegramServerException.class,
                        "server error",
                        null
                ),
                Arguments.of(
                        "502 -> TelegramServerException",
                        BAD_GATEWAY,
                        base.formatted(BAD_GATEWAY, "bad gateway"),
                        TelegramServerException.class,
                        "bad gateway",
                        null
                ),
                Arguments.of(
                        "503 -> TelegramServerException",
                        SERVICE_UNAVAILABLE,
                        base.formatted(SERVICE_UNAVAILABLE, "service unavailable"),
                        TelegramServerException.class,
                        "service unavailable",
                        null
                ),
                Arguments.of(
                        "504 -> TelegramServerException",
                        GATEWAY_TIMEOUT,
                        base.formatted(GATEWAY_TIMEOUT, "gateway timeout"),
                        TelegramServerException.class,
                        "gateway timeout",
                        null
                ),
                Arguments.of(
                        "418 -> RuntimeException unexpected status",
                        IM_A_TEAPOT,
                        base.formatted(IM_A_TEAPOT, "i'm a teapot"),
                        RuntimeException.class,
                        "Unexpected Telegram exception (418)",
                        null
                )
        );
    }


    @Test
    @DisplayName("Should return RuntimeException when response body is null")
    void shouldReturnRuntimeExceptionWhenBodyNull() {
        Exception exception = decode(BAD_REQUEST, null);
        assertThat(exception)
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Telegram returned empty error body")
                .hasMessageContaining("status: 400");
    }

    @Test
    @DisplayName("Should return FeignException when body is not valid JSON")
    void shouldReturnFeignExceptionWhenJsonInvalid() {
        Exception ex = decode(BAD_REQUEST, "not-json");
        assertThat(ex).isInstanceOf(FeignException.class);
    }


    private Exception decode(final int status, final String bodyJsonOrNull) {
        Response response = response(status, bodyJsonOrNull);
        return decoder.decode("TelegramClient#call()", response);
    }

    private Response response(final int status, final String bodyOrNull) {
        Request request = Request.create(
                Request.HttpMethod.POST,
                "https://api.telegram.org/method",
                Collections.emptyMap(),
                new byte[0],
                StandardCharsets.UTF_8,
                null
        );

        Response.Builder builder = Response.builder()
                .status(status)
                .reason("reason")
                .request(request)
                .headers(Collections.emptyMap());

        if (bodyOrNull != null) {
            builder.body(bodyOrNull, StandardCharsets.UTF_8);
        }
        return builder.build();
    }
}
