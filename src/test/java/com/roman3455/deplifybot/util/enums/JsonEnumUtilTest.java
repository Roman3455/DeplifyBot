package com.roman3455.deplifybot.util.enums;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonEnumUtilTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void shouldReturnMatchingEnumConstantIgnoringCase() {
        TestEnum result = JsonEnumUtil.fromValue(TestEnum.class, "FOO", TestEnum.UNKNOWN);
        assertEquals(TestEnum.FOO, result);
    }

    @Test
    void shouldReturnDefaultValueWhenValueIsNull() {
        TestEnum result = JsonEnumUtil.fromValue(TestEnum.class, null, TestEnum.UNKNOWN);
        assertEquals(TestEnum.UNKNOWN, result);
    }

    @Test
    void shouldReturnDefaultValueWhenValueIsUnknown() {
        TestEnum result = JsonEnumUtil.fromValue(TestEnum.class, "baz", TestEnum.UNKNOWN);
        assertEquals(TestEnum.UNKNOWN, result);
    }

    @Test
    void shouldWorkThroughJsonEnumStaticMethod() {
        TestEnum result = JsonEnum.fromValue(TestEnum.class, "bar", TestEnum.UNKNOWN);
        assertEquals(TestEnum.BAR, result);
    }

    @Test
    void shouldSerializeEnumUsingJsonValue() throws JsonProcessingException {
        String json = objectMapper.writeValueAsString(TestEnum.FOO);
        assertEquals("\"foo\"", json);
    }

    @Test
    void shouldDeserializeEnumUsingJsonCreator() throws JsonProcessingException {
        TestEnum result = objectMapper.readValue("\"bar\"", TestEnum.class);
        assertEquals(TestEnum.BAR, result);
    }

    @Test
    void shouldDeserializeUnknownValueToDefault() throws JsonProcessingException {
        TestEnum result = objectMapper.readValue("\"something_else\"", TestEnum.class);
        assertEquals(TestEnum.UNKNOWN, result);
    }
}
