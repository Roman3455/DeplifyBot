package com.roman3455.deplifybot.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CharacterEscapeUtilTest {

    @Test
    void escapeMarkdownV2NullOrEmptyReturnsEmpty() {
        assertEquals("", CharacterEscapeUtil.escapeMarkdownV2(null));
        assertEquals("", CharacterEscapeUtil.escapeMarkdownV2(""));
    }

    @Test
    void escapeMarkdownV2EscapesAllSpecials() {
        String input = "_[]()~`>#+=-|{}.!*";
        String expected = "\\_\\[\\]\\(\\)\\~\\`\\>\\#\\+\\=\\-\\|\\{\\}\\.\\!\\*";
        assertEquals(expected, CharacterEscapeUtil.escapeMarkdownV2(input));
    }

    @Test
    void escapeMarkdownV2MixedTextIsEscaped() {
        String input = "Hello *world*! [link](url) a_b c-d {x}";
        String expected = "Hello \\*world\\*\\! \\[link\\]\\(url\\) a\\_b c\\-d \\{x\\}";
        assertEquals(expected, CharacterEscapeUtil.escapeMarkdownV2(input));
    }

    @Test
    void escapeMarkdownV2UnicodeAndSafeCharsRemainUntouched() {
        String input = "Hello world 123 😊";
        assertEquals(input, CharacterEscapeUtil.escapeMarkdownV2(input));
    }

    @Test
    void escapeHtmlNullOrEmptyReturnsEmpty() {
        assertEquals("", CharacterEscapeUtil.escapeHtml(null));
        assertEquals("", CharacterEscapeUtil.escapeHtml(""));
    }

    @Test
    void escapeHtmlEscapesAllReserved() {
        String input = "&<>\"'";
        String expected = "&amp;&lt;&gt;&quot;&#x27;";
        assertEquals(expected, CharacterEscapeUtil.escapeHtml(input));
    }

    @Test
    void escapeHtmlMixedTextIsEscaped() {
        String input = "Hello & <b>bold</b> 'q'";
        String expected = "Hello &amp; &lt;b&gt;bold&lt;/b&gt; &#x27;q&#x27;";
        assertEquals(expected, CharacterEscapeUtil.escapeHtml(input));
    }

    @Test
    void escapeHtmlAmpersandHandledFirstNoDoubleEscape() {
        String input = "&<";
        String expected = "&amp;&lt;";
        assertEquals(expected, CharacterEscapeUtil.escapeHtml(input));
    }

}
