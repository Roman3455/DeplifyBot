package com.roman3455.deplifybot.dto.telegram.api.request;

import com.roman3455.deplifybot.util.ValidationTestSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

@DisplayName("Bean Validation of BotDescriptionRequest")
class BotDescriptionRequestValidationTest extends ValidationTestSupport {

    @Test
    @DisplayName("Valid payload passes validation")
    void validPayload() {
        var valid = new BotDescriptionRequest("Description", "en");
        assertValid(valid);
    }

    @Test
    @DisplayName("Field 'description' @Size: Value above max should fail")
    void descriptionConstraintAboveMax() {
        final int outOfBoundLength = 513;
        final String field = "description";
        final String messageTemplate = "{Size.max.message}";
        var invalid = new BotDescriptionRequest("x".repeat(outOfBoundLength), null);
        assertViolationContains(invalid, field, messageTemplate);
    }

    @ParameterizedTest(name = "[{index}] {0}")
    @MethodSource("invalidBotDescriptionCases")
    @DisplayName("BotDescriptionRequest — bean validation failures")
    void validationFails(
            final String caseName,
            final BotDescriptionRequest invalid,
            final String field,
            final String messageTemplate
    ) {
        assertViolationContains(invalid, field, messageTemplate);
    }

    static Stream<Arguments> invalidBotDescriptionCases() {
        return Stream.of(
                Arguments.of(
                        "ISO6391: unknown language code 'xx'",
                        new BotDescriptionRequest(null, "xx"),
                        "languageCode",
                        "{ISO6391.languageCode.message}"
                ),
                Arguments.of(
                        "ISO6391: invalid length 'eng'",
                        new BotDescriptionRequest(null, "eng"),
                        "languageCode",
                        "{ISO6391.languageCode.message}"
                ),
                Arguments.of(
                        "@AssertTrue: both description and languageCode are null",
                        new BotDescriptionRequest(null, null),
                        "anyProvided",
                        "{BotDescriptionRequest.isAnyProvided.AssertTrue}"
                )
        );
    }

}
