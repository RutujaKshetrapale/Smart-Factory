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

import com.example.demo.dto.PageResponse;
import com.example.demo.dto.SensorRequest;
import com.example.demo.entity.Sensor;
import com.example.demo.service.SensorService;

@ExtendWith(MockitoExtension.class)
class SensorControllerTest {

    @Mock
    private SensorService sensorService;

    @InjectMocks
    private SensorController sensorController;

    private SensorRequest request;
    private Sensor sensor;

    @BeforeEach
    void setUp() {

        request = new SensorRequest();
        request.setName("Temperature Sensor");
        request.setType("TEMPERATURE");
        request.setUnit("C");
        request.setMachineId(1L);

        sensor = new Sensor();
        sensor.setName("Temperature Sensor");
        sensor.setType("TEMPERATURE");
        sensor.setUnit("C");
        sensor.setActive(true);
    }

    // =========================
    // CREATE SENSOR
    // =========================

    @Test
    @DisplayName("Should create sensor successfully")
    void shouldCreateSensorSuccessfully() {

        when(sensorService.create(request))
                .thenReturn(sensor);

        ResponseEntity<Sensor> response =
                sensorController.create(request);

        assertNotNull(response);
        assertEquals(
                HttpStatus.CREATED,
                response.getStatusCode()
        );
        assertEquals(
                sensor,
                response.getBody()
        );

        verify(sensorService, times(1))
                .create(request);
    }

    // =========================
    // GET ALL SENSORS
    // =========================

    @Test
    @DisplayName("Should get all sensors successfully")
    void shouldGetAllSensorsSuccessfully() {

        PageImpl<Sensor> page =
                new PageImpl<>(
                        Arrays.asList(sensor)
                );

        when(sensorService.getAll(any()))
                .thenReturn(page);

        ResponseEntity<PageResponse<Sensor>> response =
                sensorController.getAll(
                        0,
                        10,
                        "name",
                        "asc"
                );

        assertNotNull(response);
        assertEquals(
                HttpStatus.OK,
                response.getStatusCode()
        );
        assertNotNull(response.getBody());

        verify(sensorService, times(1))
                .getAll(any());
    }

    // =========================
    // GET SENSOR BY ID
    // =========================

    @Test
    @DisplayName("Should get sensor by id successfully")
    void shouldGetSensorByIdSuccessfully() {

        when(sensorService.getById(1L))
                .thenReturn(sensor);

        ResponseEntity<Sensor> response =
                sensorController.getById(1L);

        assertNotNull(response);
        assertEquals(
                HttpStatus.OK,
                response.getStatusCode()
        );
        assertEquals(
                sensor,
                response.getBody()
        );

        verify(sensorService, times(1))
                .getById(1L);
    }

    // =========================
    // GET SENSORS BY MACHINE
    // =========================

    @Test
    @DisplayName("Should get sensors by machine successfully")
    void shouldGetSensorsByMachineSuccessfully() {

        PageImpl<Sensor> page =
                new PageImpl<>(
                        Arrays.asList(sensor)
                );

        when(sensorService.getByMachine(
                eq(1L),
                any()
        ))
                .thenReturn(page);

        ResponseEntity<PageResponse<Sensor>> response =
                sensorController.getByMachine(
                        1L,
                        0,
                        10,
                        "name",
                        "asc"
                );

        assertNotNull(response);
        assertEquals(
                HttpStatus.OK,
                response.getStatusCode()
        );
        assertNotNull(response.getBody());

        verify(sensorService, times(1))
                .getByMachine(eq(1L), any());
    }

    // =========================
    // GET ACTIVE SENSORS
    // =========================

    @Test
    @DisplayName("Should get active sensors successfully")
    void shouldGetActiveSensorsSuccessfully() {

        PageImpl<Sensor> page =
                new PageImpl<>(
                        Arrays.asList(sensor)
                );

        when(sensorService.getActive(any()))
                .thenReturn(page);

        ResponseEntity<PageResponse<Sensor>> response =
                sensorController.getActive(
                        0,
                        10,
                        "name",
                        "asc"
                );

        assertNotNull(response);
        assertEquals(
                HttpStatus.OK,
                response.getStatusCode()
        );
        assertNotNull(response.getBody());

        verify(sensorService, times(1))
                .getActive(any());
    }

    // =========================
    // GET INACTIVE SENSORS
    // =========================

