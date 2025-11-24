package com.roman3455.deplifybot.util.validator.bytes_length;

import com.roman3455.deplifybot.util.ValidationTestSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Bean Validation of BytesLengthValidator")
class BytesLengthValidatorTest extends ValidationTestSupport {

    private static final String FIELD = "text";
    private static final String MESSAGE_PART = "{BytesLength.validation.constraints.message}";
    private static final int ALLOWED_MIN_LENGTH = 2;
    private static final int ALLOWED_MAX_LENGTH = 4;

    private record DefaultTestDto(
            @BytesLength
            String text
    ) {
    }

    private record TestDto(
            @BytesLength(min = ALLOWED_MIN_LENGTH, max = ALLOWED_MAX_LENGTH)
            String text
    ) {
    }

    @Test
    @DisplayName("Should allow null string value")
    void shouldAllowNullString() {
        var valid = new DefaultTestDto(null);
        assertValid(valid);
    }

    @Test
    @DisplayName("Should allow empty string value")
    void shouldAllowEmptyString() {
        var valid = new DefaultTestDto("");
        assertValid(valid);
    }

    @Test
    @DisplayName("Should accept bytes length equals allowed min value")
    void shouldAcceptMinValue() {
        var valid = new TestDto("x".repeat(ALLOWED_MIN_LENGTH));
        assertValid(valid);
    }

    @Test
    @DisplayName("Should accept bytes length equals allowed max value")
    void shouldAcceptMaxValue() {
        var valid = new TestDto("x".repeat(ALLOWED_MAX_LENGTH));
        assertValid(valid);
    }

    @Test
    @DisplayName("Should reject bytes length below allowed min value")
    void shouldRejectValueBelowMin() {
        var invalid = new TestDto("x".repeat(ALLOWED_MIN_LENGTH - 1));
        assertViolationContains(invalid, FIELD, MESSAGE_PART);
    }

    @Test
    @DisplayName("Should reject bytes length above allowed max value")
    void shouldRejectValueAboveMax() {
        var invalid = new TestDto("x".repeat(ALLOWED_MAX_LENGTH + 1));
        assertViolationContains(invalid, FIELD, MESSAGE_PART);
    }

}
