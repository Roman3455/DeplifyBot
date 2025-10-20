package com.roman3455.deplifybot.dto.telegram.api.enums;

import com.roman3455.deplifybot.util.enums.JsonEnum;

public enum UpdateType implements JsonEnum {

    MESSAGE("message"),
    EDITED_MESSAGE("edited_message"),
    CALLBACK_QUERY("callback_query"),
    MY_CHAT_MEMBER("my_chat_member");

    private final String value;

    UpdateType(final String value) {
        this.value = value;
    }

    @Override
    public String getValue() {
        return value;
    }

}
