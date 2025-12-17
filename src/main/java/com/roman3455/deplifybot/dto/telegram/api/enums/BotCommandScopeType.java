package com.roman3455.deplifybot.dto.telegram.api.enums;

import com.roman3455.deplifybot.util.enums.JsonEnum;

/**
 * Represents the supported command scope types for Telegram bot commands.
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
    CHAT("chat");

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

}
