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
import com.example.demo.dto.ProductionRequest;
import com.example.demo.entity.Production;
import com.example.demo.service.ProductionService;

@ExtendWith(MockitoExtension.class)
class ProductionControllerTest {

    @Mock
    private ProductionService productionService;

    @InjectMocks
    private ProductionController productionController;

    private ProductionRequest request;
    private Production production;

    @BeforeEach
    void setUp() {

        request = new ProductionRequest();
        request.setMachineId(1L);
        request.setProductName("Product A");
        request.setQuantityProduced(100);
        request.setQuantityRejected(10);
        request.setStatus("COMPLETED");

        production = new Production();
        production.setProductName("Product A");
        production.setQuantityProduced(100);
        production.setQuantityRejected(10);
        production.setStatus("COMPLETED");
    }

    // =========================
    // CREATE PRODUCTION
    // =========================

    @Test
    @DisplayName("Should create production successfully")
    void shouldCreateProductionSuccessfully() {

        when(productionService.create(request))
                .thenReturn(production);

        ResponseEntity<Production> response =
                productionController.create(request);

        assertNotNull(response);
        assertEquals(
                HttpStatus.CREATED,
                response.getStatusCode()
        );
        assertEquals(
                production,
                response.getBody()
        );

        verify(productionService, times(1))
                .create(request);
    }

    // =========================
    // GET ALL PRODUCTION
    // =========================

    @Test
    @DisplayName("Should get all production successfully")
    void shouldGetAllProductionSuccessfully() {

        when(productionService.getAll())
                .thenReturn(Arrays.asList(production));

        ResponseEntity<?> response =
                productionController.getAll(
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

        verify(productionService, times(1))
                .getAll();
    }

    // =========================
    // GET PAGINATED PRODUCTION
    // =========================

    @Test
    @DisplayName("Should get paginated production successfully")
    void shouldGetPaginatedProductionSuccessfully() {

        PageImpl<Production> page =
                new PageImpl<>(
                        Arrays.asList(production)
                );

        when(productionService.getAll(any()))
                .thenReturn(page);

        ResponseEntity<?> response =
                productionController.getAll(
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

        verify(productionService, times(1))
                .getAll(any());
    }

    // =========================
    // GET PRODUCTION BY ID
    // =========================

    @Test
    @DisplayName("Should get production by id successfully")
    void shouldGetProductionByIdSuccessfully() {

        when(productionService.getById(1L))
                .thenReturn(production);

        ResponseEntity<Production> response =
                productionController.getById(1L);

        assertNotNull(response);
        assertEquals(
                HttpStatus.OK,
                response.getStatusCode()
        );
        assertEquals(
                production,
                response.getBody()
        );

        verify(productionService, times(1))
                .getById(1L);
    }

    // =========================
    // GET PRODUCTION BY MACHINE
    // =========================

    @Test
    @DisplayName("Should get production by machine successfully")
    void shouldGetProductionByMachineSuccessfully() {

        when(productionService.getByMachine(1L))
                .thenReturn(Arrays.asList(production));

        ResponseEntity<?> response =
                productionController.getByMachine(
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

        verify(productionService, times(1))
                .getByMachine(1L);
    }

    // =========================
    // GET PAGINATED PRODUCTION BY MACHINE
    // =========================

    @Test
    @DisplayName("Should get paginated production by machine successfully")
    void shouldGetPaginatedProductionByMachineSuccessfully() {

        PageImpl<Production> page =
                new PageImpl<>(
                        Arrays.asList(production)
                );

        when(productionService.getByMachine(
                eq(1L),
                any()
        ))
                .thenReturn(page);

        ResponseEntity<?> response =
                productionController.getByMachine(
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

        verify(productionService, times(1))
                .getByMachine(eq(1L), any());
    }

    // =========================
    // GET PRODUCTION BY STATUS
    // =========================

    @Test
    @DisplayName("Should get production by status successfully")
    void shouldGetProductionByStatusSuccessfully() {

        when(productionService.getByStatus("COMPLETED"))
                .thenReturn(Arrays.asList(production));

        ResponseEntity<?> response =
                productionController.getByStatus(
                        "COMPLETED",
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

        verify(productionService, times(1))
                .getByStatus("COMPLETED");
    }

    // =========================
    // GET PAGINATED PRODUCTION BY STATUS
    // =========================

    @Test
    @DisplayName("Should get paginated production by status successfully")
    void shouldGetPaginatedProductionByStatusSuccessfully() {

        PageImpl<Production> page =
                new PageImpl<>(
                        Arrays.asList(production)
                );

        when(productionService.getByStatus(
                eq("COMPLETED"),
                any()
        ))
                .thenReturn(page);

        ResponseEntity<?> response =
                productionController.getByStatus(
                        "COMPLETED",
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

        verify(productionService, times(1))
                .getByStatus(eq("COMPLETED"), any());
    }

    // =========================
    // UPDATE PRODUCTION
    // =========================

    @Test
    @DisplayName("Should update production successfully")
    void shouldUpdateProductionSuccessfully() {

        when(productionService.update(
                1L,
                request
        ))
                .thenReturn(production);

        ResponseEntity<Production> response =
                productionController.update(
                        1L,
                        request
                );

        assertNotNull(response);
        assertEquals(
                HttpStatus.OK,
                response.getStatusCode()
        );

        verify(productionService, times(1))
                .update(1L, request);
    }

    // =========================
    // DELETE PRODUCTION
    // =========================

    @Test
    @DisplayName("Should delete production successfully")
    void shouldDeleteProductionSuccessfully() {

        ResponseEntity<Void> response =
                productionController.delete(1L);

        assertNotNull(response);
        assertEquals(
                HttpStatus.NO_CONTENT,
                response.getStatusCode()
        );

        verify(productionService, times(1))
                .delete(1L);
    }
}