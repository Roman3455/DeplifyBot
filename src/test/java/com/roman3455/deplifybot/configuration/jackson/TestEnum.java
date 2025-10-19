package com.roman3455.deplifybot.configuration.jackson;

import com.fasterxml.jackson.annotation.JsonEnumDefaultValue;

enum TestEnum {
    VALUE,

    @JsonEnumDefaultValue
    UNKNOWN
}
