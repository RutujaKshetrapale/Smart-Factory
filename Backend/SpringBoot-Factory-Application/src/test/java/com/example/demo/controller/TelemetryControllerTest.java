package com.example.demo.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

import static org.mockito.Mockito.when;

import java.time.LocalDateTime;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.DisplayName;

import org.junit.jupiter.api.Test;

import org.mockito.InjectMocks;

import org.mockito.Mock;

import org.mockito.MockitoAnnotations;

import org.springframework.data.domain.PageImpl;

import org.springframework.data.domain.PageRequest;

import org.springframework.http.ResponseEntity;

import com.example.demo.dto.PageResponse;

import com.example.demo.dto.TelemetryRequest;

import com.example.demo.entity.Telemetry;

import com.example.demo.service.TelemetryService;

class TelemetryControllerTest {

    @Mock
    private TelemetryService telemetryService;

    @InjectMocks
    private TelemetryController telemetryController;

    private TelemetryRequest request;

    private Telemetry telemetry;

    @BeforeEach
    void setUp() {

        MockitoAnnotations.openMocks(this);

        request = new TelemetryRequest();

        request.setMachineId(1L);

        request.setTemperature(75.5);

        request.setVibration(2.5);

        request.setPressure(10.5);

        request.setRpm(1500.0);

        telemetry = new Telemetry();

    }

    // =========================
    // CREATE TELEMETRY
    // =========================

    @Test
    @DisplayName("Should create telemetry successfully")
    void shouldCreateTelemetrySuccessfully() {

        when(telemetryService.create(
                any(TelemetryRequest.class)))

                .thenReturn(telemetry);

        ResponseEntity<Telemetry> result =

                telemetryController.create(request);

        assertNotNull(result);

        assertEquals(
                201,
                result.getStatusCode().value()
        );

        assertEquals(
                telemetry,
                result.getBody()
        );

    }

    // =========================
    // GET ALL TELEMETRY
    // =========================

    @Test
    @DisplayName("Should get all telemetry successfully")
    void shouldGetAllTelemetrySuccessfully() {

        Telemetry telemetry1 = new Telemetry();

        Telemetry telemetry2 = new Telemetry();

        when(telemetryService.getAll(
                any(org.springframework.data.domain.Pageable.class)))

                .thenReturn(

                        new PageImpl<>(
                                List.of(
                                        telemetry1,
                                        telemetry2
                                ),
                                PageRequest.of(
                                        0,
                                        10
                                ),
                                2
                        )

                );

        ResponseEntity<PageResponse<Telemetry>> result =

                telemetryController.getAll(
                        0,
                        10,
                        "timestamp",
                        "desc"
                );

        assertNotNull(result);

        assertEquals(
                200,
                result.getStatusCode().value()
        );

        assertNotNull(result.getBody());

        assertEquals(
                2,
                result.getBody()
                        .getContent()
                        .size()
        );

    }

    // =========================
    // GET TELEMETRY BY ID
    // =========================

    @Test
    @DisplayName("Should get telemetry by id successfully")
    void shouldGetTelemetryByIdSuccessfully() {

        when(telemetryService.getById(1L))

                .thenReturn(telemetry);

        ResponseEntity<Telemetry> result =

                telemetryController.getById(1L);

        assertNotNull(result);

        assertEquals(
                200,
                result.getStatusCode().value()
        );

        assertEquals(
                telemetry,
                result.getBody()
        );

    }

    // =========================
    // GET TELEMETRY BY MACHINE
    // =========================

    @Test
    @DisplayName("Should get telemetry by machine successfully")
    void shouldGetTelemetryByMachineSuccessfully() {

        Telemetry telemetry1 = new Telemetry();

        Telemetry telemetry2 = new Telemetry();

        when(telemetryService.getByMachine(
                eq(1L),
                any(org.springframework.data.domain.Pageable.class)))

                .thenReturn(

                        new PageImpl<>(
                                List.of(
                                        telemetry1,
                                        telemetry2
                                ),
                                PageRequest.of(
                                        0,
                                        10
                                ),
                                2
                        )

                );

        ResponseEntity<PageResponse<Telemetry>> result =

                telemetryController.getByMachine(
                        1L,
                        0,
                        10,
                        "timestamp",
                        "desc"
                );

        assertNotNull(result);

        assertEquals(
                200,
                result.getStatusCode().value()
        );

        assertNotNull(result.getBody());

        assertEquals(
                2,
                result.getBody()
                        .getContent()
                        .size()
        );

    }

