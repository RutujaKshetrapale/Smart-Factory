package com.example.demo.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.example.demo.entity.Machine;
import com.example.demo.entity.Maintenance;
import com.example.demo.entity.Plant;

@DataJpaTest
class MaintenanceRepositoryTest {

    @Autowired
    private MaintenanceRepository maintenanceRepository;

    @Autowired
    private MachineRepository machineRepository;

    @Autowired
    private PlantRepository plantRepository;

    // =========================
    // SAVE MAINTENANCE
    // =========================

    @Test
    @DisplayName("Should save maintenance successfully")
    void shouldSaveMaintenanceSuccessfully() {

        Machine machine = createMachine();

        Maintenance maintenance =
                createMaintenance(
                        machine,
                        "SCHEDULED"
                );

        Maintenance savedMaintenance =
                maintenanceRepository.save(
                        maintenance
                );

        assertEquals(
                "SCHEDULED",
                savedMaintenance.getStatus()
        );

    }

    // =========================
    // FIND BY MACHINE
    // =========================

    @Test
    @DisplayName("Should find maintenance by machine successfully")
    void shouldFindMaintenanceByMachineSuccessfully() {

        Machine machine = createMachine();

        maintenanceRepository.save(
                createMaintenance(
                        machine,
                        "SCHEDULED"
                )
        );

        List<Maintenance> result =
                maintenanceRepository.findByMachineId(
                        machine.getId()
                );

        assertEquals(
                1,
                result.size()
        );

    }

    // =========================
    // FIND BY STATUS
    // =========================

    @Test
    @DisplayName("Should find maintenance by status successfully")
    void shouldFindMaintenanceByStatusSuccessfully() {

        Machine machine = createMachine();

        maintenanceRepository.save(
                createMaintenance(
                        machine,
                        "COMPLETED"
                )
        );

        List<Maintenance> result =
                maintenanceRepository.findByStatus(
                        "COMPLETED"
                );

        assertEquals(
                1,
                result.size()
        );

    }

    // =========================
    // FIND BY STATUS ORDERED
    // =========================

    @Test
    @DisplayName("Should find maintenance ordered by scheduled date")
    void shouldFindMaintenanceOrderedByScheduledDate() {

        Machine machine = createMachine();

        Maintenance first =
                createMaintenance(
                        machine,
                        "SCHEDULED"
                );

        first.setScheduledDate(
                LocalDate.of(
                        2026,
                        9,
                        10
                )
        );

        Maintenance second =
                createMaintenance(
                        machine,
                        "SCHEDULED"
                );

        second.setScheduledDate(
                LocalDate.of(
                        2026,
                        9,
                        5
                )
        );

        maintenanceRepository.save(first);
        maintenanceRepository.save(second);

        List<Maintenance> result =
                maintenanceRepository
                        .findByStatusOrderByScheduledDateAsc(
                                "SCHEDULED"
                        );

        assertEquals(
                2,
                result.size()
        );

        assertEquals(
                LocalDate.of(
                        2026,
                        9,
                        5
                ),
                result.get(0)
                        .getScheduledDate()
        );

    }

    // =========================
    // PAGINATION
    // =========================

    @Test
    @DisplayName("Should get paginated maintenance successfully")
    void shouldGetPaginatedMaintenanceSuccessfully() {

        Machine machine = createMachine();

        maintenanceRepository.save(
                createMaintenance(
                        machine,
                        "SCHEDULED"
                )
        );

        Page<Maintenance> result =
                maintenanceRepository.findAll(
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

    private Maintenance createMaintenance(
            Machine machine,
            String status) {

        Maintenance maintenance =
                new Maintenance();

        maintenance.setType(
                "PREVENTIVE"
        );

        maintenance.setDescription(
                "Routine machine maintenance"
        );

        maintenance.setScheduledDate(
                LocalDate.now()
        );

        maintenance.setStatus(
                status
        );

        maintenance.setTechnician(
                "Technician 01"
        );

        maintenance.setCreatedAt(
                LocalDateTime.now()
        );

        maintenance.setMachine(
                machine
        );

        return maintenance;
    }

}