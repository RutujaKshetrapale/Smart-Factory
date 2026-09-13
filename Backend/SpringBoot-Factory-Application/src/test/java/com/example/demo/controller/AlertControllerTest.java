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
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.demo.dto.AlertRequest;
import com.example.demo.dto.PageResponse;
import com.example.demo.entity.Alert;
import com.example.demo.service.AlertService;

@ExtendWith(MockitoExtension.class)
class AlertControllerTest {

    @Mock
    private AlertService alertService;

    @InjectMocks
    private AlertController alertController;

    private AlertRequest request;
    private Alert alert;

    @BeforeEach
    void setUp() {

        request = new AlertRequest();
        request.setMachineId(1L);
        request.setType("HIGH_TEMPERATURE");
        request.setSeverity("CRITICAL");
        request.setMessage("Machine temperature is too high");
        request.setResolved(false);

        alert = new Alert();
        alert.setType("HIGH_TEMPERATURE");
        alert.setSeverity("CRITICAL");
        alert.setMessage("Machine temperature is too high");
        alert.setResolved(false);
    }

    // =========================
    // CREATE ALERT
    // =========================

    @Test
    @DisplayName("Should create alert successfully")
    void shouldCreateAlertSuccessfully() {

        when(alertService.create(request))
                .thenReturn(alert);

        ResponseEntity<Alert> response =
                alertController.create(request);

        assertNotNull(response);
        assertEquals(
                HttpStatus.CREATED,
                response.getStatusCode()
        );
        assertEquals(
                alert,
                response.getBody()
        );

        verify(alertService, times(1))
                .create(request);
    }

    // =========================
    // GET ALL ALERTS - NON PAGINATED
    // =========================

    @Test
    @DisplayName("Should get all alerts successfully")
    void shouldGetAllAlertsSuccessfully() {

        when(alertService.getAll())
                .thenReturn(Arrays.asList(alert));

        ResponseEntity<?> response =
                alertController.getAll(
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
        assertEquals(
                Arrays.asList(alert),
                response.getBody()
        );

        verify(alertService, times(1))
                .getAll();
    }

    // =========================
    // GET ALL ALERTS - PAGINATED
    // =========================

    @Test
    @DisplayName("Should get paginated alerts successfully")
    void shouldGetPaginatedAlertsSuccessfully() {

        PageImpl<Alert> page =
                new PageImpl<>(
                        Arrays.asList(alert)
                );

        when(alertService.getAll(any()))
                .thenReturn(page);

        ResponseEntity<?> response =
                alertController.getAll(
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
        assertNotNull(response.getBody());

        verify(alertService, times(1))
                .getAll(any());
    }

    // =========================
    // GET ALERT BY ID
    // =========================

    @Test
    @DisplayName("Should get alert by id successfully")
    void shouldGetAlertByIdSuccessfully() {

        when(alertService.getById(1L))
                .thenReturn(alert);

        ResponseEntity<Alert> response =
                alertController.getById(1L);

        assertNotNull(response);
        assertEquals(
                HttpStatus.OK,
                response.getStatusCode()
        );
        assertEquals(
                alert,
                response.getBody()
        );

        verify(alertService, times(1))
                .getById(1L);
    }

    // =========================
    // GET ALERTS BY MACHINE
    // =========================

    @Test
    @DisplayName("Should get alerts by machine successfully")
    void shouldGetAlertsByMachineSuccessfully() {

        when(alertService.getByMachine(1L))
                .thenReturn(Arrays.asList(alert));

        ResponseEntity<?> response =
                alertController.getByMachine(
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

        verify(alertService, times(1))
                .getByMachine(1L);
    }

    // =========================
    // GET ALERTS BY MACHINE - PAGINATED
    // =========================

    @Test
    @DisplayName("Should get paginated alerts by machine successfully")
    void shouldGetPaginatedAlertsByMachineSuccessfully() {

        PageImpl<Alert> page =
                new PageImpl<>(
                        Arrays.asList(alert)
                );

        when(alertService.getByMachine(
                eq(1L),
                any()
        ))
                .thenReturn(page);

        ResponseEntity<?> response =
                alertController.getByMachine(
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

        verify(alertService, times(1))
                .getByMachine(eq(1L), any());
    }

    // =========================
    // GET UNRESOLVED ALERTS
    // =========================

    @Test
    @DisplayName("Should get unresolved alerts successfully")
    void shouldGetUnresolvedAlertsSuccessfully() {

        when(alertService.getUnresolved())
                .thenReturn(Arrays.asList(alert));

        ResponseEntity<?> response =
                alertController.getUnresolved(
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

        verify(alertService, times(1))
                .getUnresolved();
    }

    // =========================
    // GET UNRESOLVED ALERTS - PAGINATED
    // =========================

    @Test
    @DisplayName("Should get paginated unresolved alerts successfully")
    void shouldGetPaginatedUnresolvedAlertsSuccessfully() {

        PageImpl<Alert> page =
                new PageImpl<>(
                        Arrays.asList(alert)
                );

        when(alertService.getUnresolved(any()))
                .thenReturn(page);

        ResponseEntity<?> response =
                alertController.getUnresolved(
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

        verify(alertService, times(1))
                .getUnresolved(any());
    }

    // =========================
    // GET ALERTS BY SEVERITY
    // =========================

    @Test
    @DisplayName("Should get alerts by severity successfully")
    void shouldGetAlertsBySeveritySuccessfully() {

        when(alertService.getBySeverity("CRITICAL"))
                .thenReturn(Arrays.asList(alert));

        ResponseEntity<?> response =
                alertController.getBySeverity(
                        "CRITICAL",
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

        verify(alertService, times(1))
                .getBySeverity("CRITICAL");
    }

    // =========================
    // GET ALERTS BY SEVERITY - PAGINATED
    // =========================

    @Test
    @DisplayName("Should get paginated alerts by severity successfully")
    void shouldGetPaginatedAlertsBySeveritySuccessfully() {

        PageImpl<Alert> page =
                new PageImpl<>(
                        Arrays.asList(alert)
                );

        when(alertService.getBySeverity(
                eq("CRITICAL"),
                any()
        ))
                .thenReturn(page);

        ResponseEntity<?> response =
                alertController.getBySeverity(
                        "CRITICAL",
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

        verify(alertService, times(1))
                .getBySeverity(
                        eq("CRITICAL"),
                        any()
                );
    }

    // =========================
    // UPDATE ALERT
    // =========================

    @Test
    @DisplayName("Should update alert successfully")
    void shouldUpdateAlertSuccessfully() {

        when(alertService.update(1L, request))
                .thenReturn(alert);

        ResponseEntity<Alert> response =
                alertController.update(
                        1L,
                        request
                );

        assertNotNull(response);
        assertEquals(
                HttpStatus.OK,
                response.getStatusCode()
        );
        assertEquals(
                alert,
                response.getBody()
        );

        verify(alertService, times(1))
                .update(1L, request);
    }

    // =========================
    // DELETE ALERT
    // =========================

    @Test
    @DisplayName("Should delete alert successfully")
    void shouldDeleteAlertSuccessfully() {

        ResponseEntity<Void> response =
                alertController.delete(1L);

        assertNotNull(response);
        assertEquals(
                HttpStatus.NO_CONTENT,
                response.getStatusCode()
        );

        verify(alertService, times(1))
                .delete(1L);
    }
}