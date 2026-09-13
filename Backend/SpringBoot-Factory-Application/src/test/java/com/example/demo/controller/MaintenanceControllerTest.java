package com.example.demo.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

import static org.mockito.Mockito.when;

import java.time.LocalDate;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import org.springframework.http.ResponseEntity;

import com.example.demo.dto.MaintenanceRequest;
import com.example.demo.entity.Maintenance;
import com.example.demo.service.MaintenanceService;

class MaintenanceControllerTest {

    @Mock
    private MaintenanceService maintenanceService;

    @InjectMocks
    private MaintenanceController maintenanceController;

    private MaintenanceRequest request;

    private Maintenance maintenance;

    @BeforeEach
    void setUp() {

        MockitoAnnotations.openMocks(this);

        request = new MaintenanceRequest();

        request.setMachineId(1L);

        request.setType("PREVENTIVE");

        request.setDescription(
                "Routine machine maintenance"
        );

        request.setScheduledDate(
                LocalDate.now().plusDays(1)
        );

        request.setStatus("SCHEDULED");

        request.setTechnician("John");

        maintenance = new Maintenance();

    }

    // =========================
    // CREATE MAINTENANCE
    // =========================

    @Test
    @DisplayName("Should create maintenance successfully")
    void shouldCreateMaintenanceSuccessfully() {

        when(maintenanceService.create(
                any(MaintenanceRequest.class)))

                .thenReturn(maintenance);

        ResponseEntity<Maintenance> result =

                maintenanceController.create(request);

        assertNotNull(result);

        assertEquals(
                201,
                result.getStatusCode().value()
        );

        assertEquals(
                maintenance,
                result.getBody()
        );

    }

    // =========================
    // GET ALL MAINTENANCE
    // =========================

    @Test
    @DisplayName("Should get all maintenance successfully")
    void shouldGetAllMaintenanceSuccessfully() {

        Maintenance maintenance1 = new Maintenance();

        Maintenance maintenance2 = new Maintenance();

        when(maintenanceService.getAll())

                .thenReturn(

                        List.of(

                                maintenance1,

                                maintenance2

                        )

                );

        ResponseEntity<?> result =

                maintenanceController.getAll(
                        null,
                        null,
                        "id",
                        "asc"
                );

        assertNotNull(result);

        assertEquals(
                200,
                result.getStatusCode().value()
        );

        List<?> response =

                (List<?>) result.getBody();

        assertNotNull(response);

        assertEquals(
                2,
                response.size()
        );

    }

    // =========================
    // GET ALL MAINTENANCE
    // PAGINATED
    // =========================

    @Test
    @DisplayName("Should get paginated maintenance successfully")
    void shouldGetPaginatedMaintenanceSuccessfully() {

        Maintenance maintenance1 = new Maintenance();

        Maintenance maintenance2 = new Maintenance();

        when(maintenanceService.getAll(
                any(org.springframework.data.domain.Pageable.class)))

                .thenReturn(

                        new org.springframework.data.domain.PageImpl<>(
                                List.of(
                                        maintenance1,
                                        maintenance2
                                ),
                                org.springframework.data.domain.PageRequest.of(
                                        0,
                                        10
                                ),
                                2
                        )

                );

        ResponseEntity<?> result =

                maintenanceController.getAll(
                        0,
                        10,
                        "id",
                        "asc"
                );

        assertNotNull(result);

        assertEquals(
                200,
                result.getStatusCode().value()
        );

        assertNotNull(result.getBody());

    }

    // =========================
    // GET MAINTENANCE BY ID
    // =========================

    @Test
    @DisplayName("Should get maintenance by id successfully")
    void shouldGetMaintenanceByIdSuccessfully() {

        when(maintenanceService.getById(1L))

                .thenReturn(maintenance);

        ResponseEntity<Maintenance> result =

                maintenanceController.getById(1L);

        assertNotNull(result);

        assertEquals(
                200,
                result.getStatusCode().value()
        );

        assertEquals(
                maintenance,
                result.getBody()
        );

    }

    // =========================
    // GET MAINTENANCE BY MACHINE
    // =========================

    @Test
    @DisplayName("Should get maintenance by machine successfully")
    void shouldGetMaintenanceByMachineSuccessfully() {

        Maintenance maintenance1 = new Maintenance();

        Maintenance maintenance2 = new Maintenance();

        when(maintenanceService.getByMachine(1L))

                .thenReturn(

                        List.of(

                                maintenance1,
                                maintenance2

                        )

                );

        ResponseEntity<?> result =

                maintenanceController.getByMachine(
                        1L,
                        null,
                        null,
                        "id",
                        "asc"
                );

        assertNotNull(result);

        assertEquals(
                200,
                result.getStatusCode().value()
        );

        List<?> response =

                (List<?>) result.getBody();

        assertNotNull(response);

        assertEquals(
                2,
                response.size()
        );

    }

    // =========================
    // GET MAINTENANCE BY MACHINE
    // PAGINATED
    // =========================

    @Test
    @DisplayName("Should get paginated maintenance by machine successfully")
    void shouldGetPaginatedMaintenanceByMachineSuccessfully() {

        Maintenance maintenance1 = new Maintenance();

        Maintenance maintenance2 = new Maintenance();

        when(maintenanceService.getByMachine(
                eq(1L),
                any(org.springframework.data.domain.Pageable.class)))

                .thenReturn(

                        new org.springframework.data.domain.PageImpl<>(
                                List.of(
                                        maintenance1,
                                        maintenance2
                                ),
                                org.springframework.data.domain.PageRequest.of(
                                        0,
                                        10
                                ),
                                2
                        )

                );

        ResponseEntity<?> result =

                maintenanceController.getByMachine(
                        1L,
                        0,
                        10,
                        "id",
                        "asc"
                );

        assertNotNull(result);

        assertEquals(
                200,
                result.getStatusCode().value()
        );

        assertNotNull(result.getBody());

    }

