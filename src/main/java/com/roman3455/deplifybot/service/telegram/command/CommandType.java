package com.roman3455.deplifybot.service.telegram.command;

import com.roman3455.deplifybot.service.telegram.BotActionType;

public enum CommandType implements BotActionType {

    START("/start", "Начало работы");

    private final String name;
    private final String description;

    CommandType(final String name, final String description) {
        this.name = name;
        this.description = description;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDescription() {
        return description;
    }

    public String getNameWithoutSlash() {
        return this.name.replace("/", "");
    }

}
