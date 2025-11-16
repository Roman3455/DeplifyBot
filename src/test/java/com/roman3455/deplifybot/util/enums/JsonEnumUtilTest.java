package com.roman3455.deplifybot.util.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("JsonEnumUtil & JsonEnum — value mapping, serialization & deserialization")
class JsonEnumUtilTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    private enum TestEnum implements JsonEnum {
        FOO("foo"), BAR("bar"), UNKNOWN("unknown");
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

    @Test
    @DisplayName("Should map string value to enum constant ignoring case")
    void shouldMapStringValueToEnumIgnoringCase() {
        TestEnum actual = JsonEnumUtil.fromValue(TestEnum.class, "FOO", TestEnum.UNKNOWN);
        assertEquals(TestEnum.FOO, actual);
    }

    @Test
    @DisplayName("Should return default enum when value is null")
    void shouldReturnDefaultWhenValueIsNull() {
        TestEnum actual = JsonEnumUtil.fromValue(TestEnum.class, null, TestEnum.UNKNOWN);
        assertEquals(TestEnum.UNKNOWN, actual);
    }

    @Test
    @DisplayName("Should return default enum when value is unknown")
    void shouldReturnDefaultWhenValueIsUnknown() {
        TestEnum actual = JsonEnumUtil.fromValue(TestEnum.class, "baz", TestEnum.UNKNOWN);
        assertEquals(TestEnum.UNKNOWN, actual);
    }

    @Test
    @DisplayName("Should map value using JsonEnum static helper method")
    void shouldMapValueThroughJsonEnumStaticMethod() {
        TestEnum actual = JsonEnum.fromValue(TestEnum.class, "bar", TestEnum.UNKNOWN);
        assertEquals(TestEnum.BAR, actual);
    }

    @Test
    @DisplayName("Should serialize enum using @JsonValue")
    void shouldSerializeEnumUsingJsonValue() throws JsonProcessingException {
        String actual = objectMapper.writeValueAsString(TestEnum.FOO);
        assertEquals("\"foo\"", actual);
    }

    @Test
    @DisplayName("Should deserialize enum using @JsonCreator")
    void shouldDeserializeEnumUsingJsonCreator() throws JsonProcessingException {
        TestEnum actual = objectMapper.readValue("\"bar\"", TestEnum.class);
        assertEquals(TestEnum.BAR, actual);
    }

    @Test
    @DisplayName("Should deserialize unknown value to default enum")
    void shouldDeserializeUnknownValueToDefaultEnum() throws JsonProcessingException {
        TestEnum actual = objectMapper.readValue("\"something_else\"", TestEnum.class);
        assertEquals(TestEnum.UNKNOWN, actual);
    }

}
