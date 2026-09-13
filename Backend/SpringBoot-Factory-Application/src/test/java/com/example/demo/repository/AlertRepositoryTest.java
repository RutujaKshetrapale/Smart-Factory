package com.example.demo.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.example.demo.entity.Alert;
import com.example.demo.entity.Machine;
import com.example.demo.entity.Plant;

@DataJpaTest
class AlertRepositoryTest {

    @Autowired
    private AlertRepository alertRepository;

    @Autowired
    private MachineRepository machineRepository;

    @Autowired
    private PlantRepository plantRepository;

    // =========================
    // SAVE ALERT
    // =========================

    @Test
    @DisplayName("Should save alert successfully")
    void shouldSaveAlertSuccessfully() {

        Machine machine = createMachine();

        Alert alert =
                createAlert(
                        machine,
                        "HIGH"
                );

        Alert savedAlert =
                alertRepository.save(alert);

        assertEquals(
                "HIGH",
                savedAlert.getSeverity()
        );

    }

    // =========================
    // FIND BY MACHINE
    // =========================

    @Test
    @DisplayName("Should find alerts by machine successfully")
    void shouldFindAlertsByMachineSuccessfully() {

        Machine machine = createMachine();

        alertRepository.save(
                createAlert(
                        machine,
                        "HIGH"
                )
        );

        List<Alert> result =
                alertRepository.findByMachineId(
                        machine.getId()
                );

        assertEquals(
                1,
                result.size()
        );

    }

    // =========================
    // FIND UNRESOLVED
    // =========================

    @Test
    @DisplayName("Should find unresolved alerts successfully")
    void shouldFindUnresolvedAlertsSuccessfully() {

        Machine machine = createMachine();

        Alert alert =
                createAlert(
                        machine,
                        "CRITICAL"
                );

        alert.setResolved(false);

        alertRepository.save(alert);

        List<Alert> result =
                alertRepository.findByResolvedFalse();

        assertEquals(
                1,
                result.size()
        );

    }

    // =========================
    // FIND BY SEVERITY
    // =========================

    @Test
    @DisplayName("Should find alerts by severity successfully")
    void shouldFindAlertsBySeveritySuccessfully() {

        Machine machine = createMachine();

        alertRepository.save(
                createAlert(
                        machine,
                        "HIGH"
                )
        );

        List<Alert> result =
                alertRepository.findBySeverity(
                        "HIGH"
                );

        assertEquals(
                1,
                result.size()
        );

    }

    // =========================
    // PAGINATION
    // =========================

    @Test
    @DisplayName("Should get paginated alerts successfully")
    void shouldGetPaginatedAlertsSuccessfully() {

        Machine machine = createMachine();

        alertRepository.save(
                createAlert(
                        machine,
                        "HIGH"
                )
        );

        alertRepository.save(
                createAlert(
                        machine,
                        "CRITICAL"
                )
        );

        Page<Alert> result =
                alertRepository.findAll(
                        PageRequest.of(0, 1)
                );

        assertEquals(
                1,
                result.getContent().size()
        );

        assertEquals(
                2,
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

    private Alert createAlert(
            Machine machine,
            String severity) {

        Alert alert = new Alert();

        alert.setType("TEMPERATURE");
        alert.setSeverity(severity);
        alert.setMessage(
                "Machine temperature is high"
        );
        alert.setResolved(false);
        alert.setCreatedAt(
                LocalDateTime.now()
        );
        alert.setMachine(machine);

        return alert;
    }

}