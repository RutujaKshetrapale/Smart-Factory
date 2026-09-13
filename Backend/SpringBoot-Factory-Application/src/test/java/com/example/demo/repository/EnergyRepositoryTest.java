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

import com.example.demo.entity.Energy;
import com.example.demo.entity.Machine;
import com.example.demo.entity.Plant;

@DataJpaTest
class EnergyRepositoryTest {

    @Autowired
    private EnergyRepository energyRepository;

    @Autowired
    private MachineRepository machineRepository;

    @Autowired
    private PlantRepository plantRepository;

    // =========================
    // SAVE ENERGY
    // =========================

    @Test
    @DisplayName("Should save energy successfully")
    void shouldSaveEnergySuccessfully() {

        Machine machine = createMachine();

        Energy energy =
                createEnergy(
                        machine,
                        125.50
                );

        Energy savedEnergy =
                energyRepository.save(
                        energy
                );

        assertEquals(
                125.50,
                savedEnergy
                        .getEnergyConsumption()
        );

    }

    // =========================
    // FIND BY MACHINE
    // =========================

    @Test
    @DisplayName("Should find energy by machine successfully")
    void shouldFindEnergyByMachineSuccessfully() {

        Machine machine = createMachine();

        energyRepository.save(
                createEnergy(
                        machine,
                        125.50
                )
        );

        List<Energy> result =
                energyRepository.findByMachineId(
                        machine.getId()
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
    @DisplayName("Should get paginated energy successfully")
    void shouldGetPaginatedEnergySuccessfully() {

        Machine machine = createMachine();

        energyRepository.save(
                createEnergy(
                        machine,
                        125.50
                )
        );

        energyRepository.save(
                createEnergy(
                        machine,
                        150.75
                )
        );

        Page<Energy> result =
                energyRepository.findAll(
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

    private Energy createEnergy(
            Machine machine,
            Double consumption) {

        Energy energy =
                new Energy();

        energy.setEnergyConsumption(
                consumption
        );

        energy.setRecordedAt(
                LocalDateTime.now()
        );

        energy.setMachine(
                machine
        );

        return energy;
    }

}