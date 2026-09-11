package com.example.demo.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import static org.mockito.ArgumentMatchers.any;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.example.demo.dto.PlantRequest;
import com.example.demo.entity.Plant;
import com.example.demo.exception.BusinessValidationException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.PlantRepository;

@ExtendWith(MockitoExtension.class)
class PlantServiceTest {

    @Mock
    private PlantRepository plantRepository;

    @InjectMocks
    private PlantService plantService;

    private Plant plant;
    private Plant plant2;
    private PlantRequest request;

    @BeforeEach
    void setUp() {

        plant = new Plant();
        plant.setName("Pune Manufacturing Plant");
        plant.setLocation("Pune, Maharashtra");
        plant.setActive(true);

        plant2 = new Plant();
        plant2.setName("Mumbai Manufacturing Plant");
        plant2.setLocation("Mumbai, Maharashtra");
        plant2.setActive(true);

        request = new PlantRequest();
        request.setName("Pune Manufacturing Plant");
        request.setLocation("Pune, Maharashtra");
    }

    // =========================================================
    // CREATE
    // =========================================================

    @Test
    @DisplayName("Should create plant successfully")
    void shouldCreatePlantSuccessfully() {

        when(
                plantRepository.existsByNameIgnoreCase(
                        "Pune Manufacturing Plant"
                )
        ).thenReturn(false);

        when(
                plantRepository.save(any(Plant.class))
        ).thenReturn(plant);

        Plant result = plantService.create(request);

        assertNotNull(result);

        assertEquals(
                "Pune Manufacturing Plant",
                result.getName()
        );

        assertEquals(
                "Pune, Maharashtra",
                result.getLocation()
        );

        assertTrue(result.isActive());

        verify(
                plantRepository,
                times(1)
        ).existsByNameIgnoreCase(
                "Pune Manufacturing Plant"
        );

        verify(
                plantRepository,
                times(1)
        ).save(any(Plant.class));
    }

    @Test
    @DisplayName("Should reject duplicate plant name")
    void shouldRejectDuplicatePlantName() {

        when(
                plantRepository.existsByNameIgnoreCase(
                        "Pune Manufacturing Plant"
                )
        ).thenReturn(true);

        BusinessValidationException exception =
                assertThrows(
                        BusinessValidationException.class,
                        () -> plantService.create(request)
                );

        assertEquals(
                "Plant with this name already exists",
                exception.getMessage()
        );

        verify(
                plantRepository,
                times(1)
        ).existsByNameIgnoreCase(
                "Pune Manufacturing Plant"
        );

        verify(
                plantRepository,
                never()
        ).save(any(Plant.class));
    }

    // =========================================================
    // GET ALL
    // =========================================================

    @Test
    @DisplayName("Should get all plants")
    void shouldGetAllPlants() {

        List<Plant> plants =
                Arrays.asList(
                        plant,
                        plant2
                );

        when(
                plantRepository.findAll()
        ).thenReturn(plants);

        List<Plant> result =
                plantService.getAll();

        assertNotNull(result);

        assertEquals(
                2,
                result.size()
        );

        assertEquals(
                "Pune Manufacturing Plant",
                result.get(0).getName()
        );

        assertEquals(
                "Mumbai Manufacturing Plant",
                result.get(1).getName()
        );

        verify(
                plantRepository,
                times(1)
        ).findAll();
    }

    // =========================================================
    // GET ALL - PAGINATION
    // =========================================================

    @Test
    @DisplayName("Should get all plants with pagination")
    void shouldGetAllPlantsWithPagination() {

        Pageable pageable =
                PageRequest.of(0, 2);

        List<Plant> plants =
                Arrays.asList(
                        plant,
                        plant2
                );

        Page<Plant> page =
                new PageImpl<>(
                        plants,
                        pageable,
                        2
                );

        when(
                plantRepository.findAll(pageable)
        ).thenReturn(page);

        Page<Plant> result =
                plantService.getAll(pageable);

        assertNotNull(result);

        assertEquals(
                2,
                result.getContent().size()
        );

        assertEquals(
                2,
                result.getTotalElements()
        );

        assertEquals(
                1,
                result.getTotalPages()
        );

        verify(
                plantRepository,
                times(1)
        ).findAll(pageable);
    }

    // =========================================================
    // GET BY ID
    // =========================================================

    @Test
    @DisplayName("Should get plant by ID")
    void shouldGetPlantById() {

        when(
                plantRepository.findById(1L)
        ).thenReturn(
                Optional.of(plant)
        );

        Plant result =
                plantService.getById(1L);

        assertNotNull(result);

        assertEquals(
                "Pune Manufacturing Plant",
                result.getName()
        );

        verify(
                plantRepository,
                times(1)
        ).findById(1L);
    }

    @Test
    @DisplayName("Should throw exception when plant is not found")
    void shouldThrowExceptionWhenPlantNotFound() {

        when(
                plantRepository.findById(999L)
        ).thenReturn(
                Optional.empty()
        );

        ResourceNotFoundException exception =
                assertThrows(
                        ResourceNotFoundException.class,
                        () -> plantService.getById(999L)
                );

        assertTrue(
                exception.getMessage()
                        .contains(
                                "PLANT NOT FOUND: 999"
                        )
        );

        verify(
                plantRepository,
                times(1)
        ).findById(999L);
    }