    // =========================
    // GET MAINTENANCE BY STATUS
    // =========================

    @Test
    @DisplayName("Should get maintenance by status successfully")
    void shouldGetMaintenanceByStatusSuccessfully() {

        Maintenance maintenance1 = new Maintenance();

        Maintenance maintenance2 = new Maintenance();

        when(maintenanceService.getByStatus(
                "SCHEDULED"))

                .thenReturn(

                        List.of(

                                maintenance1,
                                maintenance2

                        )

                );

        ResponseEntity<?> result =

                maintenanceController.getByStatus(
                        "SCHEDULED",
                        null,
                        null,
                        "id",
                        "asc"
                );

        assertNotNull(result);

        assertEquals(
                200,
                result.getStatusCode().value()
        );

        List<?> response =

                (List<?>) result.getBody();

        assertNotNull(response);

        assertEquals(
                2,
                response.size()
        );

    }

    // =========================
    // GET MAINTENANCE BY STATUS
    // PAGINATED
    // =========================

    @Test
    @DisplayName("Should get paginated maintenance by status successfully")
    void shouldGetPaginatedMaintenanceByStatusSuccessfully() {

        Maintenance maintenance1 = new Maintenance();

        Maintenance maintenance2 = new Maintenance();

        when(maintenanceService.getByStatus(
                eq("SCHEDULED"),
                any(org.springframework.data.domain.Pageable.class)))

                .thenReturn(

                        new org.springframework.data.domain.PageImpl<>(
                                List.of(
                                        maintenance1,
                                        maintenance2
                                ),
                                org.springframework.data.domain.PageRequest.of(
                                        0,
                                        10
                                ),
                                2
                        )

                );

        ResponseEntity<?> result =

                maintenanceController.getByStatus(
                        "SCHEDULED",
                        0,
                        10,
                        "id",
                        "asc"
                );

        assertNotNull(result);

        assertEquals(
                200,
                result.getStatusCode().value()
        );

        assertNotNull(result.getBody());

    }

    // =========================
    // GET MAINTENANCE BY STATUS
    // ORDERED BY DATE
    // =========================

    @Test
    @DisplayName("Should get maintenance by status ordered by date successfully")
    void shouldGetMaintenanceByStatusOrderedByDateSuccessfully() {

        Maintenance maintenance1 = new Maintenance();

        Maintenance maintenance2 = new Maintenance();

        when(maintenanceService.getByStatusOrderByDate(
                "SCHEDULED"))

                .thenReturn(

                        List.of(

                                maintenance1,
                                maintenance2

                        )

                );

        ResponseEntity<?> result =

                maintenanceController.getByStatusSorted(
                        "SCHEDULED",
                        null,
                        null,
                        "scheduledDate",
                        "asc"
                );

        assertNotNull(result);

        assertEquals(
                200,
                result.getStatusCode().value()
        );

        List<?> response =

                (List<?>) result.getBody();

        assertNotNull(response);

        assertEquals(
                2,
                response.size()
        );

    }

    // =========================
    // GET MAINTENANCE BY STATUS
    // ORDERED BY DATE
    // PAGINATED
    // =========================

    @Test
    @DisplayName("Should get paginated maintenance by status ordered by date successfully")
    void shouldGetPaginatedMaintenanceByStatusOrderedByDateSuccessfully() {

        Maintenance maintenance1 = new Maintenance();

        Maintenance maintenance2 = new Maintenance();

        when(maintenanceService.getByStatusOrderByDate(
                eq("SCHEDULED"),
                any(org.springframework.data.domain.Pageable.class)))

                .thenReturn(

                        new org.springframework.data.domain.PageImpl<>(
                                List.of(
                                        maintenance1,
                                        maintenance2
                                ),
                                org.springframework.data.domain.PageRequest.of(
                                        0,
                                        10
                                ),
                                2
                        )

                );

        ResponseEntity<?> result =

                maintenanceController.getByStatusSorted(
                        "SCHEDULED",
                        0,
                        10,
                        "scheduledDate",
                        "asc"
                );

        assertNotNull(result);

        assertEquals(
                200,
                result.getStatusCode().value()
        );

        assertNotNull(result.getBody());

    }

    // =========================
    // UPDATE MAINTENANCE
    // =========================

    @Test
    @DisplayName("Should update maintenance successfully")
    void shouldUpdateMaintenanceSuccessfully() {

        when(maintenanceService.update(
                1L,
                request))

                .thenReturn(maintenance);

        ResponseEntity<Maintenance> result =

                maintenanceController.update(
                        1L,
                        request
                );

        assertNotNull(result);

        assertEquals(
                200,
                result.getStatusCode().value()
        );

        assertEquals(
                maintenance,
                result.getBody()
        );

    }

    // =========================
    // DELETE MAINTENANCE
    // =========================

    @Test
    @DisplayName("Should delete maintenance successfully")
    void shouldDeleteMaintenanceSuccessfully() {

        ResponseEntity<Void> result =

                maintenanceController.delete(1L);

        assertNotNull(result);

        assertEquals(
                204,
                result.getStatusCode().value()
        );

    }

}