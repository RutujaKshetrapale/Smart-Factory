package com.example.demo.dto;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class PlantRequestValidationTest {

    private static ValidatorFactory validatorFactory;

    private static Validator validator;


    // ==========================================
    // SETUP
    // ==========================================

    @BeforeAll
    static void setUp() {

        validatorFactory =
                Validation.buildDefaultValidatorFactory();

        validator =
                validatorFactory.getValidator();
    }


    // ==========================================
    // CLEANUP
    // ==========================================

    @AfterAll
    static void tearDown() {

        validatorFactory.close();
    }


    // ==========================================
    // VALID REQUEST
    // ==========================================

    @Test
    void validPlantRequest_shouldHaveNoValidationErrors() {

        PlantRequest request = new PlantRequest();

        request.setName("Pune Manufacturing Plant");
        request.setLocation("Pune, Maharashtra");

        Set<ConstraintViolation<PlantRequest>> violations =
                validator.validate(request);

        assertTrue(
                violations.isEmpty(),
                "Valid PlantRequest should not contain validation errors"
        );
    }


    // ==========================================
    // NAME REQUIRED
    // ==========================================

    @Test
    void plantName_shouldBeRequired() {

        PlantRequest request = new PlantRequest();

        request.setName(null);
        request.setLocation("Pune, Maharashtra");

        Set<ConstraintViolation<PlantRequest>> violations =
                validator.validate(request);

        assertTrue(
                hasMessage(
                        violations,
                        "Plant name is required"
                )
        );
    }


    // ==========================================
    // NAME TOO SHORT
    // ==========================================

    @Test
    void plantName_shouldHaveMinimumTwoCharacters() {

        PlantRequest request = new PlantRequest();

        request.setName("A");
        request.setLocation("Pune, Maharashtra");

        Set<ConstraintViolation<PlantRequest>> violations =
                validator.validate(request);

        assertTrue(
                hasMessage(
                        violations,
                        "Plant name must be between 2 and 100 characters"
                )
        );
    }


    // ==========================================
    // NAME TOO LONG
    // ==========================================

    @Test
    void plantName_shouldNotExceed100Characters() {

        PlantRequest request = new PlantRequest();

        request.setName(
                "A".repeat(101)
        );

        request.setLocation("Pune, Maharashtra");

        Set<ConstraintViolation<PlantRequest>> violations =
                validator.validate(request);

        assertTrue(
                hasMessage(
                        violations,
                        "Plant name must be between 2 and 100 characters"
                )
        );
    }


    // ==========================================
    // LOCATION REQUIRED
    // ==========================================

    @Test
    void plantLocation_shouldBeRequired() {

        PlantRequest request = new PlantRequest();

        request.setName("Pune Plant");
        request.setLocation(null);

        Set<ConstraintViolation<PlantRequest>> violations =
                validator.validate(request);

        assertTrue(
                hasMessage(
                        violations,
                        "Plant location is required"
                )
        );
    }


    // ==========================================
    // LOCATION TOO SHORT
    // ==========================================

    @Test
    void plantLocation_shouldHaveMinimumTwoCharacters() {

        PlantRequest request = new PlantRequest();

        request.setName("Pune Plant");
        request.setLocation("A");

        Set<ConstraintViolation<PlantRequest>> violations =
                validator.validate(request);

        assertTrue(
                hasMessage(
                        violations,
                        "Plant location must be between 2 and 150 characters"
                )
        );
    }


    // ==========================================
    // LOCATION TOO LONG
    // ==========================================

    @Test
    void plantLocation_shouldNotExceed150Characters() {

        PlantRequest request = new PlantRequest();

        request.setName("Pune Plant");

        request.setLocation(
                "A".repeat(151)
        );

        Set<ConstraintViolation<PlantRequest>> violations =
                validator.validate(request);

        assertTrue(
                hasMessage(
                        violations,
                        "Plant location must be between 2 and 150 characters"
                )
        );
    }


    // ==========================================
    // BOTH FIELDS INVALID
    // ==========================================

    @Test
    void plantRequest_shouldReturnMultipleValidationErrors() {

        PlantRequest request = new PlantRequest();

        request.setName(null);
        request.setLocation(null);

        Set<ConstraintViolation<PlantRequest>> violations =
                validator.validate(request);

        assertTrue(
                hasMessage(
                        violations,
                        "Plant name is required"
                )
        );

        assertTrue(
                hasMessage(
                        violations,
                        "Plant location is required"
                )
        );
    }


    // ==========================================
    // HELPER METHOD
    // ==========================================

    private boolean hasMessage(
            Set<ConstraintViolation<PlantRequest>> violations,
            String expectedMessage) {

        return violations.stream()
                .anyMatch(
                        violation ->
                                violation
                                        .getMessage()
                                        .equals(expectedMessage)
                );
    }
}