package com.roman3455.deplifybot.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("CharacterEscapeUtil")
class CharacterEscapeUtilTest {

    @Test
    @DisplayName("Should return empty string when escapeMarkdownV2() input is null or empty")
    void shouldReturnEmptyStringWhenEscapeMarkdownV2InputNullOrEmpty() {
        assertEquals("", CharacterEscapeUtil.escapeMarkdownV2(null));
        assertEquals("", CharacterEscapeUtil.escapeMarkdownV2(""));
    }

    @Test
    @DisplayName("Should escape all special characters in escapeMarkdownV2()")
    void shouldEscapeAllSpecialCharsInEscapeMarkdownV2() {
        String input = "_[]()~`>#+=-|{}.!*";
        String expected = "\\_\\[\\]\\(\\)\\~\\`\\>\\#\\+\\=\\-\\|\\{\\}\\.\\!\\*";
        assertEquals(expected, CharacterEscapeUtil.escapeMarkdownV2(input));
    }

    @Test
    @DisplayName("Should correctly escape mixed text in escapeMarkdownV2()")
    void shouldEscapeMixedTextIsEscapedMarkdownV2() {
        String input = "Hello *world*! [link](url) a_b c-d {x}";
        String expected = "Hello \\*world\\*\\! \\[link\\]\\(url\\) a\\_b c\\-d \\{x\\}";
        assertEquals(expected, CharacterEscapeUtil.escapeMarkdownV2(input));
    }

    @Test
    @DisplayName("Should leave safe characters and Unicode untouched in escapeMarkdownV2()")
    void shouldLeaveSafeCharsAndUnicodeUntouched() {
        String input = "Hello world 123 😊";
        assertEquals(input, CharacterEscapeUtil.escapeMarkdownV2(input));
    }

    @Test
    @DisplayName("Should return empty string when escapeHtml() input is null or empty")
    void shouldReturnEmptyStringWhenEscapeHtmlInputNullOrEmpty() {
        assertEquals("", CharacterEscapeUtil.escapeHtml(null));
        assertEquals("", CharacterEscapeUtil.escapeHtml(""));
    }

    @ParameterizedTest(name = "[{index}] {0}")
    @MethodSource("escapeHtmlCases")
    @DisplayName("Should escape HTML correctly across different scenarios")
    void shouldEscapeHtmlCorrectlyParameterized(
            final String caseName,
            final String input,
            final String expected
    ) {
        assertEquals(expected, CharacterEscapeUtil.escapeHtml(input));
    }

    static Stream<Arguments> escapeHtmlCases() {
        return Stream.of(
                Arguments.of(
                        "Escapes all reserved characters",
                        "&<>\"'",
                        "&amp;&lt;&gt;&quot;&#x27;"
                ),
                Arguments.of(
                        "Escapes mixed text correctly",
                        "Hello & <b>bold</b> 'q'",
                        "Hello &amp; &lt;b&gt;bold&lt;/b&gt; &#x27;q&#x27;"
                ),
                Arguments.of(
                        "Handles ampersand first to prevent double escaping",
                        "&<",
                        "&amp;&lt;"
                )
        );
    }

}
