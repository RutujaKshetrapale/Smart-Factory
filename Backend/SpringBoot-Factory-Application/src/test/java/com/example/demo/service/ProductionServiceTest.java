package com.example.demo.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.example.demo.dto.ProductionRequest;
import com.example.demo.entity.Machine;
import com.example.demo.entity.Production;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.MachineRepository;
import com.example.demo.repository.ProductionRepository;

@ExtendWith(MockitoExtension.class)
class ProductionServiceTest {

    @Mock
    private ProductionRepository productionRepository;

    @Mock
    private MachineRepository machineRepository;

    @InjectMocks
    private ProductionService productionService;

    private Production production;
    private Machine machine;
    private ProductionRequest request;

    @BeforeEach
    void setUp() {

        LocalDateTime start =
                LocalDateTime.of(
                        2026,
                        9,
                        11,
                        8,
                        0
                );

        LocalDateTime end =
                LocalDateTime.of(
                        2026,
                        9,
                        11,
                        16,
                        0
                );

        machine = new Machine();

        machine.setName("CNC Machine 01");
        machine.setType("CNC");
        machine.setStatus("RUNNING");

        production = new Production();

        production.setMachine(machine);
        production.setProductName("Gear");
        production.setQuantityProduced(1000);
        production.setQuantityRejected(20);
        production.setProductionStart(start);
        production.setProductionEnd(end);
        production.setStatus("COMPLETED");

        request = new ProductionRequest();

        request.setMachineId(1L);
        request.setProductName("Gear");
        request.setQuantityProduced(1000);
        request.setQuantityRejected(20);
        request.setProductionStart(start);
        request.setProductionEnd(end);
        request.setStatus("COMPLETED");
    }

    // =========================================================
    // CREATE
    // =========================================================

    @Test
    @DisplayName("Should create production successfully")
    void shouldCreateProductionSuccessfully() {

        when(machineRepository.findById(1L))
                .thenReturn(Optional.of(machine));

        when(productionRepository.save(
                any(Production.class)))
                .thenReturn(production);

        Production result =
                productionService.create(request);

        assertNotNull(result);
        assertEquals("Gear", result.getProductName());
        assertEquals(
                1000,
                result.getQuantityProduced()
        );
        assertEquals(
                20,
                result.getQuantityRejected()
        );
        assertEquals(machine, result.getMachine());

        verify(productionRepository, times(1))
                .save(any(Production.class));
    }

