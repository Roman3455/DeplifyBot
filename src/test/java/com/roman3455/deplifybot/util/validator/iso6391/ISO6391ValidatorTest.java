package com.roman3455.deplifybot.util.validator.iso6391;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ISO6391ValidatorTest {

    private static ValidatorFactory validatorFactory;
    private static Validator validator;

    @BeforeAll
    public static void setUp() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @AfterAll
    public static void tearDown() {
        validatorFactory.close();
    }

    record TestDto(@ISO6391 String languageCode) {
    }

    @Test
    void testNullLanguageCode() {
        TestDto dto = new TestDto(null);
        Set<ConstraintViolation<TestDto>> violations = validator.validate(dto);
        assertTrue(violations.isEmpty(), "Null value should be valid");
    }

    @Test
    void testEmptyLanguageCode() {
        TestDto dto = new TestDto("");
        Set<ConstraintViolation<TestDto>> violations = validator.validate(dto);
        assertTrue(violations.isEmpty(), "Empty string should be valid");
    }

    @Test
    void testValidCodes() {
        TestDto lowercaseDto = new TestDto("en");
        TestDto uppercaseDto = new TestDto("RU");
        Set<ConstraintViolation<TestDto>> violations1 = validator.validate(lowercaseDto);
        Set<ConstraintViolation<TestDto>> violations2 = validator.validate(uppercaseDto);
        assertTrue(violations1.isEmpty(), "'en' should be valid");
        assertTrue(violations2.isEmpty(), "'RU' should be valid (case-insensitive)");
    }

    @Test
    void testInvalidCodes() {
        TestDto shortCodeDto = new TestDto("x");
        TestDto longCodeDto = new TestDto("eng");
        TestDto unexistedCodeDto = new TestDto("zz");
        Set<ConstraintViolation<TestDto>> violations1 = validator.validate(shortCodeDto);
        Set<ConstraintViolation<TestDto>> violations2 = validator.validate(longCodeDto);
        Set<ConstraintViolation<TestDto>> violations3 = validator.validate(unexistedCodeDto);
        assertEquals(1, violations1.size(), "'x' should be invalid");
        assertEquals(1, violations2.size(), "'eng' should be invalid");
        assertEquals(1, violations3.size(), "'zz' should be invalid");
    }
}
