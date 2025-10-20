package com.roman3455.deplifybot.util.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Common interface for enums that can be serialized and deserialized to/from JSON using a custom string value.
 *
 * <p>Implementing this interface allows an enum to define its own JSON representation via {@link #getValue()},
 * which will be used by Jackson during serialization and deserialization.</p>
 *
 * @see JsonEnumUtil
 */
public interface JsonEnum {

    /**
     * Returns the string value used for JSON serialization.
     *
     * <p>This method is annotated with {@link JsonValue}, which tells Jackson to use the returned value as the
     * JSON representation of the enum constant.</p>
     *
     * @return the string value corresponding to the enum constant.
     */
    @JsonValue
    String getValue();

    /**
     * Converts the provided string {@code value} to a corresponding enum constant of the specified {@code enumClass}.
     * If the value does not match any known enum constant, the provided {@code defaultValue} will be returned instead.
     *
     * <p>This static factory method is annotated with {@link JsonCreator}, which
     * allows Jackson to use it during deserialization.</p>
     *
     * @param enumClass    the enum class implementing {@code JsonEnum}.
     * @param value        the string representation to match.
     * @param defaultValue the default enum constant to return if no match is found.
     * @param <E>          the enum type.
     * @return the matching enum constant, or {@code defaultValue} if none matched.
     */
    @JsonCreator
    static <E extends Enum<E> & JsonEnum> E fromValue(Class<E> enumClass, String value, E defaultValue) {
        return JsonEnumUtil.fromValue(enumClass, value, defaultValue);
    }

}
