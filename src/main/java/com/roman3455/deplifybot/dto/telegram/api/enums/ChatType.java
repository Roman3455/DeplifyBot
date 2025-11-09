package com.roman3455.deplifybot.dto.telegram.api.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.roman3455.deplifybot.util.enums.JsonEnum;
import com.roman3455.deplifybot.util.enums.JsonEnumUtil;

/**
 * Represents the supported chat types returned by the Telegram Bot API.
 *
 * <p>Each constant is mapped to its corresponding lowercase string value, matching the JSON payload received
 * from Telegram. The {@link #UNKNOWN} value acts as a fallback for any unrecognized or future chat types,
 * ensuring forward compatibility and safe deserialization.</p>
 *
 * @see <a href="https://core.telegram.org/bots/api#chat">Telegram Bot API — Chat</a>
 * @see com.roman3455.deplifybot.util.enums.JsonEnum
 * @see com.roman3455.deplifybot.util.enums.JsonEnumUtil
 */
public enum ChatType implements JsonEnum {

    /**
     * A private chat between the bot and a single user.
     */
    PRIVATE("private"),

    /**
     * A basic group chat.
     */
    GROUP("group"),

    /**
     * A supergroup chat, which may support additional features such as message topics.
     */
    SUPERGROUP("supergroup"),

    /**
     * A broadcast channel. Messages in channels appear as posts without a reply UI by default.
     */
    CHANNEL("channel"),

    /**
     * Fallback for unrecognized or new chat types.
     */
    UNKNOWN("unknown");

    private final String value;

    ChatType(final String value) {
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
     * Resolves a {@code ChatType} from its string value.
     *
     * @param value the string value from the incoming JSON payload.
     * @return the corresponding {@code ChatType}, or {@link #UNKNOWN} if no match is found.
     */
    @JsonCreator
    public static ChatType fromValue(final String value) {
        return JsonEnumUtil.fromValue(ChatType.class, value, UNKNOWN);
    }

}
