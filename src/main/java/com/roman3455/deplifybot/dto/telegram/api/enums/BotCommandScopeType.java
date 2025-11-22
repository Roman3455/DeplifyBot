package com.roman3455.deplifybot.dto.telegram.api.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.roman3455.deplifybot.util.enums.JsonEnum;
import com.roman3455.deplifybot.util.enums.JsonEnumUtil;

/**
 * Represents the supported command scope types for Telegram bot commands.
 *
 * <p>Each constant corresponds to a scope type defined in the Telegram Bot API. {@link #UNKNOWN} is used as a
 * fallback for unrecognized values.<p/>
 *
 * @see <a href="https://core.telegram.org/bots/api#botcommandscope">Telegram Bot API — BotCommandScope</a>
 */
public enum BotCommandScopeType implements JsonEnum {

    /**
     * The default command scope.
     */
    DEFAULT("default"),

    /**
     * Commands are available to all private chats.
     */
    ALL_PRIVATE_CHATS("all_private_chats"),

    /**
     * Commands are available to all group chats.
     */
    ALL_GROUP_CHATS("all_group_chats"),

    /**
     * Commands are available to all chat administrators.
     */
    ALL_CHAT_ADMINISTRATORS("all_chat_administrators"),

    /**
     * Commands are available to a specific chat.
     */
    CHAT("chat"),

    /**
     * Fallback for unrecognized or new scope types.
     */
    UNKNOWN("unknown");

    private final String value;

    BotCommandScopeType(final String value) {
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
     * Resolves a {@code BotCommandScopeType} from its string value.
     *
     * @param value the string value from the incoming JSON payload
     * @return the corresponding {@code BotCommandScopeType}, or {@link #UNKNOWN} if no match is found
     */
    @JsonCreator
    public static BotCommandScopeType fromValue(final String value) {
        return JsonEnumUtil.fromValue(BotCommandScopeType.class, value, UNKNOWN);
    }

}
