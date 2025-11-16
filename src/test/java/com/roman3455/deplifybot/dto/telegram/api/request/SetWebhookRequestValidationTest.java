package com.roman3455.deplifybot.dto.telegram.api.request;

import com.roman3455.deplifybot.dto.telegram.api.enums.UpdateType;
import com.roman3455.deplifybot.util.ValidationTestSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

@DisplayName("SetWebhookRequest - bean validation")
class SetWebhookRequestValidationTest extends ValidationTestSupport {

    private static final String VALID_URL = "https://ok";
    private static final String VALID_TOKEN = "A_token-123";

    @Test
    @DisplayName("Should pass validation for valid full payload")
    void shouldPassValidationFullPayload() {
        final int maxConnections = 80;
        var valid = new SetWebhookRequest(
                VALID_URL,
                maxConnections,
                List.of(UpdateType.MESSAGE),
                Boolean.TRUE,
                VALID_TOKEN
        );
        assertValid(valid);
    }

    @ParameterizedTest(name = "[{index}] {0}")
    @MethodSource("invalidWebhookUrlCases")
    @DisplayName("Should fail validation when field 'url'")
    void shouldFailValidationUrlParameterized(
            final String caseName,
            final SetWebhookRequest invalid,
            final String field,
            final String messageTemplate
    ) {
        assertViolationContains(invalid, field, messageTemplate);
    }

    static Stream<Arguments> invalidWebhookUrlCases() {
        return Stream.of(
                Arguments.of(
                        "is null (@NotBlank)",
                        new SetWebhookRequest(null, null, null, null, null),
                        "url",
                        "{jakarta.validation.constraints.NotBlank.message}"
                ),
                Arguments.of(
                        "is blank (@NotBlank)",
                        new SetWebhookRequest("   ", null, null, null, null),
                        "url",
                        "{jakarta.validation.constraints.NotBlank.message}"
                ),
                Arguments.of(
                        "mismatch pattern (@Pattern)",
                        new SetWebhookRequest("http://ok", null, null, null, null),
                        "url",
                        "{SetWebhookRequest.url.Pattern.message}"
                )
        );
    }

    @SuppressWarnings("DataFlowIssue")
    @Test
    @DisplayName("Should fail validation when field 'maxConnections' has value below min (@Min)")
    void shouldFailValidationMaxConnectionsBelowMinConstraint() {
        final String field = "maxConnections";
        final String messageTemplate = "{Size.min.message}";
        var invalid = new SetWebhookRequest(VALID_URL, 0, null, null, null);
        assertViolationContains(invalid, field, messageTemplate);
    }

    @SuppressWarnings("DataFlowIssue")
    @Test
    @DisplayName("Should fail validation when field 'maxConnections' has value above max (@Max)")
    void shouldFailValidationMaxConnectionsAboveMaxConstraint() {
        final int outOfBoundConnections = 101;
        final String field = "maxConnections";
        final String messageTemplate = "{Size.max.message}";
        var invalid = new SetWebhookRequest(VALID_URL, outOfBoundConnections, null, null, null);
        assertViolationContains(invalid, field, messageTemplate);
    }

    @Test
    @DisplayName("Should fail validation when field 'secretToken' has value below min (@Size)")
    void shouldFailValidationSecretTokenBelowMinConstraint() {
        final String field = "secretToken";
        final String messageTemplate = "{jakarta.validation.constraints.Size.message}";
        var invalid = new SetWebhookRequest(VALID_URL, null, null, null, "");
        assertViolationContains(invalid, field, messageTemplate);
    }

    @Test
    @DisplayName("Should fail validation when field 'secretToken' has value above max (@Size)")
    void shouldFailValidationSecretTokenAboveMaxConstraint() {
        final int outOfBoundToken = 257;
        final String field = "secretToken";
        final String messageTemplate = "{jakarta.validation.constraints.Size.message}";
        var invalid = new SetWebhookRequest(VALID_URL, null, null, null, "1".repeat(outOfBoundToken));
        assertViolationContains(invalid, field, messageTemplate);
    }

    @Test
    @DisplayName("Should fail validation when field 'secretToken' mismatch pattern (@Pattern)")
    void shouldFailValidationSecretTokenMismatchPatternConstraint() {
        final String field = "secretToken";
        final String messageTemplate = "{SetWebhookRequest.secretToken.Pattern.message}";
        var invalid = new SetWebhookRequest(VALID_URL, null, null, null, "bad*token!");
        assertViolationContains(invalid, field, messageTemplate);
    }

}
