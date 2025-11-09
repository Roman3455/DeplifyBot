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
    @DisplayName("escapeMarkdownV2: null or empty → empty string")
    void escapeMarkdownV2NullOrEmptyReturnsEmpty() {
        assertEquals("", CharacterEscapeUtil.escapeMarkdownV2(null));
        assertEquals("", CharacterEscapeUtil.escapeMarkdownV2(""));
    }

    @Test
    @DisplayName("escapeMarkdownV2: all special characters are escaped")
    void escapeMarkdownV2EscapesAllSpecials() {
        String input = "_[]()~`>#+=-|{}.!*";
        String expected = "\\_\\[\\]\\(\\)\\~\\`\\>\\#\\+\\=\\-\\|\\{\\}\\.\\!\\*";
        assertEquals(expected, CharacterEscapeUtil.escapeMarkdownV2(input));
    }

    @Test
    @DisplayName("escapeMarkdownV2: mixed text is correctly escaped")
    void escapeMarkdownV2MixedTextIsEscaped() {
        String input = "Hello *world*! [link](url) a_b c-d {x}";
        String expected = "Hello \\*world\\*\\! \\[link\\]\\(url\\) a\\_b c\\-d \\{x\\}";
        assertEquals(expected, CharacterEscapeUtil.escapeMarkdownV2(input));
    }

    @Test
    @DisplayName("escapeMarkdownV2: safe chars and Unicode remain untouched")
    void escapeMarkdownV2UnicodeAndSafeCharsRemainUntouched() {
        String input = "Hello world 123 😊";
        assertEquals(input, CharacterEscapeUtil.escapeMarkdownV2(input));
    }

    @Test
    @DisplayName("escapeHtml: null or empty → empty string")
    void escapeHtmlNullOrEmptyReturnsEmpty() {
        assertEquals("", CharacterEscapeUtil.escapeHtml(null));
        assertEquals("", CharacterEscapeUtil.escapeHtml(""));
    }

    @ParameterizedTest(name = "[{index}] {0}")
    @MethodSource("escapeHtmlCases")
    @DisplayName("CharacterEscapeUtil.escapeHtml() — escaping scenarios")
    void escapeHtmlParameterized(
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
                        "Mixed text is escaped correctly",
                        "Hello & <b>bold</b> 'q'",
                        "Hello &amp; &lt;b&gt;bold&lt;/b&gt; &#x27;q&#x27;"
                ),
                Arguments.of(
                        "Ampersand handled first (no double escape)",
                        "&<",
                        "&amp;&lt;"
                )
        );
    }

}
