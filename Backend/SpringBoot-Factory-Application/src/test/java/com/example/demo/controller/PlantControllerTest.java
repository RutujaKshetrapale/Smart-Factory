package com.example.demo.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.demo.dto.PlantRequest;
import com.example.demo.entity.Plant;
import com.example.demo.service.PlantService;

@ExtendWith(MockitoExtension.class)
class PlantControllerTest {

    @Mock
    private PlantService plantService;

    @InjectMocks
    private PlantController plantController;

    private PlantRequest request;
    private Plant plant;

    @BeforeEach
    void setUp() {

        request = new PlantRequest();
        request.setName("Pune Manufacturing Plant");
        request.setLocation("Pune, Maharashtra");

        plant = new Plant();
        plant.setName("Pune Manufacturing Plant");
        plant.setLocation("Pune, Maharashtra");
        plant.setActive(true);
    }

    // =========================
    // CREATE PLANT
    // =========================

    @Test
    @DisplayName("Should create plant successfully")
    void shouldCreatePlantSuccessfully() {

        when(plantService.create(request))
                .thenReturn(plant);

        ResponseEntity<Plant> response =
                plantController.create(request);

        assertNotNull(response);
        assertEquals(
                HttpStatus.CREATED,
                response.getStatusCode()
        );
        assertEquals(
                plant,
                response.getBody()
        );

        verify(plantService, times(1))
                .create(request);
    }

    // =========================
    // GET PLANT BY ID
    // =========================

    @Test
    @DisplayName("Should get plant by id successfully")
    void shouldGetPlantByIdSuccessfully() {

        when(plantService.getById(1L))
                .thenReturn(plant);

        ResponseEntity<Plant> response =
                plantController.getById(1L);

        assertNotNull(response);
        assertEquals(
                HttpStatus.OK,
                response.getStatusCode()
        );
        assertEquals(
                plant,
                response.getBody()
        );

        verify(plantService, times(1))
                .getById(1L);
    }
}