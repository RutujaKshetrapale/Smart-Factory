package com.example.demo.controller;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.example.demo.SpringBootFactoryApplication;
import com.example.demo.dto.SensorRequest;
import com.example.demo.service.SensorService;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest(classes = SpringBootFactoryApplication.class)
@AutoConfigureMockMvc(addFilters = false)
class SensorValidationErrorResponseTest {

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @MockitoBean
    private SensorService sensorService;


    // ==========================================
    // SETUP
    // ==========================================

    @BeforeEach
    void setUp() {

        objectMapper = new ObjectMapper();
    }


    // ==========================================
    // INVALID NAME
    // ==========================================

    @Test
    void createSensor_shouldReturnValidationError_whenNameIsNull()
            throws Exception {

        SensorRequest request = new SensorRequest();

        request.setName(null);
        request.setType("TEMPERATURE");
        request.setUnit("C");
        request.setMachineId(1L);
        request.setActive(true);

        mockMvc.perform(
                post("/api/sensors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(
                                objectMapper.writeValueAsString(request)
                        )
        )
        .andExpect(status().isBadRequest())

        .andExpect(jsonPath("$.status").value(400))
        .andExpect(jsonPath("$.error")
                .value("VALIDATION_FAILED"))
        .andExpect(jsonPath("$.message")
                .value("Request validation failed"))
        .andExpect(jsonPath("$.path")
                .value("/api/sensors"))
        .andExpect(jsonPath("$.timestamp")
                .exists())
        .andExpect(jsonPath("$.errors.name")
                .value("Sensor name is required"));

        verify(sensorService, never()).create(request);
    }


    // ==========================================
    // INVALID TYPE
    // ==========================================

    @Test
    void createSensor_shouldReturnValidationError_whenTypeIsNull()
            throws Exception {

        SensorRequest request = new SensorRequest();

        request.setName("Temperature Sensor");
        request.setType(null);
        request.setUnit("C");
        request.setMachineId(1L);
        request.setActive(true);

        mockMvc.perform(
                post("/api/sensors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(
                                objectMapper.writeValueAsString(request)
                        )
        )
        .andExpect(status().isBadRequest())

        .andExpect(jsonPath("$.status").value(400))
        .andExpect(jsonPath("$.error")
                .value("VALIDATION_FAILED"))
        .andExpect(jsonPath("$.message")
                .value("Request validation failed"))
        .andExpect(jsonPath("$.path")
                .value("/api/sensors"))
        .andExpect(jsonPath("$.timestamp")
                .exists())
        .andExpect(jsonPath("$.errors.type")
                .value("Sensor type is required"));

        verify(sensorService, never()).create(request);
    }


    // ==========================================
    // INVALID UNIT
    // ==========================================

    @Test
    void createSensor_shouldReturnValidationError_whenUnitIsNull()
            throws Exception {

        SensorRequest request = new SensorRequest();

        request.setName("Temperature Sensor");
        request.setType("TEMPERATURE");
        request.setUnit(null);
        request.setMachineId(1L);
        request.setActive(true);

        mockMvc.perform(
                post("/api/sensors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(
                                objectMapper.writeValueAsString(request)
                        )
        )
        .andExpect(status().isBadRequest())

        .andExpect(jsonPath("$.status").value(400))
        .andExpect(jsonPath("$.error")
                .value("VALIDATION_FAILED"))
        .andExpect(jsonPath("$.message")
                .value("Request validation failed"))
        .andExpect(jsonPath("$.path")
                .value("/api/sensors"))
        .andExpect(jsonPath("$.timestamp")
                .exists())
        .andExpect(jsonPath("$.errors.unit")
                .value("Sensor unit is required"));

        verify(sensorService, never()).create(request);
    }


    // ==========================================
    // INVALID MACHINE ID
    // ==========================================

    @Test
    void createSensor_shouldReturnValidationError_whenMachineIdIsNull()
            throws Exception {

        SensorRequest request = new SensorRequest();

        request.setName("Temperature Sensor");
        request.setType("TEMPERATURE");
        request.setUnit("C");
        request.setMachineId(null);
        request.setActive(true);

        mockMvc.perform(
                post("/api/sensors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(
                                objectMapper.writeValueAsString(request)
                        )
        )
        .andExpect(status().isBadRequest())

        .andExpect(jsonPath("$.status").value(400))
        .andExpect(jsonPath("$.error")
                .value("VALIDATION_FAILED"))
        .andExpect(jsonPath("$.message")
                .value("Request validation failed"))
        .andExpect(jsonPath("$.path")
                .value("/api/sensors"))
        .andExpect(jsonPath("$.timestamp")
                .exists())
        .andExpect(jsonPath("$.errors.machineId")
                .value("Machine ID is required"));

        verify(sensorService, never()).create(request);
    }


    // ==========================================
    // MULTIPLE VALIDATION ERRORS
    // ==========================================

    @Test
    void createSensor_shouldReturnMultipleValidationErrors()
            throws Exception {

        SensorRequest request = new SensorRequest();

        request.setName(null);
        request.setType(null);
        request.setUnit(null);
        request.setMachineId(null);
        request.setActive(true);

        mockMvc.perform(
                post("/api/sensors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(
                                objectMapper.writeValueAsString(request)
                        )
        )
        .andExpect(status().isBadRequest())

        .andExpect(jsonPath("$.status").value(400))
        .andExpect(jsonPath("$.error")
                .value("VALIDATION_FAILED"))
        .andExpect(jsonPath("$.message")
                .value("Request validation failed"))
        .andExpect(jsonPath("$.path")
                .value("/api/sensors"))
        .andExpect(jsonPath("$.timestamp")
                .exists())

        .andExpect(jsonPath("$.errors.name")
                .value("Sensor name is required"))
        .andExpect(jsonPath("$.errors.type")
                .value("Sensor type is required"))
        .andExpect(jsonPath("$.errors.unit")
                .value("Sensor unit is required"))
        .andExpect(jsonPath("$.errors.machineId")
                .value("Machine ID is required"));

        verify(sensorService, never()).create(request);
    }
}