package com.roman3455.deplifybot.util.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum TestEnum implements JsonEnum {
    FOO("foo"),
    BAR("bar"),
    UNKNOWN("unknown");

    private final String value;

    TestEnum(final String value) {
        this.value = value;
    }

    @Override
    public String getValue() {
        return value;
    }

    @JsonCreator
    public static TestEnum fromValue(final String value) {
        return JsonEnumUtil.fromValue(TestEnum.class, value, UNKNOWN);
    }
}