    // =========================================================
    // GET ACTIVE
    // =========================================================

    @Test
    @DisplayName("Should get active plants")
    void shouldGetActivePlants() {

        List<Plant> activePlants =
                Arrays.asList(
                        plant,
                        plant2
                );

        when(
                plantRepository.findByActiveTrue()
        ).thenReturn(activePlants);

        List<Plant> result =
                plantService.getActive();

        assertNotNull(result);

        assertEquals(
                2,
                result.size()
        );

        assertTrue(
                result.get(0).isActive()
        );

        assertTrue(
                result.get(1).isActive()
        );

        verify(
                plantRepository,
                times(1)
        ).findByActiveTrue();
    }

    // =========================================================
    // GET ACTIVE - PAGINATION
    // =========================================================

    @Test
    @DisplayName("Should get active plants with pagination")
    void shouldGetActivePlantsWithPagination() {

        Pageable pageable =
                PageRequest.of(0, 2);

        List<Plant> activePlants =
                Arrays.asList(
                        plant,
                        plant2
                );

        Page<Plant> page =
                new PageImpl<>(
                        activePlants,
                        pageable,
                        2
                );

        when(
                plantRepository.findByActiveTrue(pageable)
        ).thenReturn(page);

        Page<Plant> result =
                plantService.getActive(pageable);

        assertNotNull(result);

        assertEquals(
                2,
                result.getContent().size()
        );

        assertEquals(
                2,
                result.getTotalElements()
        );

        verify(
                plantRepository,
                times(1)
        ).findByActiveTrue(pageable);
    }

    // =========================================================
    // GET INACTIVE
    // =========================================================

    @Test
    @DisplayName("Should get inactive plants")
    void shouldGetInactivePlants() {

        Plant inactivePlant =
                new Plant();

        inactivePlant.setName(
                "Inactive Plant"
        );

        inactivePlant.setLocation(
                "Nashik, Maharashtra"
        );

        inactivePlant.setActive(false);

        List<Plant> inactivePlants =
                Arrays.asList(
                        inactivePlant
                );

        when(
                plantRepository.findByActiveFalse()
        ).thenReturn(inactivePlants);

        List<Plant> result =
                plantService.getInactive();

        assertNotNull(result);

        assertEquals(
                1,
                result.size()
        );

        assertTrue(
                !result.get(0).isActive()
        );

        verify(
                plantRepository,
                times(1)
        ).findByActiveFalse();
    }

    // =========================================================
    // GET INACTIVE - PAGINATION
    // =========================================================

    @Test
    @DisplayName("Should get inactive plants with pagination")
    void shouldGetInactivePlantsWithPagination() {

        Pageable pageable =
                PageRequest.of(0, 2);

        Plant inactivePlant =
                new Plant();

        inactivePlant.setName(
                "Inactive Plant"
        );

        inactivePlant.setLocation(
                "Nashik, Maharashtra"
        );

        inactivePlant.setActive(false);

        List<Plant> inactivePlants =
                Arrays.asList(
                        inactivePlant
                );

        Page<Plant> page =
                new PageImpl<>(
                        inactivePlants,
                        pageable,
                        1
                );

        when(
                plantRepository.findByActiveFalse(pageable)
        ).thenReturn(page);

        Page<Plant> result =
                plantService.getInactive(pageable);

        assertNotNull(result);

        assertEquals(
                1,
                result.getContent().size()
        );

        assertEquals(
                1,
                result.getTotalElements()
        );

        verify(
                plantRepository,
                times(1)
        ).findByActiveFalse(pageable);
    }

    // =========================================================
    // SEARCH BY NAME
    // =========================================================

    @Test
    @DisplayName("Should search plants by name")
    void shouldSearchPlantsByName() {

        List<Plant> plants =
                Arrays.asList(
                        plant
                );

        when(
                plantRepository
                        .findByNameContainingIgnoreCase(
                                "Pune"
                        )
        ).thenReturn(plants);

        List<Plant> result =
                plantService.searchByName(
                        "Pune"
                );

        assertNotNull(result);

        assertEquals(
                1,
                result.size()
        );

        assertEquals(
                "Pune Manufacturing Plant",
                result.get(0).getName()
        );

        verify(
                plantRepository,
                times(1)
        ).findByNameContainingIgnoreCase(
                "Pune"
        );
    }

    // =========================================================
    // SEARCH BY NAME - PAGINATION
    // =========================================================

    @Test
    @DisplayName("Should search plants by name with pagination")
    void shouldSearchPlantsByNameWithPagination() {

        Pageable pageable =
                PageRequest.of(0, 5);

        List<Plant> plants =
                Arrays.asList(
                        plant
                );

        Page<Plant> page =
                new PageImpl<>(
                        plants,
                        pageable,
                        1
                );

        when(
                plantRepository
                        .findByNameContainingIgnoreCase(
                                "Pune",
                                pageable
                        )
        ).thenReturn(page);

        Page<Plant> result =
                plantService.searchByName(
                        "Pune",
                        pageable
                );

        assertNotNull(result);

        assertEquals(
                1,
                result.getContent().size()
        );

        assertEquals(
                1,
                result.getTotalElements()
        );

        verify(
                plantRepository,
                times(1)
        ).findByNameContainingIgnoreCase(
                "Pune",
                pageable
        );
    }

