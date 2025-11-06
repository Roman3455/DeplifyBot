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
        var valid = new SetWebhookRequest(
                VALID_URL,
                maxConnections,
                List.of(UpdateType.MESSAGE),
                Boolean.TRUE,
                VALID_TOKEN
        );
        assertValid(valid);
    }

    @Test
    @DisplayName("Field 'url' @NotBlank: Null value should fail")
    void urlConstraintsNullValue() {
        final String field = "url";
        final String messageTemplate = "{jakarta.validation.constraints.NotBlank.message}";
        var invalid = new SetWebhookRequest(null, null, null, null, null);
        assertViolationContains(invalid, field, messageTemplate);
    }

    @Test
    @DisplayName("Field 'url' @NotBlank: Blank value should fail")
    void urlConstraintsBlankValue() {
        final String field = "url";
        final String messageTemplate = "{jakarta.validation.constraints.NotBlank.message}";
        var invalid = new SetWebhookRequest("   ", null, null, null, null);
        assertViolationContains(invalid, field, messageTemplate);
    }

    @Test
    @DisplayName("Field 'url' @Pattern: Pattern mismatch should fail")
    void urlConstraintsMismatchPattern() {
        final String field = "url";
        final String messageTemplate = "{SetWebhookRequest.url.Pattern.message}";
        var invalid = new SetWebhookRequest("http://ok", null, null, null, null);
        assertViolationContains(invalid, field, messageTemplate);
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
