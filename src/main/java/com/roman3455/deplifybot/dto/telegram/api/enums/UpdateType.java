package com.roman3455.deplifybot.dto.telegram.api.enums;

import com.roman3455.deplifybot.util.enums.JsonEnum;

/**
 * Represents supported update types received from the Telegram Bot API.
 *
 * <p>Each constant is mapped to its corresponding lowercase string value, which aligns with the JSON payload
 * returned by the API.</p>
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
    MY_CHAT_MEMBER("my_chat_member");

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

}
