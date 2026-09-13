package com.example.demo.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import org.springframework.http.MediaType;

import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.example.demo.SpringBootFactoryApplication;
import com.example.demo.entity.Machine;
import com.example.demo.entity.Sensor;
import com.example.demo.service.SensorService;

@SpringBootTest(
        classes = SpringBootFactoryApplication.class
)
@AutoConfigureMockMvc(addFilters = false)
class SensorControllerMockMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private SensorService sensorService;

    @Test
    @DisplayName("Should create sensor successfully")
    void shouldCreateSensorSuccessfully() throws Exception {

        Machine machine = new Machine();

        Sensor sensor = new Sensor();

        sensor.setId(1L);
        sensor.setName("Temperature Sensor");
        sensor.setType("TEMPERATURE");
        sensor.setUnit("°C");
        sensor.setActive(true);
        sensor.setMachine(machine);

        when(sensorService.create(any()))
                .thenReturn(sensor);

        String requestBody = """
                {
                    "name": "Temperature Sensor",
                    "type": "TEMPERATURE",
                    "unit": "°C",
                    "machineId": 1,
                    "active": true
                }
                """;

        mockMvc.perform(
                post("/api/sensors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody)
        )
        .andExpect(status().isCreated())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.id").value(1))
        .andExpect(jsonPath("$.name").value("Temperature Sensor"))
        .andExpect(jsonPath("$.type").value("TEMPERATURE"))
        .andExpect(jsonPath("$.unit").value("°C"))
        .andExpect(jsonPath("$.active").value(true));
    }

    @Test
    @DisplayName("Should get sensor by ID successfully")
    void shouldGetSensorByIdSuccessfully() throws Exception {

        Sensor sensor = new Sensor();

        sensor.setId(1L);
        sensor.setName("Temperature Sensor");
        sensor.setType("TEMPERATURE");
        sensor.setUnit("°C");
        sensor.setActive(true);

        when(sensorService.getById(eq(1L)))
                .thenReturn(sensor);

        mockMvc.perform(
                get("/api/sensors/1")
        )
        .andExpect(status().isOk())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.id").value(1))
        .andExpect(jsonPath("$.name").value("Temperature Sensor"))
        .andExpect(jsonPath("$.type").value("TEMPERATURE"))
        .andExpect(jsonPath("$.unit").value("°C"))
        .andExpect(jsonPath("$.active").value(true));
    }

    @Test
    @DisplayName("Should get all sensors with pagination successfully")
    void shouldGetAllSensorsSuccessfully() throws Exception {

        Sensor sensor = new Sensor();

        sensor.setId(1L);
        sensor.setName("Temperature Sensor");
        sensor.setType("TEMPERATURE");
        sensor.setUnit("°C");
        sensor.setActive(true);

        PageImpl<Sensor> page = new PageImpl<>(
                List.of(sensor),
                PageRequest.of(0, 10),
                1
        );

        when(sensorService.getAll(any()))
                .thenReturn(page);

        mockMvc.perform(
                get("/api/sensors")
                        .param("page", "0")
                        .param("size", "10")
                        .param("sortBy", "name")
                        .param("direction", "asc")
        )
        .andExpect(status().isOk())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.content[0].id").value(1))
        .andExpect(jsonPath("$.content[0].name").value("Temperature Sensor"))
        .andExpect(jsonPath("$.content[0].type").value("TEMPERATURE"))
        .andExpect(jsonPath("$.content[0].unit").value("°C"))
        .andExpect(jsonPath("$.content[0].active").value(true))
        .andExpect(jsonPath("$.page").value(0))
        .andExpect(jsonPath("$.size").value(10))
        .andExpect(jsonPath("$.totalElements").value(1))
        .andExpect(jsonPath("$.totalPages").value(1))
        .andExpect(jsonPath("$.first").value(true))
        .andExpect(jsonPath("$.last").value(true));
    }

    @Test
    @DisplayName("Should get sensors by machine successfully")
    void shouldGetSensorsByMachineSuccessfully() throws Exception {

        Sensor sensor = new Sensor();

        sensor.setId(1L);
        sensor.setName("Temperature Sensor");

        PageImpl<Sensor> page = new PageImpl<>(
                List.of(sensor),
                PageRequest.of(0, 10),
                1
        );

        when(sensorService.getByMachine(
                eq(1L),
                any()
        ))
        .thenReturn(page);

        mockMvc.perform(
                get("/api/sensors/machine/1")
                        .param("page", "0")
                        .param("size", "10")
                        .param("sortBy", "name")
                        .param("direction", "asc")
        )
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.content[0].id").value(1))
        .andExpect(jsonPath("$.content[0].name").value("Temperature Sensor"))
        .andExpect(jsonPath("$.totalElements").value(1));
    }

    @Test
    @DisplayName("Should get active sensors successfully")
    void shouldGetActiveSensorsSuccessfully() throws Exception {

        Sensor sensor = new Sensor();

        sensor.setId(1L);
        sensor.setName("Temperature Sensor");
        sensor.setActive(true);

        PageImpl<Sensor> page = new PageImpl<>(
                List.of(sensor),
                PageRequest.of(0, 10),
                1
        );

        when(sensorService.getActive(any()))
                .thenReturn(page);

        mockMvc.perform(
                get("/api/sensors/active")
                        .param("page", "0")
                        .param("size", "10")
                        .param("sortBy", "name")
                        .param("direction", "asc")
        )
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.content[0].id").value(1))
        .andExpect(jsonPath("$.content[0].name").value("Temperature Sensor"))
        .andExpect(jsonPath("$.content[0].active").value(true))
        .andExpect(jsonPath("$.totalElements").value(1));
    }

    @Test
    @DisplayName("Should get inactive sensors successfully")
    void shouldGetInactiveSensorsSuccessfully() throws Exception {

        Sensor sensor = new Sensor();

        sensor.setId(2L);
        sensor.setName("Pressure Sensor");
        sensor.setActive(false);

        PageImpl<Sensor> page = new PageImpl<>(
                List.of(sensor),
                PageRequest.of(0, 10),
                1
        );

        when(sensorService.getInactive(any()))
                .thenReturn(page);

        mockMvc.perform(
                get("/api/sensors/inactive")
                        .param("page", "0")
                        .param("size", "10")
                        .param("sortBy", "name")
                        .param("direction", "asc")
        )
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.content[0].id").value(2))
        .andExpect(jsonPath("$.content[0].name").value("Pressure Sensor"))
        .andExpect(jsonPath("$.content[0].active").value(false))
        .andExpect(jsonPath("$.totalElements").value(1));
    }

    @Test
    @DisplayName("Should get sensors by type successfully")
    void shouldGetSensorsByTypeSuccessfully() throws Exception {

        Sensor sensor = new Sensor();

        sensor.setId(1L);
        sensor.setName("Temperature Sensor");
        sensor.setType("TEMPERATURE");

        PageImpl<Sensor> page = new PageImpl<>(
                List.of(sensor),
                PageRequest.of(0, 10),
                1
        );

        when(sensorService.getByType(
                eq("TEMPERATURE"),
                any()
        ))
        .thenReturn(page);

        mockMvc.perform(
                get("/api/sensors/type/TEMPERATURE")
                        .param("page", "0")
                        .param("size", "10")
                        .param("sortBy", "name")
                        .param("direction", "asc")
        )
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.content[0].id").value(1))
        .andExpect(jsonPath("$.content[0].type").value("TEMPERATURE"))
        .andExpect(jsonPath("$.totalElements").value(1));
    }

    @Test
    @DisplayName("Should get sensors by machine and type successfully")
    void shouldGetSensorsByMachineAndTypeSuccessfully() throws Exception {

        Sensor sensor = new Sensor();

        sensor.setId(1L);
        sensor.setName("Temperature Sensor");
        sensor.setType("TEMPERATURE");

        PageImpl<Sensor> page = new PageImpl<>(
                List.of(sensor),
                PageRequest.of(0, 10),
                1
        );

        when(sensorService.getByMachineAndType(
                eq(1L),
                eq("TEMPERATURE"),
                any()
        ))
        .thenReturn(page);

        mockMvc.perform(
                get("/api/sensors/machine/1/type/TEMPERATURE")
                        .param("page", "0")
                        .param("size", "10")
                        .param("sortBy", "name")
                        .param("direction", "asc")
        )
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.content[0].id").value(1))
        .andExpect(jsonPath("$.content[0].type").value("TEMPERATURE"))
        .andExpect(jsonPath("$.totalElements").value(1));
    }

    @Test
    @DisplayName("Should search sensors by name successfully")
    void shouldSearchSensorsByNameSuccessfully() throws Exception {

        Sensor sensor = new Sensor();

        sensor.setId(1L);
        sensor.setName("Temperature Sensor");

        PageImpl<Sensor> page = new PageImpl<>(
                List.of(sensor),
                PageRequest.of(0, 10),
                1
        );

        when(sensorService.searchByName(
                eq("Temperature"),
                any()
        ))
        .thenReturn(page);

        mockMvc.perform(
                get("/api/sensors/search/name")
                        .param("name", "Temperature")
                        .param("page", "0")
                        .param("size", "10")
                        .param("sortBy", "name")
                        .param("direction", "asc")
        )
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.content[0].id").value(1))
        .andExpect(jsonPath("$.content[0].name").value("Temperature Sensor"))
        .andExpect(jsonPath("$.totalElements").value(1));
    }

    @Test
    @DisplayName("Should update sensor successfully")
    void shouldUpdateSensorSuccessfully() throws Exception {

        Sensor sensor = new Sensor();

        sensor.setId(1L);
        sensor.setName("Updated Temperature Sensor");
        sensor.setType("TEMPERATURE");
        sensor.setUnit("°C");
        sensor.setActive(true);

        when(sensorService.update(
                eq(1L),
                any()
        ))
        .thenReturn(sensor);

        String requestBody = """
                {
                    "name": "Updated Temperature Sensor",
                    "type": "TEMPERATURE",
                    "unit": "°C",
                    "machineId": 1,
                    "active": true
                }
                """;

        mockMvc.perform(
                put("/api/sensors/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody)
        )
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(1))
        .andExpect(jsonPath("$.name").value("Updated Temperature Sensor"))
        .andExpect(jsonPath("$.type").value("TEMPERATURE"))
        .andExpect(jsonPath("$.unit").value("°C"))
        .andExpect(jsonPath("$.active").value(true));
    }

    @Test
    @DisplayName("Should delete sensor successfully")
    void shouldDeleteSensorSuccessfully() throws Exception {

        doNothing()
                .when(sensorService)
                .delete(eq(1L));

        mockMvc.perform(
                delete("/api/sensors/1")
        )
        .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("Should return bad request for invalid sensor request")
    void shouldReturnBadRequestForInvalidSensorRequest() throws Exception {

        String requestBody = """
                {
                    "name": "",
                    "type": "",
                    "unit": "",
                    "machineId": null
                }
                """;

        mockMvc.perform(
                post("/api/sensors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody)
        )
        .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Should return bad request for negative page")
    void shouldReturnBadRequestForNegativePage() throws Exception {

        mockMvc.perform(
                get("/api/sensors")
                        .param("page", "-1")
                        .param("size", "10")
                        .param("sortBy", "name")
                        .param("direction", "asc")
        )
        .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Should return bad request for invalid page size")
    void shouldReturnBadRequestForInvalidPageSize() throws Exception {

        mockMvc.perform(
                get("/api/sensors")
                        .param("page", "0")
                        .param("size", "101")
                        .param("sortBy", "name")
                        .param("direction", "asc")
        )
        .andExpect(status().isBadRequest());
    }
}