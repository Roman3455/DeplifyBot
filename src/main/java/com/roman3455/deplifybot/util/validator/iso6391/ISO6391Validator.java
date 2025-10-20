package com.roman3455.deplifybot.util.validator.iso6391;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Locale;

/**
 * Validator implementation for the {@link ISO6391} annotation.
 *
 * <p>This class checks whether a given string value is a valid two-letter ISO 639-1 language code.
 * The validation is case-insensitive.</p>
 *
 * <ul>
 *   <li>{@code null} and empty strings are considered valid (optional fields).</li>
 *   <li>Values must be exactly two alphabetic characters long. {@value #ISO_639_1_LANGUAGE_CODE_LENGTH}</li>
 *   <li>Values must match one of the ISO 639-1 language codes returned by
 *       {@link Locale#getISOLanguages()}.</li>
 * </ul>
 *
 * <p>Examples of valid codes: {@code "en"}, {@code "ru"}, {@code "fr"}.</p>
 */
public class ISO6391Validator implements ConstraintValidator<ISO6391, String> {

    /**
     * The required length of an ISO 639-1 language code.
     */
    private static final int ISO_639_1_LANGUAGE_CODE_LENGTH = 2;

    /**
     * Validates that the given string is either {@code null}, empty, or a valid ISO 639-1 language code.
     *
     * @param value   the string value to validate.
     * @param context the validator context (unused).
     * @return {@code true} if the value is valid or optional; {@code false} otherwise.
     */
    @Override
    public boolean isValid(final String value, final ConstraintValidatorContext context) {
        if (value == null || value.isEmpty()) {
            return true;
        }
        if (value.length() != ISO_639_1_LANGUAGE_CODE_LENGTH) {
            return false;
        }
        for (String lang : Locale.getISOLanguages()) {
            if (lang.equalsIgnoreCase(value)) {
                return true;
            }
        }
        return false;
    }

}
