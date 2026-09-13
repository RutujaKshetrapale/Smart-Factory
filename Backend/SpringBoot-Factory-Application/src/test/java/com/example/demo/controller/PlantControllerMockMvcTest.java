package com.example.demo.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.example.demo.SpringBootFactoryApplication;
import com.example.demo.entity.Plant;
import com.example.demo.service.PlantService;

@SpringBootTest(
        classes = SpringBootFactoryApplication.class
)
@AutoConfigureMockMvc(addFilters = false)
class PlantControllerMockMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PlantService plantService;

    @Test
    @DisplayName("Should create plant successfully")
    void shouldCreatePlantSuccessfully() throws Exception {

        Plant plant = new Plant(
                "Pune Manufacturing Plant",
                "Pune, Maharashtra",
                true
        );

        plant.setId(1L);

        when(plantService.create(any()))
                .thenReturn(plant);

        String requestBody = """
                {
                    "name": "Pune Manufacturing Plant",
                    "location": "Pune, Maharashtra",
                    "active": true
                }
                """;

        mockMvc.perform(
                post("/api/plants")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody)
        )
        .andExpect(status().isCreated())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.id").value(1))
        .andExpect(jsonPath("$.name").value("Pune Manufacturing Plant"))
        .andExpect(jsonPath("$.location").value("Pune, Maharashtra"))
        .andExpect(jsonPath("$.active").value(true));
    }

    @Test
    @DisplayName("Should get plant by ID successfully")
    void shouldGetPlantByIdSuccessfully() throws Exception {

        Plant plant = new Plant(
                "Pune Manufacturing Plant",
                "Pune, Maharashtra",
                true
        );

        plant.setId(1L);

        when(plantService.getById(eq(1L)))
                .thenReturn(plant);

        mockMvc.perform(
                get("/api/plants/1")
        )
        .andExpect(status().isOk())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.id").value(1))
        .andExpect(jsonPath("$.name").value("Pune Manufacturing Plant"))
        .andExpect(jsonPath("$.location").value("Pune, Maharashtra"))
        .andExpect(jsonPath("$.active").value(true));
    }
}