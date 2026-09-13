package com.example.demo.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.demo.dto.MachineRequest;
import com.example.demo.dto.PageResponse;
import com.example.demo.entity.Machine;
import com.example.demo.service.MachineService;

@ExtendWith(MockitoExtension.class)
class MachineControllerTest {

    @Mock
    private MachineService machineService;

    @InjectMocks
    private MachineController machineController;

    private MachineRequest request;
    private Machine machine;

    @BeforeEach
    void setUp() {

        request = new MachineRequest();
        request.setName("CNC Machine 01");
        request.setType("CNC");
        request.setStatus("RUNNING");
        request.setPlantId(2L);

        machine = new Machine();
        machine.setName("CNC Machine 01");
        machine.setType("CNC");
        machine.setStatus("RUNNING");
    }

    // =========================
    // CREATE MACHINE
    // =========================

    @Test
    @DisplayName("Should create machine successfully")
    void shouldCreateMachineSuccessfully() {

        when(machineService.create(request))
                .thenReturn(machine);

        ResponseEntity<Machine> response =
                machineController.create(request);

        assertNotNull(response);
        assertEquals(
                HttpStatus.CREATED,
                response.getStatusCode()
        );
        assertEquals(
                machine,
                response.getBody()
        );

        verify(machineService, times(1))
                .create(request);
    }

    // =========================
    // GET ALL MACHINES
    // =========================

