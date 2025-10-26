package com.roman3455.deplifybot.dto.telegram.api.request;

import com.roman3455.deplifybot.dto.telegram.api.enums.UpdateType;
import com.roman3455.deplifybot.util.ValidationTestSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

@DisplayName("Bean Validation of SetWebhookRequest")
class SetWebhookRequestValidationTest extends ValidationTestSupport {

    private static final String VALID_URL = "https://ok";
    private static final String VALID_TOKEN = "A_token-123";

    @Test
    @DisplayName("Valid payload passes validation")
    void payloadPassesValidation() {
        final int maxConnections = 80;
        assertValid(
                new SetWebhookRequest(
                        VALID_URL,
                        maxConnections,
                        List.of(UpdateType.MESSAGE),
                        Boolean.TRUE,
                        VALID_TOKEN
                )
        );
    }

    @Test
    @DisplayName("Field 'url' @NotBlank: Null value should fail")
    void urlConstraintsNullValue() {
        assertViolationContains(
                new SetWebhookRequest(
                        null,
                        null,
                        null,
                        null,
                        null
                ),
                "url", "Field 'url' is required."
        );
    }

    @Test
    @DisplayName("Field 'url' @NotBlank: Blank value should fail")
    void urlConstraintsBlankValue() {
        assertViolationContains(
                new SetWebhookRequest(
                        "   ",
                        null,
                        null,
                        null,
                        null
                ),
                "url", "Field 'url' is required."
        );
    }

    @Test
    @DisplayName("Field 'url' @Pattern: Pattern mismatch should fail")
    void urlConstraintsMismatchPattern() {
        assertViolationContains(
                new SetWebhookRequest(
                        "http://example.com",
                        null,
                        null,
                        null,
                        null
                ),
                "url", "Field 'url' must start with 'https://'."
        );
    }

    @SuppressWarnings("DataFlowIssue")
    @Test
    @DisplayName("Field 'maxConnections' @Min: Value below min should fail")
    void maxConnectionsConstraintsBelowMin() {
        assertViolationContains(
                new SetWebhookRequest(
                        VALID_URL,
                        0,
                        null,
                        null,
                        null
                ),
                "maxConnections", "Minimum allowed 'maxConnections' is 1"
        );
    }

    @SuppressWarnings("DataFlowIssue")
    @Test
    @DisplayName("Field 'maxConnections' @Max: Value above max should fail")
    void maxConnectionsConstraints() {
        final int outOfBoundConnections = 101;
        assertViolationContains(
                new SetWebhookRequest(
                        VALID_URL,
                        outOfBoundConnections,
                        null,
                        null,
                        null
                ),
                "maxConnections", "Maximum allowed 'maxConnections' is 100"
        );
    }

    @Test
    @DisplayName("Field 'secretToken' @Size: Value below min should fail")
    void secretTokenConstraintsBelowMin() {
        assertViolationContains(
                new SetWebhookRequest(
                        VALID_URL,
                        null,
                        null,
                        null,
                        ""
                ),
                "secretToken", "Allowed 'secretToken' length is between 1 and 256 characters."
        );
    }

    @Test
    @DisplayName("Field 'secretToken' @Size: Value above max should fail")
    void secretTokenConstraintsAboveMax() {
        final int outOfBoundToken = 257;
        assertViolationContains(
                new SetWebhookRequest(
                        VALID_URL,
                        null,
                        null,
                        null,
                        "1".repeat(outOfBoundToken)
                ),
                "secretToken", "Allowed 'secretToken' length is between 1 and 256 characters."
        );
    }

    @Test
    @DisplayName("Field 'secretToken' @Pattern: Pattern mismatch should fail")
    void secretTokenConstraintsMismatchPattern() {
        assertViolationContains(
                new SetWebhookRequest(
                        VALID_URL,
                        null,
                        null,
                        null,
                        "bad*token!"
                ),
                "secretToken", "Only characters 'A-Z', 'a-z', '0-9', '_', '-' are allowed"
        );
    }

}
