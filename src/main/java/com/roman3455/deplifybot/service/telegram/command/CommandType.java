package com.roman3455.deplifybot.service.telegram.command;

import com.roman3455.deplifybot.service.telegram.BotActionType;

public enum CommandType implements BotActionType {

    START("/start", "bot.command.start");

    private final String name;
    private final String messageCode;

    CommandType(final String name, final String messageCode) {
        this.name = name;
        this.messageCode = messageCode;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getMessageCode() {
        return messageCode;
    }

    public String getNameWithoutSlash() {
        return name.replace("/", "");
    }

}
