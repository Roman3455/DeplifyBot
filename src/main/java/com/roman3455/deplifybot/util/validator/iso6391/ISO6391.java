package com.roman3455.deplifybot.util.validator.iso6391;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Custom validation annotation that checks whether a given string is a valid ISO 639-1 language code.
 *
 * <p>This constraint accepts {@code null} or empty values as valid, allowing it to be used for optional fields.
 * If a non-empty value is provided, it must correspond to a valid two-letter ISO 639-1 language code (e.g., "en",
 * "ru").</p>
 *
 * @see ISO6391Validator
 */
@Documented
@Constraint(validatedBy = ISO6391Validator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ISO6391 {

    /**
     * The default validation message used when the value does not represent a valid ISO 639-1 language code.
     *
     * @return the error message template.
     */
    String message() default "Invalid ISO 639-1 language code";

    /**
     * Allows specification of validation groups, to which this constraint belongs.
     *
     * @return the validation groups.
     */
    Class<?>[] groups() default {};

    /**
     * Can be used by clients to assign custom payload objects to a constraint for advanced metadata use.
     *
     * @return the payload type.
     */
    Class<? extends Payload>[] payload() default {};
}
