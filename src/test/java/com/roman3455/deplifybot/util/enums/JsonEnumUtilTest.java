package com.roman3455.deplifybot.util.enums;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("JsonEnumUtil & JsonEnum — value mapping, serialization & deserialization")
class JsonEnumUtilTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    @DisplayName("Maps string value to enum constant ignoring case")
    void mapsStringValueIgnoringCase() {
        TestEnum actual = JsonEnumUtil.fromValue(TestEnum.class, "FOO", TestEnum.UNKNOWN);
        assertEquals(TestEnum.FOO, actual);
    }

    @Test
    @DisplayName("Returns default enum when value is null")
    void returnsDefaultWhenValueIsNull() {
        TestEnum actual = JsonEnumUtil.fromValue(TestEnum.class, null, TestEnum.UNKNOWN);
        assertEquals(TestEnum.UNKNOWN, actual);
    }

    @Test
    @DisplayName("Returns default enum when value is unknown")
    void returnsDefaultWhenValueIsUnknown() {
        TestEnum actual = JsonEnumUtil.fromValue(TestEnum.class, "baz", TestEnum.UNKNOWN);
        assertEquals(TestEnum.UNKNOWN, actual);
    }

    @Test
    @DisplayName("Maps value through JsonEnum static helper method")
    void mapsValueThroughJsonEnumStaticMethod() {
        TestEnum actual = JsonEnum.fromValue(TestEnum.class, "bar", TestEnum.UNKNOWN);
        assertEquals(TestEnum.BAR, actual);
    }

    @Test
    @DisplayName("Serializes enum using @JsonValue")
    void serializesEnumUsingJsonValue() throws JsonProcessingException {
        String actual = objectMapper.writeValueAsString(TestEnum.FOO);
        assertEquals("\"foo\"", actual);
    }

    @Test
    @DisplayName("Deserializes enum using @JsonCreator")
    void deserializesEnumUsingJsonCreator() throws JsonProcessingException {
        TestEnum actual = objectMapper.readValue("\"bar\"", TestEnum.class);
        assertEquals(TestEnum.BAR, actual);
    }

    @Test
    @DisplayName("Deserializes unknown value to default enum")
    void deserializesUnknownValueToDefaultEnum() throws JsonProcessingException {
        TestEnum actual = objectMapper.readValue("\"something_else\"", TestEnum.class);
        assertEquals(TestEnum.UNKNOWN, actual);
    }

}