    // =========================
    // GET TELEMETRY BY MACHINE
    // DATE RANGE
    // =========================

    @Test
    @DisplayName("Should get telemetry by machine and date range successfully")
    void shouldGetTelemetryByMachineAndDateRangeSuccessfully() {

        Telemetry telemetry1 = new Telemetry();

        Telemetry telemetry2 = new Telemetry();

        LocalDateTime start =

                LocalDateTime.of(
                        2026,
                        9,
                        1,
                        0,
                        0
                );

        LocalDateTime end =

                LocalDateTime.of(
                        2026,
                        9,
                        1,
                        23,
                        59
                );

        when(telemetryService.getByMachineAndDateRange(
                eq(1L),
                eq(start),
                eq(end),
                any(org.springframework.data.domain.Pageable.class)))

                .thenReturn(

                        new PageImpl<>(
                                List.of(
                                        telemetry1,
                                        telemetry2
                                ),
                                PageRequest.of(
                                        0,
                                        10
                                ),
                                2
                        )

                );

        ResponseEntity<PageResponse<Telemetry>> result =

                telemetryController
                        .getByMachineAndDateRange(
                                1L,
                                start,
                                end,
                                0,
                                10,
                                "timestamp",
                                "desc"
                        );

        assertNotNull(result);

        assertEquals(
                200,
                result.getStatusCode().value()
        );

        assertNotNull(result.getBody());

        assertEquals(
                2,
                result.getBody()
                        .getContent()
                        .size()
        );

    }

    // =========================
    // GET TELEMETRY
    // DATE RANGE
    // =========================

    @Test
    @DisplayName("Should get telemetry by date range successfully")
    void shouldGetTelemetryByDateRangeSuccessfully() {

        Telemetry telemetry1 = new Telemetry();

        Telemetry telemetry2 = new Telemetry();

        LocalDateTime start =

                LocalDateTime.of(
                        2026,
                        9,
                        1,
                        0,
                        0
                );

        LocalDateTime end =

                LocalDateTime.of(
                        2026,
                        9,
                        1,
                        23,
                        59
                );

        when(telemetryService.getByDateRange(
                eq(start),
                eq(end),
                any(org.springframework.data.domain.Pageable.class)))

                .thenReturn(

                        new PageImpl<>(
                                List.of(
                                        telemetry1,
                                        telemetry2
                                ),
                                PageRequest.of(
                                        0,
                                        10
                                ),
                                2
                        )

                );

        ResponseEntity<PageResponse<Telemetry>> result =

                telemetryController.getByDateRange(
                        start,
                        end,
                        0,
                        10,
                        "timestamp",
                        "desc"
                );

        assertNotNull(result);

        assertEquals(
                200,
                result.getStatusCode().value()
        );

        assertNotNull(result.getBody());

        assertEquals(
                2,
                result.getBody()
                        .getContent()
                        .size()
        );

    }

    // =========================
    // GET LATEST TELEMETRY
    // =========================

    @Test
    @DisplayName("Should get latest telemetry successfully")
    void shouldGetLatestTelemetrySuccessfully() {

        Telemetry telemetry1 = new Telemetry();

        Telemetry telemetry2 = new Telemetry();

        when(telemetryService.getLatestByMachine(1L))

                .thenReturn(

                        List.of(
                                telemetry1,
                                telemetry2
                        )

                );

        ResponseEntity<?> result =

                telemetryController
                        .getLatestByMachine(1L);

        assertNotNull(result);

        assertEquals(
                200,
                result.getStatusCode().value()
        );

        assertNotNull(result.getBody());

        List<?> response =

                (List<?>) result.getBody();

        assertEquals(
                2,
                response.size()
        );

    }

}