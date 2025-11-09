package com.roman3455.deplifybot.dto.telegram.api.request;

import com.roman3455.deplifybot.util.ValidationTestSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

@DisplayName("Bean Validation of BotShortDescriptionRequest")
class BotShortDescriptionRequestValidationTest extends ValidationTestSupport {

    @Test
    @DisplayName("Valid payload passes validation")
    void validPayload() {
        var valid = new BotShortDescriptionRequest("Short description", "en");
        assertValid(valid);
    }

    @Test
    @DisplayName("Field 'shortDescription' @Size: Value above max should fail")
    void shortDescriptionConstraintAboveMax() {
        final int outOfBoundLength = 121;
        final String field = "shortDescription";
        final String messageTemplate = "{Size.max.message}";
        var invalid = new BotShortDescriptionRequest("x".repeat(outOfBoundLength), null);
        assertViolationContains(invalid, field, messageTemplate);
    }

    @ParameterizedTest(name = "[{index}] {0}")
    @MethodSource("invalidBotShortDescriptionCases")
    @DisplayName("BotShortDescriptionRequest — bean validation failures")
    void validationFails(
            final String caseName,
            final BotShortDescriptionRequest invalid,
            final String field,
            final String messageTemplate
    ) {
        assertViolationContains(invalid, field, messageTemplate);
    }

    static Stream<Arguments> invalidBotShortDescriptionCases() {
        return Stream.of(
                Arguments.of(
                        "ISO6391: unknown language code 'xx'",
                        new BotShortDescriptionRequest(null, "xx"),
                        "languageCode",
                        "{ISO6391.languageCode.message}"
                ),
                Arguments.of(
                        "ISO6391: invalid language code length 'eng'",
                        new BotShortDescriptionRequest(null, "eng"),
                        "languageCode",
                        "{ISO6391.languageCode.message}"
                ),
                Arguments.of(
                        "@AssertTrue: both shortDescription and languageCode are null",
                        new BotShortDescriptionRequest(null, null),
                        "anyProvided",
                        "{BotShortDescriptionRequest.isAnyProvided.AssertTrue}"
                )
        );
    }

}