    // =========================================================
    // SEARCH BY LOCATION
    // =========================================================

    @Test
    @DisplayName("Should search plants by location")
    void shouldSearchPlantsByLocation() {

        List<Plant> plants =
                Arrays.asList(
                        plant,
                        plant2
                );

        when(
                plantRepository
                        .findByLocationContainingIgnoreCase(
                                "Maharashtra"
                        )
        ).thenReturn(plants);

        List<Plant> result =
                plantService.searchByLocation(
                        "Maharashtra"
                );

        assertNotNull(result);

        assertEquals(
                2,
                result.size()
        );

        verify(
                plantRepository,
                times(1)
        ).findByLocationContainingIgnoreCase(
                "Maharashtra"
        );
    }

    // =========================================================
    // SEARCH BY LOCATION - PAGINATION
    // =========================================================

    @Test
    @DisplayName("Should search plants by location with pagination")
    void shouldSearchPlantsByLocationWithPagination() {

        Pageable pageable =
                PageRequest.of(0, 5);

        List<Plant> plants =
                Arrays.asList(
                        plant,
                        plant2
                );

        Page<Plant> page =
                new PageImpl<>(
                        plants,
                        pageable,
                        2
                );

        when(
                plantRepository
                        .findByLocationContainingIgnoreCase(
                                "Maharashtra",
                                pageable
                        )
        ).thenReturn(page);

        Page<Plant> result =
                plantService.searchByLocation(
                        "Maharashtra",
                        pageable
                );

        assertNotNull(result);

        assertEquals(
                2,
                result.getContent().size()
        );

        assertEquals(
                2,
                result.getTotalElements()
        );

        verify(
                plantRepository,
                times(1)
        ).findByLocationContainingIgnoreCase(
                "Maharashtra",
                pageable
        );
    }

    // =========================================================
    // UPDATE
    // =========================================================

    @Test
    @DisplayName("Should update plant successfully")
    void shouldUpdatePlantSuccessfully() {

        PlantRequest updateRequest =
                new PlantRequest();

        updateRequest.setName(
                "Pune Smart Manufacturing Plant"
        );

        updateRequest.setLocation(
                "Pune, Maharashtra"
        );

        when(
                plantRepository.findById(1L)
        ).thenReturn(
                Optional.of(plant)
        );

        when(
                plantRepository.save(any(Plant.class))
        ).thenReturn(plant);

        Plant result =
                plantService.update(
                        1L,
                        updateRequest
                );

        assertNotNull(result);

        assertEquals(
                "Pune Smart Manufacturing Plant",
                result.getName()
        );

        assertEquals(
                "Pune, Maharashtra",
                result.getLocation()
        );

        verify(
                plantRepository,
                times(1)
        ).findById(1L);

        verify(
                plantRepository,
                times(1)
        ).existsByNameIgnoreCase(
                "Pune Smart Manufacturing Plant"
        );

        verify(
                plantRepository,
                times(1)
        ).save(any(Plant.class)
        );
    }

    // =========================================================
    // UPDATE - DUPLICATE NAME
    // =========================================================

    @Test
    @DisplayName("Should reject update when plant name already exists")
    void shouldRejectUpdateWhenPlantNameAlreadyExists() {

        PlantRequest updateRequest =
                new PlantRequest();

        updateRequest.setName(
                "Mumbai Manufacturing Plant"
        );

        updateRequest.setLocation(
                "Pune, Maharashtra"
        );

        when(
                plantRepository.findById(1L)
        ).thenReturn(
                Optional.of(plant)
        );

        when(
                plantRepository.existsByNameIgnoreCase(
                        "Mumbai Manufacturing Plant"
                )
        ).thenReturn(true);

        BusinessValidationException exception =
                assertThrows(
                        BusinessValidationException.class,
                        () -> plantService.update(
                                1L,
                                updateRequest
                        )
                );

        assertEquals(
                "Plant with this name already exists",
                exception.getMessage()
        );

        verify(
                plantRepository,
                times(1)
        ).findById(1L);

        verify(
                plantRepository,
                times(1)
        ).existsByNameIgnoreCase(
                "Mumbai Manufacturing Plant"
        );

        verify(
                plantRepository,
                never()
        ).save(any(Plant.class));
    }

    // =========================================================
    // DELETE
    // =========================================================

    @Test
    @DisplayName("Should delete plant successfully")
    void shouldDeletePlantSuccessfully() {

        when(
                plantRepository.findById(1L)
        ).thenReturn(
                Optional.of(plant)
        );

        plantService.delete(1L);

        verify(
                plantRepository,
                times(1)
        ).findById(1L);

        verify(
                plantRepository,
                times(1)
        ).delete(plant);
    }
}