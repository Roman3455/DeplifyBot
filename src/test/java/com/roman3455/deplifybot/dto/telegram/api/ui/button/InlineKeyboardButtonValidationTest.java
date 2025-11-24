package com.roman3455.deplifybot.dto.telegram.api.ui.button;

import com.roman3455.deplifybot.util.ValidationTestSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

@DisplayName("InlineKeyboardButton - bean validation")
class InlineKeyboardButtonValidationTest extends ValidationTestSupport {

    public static final int MAX_CALLBACK_DATA_LENGTH = 64;
    private static final String BUTTON_LABEL = "Button";
    private static final String URL = "https://example.com";
    private static final String CALLBACK_DATA = "callback";
    private static final CopyTextButton COPY_BUTTON = new CopyTextButton("Text to copy");

    @ParameterizedTest(name = "[{index}] {0}")
    @MethodSource("validInlineKeyboardButtonCases")
    @DisplayName("Should pass validation for valid")
    void shouldPassValidationParametrized(
            final String caseName,
            final InlineKeyboardButton valid
    ) {
        assertValid(valid);
    }

    static Stream<Arguments> validInlineKeyboardButtonCases() {
        return Stream.of(
                Arguments.of(
                        "'callbackData' payload",
                        new InlineKeyboardButton(BUTTON_LABEL, null, CALLBACK_DATA, null)
                ),
                Arguments.of(
                        "'callbackData' button fabric payload",
                        InlineKeyboardButton.ofCallbackData(BUTTON_LABEL, CALLBACK_DATA)
                ),
                Arguments.of(
                        "'copyText' payload",
                        new InlineKeyboardButton(BUTTON_LABEL, null, null, COPY_BUTTON)
                ),
                Arguments.of(
                        "'copyText' button fabric payload",
                        InlineKeyboardButton.ofCopyText(BUTTON_LABEL, "Text to copy")
                ),
                Arguments.of(
                        "'url' payload",
                        new InlineKeyboardButton(BUTTON_LABEL, URL, null, null)
                ),
                Arguments.of(
                        "'url' button fabric payload",
                        InlineKeyboardButton.ofUrl(BUTTON_LABEL, URL)
                )
        );
    }

    @ParameterizedTest(name = "[{index}] {0}")
    @MethodSource("invalidInlineKeyboardButtonCases")
    @DisplayName("Should fail validation when field")
    void shouldFailValidationParametrized(
            final String caseName,
            final String field,
            final String messageTemplate,
            final InlineKeyboardButton invalid
    ) {
        assertViolationContains(invalid, field, messageTemplate);
    }

    static Stream<Arguments> invalidInlineKeyboardButtonCases() {
        return Stream.of(
                Arguments.of(
                        "'text' is null (@NotNull)",
                        "text",
                        "{jakarta.validation.constraints.NotNull.message}",
                        new InlineKeyboardButton(null, null, CALLBACK_DATA, null)
                ),
                Arguments.of(
                        "'url' mismatch pattern (@Pattern)",
                        "url",
                        "{InlineKeyboardButton.url.Pattern.message}",
                        new InlineKeyboardButton(BUTTON_LABEL, "ttp://example.com", null, null)
                ),
                Arguments.of(
                        "'callbackData' bytes length below min value (@BytesLength)",
                        "callbackData",
                        "{BytesLength.validation.constraints.message}",
                        new InlineKeyboardButton(BUTTON_LABEL, null, "", null)
                ),
                Arguments.of(
                        "'callbackData' bytes length above max value (@BytesLength)",
                        "callbackData",
                        "{BytesLength.validation.constraints.message}",
                        new InlineKeyboardButton(
                                BUTTON_LABEL,
                                null,
                                "x".repeat(MAX_CALLBACK_DATA_LENGTH +  1),
                                null
                        )
                ),
                Arguments.of(
                        "'copyText' has invalid CopyTextButton (@Valid)",
                        "copyText.text",
                        "{jakarta.validation.constraints.NotNull.message}",
                        new InlineKeyboardButton(BUTTON_LABEL, null, null, new CopyTextButton(null))
                ),
                Arguments.of(
                        "'url', 'callbackData', 'copyText' not present (@AssertTrue)",
                        "anyProvided",
                        "{InlineKeyboardButton.isAnyProvided.AssertTrue}",
                        new InlineKeyboardButton(BUTTON_LABEL, null, null, null)
                ),
                Arguments.of(
                        "'url', 'callbackData', 'copyText' present (@AssertTrue)",
                        "anyProvided",
                        "{InlineKeyboardButton.isAnyProvided.AssertTrue}",
                        new InlineKeyboardButton(BUTTON_LABEL, URL, CALLBACK_DATA, COPY_BUTTON)
                )
        );
    }

}
