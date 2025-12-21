package com.roman3455.deplifybot.test_utils;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Set;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Base contract for DTO Bean Validation tests.
 *
 * <p>This support class verifies javax/jakarta validation constraints
 * applied on a DTO type {@code T}, including:
 * <ul>
 *   <li>Positive scenarios – valid payloads pass validation without violations.</li>
 *   <li>Negative scenarios – invalid payloads trigger expected violations with:
 *       <ul>
 *         <li>expected field (property path), and</li>
 *         <li>expected violation message template substring.</li>
 *       </ul>
 *   </li>
 * </ul>
 * </p>
 *
 * @param <T> DTO type under validation.
 * @see jakarta.validation.Validator
 * @see jakarta.validation.ValidatorFactory
 * @see org.assertj.core.api.Assertions
 */
@SuppressWarnings("unused")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public abstract class DtoValidationTestSupport<T> {

    protected static final String CASE_NAME_FULL_PAYLOAD = "full payload";
    protected static final String CASE_NAME_REQUIRED_PAYLOAD = "required payload";
    protected static final String MESSAGE_TEMPLATE_NOT_NULL = "{jakarta.validation.constraints.NotNull.message}";
    protected static final String MESSAGE_TEMPLATE_NOT_BLANK = "{jakarta.validation.constraints.NotBlank.message}";
    protected static final String MESSAGE_TEMPLATE_NOT_EMPTY = "{jakarta.validation.constraints.NotEmpty.message}";
    protected static final String MESSAGE_TEMPLATE_ISO6391 = "{ISO6391.validation.constraints.message}";
    protected static final String MESSAGE_TEMPLATE_MAX_SIZE = "{Size.max.message}";
    protected static final String MESSAGE_TEMPLATE_MAX_VALUE = "{jakarta.validation.constraints.Max.message}";
    protected static final String MESSAGE_TEMPLATE_MIN_SIZE = "{Size.min.message}";
    protected static final String MESSAGE_TEMPLATE_MIN_VALUE = "{jakarta.validation.constraints.Min.message}";
    protected static final String MESSAGE_TEMPLATE_POSITIVE = "{jakarta.validation.constraints.Positive.message}";
    protected static final String MESSAGE_TEMPLATE_SIZE = "{jakarta.validation.constraints.Size.message}";

    private static ValidatorFactory validatorFactory;
    private static Validator validator;

    /**
     * Provides valid test cases for DTO validation.
     *
     * <p>Each {@link Arguments} instance <strong>must</strong> follow the contract:
     * <pre>
     * Arguments.of(
     *     String caseName,
     *     T payload
     * )
     * </pre>
     * </p>
     *
     * @return stream of valid test cases.
     */
    protected abstract Stream<Arguments> provideValidArguments();

    /**
     * Provides invalid test cases for DTO validation.
     *
     * <p>Each {@link Arguments} instance <strong>must</strong> follow the contract:
     * <pre>
     * Arguments.of(
     *     String caseName,
     *     T payload,
     *     String field,
     *     String messageTemplate
     * )
     * </pre>
     * </p>
     *
     * @return stream of invalid test cases.
     */
    protected abstract Stream<Arguments> provideInvalidArguments();

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
     * Verifies that a given invalid DTO instance triggers at least one matching violation:
     *
     * <p>The violation must reference the expected {@code field}, and its {@code messageTemplate}
     * must contain the expected substring.</p>
     */
    @ParameterizedTest(name = "[{index}] {0}")
    @MethodSource("provideInvalidArguments")
    @DisplayName("Should fail payload validation when:")
    void assertViolationContains(
            final String caseName,
            final T payload,
            final String field,
            final String messageTemplate
    ) {
        Set<ConstraintViolation<T>> violations = validator.validate(payload);
        assertThat(violations)
                .as("Expected violation on '%s' containing '%s'", field, messageTemplate)
                .anySatisfy(v -> {
                    assertThat(v.getPropertyPath()).hasToString(field);
                    assertThat(v.getMessageTemplate()).contains(messageTemplate);
                });
    }

    /**
     * Verifies that a given valid DTO instance passes Bean Validation
     * without producing any violations.
     */
    @ParameterizedTest(name = "[{index}] {0}")
    @MethodSource("provideValidArguments")
    @DisplayName("Should pass validation for valid payload:")
    void assertValid(
            final String caseName,
            final T payload
    ) {
        assertThat(validator.validate(payload)).isEmpty();
    }

}