    @Test
    @DisplayName("Should throw exception when machine does not exist")
    void shouldThrowExceptionWhenMachineDoesNotExist() {

        when(machineRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(
                ResourceNotFoundException.class,
                () -> productionService.create(request)
        );

        verify(productionRepository, never())
                .save(any(Production.class));
    }

    // =========================================================
    // GET ALL
    // =========================================================

    @Test
    @DisplayName("Should get all production records")
    void shouldGetAllProduction() {

        when(productionRepository.findAll())
                .thenReturn(List.of(production));

        List<Production> result =
                productionService.getAll();

        assertEquals(1, result.size());
    }

    @Test
    @DisplayName("Should get all production records with pagination")
    void shouldGetAllProductionWithPagination() {

        Pageable pageable = PageRequest.of(0, 10);

        when(productionRepository.findAll(pageable))
                .thenReturn(
                        new PageImpl<>(List.of(production))
                );

        Page<Production> result =
                productionService.getAll(pageable);

        assertEquals(1, result.getContent().size());
    }

    // =========================================================
    // GET BY ID
    // =========================================================

    @Test
    @DisplayName("Should get production by id")
    void shouldGetProductionById() {

        when(productionRepository.findById(1L))
                .thenReturn(Optional.of(production));

        Production result =
                productionService.getById(1L);

        assertNotNull(result);
        assertEquals("Gear", result.getProductName());
    }

    @Test
    @DisplayName("Should throw exception when production does not exist")
    void shouldThrowExceptionWhenProductionDoesNotExist() {

        when(productionRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(
                ResourceNotFoundException.class,
                () -> productionService.getById(1L)
        );
    }

    // =========================================================
    // GET BY MACHINE
    // =========================================================

    @Test
    @DisplayName("Should get production by machine")
    void shouldGetProductionByMachine() {

        when(machineRepository.existsById(1L))
                .thenReturn(true);

        when(productionRepository.findByMachineId(1L))
                .thenReturn(List.of(production));

        List<Production> result =
                productionService.getByMachine(1L);

        assertEquals(1, result.size());
    }

    @Test
    @DisplayName("Should get production by machine with pagination")
    void shouldGetProductionByMachineWithPagination() {

        Pageable pageable = PageRequest.of(0, 10);

        when(machineRepository.existsById(1L))
                .thenReturn(true);

        when(productionRepository.findByMachineId(
                1L,
                pageable))
                .thenReturn(
                        new PageImpl<>(List.of(production))
                );

        Page<Production> result =
                productionService.getByMachine(
                        1L,
                        pageable
                );

        assertEquals(1, result.getContent().size());
    }

    @Test
    @DisplayName("Should throw exception when machine does not exist")
    void shouldThrowExceptionWhenMachineDoesNotExistForGet() {

        when(machineRepository.existsById(1L))
                .thenReturn(false);

        assertThrows(
                ResourceNotFoundException.class,
                () -> productionService.getByMachine(1L)
        );
    }

    // =========================================================
    // STATUS
    // =========================================================

    @Test
    @DisplayName("Should get production by status")
    void shouldGetProductionByStatus() {

        when(productionRepository.findByStatus(
                "COMPLETED"))
                .thenReturn(List.of(production));

        List<Production> result =
                productionService.getByStatus(
                        "COMPLETED"
                );

        assertEquals(1, result.size());
    }

    @Test
    @DisplayName("Should get production by status with pagination")
    void shouldGetProductionByStatusWithPagination() {

        Pageable pageable = PageRequest.of(0, 10);

        when(productionRepository.findByStatus(
                "COMPLETED",
                pageable))
                .thenReturn(
                        new PageImpl<>(List.of(production))
                );

        Page<Production> result =
                productionService.getByStatus(
                        "COMPLETED",
                        pageable
                );

        assertEquals(1, result.getContent().size());
    }

    // =========================================================
    // UPDATE
    // =========================================================

    @Test
    @DisplayName("Should update production successfully")
    void shouldUpdateProductionSuccessfully() {

        when(productionRepository.findById(1L))
                .thenReturn(Optional.of(production));

        when(machineRepository.findById(1L))
                .thenReturn(Optional.of(machine));

        when(productionRepository.save(
                any(Production.class)))
                .thenAnswer(invocation ->
                        invocation.getArgument(0));

        request.setQuantityProduced(1200);
        request.setQuantityRejected(15);

        Production result =
                productionService.update(
                        1L,
                        request
                );

        assertEquals(
                1200,
                result.getQuantityProduced()
        );

        assertEquals(
                15,
                result.getQuantityRejected()
        );

        verify(productionRepository, times(1))
                .save(any(Production.class));
    }

    @Test
    @DisplayName("Should throw exception when updating non-existing production")
    void shouldThrowExceptionWhenUpdatingNonExistingProduction() {

        when(productionRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(
                ResourceNotFoundException.class,
                () -> productionService.update(
                        1L,
                        request
                )
        );

        verify(productionRepository, never())
                .save(any(Production.class));
    }

    @Test
    @DisplayName("Should throw exception when update machine does not exist")
    void shouldThrowExceptionWhenUpdateMachineDoesNotExist() {

        when(productionRepository.findById(1L))
                .thenReturn(Optional.of(production));

        when(machineRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(
                ResourceNotFoundException.class,
                () -> productionService.update(
                        1L,
                        request
                )
        );

        verify(productionRepository, never())
                .save(any(Production.class));
    }

    // =========================================================
    // DELETE
    // =========================================================

    @Test
    @DisplayName("Should delete production successfully")
    void shouldDeleteProductionSuccessfully() {

        when(productionRepository.findById(1L))
                .thenReturn(Optional.of(production));

        productionService.delete(1L);

        verify(productionRepository, times(1))
                .delete(production);
    }

    @Test
    @DisplayName("Should throw exception when deleting non-existing production")
    void shouldThrowExceptionWhenDeletingNonExistingProduction() {

        when(productionRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(
                ResourceNotFoundException.class,
                () -> productionService.delete(1L)
        );

        verify(productionRepository, never())
                .delete(any(Production.class));
    }
}