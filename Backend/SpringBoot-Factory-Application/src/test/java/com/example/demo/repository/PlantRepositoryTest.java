package com.example.demo.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.example.demo.entity.Plant;

@DataJpaTest
class PlantRepositoryTest {

    @Autowired
    private PlantRepository plantRepository;

    // =========================
    // SAVE PLANT
    // =========================

    @Test
    @DisplayName("Should save plant successfully")
    void shouldSavePlantSuccessfully() {

        Plant plant = new Plant(
                "Pune Manufacturing Plant",
                "Pune, Maharashtra",
                true
        );

        Plant savedPlant = plantRepository.save(plant);

        assertTrue(savedPlant.getId() > 0);

        assertEquals(
                "Pune Manufacturing Plant",
                savedPlant.getName()
        );

    }

    // =========================
    // FIND ACTIVE PLANTS
    // =========================

    @Test
    @DisplayName("Should find active plants successfully")
    void shouldFindActivePlantsSuccessfully() {

        plantRepository.save(
                new Plant(
                        "Pune Plant",
                        "Pune",
                        true
                )
        );

        plantRepository.save(
                new Plant(
                        "Mumbai Plant",
                        "Mumbai",
                        false
                )
        );

        List<Plant> plants =
                plantRepository.findByActiveTrue();

        assertEquals(
                1,
                plants.size()
        );

        assertEquals(
                "Pune Plant",
                plants.get(0).getName()
        );

    }

    // =========================
    // FIND BY NAME
    // =========================

    @Test
    @DisplayName("Should find plant by name ignoring case")
    void shouldFindPlantByNameIgnoringCase() {

        plantRepository.save(
                new Plant(
                        "Pune Manufacturing Plant",
                        "Pune",
                        true
                )
        );

        assertTrue(
                plantRepository
                        .findByNameIgnoreCase(
                                "pune manufacturing plant"
                        )
                        .isPresent()
        );

    }

    // =========================
    // EXISTS BY NAME
    // =========================

    @Test
    @DisplayName("Should check plant name existence")
    void shouldCheckPlantNameExistence() {

        plantRepository.save(
                new Plant(
                        "Pune Plant",
                        "Pune",
                        true
                )
        );

        assertTrue(
                plantRepository
                        .existsByNameIgnoreCase(
                                "pune plant"
                        )
        );

        assertFalse(
                plantRepository
                        .existsByNameIgnoreCase(
                                "Delhi Plant"
                        )
        );

    }

    // =========================
    // PAGINATION
    // =========================

    @Test
    @DisplayName("Should get paginated plants successfully")
    void shouldGetPaginatedPlantsSuccessfully() {

        plantRepository.save(
                new Plant(
                        "Plant 1",
                        "Pune",
                        true
                )
        );

        plantRepository.save(
                new Plant(
                        "Plant 2",
                        "Mumbai",
                        true
                )
        );

        Page<Plant> result =
                plantRepository.findAll(
                        PageRequest.of(0, 1)
                );

        assertEquals(
                1,
                result.getContent().size()
        );

        assertEquals(
                2,
                result.getTotalElements()
        );

    }

}