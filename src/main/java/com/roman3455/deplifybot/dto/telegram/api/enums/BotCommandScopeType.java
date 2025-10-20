package com.roman3455.deplifybot.dto.telegram.api.enums;

import com.roman3455.deplifybot.util.enums.JsonEnum;

public enum BotCommandScopeType implements JsonEnum {

    DEFAULT("default"),
    ALL_PRIVATE_CHATS("all_private_chats"),
    ALL_GROUP_CHATS("all_group_chats"),
    ALL_CHAT_ADMINISTRATORS("all_chat_administrators"),
    CHAT("chat");

    private final String value;

    BotCommandScopeType(final String value) {
        this.value = value;
    }

    @Override
    public String getValue() {
        return value;
    }

}
