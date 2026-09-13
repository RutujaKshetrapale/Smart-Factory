package com.example.demo.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.example.demo.entity.Machine;
import com.example.demo.entity.Plant;
import com.example.demo.entity.Telemetry;

@DataJpaTest
class TelemetryRepositoryTest {

    @Autowired
    private TelemetryRepository telemetryRepository;

    @Autowired
    private MachineRepository machineRepository;

    @Autowired
    private PlantRepository plantRepository;

    // =========================
    // SAVE TELEMETRY
    // =========================

    @Test
    @DisplayName("Should save telemetry successfully")
    void shouldSaveTelemetrySuccessfully() {

        Machine machine = createMachine();

        Telemetry telemetry =
                createTelemetry(
                        machine,
                        LocalDateTime.of(
                                2026,
                                9,
                                1,
                                10,
                                0
                        )
                );

        Telemetry savedTelemetry =
                telemetryRepository.save(
                        telemetry
                );

        assertTrue(
                savedTelemetry.getId() > 0
        );

        assertEquals(
                75.5,
                savedTelemetry.getTemperature()
        );

    }

    // =========================
    // FIND BY MACHINE
    // =========================

    @Test
    @DisplayName("Should find telemetry by machine successfully")
    void shouldFindTelemetryByMachineSuccessfully() {

        Machine machine = createMachine();

        telemetryRepository.save(
                createTelemetry(
                        machine,
                        LocalDateTime.now()
                )
        );

        List<Telemetry> result =
                telemetryRepository.findByMachineId(
                        machine.getId()
                );

        assertEquals(
                1,
                result.size()
        );

    }

    // =========================
    // FIND BY DATE RANGE
    // =========================

    @Test
    @DisplayName("Should find telemetry by date range successfully")
    void shouldFindTelemetryByDateRangeSuccessfully() {

        Machine machine = createMachine();

        LocalDateTime timestamp =
                LocalDateTime.of(
                        2026,
                        9,
                        1,
                        10,
                        0
                );

        telemetryRepository.save(
                createTelemetry(
                        machine,
                        timestamp
                )
        );

        List<Telemetry> result =
                telemetryRepository
                        .findByMachineIdAndTimestampBetween(
                                machine.getId(),
                                timestamp.minusHours(1),
                                timestamp.plusHours(1)
                        );

        assertEquals(
                1,
                result.size()
        );

    }

    // =========================
    // FIND LATEST
    // =========================

    @Test
    @DisplayName("Should find latest telemetry successfully")
    void shouldFindLatestTelemetrySuccessfully() {

        Machine machine = createMachine();

        telemetryRepository.save(
                createTelemetry(
                        machine,
                        LocalDateTime.of(
                                2026,
                                9,
                                1,
                                10,
                                0
                        )
                )
        );

        telemetryRepository.save(
                createTelemetry(
                        machine,
                        LocalDateTime.of(
                                2026,
                                9,
                                1,
                                11,
                                0
                        )
                )
        );

        List<Telemetry> result =
                telemetryRepository
                        .findTop10ByMachineIdOrderByTimestampDesc(
                                machine.getId()
                        );

        assertEquals(
                2,
                result.size()
        );

        assertEquals(
                11,
                result.get(0)
                        .getTimestamp()
                        .getHour()
        );

    }

    // =========================
    // PAGINATION
    // =========================

    @Test
    @DisplayName("Should get paginated telemetry successfully")
    void shouldGetPaginatedTelemetrySuccessfully() {

        Machine machine = createMachine();

        telemetryRepository.save(
                createTelemetry(
                        machine,
                        LocalDateTime.now()
                )
        );

        Page<Telemetry> result =
                telemetryRepository.findAll(
                        PageRequest.of(0, 10)
                );

        assertEquals(
                1,
                result.getTotalElements()
        );

    }

    // =========================
    // HELPER METHODS
    // =========================

    private Machine createMachine() {

        Plant plant =
                plantRepository.save(
                        new Plant(
                                "Pune Plant",
                                "Pune",
                                true
                        )
                );

        Machine machine = new Machine();

        machine.setName("CNC Machine");
        machine.setType("CNC");
        machine.setStatus("ACTIVE");
        machine.setPlant(plant);

        return machineRepository.save(machine);
    }

    private Telemetry createTelemetry(
            Machine machine,
            LocalDateTime timestamp) {

        Telemetry telemetry =
                new Telemetry();

        telemetry.setTemperature(75.5);
        telemetry.setVibration(2.5);
        telemetry.setPressure(10.5);
        telemetry.setRpm(1500.0);
        telemetry.setTimestamp(timestamp);
        telemetry.setMachine(machine);

        return telemetry;
    }

}