package com.example.demo.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
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
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import org.springframework.http.MediaType;

import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.example.demo.SpringBootFactoryApplication;
import com.example.demo.entity.Machine;
import com.example.demo.entity.Plant;
import com.example.demo.service.MachineService;

@SpringBootTest(
        classes = SpringBootFactoryApplication.class
)
@AutoConfigureMockMvc(addFilters = false)
class MachineControllerMockMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private MachineService machineService;

    @Test
    @DisplayName("Should create machine successfully")
    void shouldCreateMachineSuccessfully() throws Exception {

        Plant plant = new Plant(
                "Pune Manufacturing Plant",
                "Pune, Maharashtra",
                true
        );

        Machine machine = mock(Machine.class);

        when(machine.getId())
                .thenReturn(1L);

        when(machine.getName())
                .thenReturn("CNC Machine 01");

        when(machine.getType())
                .thenReturn("CNC");

        when(machine.getStatus())
                .thenReturn("ACTIVE");

        when(machine.getPlant())
                .thenReturn(plant);

        when(machineService.create(any()))
                .thenReturn(machine);

        String requestBody = """
                {
                    "name": "CNC Machine 01",
                    "type": "CNC",
                    "status": "ACTIVE",
                    "plantId": 2
                }
                """;

        mockMvc.perform(
                post("/api/machines")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody)
        )
        .andExpect(status().isCreated())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.id").value(1))
        .andExpect(jsonPath("$.name").value("CNC Machine 01"))
        .andExpect(jsonPath("$.type").value("CNC"))
        .andExpect(jsonPath("$.status").value("ACTIVE"));
    }

    @Test
    @DisplayName("Should get machine by ID successfully")
    void shouldGetMachineByIdSuccessfully() throws Exception {

        Machine machine = mock(Machine.class);

        when(machine.getId())
                .thenReturn(1L);

        when(machine.getName())
                .thenReturn("CNC Machine 01");

        when(machine.getType())
                .thenReturn("CNC");

        when(machine.getStatus())
                .thenReturn("ACTIVE");

        when(machineService.getById(eq(1L)))
                .thenReturn(machine);

        mockMvc.perform(
                get("/api/machines/1")
        )
        .andExpect(status().isOk())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.id").value(1))
        .andExpect(jsonPath("$.name").value("CNC Machine 01"))
        .andExpect(jsonPath("$.type").value("CNC"))
        .andExpect(jsonPath("$.status").value("ACTIVE"));
    }

    @Test
    @DisplayName("Should get all machines with pagination successfully")
    void shouldGetAllMachinesSuccessfully() throws Exception {

        Machine machine = mock(Machine.class);

        when(machine.getId())
                .thenReturn(1L);

        when(machine.getName())
                .thenReturn("CNC Machine 01");

        when(machine.getType())
                .thenReturn("CNC");

        when(machine.getStatus())
                .thenReturn("ACTIVE");

        PageImpl<Machine> page = new PageImpl<>(
                List.of(machine),
                PageRequest.of(0, 10),
                1
        );

        when(machineService.getAll(any()))
                .thenReturn(page);

        mockMvc.perform(
                get("/api/machines")
                        .param("page", "0")
                        .param("size", "10")
                        .param("sortBy", "name")
                        .param("direction", "asc")
        )
        .andExpect(status().isOk())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.content[0].id").value(1))
        .andExpect(jsonPath("$.content[0].name").value("CNC Machine 01"))
        .andExpect(jsonPath("$.content[0].type").value("CNC"))
        .andExpect(jsonPath("$.content[0].status").value("ACTIVE"))
        .andExpect(jsonPath("$.page").value(0))
        .andExpect(jsonPath("$.size").value(10))
        .andExpect(jsonPath("$.totalElements").value(1))
        .andExpect(jsonPath("$.totalPages").value(1))
        .andExpect(jsonPath("$.first").value(true))
        .andExpect(jsonPath("$.last").value(true));
    }

    @Test
    @DisplayName("Should get machines by plant successfully")
    void shouldGetMachinesByPlantSuccessfully() throws Exception {

        Machine machine = mock(Machine.class);

        when(machine.getId())
                .thenReturn(1L);

        when(machine.getName())
                .thenReturn("CNC Machine 01");

        PageImpl<Machine> page = new PageImpl<>(
                List.of(machine),
                PageRequest.of(0, 10),
                1
        );

        when(machineService.getByPlant(
                eq(2L),
                any()
        ))
        .thenReturn(page);

        mockMvc.perform(
                get("/api/machines/plant/2")
                        .param("page", "0")
                        .param("size", "10")
                        .param("sortBy", "name")
                        .param("direction", "asc")
        )
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.content[0].id").value(1))
        .andExpect(jsonPath("$.content[0].name").value("CNC Machine 01"))
        .andExpect(jsonPath("$.totalElements").value(1));
    }

    @Test
    @DisplayName("Should get machines by status successfully")
    void shouldGetMachinesByStatusSuccessfully() throws Exception {

        Machine machine = mock(Machine.class);

        when(machine.getId())
                .thenReturn(1L);

        when(machine.getName())
                .thenReturn("CNC Machine 01");

        when(machine.getStatus())
                .thenReturn("ACTIVE");

        PageImpl<Machine> page = new PageImpl<>(
                List.of(machine),
                PageRequest.of(0, 10),
                1
        );

        when(machineService.getByStatus(
                eq("ACTIVE"),
                any()
        ))
        .thenReturn(page);

        mockMvc.perform(
                get("/api/machines/status/ACTIVE")
                        .param("page", "0")
                        .param("size", "10")
                        .param("sortBy", "name")
                        .param("direction", "asc")
        )
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.content[0].id").value(1))
        .andExpect(jsonPath("$.content[0].status").value("ACTIVE"))
        .andExpect(jsonPath("$.totalElements").value(1));
    }

    @Test
    @DisplayName("Should search machines by name successfully")
    void shouldSearchMachinesByNameSuccessfully() throws Exception {

        Machine machine = mock(Machine.class);

        when(machine.getId())
                .thenReturn(1L);

        when(machine.getName())
                .thenReturn("CNC Machine 01");

        PageImpl<Machine> page = new PageImpl<>(
                List.of(machine),
                PageRequest.of(0, 10),
                1
        );

        when(machineService.searchByName(
                eq("CNC"),
                any()
        ))
        .thenReturn(page);

        mockMvc.perform(
                get("/api/machines/search/name")
                        .param("name", "CNC")
                        .param("page", "0")
                        .param("size", "10")
                        .param("sortBy", "name")
                        .param("direction", "asc")
        )
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.content[0].id").value(1))
        .andExpect(jsonPath("$.content[0].name").value("CNC Machine 01"))
        .andExpect(jsonPath("$.totalElements").value(1));
    }

    @Test
    @DisplayName("Should update machine successfully")
    void shouldUpdateMachineSuccessfully() throws Exception {

        Machine machine = mock(Machine.class);

        when(machine.getId())
                .thenReturn(1L);

        when(machine.getName())
                .thenReturn("Updated CNC Machine");

        when(machine.getType())
                .thenReturn("CNC");

        when(machine.getStatus())
                .thenReturn("ACTIVE");

        when(machineService.update(
                eq(1L),
                any()
        ))
        .thenReturn(machine);

        String requestBody = """
                {
                    "name": "Updated CNC Machine",
                    "type": "CNC",
                    "status": "ACTIVE",
                    "plantId": 2
                }
                """;

        mockMvc.perform(
                put("/api/machines/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody)
        )
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(1))
        .andExpect(jsonPath("$.name").value("Updated CNC Machine"))
        .andExpect(jsonPath("$.type").value("CNC"))
        .andExpect(jsonPath("$.status").value("ACTIVE"));
    }

    @Test
    @DisplayName("Should delete machine successfully")
    void shouldDeleteMachineSuccessfully() throws Exception {

        doNothing()
                .when(machineService)
                .delete(1L);

        MachineController machineController =
                new MachineController(machineService);

        MockMvc standaloneMockMvc =
                MockMvcBuilders
                        .standaloneSetup(machineController)
                        .build();

        standaloneMockMvc.perform(
                delete("/api/machines/1")
        )
        .andExpect(status().isNoContent());

        verify(machineService)
                .delete(1L);
    }

    @Test
    @DisplayName("Should return bad request for invalid machine request")
    void shouldReturnBadRequestForInvalidMachineRequest() throws Exception {

        String requestBody = """
                {
                    "name": "",
                    "type": "",
                    "status": "",
                    "plantId": null
                }
                """;

        mockMvc.perform(
                post("/api/machines")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody)
        )
        .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Should return bad request for negative page")
    void shouldReturnBadRequestForNegativePage() throws Exception {

        mockMvc.perform(
                get("/api/machines")
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
                get("/api/machines")
                        .param("page", "0")
                        .param("size", "101")
                        .param("sortBy", "name")
                        .param("direction", "asc")
        )
        .andExpect(status().isBadRequest());
    }
}