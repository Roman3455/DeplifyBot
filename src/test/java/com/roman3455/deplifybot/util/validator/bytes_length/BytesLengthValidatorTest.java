package com.roman3455.deplifybot.util.validator.bytes_length;

import com.roman3455.deplifybot.test_utils.DtoValidationTestSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

@DisplayName("BytesLengthValidator - behavior tests")
class BytesLengthValidatorTest extends DtoValidationTestSupport<BytesLengthValidator> {

    private static final String FIELD = "text";
    private static final String MESSAGE_TEMPLATE = "{BytesLength.validation.constraints.message}";
    private static final int ALLOWED_MIN_LENGTH = 2;
    private static final int ALLOWED_MAX_LENGTH = 4;

    private record DefaultTestDto(@BytesLength String text) {
    }

    private record TestDto(@BytesLength(min = ALLOWED_MIN_LENGTH, max = ALLOWED_MAX_LENGTH) String text) {
    }

    @Override
    protected Stream<Arguments> provideInvalidArguments() {
        return Stream.of(
                Arguments.of(
                        "rejects bytes length below allowed min value",
                        new TestDto("x".repeat(ALLOWED_MIN_LENGTH - 1)),
                        FIELD,
                        MESSAGE_TEMPLATE
                ),
                Arguments.of(
                        "rejects bytes length above allowed max value",
                        new TestDto("x".repeat(ALLOWED_MAX_LENGTH + 1)),
                        FIELD,
                        MESSAGE_TEMPLATE
                )
        );
    }

    @Override
    protected Stream<Arguments> provideValidArguments() {
        return Stream.of(
                Arguments.of(
                        "allows null string value",
                        new DefaultTestDto(null)
                ),
                Arguments.of(
                        "allows empty string value",
                        new DefaultTestDto("")
                ),
                Arguments.of(
                        "accepts bytes length equals allowed min value",
                        new TestDto("x".repeat(ALLOWED_MIN_LENGTH))
                ),
                Arguments.of(
                        "accepts bytes length equals allowed max value",
                        new TestDto("x".repeat(ALLOWED_MAX_LENGTH))
                )
        );
    }

}
