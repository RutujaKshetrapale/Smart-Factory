package com.example.demo.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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

import com.example.demo.dto.SensorRequest;
import com.example.demo.entity.Machine;
import com.example.demo.entity.Sensor;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.MachineRepository;
import com.example.demo.repository.SensorRepository;

@ExtendWith(MockitoExtension.class)
class SensorServiceTest {

    @Mock
    private SensorRepository sensorRepository;

    @Mock
    private MachineRepository machineRepository;

    @InjectMocks
    private SensorService sensorService;

    private Sensor sensor;
    private Machine machine;
    private SensorRequest request;

    @BeforeEach
    void setUp() {

        machine = new Machine();

        machine.setName("CNC Machine 01");
        machine.setType("CNC");
        machine.setStatus("RUNNING");

        sensor = new Sensor();

        sensor.setName("Temperature Sensor");
        sensor.setType("TEMPERATURE");
        sensor.setUnit("°C");
        sensor.setActive(true);
        sensor.setMachine(machine);

        request = new SensorRequest();

        request.setName("Temperature Sensor");
        request.setType("TEMPERATURE");
        request.setUnit("°C");
        request.setMachineId(1L);
        request.setActive(true);
    }

    // =========================================================
    // CREATE
    // =========================================================

    @Test
    @DisplayName("Should create sensor successfully")
    void shouldCreateSensorSuccessfully() {

        when(machineRepository.findById(1L))
                .thenReturn(Optional.of(machine));

        when(sensorRepository.save(any(Sensor.class)))
                .thenReturn(sensor);

        Sensor result = sensorService.create(request);

        assertNotNull(result);
        assertEquals("Temperature Sensor", result.getName());
        assertEquals("TEMPERATURE", result.getType());
        assertEquals("°C", result.getUnit());
        assertTrue(result.isActive());
        assertEquals(machine, result.getMachine());

        verify(machineRepository, times(1))
                .findById(1L);

        verify(sensorRepository, times(1))
                .save(any(Sensor.class));
    }

