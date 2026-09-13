package com.example.demo.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.demo.dto.EnergyRequest;
import com.example.demo.dto.PageResponse;
import com.example.demo.entity.Energy;
import com.example.demo.service.EnergyService;

@ExtendWith(MockitoExtension.class)
class EnergyControllerTest {

    @Mock
    private EnergyService energyService;

    @InjectMocks
    private EnergyController energyController;

    private EnergyRequest request;
    private Energy energy;

    @BeforeEach
    void setUp() {

        request = new EnergyRequest();
        request.setMachineId(1L);
        request.setEnergyConsumption(125.50);

        energy = new Energy();
        energy.setEnergyConsumption(125.50);
    }

    // =========================
    // CREATE ENERGY
    // =========================

    @Test
    @DisplayName("Should create energy successfully")
    void shouldCreateEnergySuccessfully() {

        when(energyService.create(request))
                .thenReturn(energy);

        ResponseEntity<Energy> response =
                energyController.create(request);

        assertNotNull(response);
        assertEquals(
                HttpStatus.CREATED,
                response.getStatusCode()
        );
        assertEquals(
                energy,
                response.getBody()
        );

        verify(energyService, times(1))
                .create(request);
    }

    // =========================
    // GET ALL ENERGY
    // =========================

    @Test
    @DisplayName("Should get all energy successfully")
    void shouldGetAllEnergySuccessfully() {

        when(energyService.getAll())
                .thenReturn(Arrays.asList(energy));

        ResponseEntity<?> response =
                energyController.getAll(
                        null,
                        null,
                        "id",
                        "asc"
                );

        assertNotNull(response);
        assertEquals(
                HttpStatus.OK,
                response.getStatusCode()
        );

        verify(energyService, times(1))
                .getAll();
    }

    // =========================
    // GET PAGINATED ENERGY
    // =========================

    @Test
    @DisplayName("Should get paginated energy successfully")
    void shouldGetPaginatedEnergySuccessfully() {

        org.springframework.data.domain.PageImpl<Energy> page =
                new org.springframework.data.domain.PageImpl<>(
                        Arrays.asList(energy)
                );

        when(energyService.getAll(any()))
                .thenReturn(page);

        ResponseEntity<?> response =
                energyController.getAll(
                        0,
                        10,
                        "id",
                        "asc"
                );

        assertNotNull(response);
        assertEquals(
                HttpStatus.OK,
                response.getStatusCode()
        );

        verify(energyService, times(1))
                .getAll(any());
    }

    // =========================
    // GET ENERGY BY ID
    // =========================

    @Test
    @DisplayName("Should get energy by id successfully")
    void shouldGetEnergyByIdSuccessfully() {

        when(energyService.getById(1L))
                .thenReturn(energy);

        ResponseEntity<Energy> response =
                energyController.getById(1L);

        assertNotNull(response);
        assertEquals(
                HttpStatus.OK,
                response.getStatusCode()
        );
        assertEquals(
                energy,
                response.getBody()
        );

        verify(energyService, times(1))
                .getById(1L);
    }

    // =========================
    // GET ENERGY BY MACHINE
    // =========================

    @Test
    @DisplayName("Should get energy by machine successfully")
    void shouldGetEnergyByMachineSuccessfully() {

        when(energyService.getByMachine(1L))
                .thenReturn(Arrays.asList(energy));

        ResponseEntity<?> response =
                energyController.getByMachine(
                        1L,
                        null,
                        null,
                        "id",
                        "asc"
                );

        assertNotNull(response);
        assertEquals(
                HttpStatus.OK,
                response.getStatusCode()
        );

        verify(energyService, times(1))
                .getByMachine(1L);
    }

    // =========================
    // GET PAGINATED ENERGY BY MACHINE
    // =========================

    @Test
    @DisplayName("Should get paginated energy by machine successfully")
    void shouldGetPaginatedEnergyByMachineSuccessfully() {

        org.springframework.data.domain.PageImpl<Energy> page =
                new org.springframework.data.domain.PageImpl<>(
                        Arrays.asList(energy)
                );

        when(energyService.getByMachine(
                eq(1L),
                any()
        ))
                .thenReturn(page);

        ResponseEntity<?> response =
                energyController.getByMachine(
                        1L,
                        0,
                        10,
                        "id",
                        "asc"
                );

        assertNotNull(response);
        assertEquals(
                HttpStatus.OK,
                response.getStatusCode()
        );

        verify(energyService, times(1))
                .getByMachine(eq(1L), any());
    }

    // =========================
    // UPDATE ENERGY
    // =========================

    @Test
    @DisplayName("Should update energy successfully")
    void shouldUpdateEnergySuccessfully() {

        when(energyService.update(
                1L,
                request
        ))
                .thenReturn(energy);

        ResponseEntity<Energy> response =
                energyController.update(
                        1L,
                        request
                );

        assertNotNull(response);
        assertEquals(
                HttpStatus.OK,
                response.getStatusCode()
        );

        verify(energyService, times(1))
                .update(1L, request);
    }

    // =========================
    // DELETE ENERGY
    // =========================

    @Test
    @DisplayName("Should delete energy successfully")
    void shouldDeleteEnergySuccessfully() {

        ResponseEntity<Void> response =
                energyController.delete(1L);

        assertNotNull(response);
        assertEquals(
                HttpStatus.NO_CONTENT,
                response.getStatusCode()
        );

        verify(energyService, times(1))
                .delete(1L);
    }
}