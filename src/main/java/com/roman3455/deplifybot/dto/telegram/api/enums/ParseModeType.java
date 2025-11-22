package com.roman3455.deplifybot.dto.telegram.api.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.roman3455.deplifybot.util.enums.JsonEnum;
import com.roman3455.deplifybot.util.enums.JsonEnumUtil;

/**
 * Represents the supported Telegram message parse modes for the {@code sendMessage} endpoint.
 *
 * <p>Parse modes control how Telegram interprets and formats the provided text. The available options correspond to
 * the Telegram Bot API documentation.</p>
 *
 * @see <a href="https://core.telegram.org/bots/api#formatting-options">Telegram Bot API — Formatting options</a>
 */
public enum ParseModeType implements JsonEnum {

    /**
     * Telegram MarkdownV2 formatting mode.
     *
     * <p>Enables advanced Markdown formatting such as bold, italics, links, code blocks, spoiler tags, etc.</p>
     */
    MARKDOWN_V2("MarkdownV2"),

    /**
     * HTML formatting mode.
     *
     * <p>Supports a subset of HTML tags allowed by Telegram (e.g., {@code <b>}, {@code <i>}, {@code <u>},
     * {@code <a href="">}, {@code <code>}, {@code <pre>}).</p>
     */
    HTML("HTML"),

    /**
     * Plain text mode.
     *
     * <p>Represents messages without any formatting. Mapped to an empty string because Telegram interprets
     * absence of {@code parse_mode} as plain text rendering.
     */
    PLAIN(""),

    /**
     * Fallback for unrecognized or new parse mode types.
     */
    UNKNOWN("unknown");

    private final String value;

    ParseModeType(final String value) {
        this.value = value;
    }

    /**
     * @return the string value used for JSON serialization and matching against Telegram API payloads.
     */
    @Override
    public String getValue() {
        return value;
    }

    /**
     * Resolves a {@code ParseModeType} from its string value.
     *
     * @param value the string value from the incoming JSON payload.
     * @return the corresponding {@code ParseModeType}, or {@link #UNKNOWN} if no match is found.
     */
    @JsonCreator
    public static ParseModeType fromValue(final String value) {
        return JsonEnumUtil.fromValue(ParseModeType.class, value, UNKNOWN);
    }

}
