package com.roman3455.deplifybot.util.validator.iso6391;

import com.roman3455.deplifybot.test_utils.DtoValidationTestSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

@DisplayName("ISO6391Validator - behavior tests")
class ISO6391ValidatorTest extends DtoValidationTestSupport<ISO6391Validator> {

    private static final String MESSAGE_TEMPLATE = "{ISO6391.validation.constraints.message}";
    private static final String LANGUAGE_CODE_FIELD = "languageCode";

    private record TestDto(@ISO6391 String languageCode) {
    }

    @Override
    protected Stream<Arguments> provideInvalidArguments() {
        return Stream.of(
                Arguments.of(
                        "field 'languageCode' has value less then 2 chars",
                        new TestDto("x"),
                        LANGUAGE_CODE_FIELD,
                        MESSAGE_TEMPLATE
                ),
                Arguments.of(
                        "field 'languageCode' has value more then 2 chars",
                        new TestDto("eng"),
                        LANGUAGE_CODE_FIELD,
                        MESSAGE_TEMPLATE
                ),
                Arguments.of(
                        "field 'languageCode' has not ISO 639-1 value",
                        new TestDto("xx"),
                        LANGUAGE_CODE_FIELD,
                        MESSAGE_TEMPLATE
                )
        );
    }

    @Override
    protected Stream<Arguments> provideValidArguments() {
        return Stream.of(
                Arguments.of(
                        "field 'languageCode' accept null",
                        new TestDto(null)
                ),
                Arguments.of(
                        "field 'languageCode' accept empty",
                        new TestDto("")
                ),
                Arguments.of(
                        "field 'languageCode' accept lowercase ISO 639-1 code",
                        new TestDto("en")
                ),
                Arguments.of(
                        "field 'languageCode' accept uppercase ISO 639-1 code",
                        new TestDto("EN")
                )
        );
    }

}