    @Test
    @DisplayName("Should get all machines successfully")
    void shouldGetAllMachinesSuccessfully() {

        PageRequest pageable =
                PageRequest.of(
                        0,
                        10,
                        Sort.by(
                                Sort.Direction.ASC,
                                "name"
                        )
                );

        PageImpl<Machine> page =
                new PageImpl<>(
                        Arrays.asList(machine),
                        pageable,
                        1
                );

        when(machineService.getAll(
                org.mockito.ArgumentMatchers.any(
                        org.springframework.data.domain.Pageable.class
                )))
                .thenReturn(page);

        ResponseEntity<PageResponse<Machine>> response =
                machineController.getAll(
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
        assertEquals(
                1,
                response.getBody().getTotalElements()
        );

        verify(machineService, times(1))
                .getAll(
                        org.mockito.ArgumentMatchers.any(
                                org.springframework.data.domain.Pageable.class
                        )
                );
    }

    // =========================
    // GET MACHINE BY ID
    // =========================

    @Test
    @DisplayName("Should get machine by id successfully")
    void shouldGetMachineByIdSuccessfully() {

        when(machineService.getById(1L))
                .thenReturn(machine);

        ResponseEntity<Machine> response =
                machineController.getById(1L);

        assertNotNull(response);
        assertEquals(
                HttpStatus.OK,
                response.getStatusCode()
        );
        assertEquals(
                machine,
                response.getBody()
        );

        verify(machineService, times(1))
                .getById(1L);
    }

    // =========================
    // GET MACHINES BY PLANT
    // =========================

    @Test
    @DisplayName("Should get machines by plant successfully")
    void shouldGetMachinesByPlantSuccessfully() {

        PageRequest pageable =
                PageRequest.of(
                        0,
                        10,
                        Sort.by("name").ascending()
                );

        PageImpl<Machine> page =
                new PageImpl<>(
                        Arrays.asList(machine),
                        pageable,
                        1
                );

        when(machineService.getByPlant(
                org.mockito.ArgumentMatchers.eq(2L),
                org.mockito.ArgumentMatchers.any(
                        org.springframework.data.domain.Pageable.class
                )))
                .thenReturn(page);

        ResponseEntity<PageResponse<Machine>> response =
                machineController.getByPlant(
                        2L,
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

        verify(machineService, times(1))
                .getByPlant(
                        org.mockito.ArgumentMatchers.eq(2L),
                        org.mockito.ArgumentMatchers.any(
                                org.springframework.data.domain.Pageable.class
                        )
                );
    }

    // =========================
    // GET MACHINES BY STATUS
    // =========================

    @Test
    @DisplayName("Should get machines by status successfully")
    void shouldGetMachinesByStatusSuccessfully() {

        PageImpl<Machine> page =
                new PageImpl<>(
                        Arrays.asList(machine)
                );

        when(machineService.getByStatus(
                org.mockito.ArgumentMatchers.eq("RUNNING"),
                org.mockito.ArgumentMatchers.any(
                        org.springframework.data.domain.Pageable.class
                )))
                .thenReturn(page);

        ResponseEntity<PageResponse<Machine>> response =
                machineController.getByStatus(
                        "RUNNING",
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

        verify(machineService, times(1))
                .getByStatus(
                        org.mockito.ArgumentMatchers.eq("RUNNING"),
                        org.mockito.ArgumentMatchers.any(
                                org.springframework.data.domain.Pageable.class
                        )
                );
    }

    // =========================
    // GET MACHINES BY PLANT AND STATUS
    // =========================

    @Test
    @DisplayName("Should get machines by plant and status successfully")
    void shouldGetMachinesByPlantAndStatusSuccessfully() {

        PageImpl<Machine> page =
                new PageImpl<>(
                        Arrays.asList(machine)
                );

        when(machineService.getByPlantAndStatus(
                org.mockito.ArgumentMatchers.eq(2L),
                org.mockito.ArgumentMatchers.eq("RUNNING"),
                org.mockito.ArgumentMatchers.any(
                        org.springframework.data.domain.Pageable.class
                )))
                .thenReturn(page);

        ResponseEntity<PageResponse<Machine>> response =
                machineController.getByPlantAndStatus(
                        2L,
                        "RUNNING",
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

        verify(machineService, times(1))
                .getByPlantAndStatus(
                        org.mockito.ArgumentMatchers.eq(2L),
                        org.mockito.ArgumentMatchers.eq("RUNNING"),
                        org.mockito.ArgumentMatchers.any(
                                org.springframework.data.domain.Pageable.class
                        )
                );
    }

    // =========================
    // SEARCH MACHINES BY NAME
    // =========================

    @Test
    @DisplayName("Should search machines by name successfully")
    void shouldSearchMachinesByNameSuccessfully() {

        PageImpl<Machine> page =
                new PageImpl<>(
                        Arrays.asList(machine)
                );

        when(machineService.searchByName(
                org.mockito.ArgumentMatchers.eq("CNC"),
                org.mockito.ArgumentMatchers.any(
                        org.springframework.data.domain.Pageable.class
                )))
                .thenReturn(page);

        ResponseEntity<PageResponse<Machine>> response =
                machineController.searchByName(
                        "CNC",
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

        verify(machineService, times(1))
                .searchByName(
                        org.mockito.ArgumentMatchers.eq("CNC"),
                        org.mockito.ArgumentMatchers.any(
                                org.springframework.data.domain.Pageable.class
                        )
                );
    }

    // =========================
    // UPDATE MACHINE
    // =========================

    @Test
    @DisplayName("Should update machine successfully")
    void shouldUpdateMachineSuccessfully() {

        when(machineService.update(1L, request))
                .thenReturn(machine);

        ResponseEntity<Machine> response =
                machineController.update(
                        1L,
                        request
                );

        assertNotNull(response);
        assertEquals(
                HttpStatus.OK,
                response.getStatusCode()
        );
        assertEquals(
                machine,
                response.getBody()
        );

        verify(machineService, times(1))
                .update(1L, request);
    }

    // =========================
    // DELETE MACHINE
    // =========================

    @Test
    @DisplayName("Should delete machine successfully")
    void shouldDeleteMachineSuccessfully() {

        ResponseEntity<Void> response =
                machineController.delete(1L);

        assertNotNull(response);
        assertEquals(
                HttpStatus.NO_CONTENT,
                response.getStatusCode()
        );

        verify(machineService, times(1))
                .delete(1L);
    }
}