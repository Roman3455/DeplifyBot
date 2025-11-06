package com.roman3455.deplifybot.util;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Base test support class for simplifying {@link jakarta.validation.Validator} usage in unit tests.
 *
 * <p>This class centralizes the initialization and cleanup of the {@link ValidatorFactory}
 * and provides convenient assertion helpers for validating Java Bean Validation (JSR-380) constraints
 * without repeating boilerplate code in each test class.</p>
 *
 * <h2>Features</h2>
 * <ul>
 *   <li>Automatic setup and teardown of a shared {@link ValidatorFactory} and {@link Validator} via JUnit
 *   lifecycle hooks.</li>
 *   <li>Helper assertion {@link #assertViolationContains(Object, String, String)} to verify
 *       specific constraint violations by property name and expected message content.</li>
 *   <li>Helper assertion {@link #assertValid(Object)} to verify that no constraint violations are present.</li>
 *   <li>Accessors for {@link #getValidator()} and {@link #getValidatorFactory()} for advanced scenarios.</li>
 * </ul>
 *
 * <p>Typical usage:</p>
 * <pre>{@code
 * class MyDtoValidationTest extends ValidationTestSupport {
 *
 *     @Test
 *     void shouldFailWhenNameIsNull() {
 *         MyDto dto = new MyDto(null);
 *         assertViolationContains(dto, "name", "must not be null");
 *     }
 *
 *     @Test
 *     void shouldPassForValidDto() {
 *         MyDto dto = new MyDto("ok");
 *         assertValid(dto);
 *     }
 * }
 * }</pre>
 *
 * @see jakarta.validation.Validator
 * @see jakarta.validation.ValidatorFactory
 * @see org.assertj.core.api.Assertions
 */
public abstract class ValidationTestSupport {

    private static ValidatorFactory validatorFactory;
    private static Validator validator;

    @BeforeAll
    static void initValidator() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @AfterAll
    static void closeValidator() {
        validatorFactory.close();
    }

    /**
     * Asserts that validation of the given bean produces at least one constraint violation
     * on the specified field containing the expected message fragment.
     *
     * @param bean         the object to validate
     * @param field        the expected property path of the violation
     * @param messageTemplate  a substring expected to appear in the violation message
     * @param <T>          the type of the bean under validation
     */
    protected static <T> void assertViolationContains(final T bean, final String field, final String messageTemplate) {
        Set<ConstraintViolation<T>> violations = validator.validate(bean);
        assertThat(violations)
                .as("Expected violation on '%s' containing '%s'", field, messageTemplate)
                .anySatisfy(v -> {
                    assertThat(v.getPropertyPath().toString()).isEqualTo(field);
                    assertThat(v.getMessageTemplate()).contains(messageTemplate);
                });
    }

    /**
     * Asserts that validation of the given bean produces no constraint violations.
     *
     * @param bean the object to validate
     * @param <T>  the type of the bean under validation
     */
    protected static <T> void assertValid(final T bean) {
        assertThat(validator.validate(bean)).isEmpty();
    }

    /**
     * Provides access to the shared {@link Validator} instance.
     *
     * @return the initialized {@link Validator}
     */
    protected static Validator getValidator() {
        return validator;
    }

    /**
     * Provides access to the shared {@link ValidatorFactory} instance.
     *
     * @return the initialized {@link ValidatorFactory}
     */
    protected static ValidatorFactory getValidatorFactory() {
        return validatorFactory;
    }

}
