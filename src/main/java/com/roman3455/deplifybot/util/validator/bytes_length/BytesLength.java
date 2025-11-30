package com.roman3455.deplifybot.util.validator.bytes_length;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Custom annotation to validate the byte length of a string.
 *
 * <p>Ensures that the string's byte length is within the specified range. Can be applied to fields or parameters.
 * The validation is performed based on the specified charset (default is UTF-8).</p>
 */
@Documented
@Constraint(validatedBy = BytesLengthValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface BytesLength {

    /**
     * Error message to be used when validation fails.
     */
    String message() default "{BytesLength.validation.constraints.message}";

    /**
     * Groups for the constraint (used for grouping constraints in validation).
     */
    Class<?>[] groups() default {};

    /**
     * Payload for the constraint (can be used to carry additional data).
     */
    Class<? extends Payload>[] payload() default {};

    /**
     * Minimum byte length (inclusive).
     */
    int min() default 0;

    /**
     * Maximum byte length (inclusive).
     */
    int max() default Integer.MAX_VALUE;

    /**
     * Charset used for calculating the byte length (default is UTF-8).
     */
    String charset() default "UTF-8";

}
