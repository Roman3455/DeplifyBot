package com.roman3455.deplifybot.util.validator.bytes_length;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.nio.charset.Charset;

/**
 * Validator implementation for the {@link BytesLength} annotation.
 *
 * <p>Ensures that the length of the string, in bytes, is within the specified range. The validator uses the specified
 * charset to calculate the byte length, which may differ from the character length (especially for multibyte
 * characters in encodings like UTF-8).</p>
 */
public class BytesLengthValidator implements ConstraintValidator<BytesLength, String> {

    private int min;
    private int max;
    private Charset charset;

    /**
     * Initializes the validator with the constraint annotation.
     *
     * <p>This method extracts the configuration parameters (min, max, charset) from the annotation
     * and prepares the validator for the validation process.</p>
     *
     * @param constraintAnnotation The annotation instance that holds the configuration values for validation.
     */
    @Override
    public void initialize(final BytesLength constraintAnnotation) {
        this.min = constraintAnnotation.min();
        this.max = constraintAnnotation.max();
        this.charset = Charset.forName(constraintAnnotation.charset());
    }

    /**
     * Validates the byte length of the given string.
     *
     * <p>This method checks whether the byte length of the provided string falls within the specified
     * range defined by the min and max parameters of the annotation.
     * The length is calculated using the specified charset (default is UTF-8).</p>
     *
     * @param value The string to validate.
     * @param context Contextual data and state about the validation process.
     * @return {@code true} if the string's byte length is within the valid range, otherwise {@code false}.
     */
    @Override
    public boolean isValid(final String value, final ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        int bytesLength = value.getBytes(charset).length;
        return bytesLength >= min && bytesLength <= max;
    }

}
