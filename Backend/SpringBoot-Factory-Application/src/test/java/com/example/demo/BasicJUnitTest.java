package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class BasicJUnitTest {

    @Test
    @DisplayName("Should perform addition correctly")
    void additionTest() {

        // Arrange
        int a = 10;
        int b = 20;

        // Act
        int result = a + b;

        // Assert
        assertEquals(30, result);
    }

    @Test
    @DisplayName("Should verify true condition")
    void trueTest() {

        // Arrange
        boolean active = true;

        // Act
        boolean result = active;

        // Assert
        assertTrue(result);
    }

    @Test
    @DisplayName("Should verify false condition")
    void falseTest() {

        // Arrange
        boolean active = false;

        // Act
        boolean result = active;

        // Assert
        assertFalse(result);
    }

    @Test
    @DisplayName("Should verify object is not null")
    void notNullTest() {

        // Arrange
        String name = "Smart Factory";

        // Act
        String result = name;

        // Assert
        assertNotNull(result);
    }
}