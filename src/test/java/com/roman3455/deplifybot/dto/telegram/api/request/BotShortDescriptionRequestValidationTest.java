package com.roman3455.deplifybot.dto.telegram.api.request;

import com.roman3455.deplifybot.util.ValidationTestSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

@DisplayName("BotShortDescriptionRequest - bean validation")
class BotShortDescriptionRequestValidationTest extends ValidationTestSupport {

    @Test
    @DisplayName("Should pass validation for valid full payload")
    void shouldPassValidationFullPayload() {
        var valid = new BotShortDescriptionRequest("Short description", "en");
        assertValid(valid);
    }

    @Test
    @DisplayName("Should fail validation when field 'shortDescription' has value above max (@Size)")
    void shouldFailValidationShortDescriptionAboveMaxConstraint() {
        final int outOfBoundLength = 121;
        final String field = "shortDescription";
        final String messageTemplate = "{Size.max.message}";
        var invalid = new BotShortDescriptionRequest("x".repeat(outOfBoundLength), null);
        assertViolationContains(invalid, field, messageTemplate);
    }

    @ParameterizedTest(name = "[{index}] {0}")
    @MethodSource("invalidBotShortDescriptionCases")
    @DisplayName("Should fail validation when")
    void shouldFailValidationLanguageCodeAndShortDescriptionParameterized(
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
                        "field 'languageCode' has unknown 2 chars value (@ISO6391)",
                        new BotShortDescriptionRequest(null, "xx"),
                        "languageCode",
                        "{ISO6391.validation.constraints.message}"
                ),
                Arguments.of(
                        "field 'languageCode' has invalid length (@ISO6391)",
                        new BotShortDescriptionRequest(null, "eng"),
                        "languageCode",
                        "{ISO6391.validation.constraints.message}"
                ),
                Arguments.of(
                        "both fields 'shortDescription' and 'languageCode' are null (@AssertTrue)",
                        new BotShortDescriptionRequest(null, null),
                        "anyProvided",
                        "{BotShortDescriptionRequest.isAnyProvided.AssertTrue}"
                )
        );
    }

}