    @Test
    @DisplayName("Should get inactive sensors successfully")
    void shouldGetInactiveSensorsSuccessfully() {

        PageImpl<Sensor> page =
                new PageImpl<>(
                        Arrays.asList(sensor)
                );

        when(sensorService.getInactive(any()))
                .thenReturn(page);

        ResponseEntity<PageResponse<Sensor>> response =
                sensorController.getInactive(
                        0,
                        10,
                        "name",
                        "asc"
                );

        assertNotNull(response);
        assertEquals(
                HttpStatus.OK,
                response.getStatusCode()
        );

        verify(sensorService, times(1))
                .getInactive(any());
    }

    // =========================
    // GET SENSORS BY TYPE
    // =========================

    @Test
    @DisplayName("Should get sensors by type successfully")
    void shouldGetSensorsByTypeSuccessfully() {

        PageImpl<Sensor> page =
                new PageImpl<>(
                        Arrays.asList(sensor)
                );

        when(sensorService.getByType(
                eq("TEMPERATURE"),
                any()
        ))
                .thenReturn(page);

        ResponseEntity<PageResponse<Sensor>> response =
                sensorController.getByType(
                        "TEMPERATURE",
                        0,
                        10,
                        "name",
                        "asc"
                );

        assertNotNull(response);
        assertEquals(
                HttpStatus.OK,
                response.getStatusCode()
        );

        verify(sensorService, times(1))
                .getByType(
                        eq("TEMPERATURE"),
                        any()
                );
    }

    // =========================
    // GET SENSORS BY MACHINE AND TYPE
    // =========================

    @Test
    @DisplayName("Should get sensors by machine and type successfully")
    void shouldGetSensorsByMachineAndTypeSuccessfully() {

        PageImpl<Sensor> page =
                new PageImpl<>(
                        Arrays.asList(sensor)
                );

        when(sensorService.getByMachineAndType(
                eq(1L),
                eq("TEMPERATURE"),
                any()
        ))
                .thenReturn(page);

        ResponseEntity<PageResponse<Sensor>> response =
                sensorController.getByMachineAndType(
                        1L,
                        "TEMPERATURE",
                        0,
                        10,
                        "name",
                        "asc"
                );

        assertNotNull(response);
        assertEquals(
                HttpStatus.OK,
                response.getStatusCode()
        );

        verify(sensorService, times(1))
                .getByMachineAndType(
                        eq(1L),
                        eq("TEMPERATURE"),
                        any()
                );
    }

    // =========================
    // SEARCH SENSORS BY NAME
    // =========================

    @Test
    @DisplayName("Should search sensors by name successfully")
    void shouldSearchSensorsByNameSuccessfully() {

        PageImpl<Sensor> page =
                new PageImpl<>(
                        Arrays.asList(sensor)
                );

        when(sensorService.searchByName(
                eq("Temperature"),
                any()
        ))
                .thenReturn(page);

        ResponseEntity<PageResponse<Sensor>> response =
                sensorController.searchByName(
                        "Temperature",
                        0,
                        10,
                        "name",
                        "asc"
                );

        assertNotNull(response);
        assertEquals(
                HttpStatus.OK,
                response.getStatusCode()
        );

        verify(sensorService, times(1))
                .searchByName(
                        eq("Temperature"),
                        any()
                );
    }

    // =========================
    // UPDATE SENSOR
    // =========================

    @Test
    @DisplayName("Should update sensor successfully")
    void shouldUpdateSensorSuccessfully() {

        when(sensorService.update(1L, request))
                .thenReturn(sensor);

        ResponseEntity<Sensor> response =
                sensorController.update(
                        1L,
                        request
                );

        assertNotNull(response);
        assertEquals(
                HttpStatus.OK,
                response.getStatusCode()
        );
        assertEquals(
                sensor,
                response.getBody()
        );

        verify(sensorService, times(1))
                .update(1L, request);
    }

    // =========================
    // DELETE SENSOR
    // =========================

    @Test
    @DisplayName("Should delete sensor successfully")
    void shouldDeleteSensorSuccessfully() {

        ResponseEntity<Void> response =
                sensorController.delete(1L);

        assertNotNull(response);
        assertEquals(
                HttpStatus.NO_CONTENT,
                response.getStatusCode()
        );

        verify(sensorService, times(1))
                .delete(1L);
    }
}