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

@DisplayName("Bean Validation of SetWebhookRequest")
class SetWebhookRequestValidationTest extends ValidationTestSupport {

    private static final String VALID_URL = "https://ok";
    private static final String VALID_TOKEN = "A_token-123";

    @Test
    @DisplayName("Valid payload passes validation")
    void payloadPassesValidation() {
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
    @DisplayName("SetWebhookRequest — field 'url' validation failures")
    void urlConstraintFailures(
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
                        "url @NotBlank: null value",
                        new SetWebhookRequest(null, null, null, null, null),
                        "url",
                        "{jakarta.validation.constraints.NotBlank.message}"
                ),
                Arguments.of(
                        "url @NotBlank: blank value",
                        new SetWebhookRequest("   ", null, null, null, null),
                        "url",
                        "{jakarta.validation.constraints.NotBlank.message}"
                ),
                Arguments.of(
                        "url @Pattern: mismatch",
                        new SetWebhookRequest("http://ok", null, null, null, null),
                        "url",
                        "{SetWebhookRequest.url.Pattern.message}"
                )
        );
    }

    @SuppressWarnings("DataFlowIssue")
    @Test
    @DisplayName("Field 'maxConnections' @Min: Value below min should fail")
    void maxConnectionsConstraintsBelowMin() {
        final String field = "maxConnections";
        final String messageTemplate = "{Size.min.message}";
        var invalid = new SetWebhookRequest(VALID_URL, 0, null, null, null);
        assertViolationContains(invalid, field, messageTemplate);
    }

    @SuppressWarnings("DataFlowIssue")
    @Test
    @DisplayName("Field 'maxConnections' @Max: Value above max should fail")
    void maxConnectionsConstraints() {
        final int outOfBoundConnections = 101;
        final String field = "maxConnections";
        final String messageTemplate = "{Size.max.message}";
        var invalid = new SetWebhookRequest(VALID_URL, outOfBoundConnections, null, null, null);
        assertViolationContains(invalid, field, messageTemplate);
    }

    @Test
    @DisplayName("Field 'secretToken' @Size: Value below min should fail")
    void secretTokenConstraintsBelowMin() {
        final String field = "secretToken";
        final String messageTemplate = "{jakarta.validation.constraints.Size.message}";
        var invalid = new SetWebhookRequest(VALID_URL, null, null, null, "");
        assertViolationContains(invalid, field, messageTemplate);
    }

    @Test
    @DisplayName("Field 'secretToken' @Size: Value above max should fail")
    void secretTokenConstraintsAboveMax() {
        final int outOfBoundToken = 257;
        final String field = "secretToken";
        final String messageTemplate = "{jakarta.validation.constraints.Size.message}";
        var invalid = new SetWebhookRequest(VALID_URL, null, null, null, "1".repeat(outOfBoundToken));
        assertViolationContains(invalid, field, messageTemplate);
    }

    @Test
    @DisplayName("Field 'secretToken' @Pattern: Pattern mismatch should fail")
    void secretTokenConstraintsMismatchPattern() {
        final String field = "secretToken";
        final String messageTemplate = "{SetWebhookRequest.secretToken.Pattern.message}";
        var invalid = new SetWebhookRequest(VALID_URL, null, null, null, "bad*token!");
        assertViolationContains(invalid, field, messageTemplate);
    }

}
