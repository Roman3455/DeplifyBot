package com.roman3455.deplifybot.util;

import java.util.regex.Pattern;

/**
 * Utility class providing safe escaping of characters for text rendering in Telegram messages.
 *
 * <p>Supports escaping for two major formats:</p>
 * <ul>
 *   <li><b>MarkdownV2</b> — escapes all Telegram MarkdownV2 special characters by prefixing them with a
 *   backslash.</li>
 *   <li><b>HTML</b> — replaces reserved characters with their HTML entities to prevent HTML injection or formatting
 *   issues.</li>
 * </ul>
 *
 * <p>Both methods return an empty string if the input is {@code null} or empty.</p>
 */
public final class CharacterEscapeUtil {

    /**
     * Pattern for MarkdownV2 special characters that require escaping according to Telegram Bot API formatting rules.
     */
    private static final Pattern MARKDOWN_V2_SPECIALS_PATTERN = Pattern.compile("([_\\[\\]()~`>#+=\\-|{}.!*])");

    /**
     * Private constructor to prevent instantiation.
     */
    private CharacterEscapeUtil() {
    }

    /**
     * Escapes all special MarkdownV2 characters by prefixing them with a backslash.
     *
     * @param text the input string to escape; may be {@code null} or empty.
     * @return escaped string safe for MarkdownV2 rendering; never {@code null}.
     */
    public static String escapeMarkdownV2(final String text) {
        if (text == null || text.isEmpty()) {
            return "";
        }
        return MARKDOWN_V2_SPECIALS_PATTERN.matcher(text).replaceAll("\\\\$1");
    }

    /**
     * Escapes reserved HTML characters with their corresponding HTML entities.
     *
     * <p>Useful when sending text as {@code parse_mode=HTML} in Telegram messages.</p>
     *
     * @param text the input string to escape; may be {@code null} or empty.
     * @return escaped HTML-safe string; never {@code null}.
     */
    public static String escapeHtml(final String text) {
        if (text == null || text.isEmpty()) {
            return "";
        }
        return text
                .replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
                .replace("'", "&#x27;");
    }

}
