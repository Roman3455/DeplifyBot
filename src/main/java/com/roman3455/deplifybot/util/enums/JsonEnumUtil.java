package com.roman3455.deplifybot.util.enums;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Utility class for working with enums that implement {@link JsonEnum}.
 *
 * <p>This class provides a generic mechanism for safely mapping string values to enum constants, typically for
 * use with JSON deserialization.</p>
 * <p>If the provided value does not match any known constant, the method logs a warning and returns a default
 * fallback value (for example, {@code UNKNOWN}).</p>
 *
 * @see JsonEnum
 */
public final class JsonEnumUtil {

    private static final Logger LOG = LoggerFactory.getLogger(JsonEnumUtil.class);

    private JsonEnumUtil() {
    }

    /**
     * Resolves an enum constant from its string value.
     *
     * <p>The comparison is case-insensitive. If the provided {@code value} does not match any constant defined in
     * {@code enumClass}, this method logs a warning and returns the {@code defaultValue}.</p>
     *
     * @param enumClass    the class of the enum implementing {@link JsonEnum}.
     * @param value        the string value to resolve.
     * @param defaultValue the default enum constant to return if no match is found.
     * @param <T>          the enum type.
     * @return the resolved enum constant, or {@code defaultValue} if no match was found.
     */
    public static <T extends Enum<T> & JsonEnum> T fromValue(
            final Class<T> enumClass,
            final String value,
            final T defaultValue
    ) {
        if (value == null) {
            return defaultValue;
        }
        for (T constant : enumClass.getEnumConstants()) {
            if (constant.getValue().equalsIgnoreCase(value)) {
                return constant;
            }
        }

        LOG.warn("Unknown enum value [{}] for '{}'", value, enumClass.getSimpleName());
        return defaultValue;
    }
}