    @Test
    @DisplayName("Should set sensor active by default")
    void shouldSetSensorActiveByDefault() {

        request.setActive(null);

        when(machineRepository.findById(1L))
                .thenReturn(Optional.of(machine));

        when(sensorRepository.save(any(Sensor.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Sensor result = sensorService.create(request);

        assertTrue(result.isActive());
    }

    @Test
    @DisplayName("Should create inactive sensor")
    void shouldCreateInactiveSensor() {

        request.setActive(false);

        when(machineRepository.findById(1L))
                .thenReturn(Optional.of(machine));

        when(sensorRepository.save(any(Sensor.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Sensor result = sensorService.create(request);

        assertFalse(result.isActive());
    }

    @Test
    @DisplayName("Should throw exception when machine does not exist")
    void shouldThrowExceptionWhenMachineDoesNotExist() {

        when(machineRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(
                ResourceNotFoundException.class,
                () -> sensorService.create(request)
        );

        verify(sensorRepository, never())
                .save(any(Sensor.class));
    }

    // =========================================================
    // GET ALL
    // =========================================================

    @Test
    @DisplayName("Should get all sensors")
    void shouldGetAllSensors() {

        when(sensorRepository.findAll())
                .thenReturn(List.of(sensor));

        List<Sensor> result = sensorService.getAll();

        assertNotNull(result);
        assertEquals(1, result.size());

        verify(sensorRepository, times(1))
                .findAll();
    }

    @Test
    @DisplayName("Should get all sensors with pagination")
    void shouldGetAllSensorsWithPagination() {

        Pageable pageable = PageRequest.of(0, 10);

        Page<Sensor> page =
                new PageImpl<>(List.of(sensor));

        when(sensorRepository.findAll(pageable))
                .thenReturn(page);

        Page<Sensor> result =
                sensorService.getAll(pageable);

        assertEquals(1, result.getContent().size());

        verify(sensorRepository, times(1))
                .findAll(pageable);
    }

    // =========================================================
    // GET BY ID
    // =========================================================

    @Test
    @DisplayName("Should get sensor by id")
    void shouldGetSensorById() {

        when(sensorRepository.findById(1L))
                .thenReturn(Optional.of(sensor));

        Sensor result =
                sensorService.getById(1L);

        assertNotNull(result);
        assertEquals(
                "Temperature Sensor",
                result.getName()
        );
    }

    @Test
    @DisplayName("Should throw exception when sensor does not exist")
    void shouldThrowExceptionWhenSensorDoesNotExist() {

        when(sensorRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(
                ResourceNotFoundException.class,
                () -> sensorService.getById(1L)
        );
    }

    // =========================================================
    // GET BY MACHINE
    // =========================================================

    @Test
    @DisplayName("Should get sensors by machine")
    void shouldGetSensorsByMachine() {

        when(machineRepository.existsById(1L))
                .thenReturn(true);

        when(sensorRepository.findByMachineId(1L))
                .thenReturn(List.of(sensor));

        List<Sensor> result =
                sensorService.getByMachine(1L);

        assertEquals(1, result.size());
    }

    @Test
    @DisplayName("Should get sensors by machine with pagination")
    void shouldGetSensorsByMachineWithPagination() {

        Pageable pageable = PageRequest.of(0, 10);

        Page<Sensor> page =
                new PageImpl<>(List.of(sensor));

        when(machineRepository.existsById(1L))
                .thenReturn(true);

        when(sensorRepository.findByMachineId(
                1L,
                pageable))
                .thenReturn(page);

        Page<Sensor> result =
                sensorService.getByMachine(
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
                () -> sensorService.getByMachine(1L)
        );

        verify(sensorRepository, never())
                .findByMachineId(1L);
    }

    // =========================================================
    // ACTIVE / INACTIVE
    // =========================================================

    @Test
    @DisplayName("Should get active sensors")
    void shouldGetActiveSensors() {

        when(sensorRepository.findByActiveTrue())
                .thenReturn(List.of(sensor));

        List<Sensor> result =
                sensorService.getActive();

        assertEquals(1, result.size());
    }

    @Test
    @DisplayName("Should get active sensors with pagination")
    void shouldGetActiveSensorsWithPagination() {

        Pageable pageable = PageRequest.of(0, 10);

        when(sensorRepository.findByActiveTrue(pageable))
                .thenReturn(
                        new PageImpl<>(List.of(sensor))
                );

        Page<Sensor> result =
                sensorService.getActive(pageable);

        assertEquals(1, result.getContent().size());
    }

    @Test
    @DisplayName("Should get inactive sensors")
    void shouldGetInactiveSensors() {

        when(sensorRepository.findByActiveFalse())
                .thenReturn(List.of());

        List<Sensor> result =
                sensorService.getInactive();

        assertNotNull(result);
        assertEquals(0, result.size());
    }

    @Test
    @DisplayName("Should get inactive sensors with pagination")
    void shouldGetInactiveSensorsWithPagination() {

        Pageable pageable = PageRequest.of(0, 10);

        when(sensorRepository.findByActiveFalse(pageable))
                .thenReturn(new PageImpl<>(List.of()));

        Page<Sensor> result =
                sensorService.getInactive(pageable);

        assertNotNull(result);
    }

    // =========================================================
    // GET BY TYPE
    // =========================================================

    @Test
    @DisplayName("Should get sensors by type")
    void shouldGetSensorsByType() {

        when(sensorRepository.findByTypeIgnoreCase(
                "TEMPERATURE"))
                .thenReturn(List.of(sensor));

        List<Sensor> result =
                sensorService.getByType("TEMPERATURE");

        assertEquals(1, result.size());
    }

    @Test
    @DisplayName("Should get sensors by type with pagination")
    void shouldGetSensorsByTypeWithPagination() {

        Pageable pageable = PageRequest.of(0, 10);

        when(sensorRepository.findByTypeIgnoreCase(
                "TEMPERATURE",
                pageable))
                .thenReturn(
                        new PageImpl<>(List.of(sensor))
                );

        Page<Sensor> result =
                sensorService.getByType(
                        "TEMPERATURE",
                        pageable
                );

        assertEquals(1, result.getContent().size());
    }

    // =========================================================
    // MACHINE + TYPE
    // =========================================================

    @Test
    @DisplayName("Should get sensors by machine and type")
    void shouldGetSensorsByMachineAndType() {

        when(machineRepository.existsById(1L))
                .thenReturn(true);

        when(sensorRepository
                .findByMachineIdAndTypeIgnoreCase(
                        1L,
                        "TEMPERATURE"))
                .thenReturn(List.of(sensor));

        List<Sensor> result =
                sensorService.getByMachineAndType(
                        1L,
                        "TEMPERATURE"
                );

        assertEquals(1, result.size());
    }

    @Test
    @DisplayName("Should get sensors by machine and type with pagination")
    void shouldGetSensorsByMachineAndTypeWithPagination() {

        Pageable pageable = PageRequest.of(0, 10);

        when(machineRepository.existsById(1L))
                .thenReturn(true);

        when(sensorRepository
                .findByMachineIdAndTypeIgnoreCase(
                        1L,
                        "TEMPERATURE",
                        pageable))
                .thenReturn(
                        new PageImpl<>(List.of(sensor))
                );

        Page<Sensor> result =
                sensorService.getByMachineAndType(
                        1L,
                        "TEMPERATURE",
                        pageable
                );

        assertEquals(1, result.getContent().size());
    }

    // =========================================================
    // SEARCH BY NAME
    // =========================================================

    @Test
    @DisplayName("Should search sensors by name")
    void shouldSearchSensorsByName() {

        when(sensorRepository
                .findByNameContainingIgnoreCase("Temperature"))
                .thenReturn(List.of(sensor));

        List<Sensor> result =
                sensorService.searchByName("Temperature");

        assertEquals(1, result.size());
    }

    @Test
    @DisplayName("Should search sensors by name with pagination")
    void shouldSearchSensorsByNameWithPagination() {

        Pageable pageable = PageRequest.of(0, 10);

        when(sensorRepository
                .findByNameContainingIgnoreCase(
                        "Temperature",
                        pageable))
                .thenReturn(
                        new PageImpl<>(List.of(sensor))
                );

        Page<Sensor> result =
                sensorService.searchByName(
                        "Temperature",
                        pageable
                );

        assertEquals(1, result.getContent().size());
    }

    // =========================================================
    // UPDATE
    // =========================================================

    @Test
    @DisplayName("Should update sensor successfully")
    void shouldUpdateSensorSuccessfully() {

        when(sensorRepository.findById(1L))
                .thenReturn(Optional.of(sensor));

        when(machineRepository.findById(1L))
                .thenReturn(Optional.of(machine));

        when(sensorRepository.save(any(Sensor.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        request.setName("Updated Temperature Sensor");
        request.setType("PRESSURE");
        request.setUnit("bar");
        request.setActive(false);

        Sensor result =
                sensorService.update(
                        1L,
                        request
                );

        assertEquals(
                "Updated Temperature Sensor",
                result.getName()
        );

        assertEquals(
                "PRESSURE",
                result.getType()
        );

        assertEquals(
                "bar",
                result.getUnit()
        );

        assertFalse(result.isActive());

        verify(sensorRepository, times(1))
                .save(any(Sensor.class));
    }

    @Test
    @DisplayName("Should preserve active value when update active is null")
    void shouldPreserveActiveValueWhenUpdateActiveIsNull() {

        request.setActive(null);

        when(sensorRepository.findById(1L))
                .thenReturn(Optional.of(sensor));

        when(machineRepository.findById(1L))
                .thenReturn(Optional.of(machine));

        when(sensorRepository.save(any(Sensor.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Sensor result =
                sensorService.update(
                        1L,
                        request
                );

        assertTrue(result.isActive());
    }

    @Test
    @DisplayName("Should throw exception when updating non-existing sensor")
    void shouldThrowExceptionWhenUpdatingNonExistingSensor() {

        when(sensorRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(
                ResourceNotFoundException.class,
                () -> sensorService.update(
                        1L,
                        request
                )
        );

        verify(sensorRepository, never())
                .save(any(Sensor.class));
    }

    @Test
    @DisplayName("Should throw exception when update machine does not exist")
    void shouldThrowExceptionWhenUpdateMachineDoesNotExist() {

        when(sensorRepository.findById(1L))
                .thenReturn(Optional.of(sensor));

        when(machineRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(
                ResourceNotFoundException.class,
                () -> sensorService.update(
                        1L,
                        request
                )
        );

        verify(sensorRepository, never())
                .save(any(Sensor.class));
    }

    // =========================================================
    // DELETE
    // =========================================================

    @Test
    @DisplayName("Should delete sensor successfully")
    void shouldDeleteSensorSuccessfully() {

        when(sensorRepository.findById(1L))
                .thenReturn(Optional.of(sensor));

        sensorService.delete(1L);

        verify(sensorRepository, times(1))
                .delete(sensor);
    }

    @Test
    @DisplayName("Should throw exception when deleting non-existing sensor")
    void shouldThrowExceptionWhenDeletingNonExistingSensor() {

        when(sensorRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(
                ResourceNotFoundException.class,
                () -> sensorService.delete(1L)
        );

        verify(sensorRepository, never())
                .delete(any(Sensor.class));
    }
}