package com.example.demo.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import com.example.demo.entity.Role;
import com.example.demo.entity.User;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    // =========================
    // SAVE USER
    // =========================

    @Test
    @DisplayName("Should save user successfully")
    void shouldSaveUserSuccessfully() {

        User user =
                createUser(
                        "rutuja",
                        "rutuja@example.com"
                );

        User savedUser =
                userRepository.save(user);

        assertTrue(
                savedUser.getId() > 0
        );

        assertEquals(
                "rutuja",
                savedUser.getUsername()
        );

    }

    // =========================
    // FIND BY USERNAME
    // =========================

    @Test
    @DisplayName("Should find user by username successfully")
    void shouldFindUserByUsernameSuccessfully() {

        userRepository.save(
                createUser(
                        "rutuja",
                        "rutuja@example.com"
                )
        );

        assertTrue(
                userRepository
                        .findByUsername(
                                "rutuja"
                        )
                        .isPresent()
        );

    }

    // =========================
    // EXISTS BY USERNAME
    // =========================

    @Test
    @DisplayName("Should check username existence")
    void shouldCheckUsernameExistence() {

        userRepository.save(
                createUser(
                        "rutuja",
                        "rutuja@example.com"
                )
        );

        assertTrue(
                userRepository
                        .existsByUsername(
                                "rutuja"
                        )
        );

    }

    // =========================
    // EXISTS BY EMAIL
    // =========================

    @Test
    @DisplayName("Should check email existence")
    void shouldCheckEmailExistence() {

        userRepository.save(
                createUser(
                        "rutuja",
                        "rutuja@example.com"
                )
        );

        assertTrue(
                userRepository
                        .existsByEmail(
                                "rutuja@example.com"
                        )
        );

    }

    // =========================
    // HELPER METHODS
    // =========================

    private User createUser(
            String username,
            String email) {

        User user =
                new User();

        user.setUsername(
                username
        );

        user.setEmail(
                email
        );

        user.setPassword(
                "password123"
        );

        user.setRole(
                Role.ENGINEER
        );

        user.setActive(
                true
        );

        return user;
    }

}