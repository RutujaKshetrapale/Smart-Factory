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

import com.example.demo.dto.EnergyRequest;
import com.example.demo.entity.Energy;
import com.example.demo.entity.Machine;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.EnergyRepository;
import com.example.demo.repository.MachineRepository;

@ExtendWith(MockitoExtension.class)
class EnergyServiceTest {

    @Mock
    private EnergyRepository energyRepository;

    @Mock
    private MachineRepository machineRepository;

    @InjectMocks
    private EnergyService energyService;

    private Energy energy;
    private Machine machine;
    private EnergyRequest request;

    @BeforeEach
    void setUp() {

        machine = new Machine();

        machine.setName("CNC Machine 01");
        machine.setType("CNC");
        machine.setStatus("RUNNING");

        energy = new Energy();

        energy.setMachine(machine);
        energy.setEnergyConsumption(125.75);
        energy.setRecordedAt(
                LocalDateTime.of(
                        2026,
                        9,
                        11,
                        10,
                        0
                )
        );

        request = new EnergyRequest();

        request.setMachineId(1L);
        request.setEnergyConsumption(125.75);
        request.setRecordedAt(
                LocalDateTime.of(
                        2026,
                        9,
                        11,
                        10,
                        0
                )
        );
    }

    // =========================================================
    // CREATE
    // =========================================================

    @Test
    @DisplayName("Should create energy record successfully")
    void shouldCreateEnergySuccessfully() {

        when(machineRepository.findById(1L))
                .thenReturn(Optional.of(machine));

        when(energyRepository.save(any(Energy.class)))
                .thenReturn(energy);

        Energy result =
                energyService.create(request);

        assertNotNull(result);
        assertEquals(
                125.75,
                result.getEnergyConsumption()
        );
        assertEquals(machine, result.getMachine());
        assertNotNull(result.getRecordedAt());

        verify(energyRepository, times(1))
                .save(any(Energy.class));
    }

    @Test
    @DisplayName("Should throw exception when machine does not exist")
    void shouldThrowExceptionWhenMachineDoesNotExist() {

        when(machineRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(
                ResourceNotFoundException.class,
                () -> energyService.create(request)
        );

        verify(energyRepository, never())
                .save(any(Energy.class));
    }

    // =========================================================
    // GET ALL
    // =========================================================

    @Test
    @DisplayName("Should get all energy records")
    void shouldGetAllEnergy() {

        when(energyRepository.findAll())
                .thenReturn(List.of(energy));

        List<Energy> result =
                energyService.getAll();

        assertEquals(1, result.size());
    }

    @Test
    @DisplayName("Should get all energy records with pagination")
    void shouldGetAllEnergyWithPagination() {

        Pageable pageable = PageRequest.of(0, 10);

        when(energyRepository.findAll(pageable))
                .thenReturn(
                        new PageImpl<>(List.of(energy))
                );

        Page<Energy> result =
                energyService.getAll(pageable);

        assertEquals(1, result.getContent().size());
    }

    // =========================================================
    // GET BY ID
    // =========================================================

    @Test
    @DisplayName("Should get energy by id")
    void shouldGetEnergyById() {

        when(energyRepository.findById(1L))
                .thenReturn(Optional.of(energy));

        Energy result =
                energyService.getById(1L);

        assertNotNull(result);
        assertEquals(
                125.75,
                result.getEnergyConsumption()
        );
    }

    @Test
    @DisplayName("Should throw exception when energy does not exist")
    void shouldThrowExceptionWhenEnergyDoesNotExist() {

        when(energyRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(
                ResourceNotFoundException.class,
                () -> energyService.getById(1L)
        );
    }

    // =========================================================
    // GET BY MACHINE
    // =========================================================

    @Test
    @DisplayName("Should get energy by machine")
    void shouldGetEnergyByMachine() {

        when(machineRepository.existsById(1L))
                .thenReturn(true);

        when(energyRepository.findByMachineId(1L))
                .thenReturn(List.of(energy));

        List<Energy> result =
                energyService.getByMachine(1L);

        assertEquals(1, result.size());
    }

    @Test
    @DisplayName("Should get energy by machine with pagination")
    void shouldGetEnergyByMachineWithPagination() {

        Pageable pageable = PageRequest.of(0, 10);

        when(machineRepository.existsById(1L))
                .thenReturn(true);

        when(energyRepository.findByMachineId(
                1L,
                pageable))
                .thenReturn(
                        new PageImpl<>(List.of(energy))
                );

        Page<Energy> result =
                energyService.getByMachine(
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
                () -> energyService.getByMachine(1L)
        );
    }

    // =========================================================
    // UPDATE
    // =========================================================

    @Test
    @DisplayName("Should update energy successfully")
    void shouldUpdateEnergySuccessfully() {

        when(energyRepository.findById(1L))
                .thenReturn(Optional.of(energy));

        when(machineRepository.findById(1L))
                .thenReturn(Optional.of(machine));

        when(energyRepository.save(any(Energy.class)))
                .thenAnswer(invocation ->
                        invocation.getArgument(0));

        request.setEnergyConsumption(200.50);

        Energy result =
                energyService.update(
                        1L,
                        request
                );

        assertEquals(
                200.50,
                result.getEnergyConsumption()
        );

        verify(energyRepository, times(1))
                .save(any(Energy.class));
    }

    @Test
    @DisplayName("Should throw exception when updating non-existing energy")
    void shouldThrowExceptionWhenUpdatingNonExistingEnergy() {

        when(energyRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(
                ResourceNotFoundException.class,
                () -> energyService.update(
                        1L,
                        request
                )
        );

        verify(energyRepository, never())
                .save(any(Energy.class));
    }

    @Test
    @DisplayName("Should throw exception when update machine does not exist")
    void shouldThrowExceptionWhenUpdateMachineDoesNotExist() {

        when(energyRepository.findById(1L))
                .thenReturn(Optional.of(energy));

        when(machineRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(
                ResourceNotFoundException.class,
                () -> energyService.update(
                        1L,
                        request
                )
        );

        verify(energyRepository, never())
                .save(any(Energy.class));
    }

    // =========================================================
    // DELETE
    // =========================================================

    @Test
    @DisplayName("Should delete energy successfully")
    void shouldDeleteEnergySuccessfully() {

        when(energyRepository.findById(1L))
                .thenReturn(Optional.of(energy));

        energyService.delete(1L);

        verify(energyRepository, times(1))
                .delete(energy);
    }

    @Test
    @DisplayName("Should throw exception when deleting non-existing energy")
    void shouldThrowExceptionWhenDeletingNonExistingEnergy() {

        when(energyRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(
                ResourceNotFoundException.class,
                () -> energyService.delete(1L)
        );

        verify(energyRepository, never())
                .delete(any(Energy.class));
    }
}