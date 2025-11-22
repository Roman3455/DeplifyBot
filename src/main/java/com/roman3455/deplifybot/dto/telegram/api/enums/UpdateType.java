package com.roman3455.deplifybot.dto.telegram.api.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.roman3455.deplifybot.util.enums.JsonEnum;
import com.roman3455.deplifybot.util.enums.JsonEnumUtil;

/**
 * Represents supported update types received from the Telegram Bot API.
 *
 * <p>Each constant is mapped to its corresponding lowercase string value, which aligns with the JSON payload
 * returned by the API. The {@link #UNKNOWN} value acts as a fallback for unrecognized types, ensuring forward
 * compatibility.</p>
 *
 * @see <a href="https://core.telegram.org/bots/api#update">Telegram Bot API — Update</a>
 */
public enum UpdateType implements JsonEnum {

    /**
     * A regular incoming message.
     */
    MESSAGE("message"),

    /**
     * An incoming edited message.
     */
    EDITED_MESSAGE("edited_message"),

    /**
     * A callback query from an inline button.
     */
    CALLBACK_QUERY("callback_query"),

    /**
     * A change in the bot’s chat member status.
     */
    MY_CHAT_MEMBER("my_chat_member"),

    /**
     * Fallback for unrecognized or new update types.
     */
    UNKNOWN("unknown");

    private final String value;

    UpdateType(final String value) {
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
     * Resolves an {@code UpdateType} from its string value.
     *
     * @param value the string value from the incoming JSON payload.
     * @return the corresponding {@code UpdateType}, or {@link #UNKNOWN} if no match is found.
     */
    @JsonCreator
    public static UpdateType fromValue(final String value) {
        return JsonEnumUtil.fromValue(UpdateType.class, value, UNKNOWN);
    }

}
