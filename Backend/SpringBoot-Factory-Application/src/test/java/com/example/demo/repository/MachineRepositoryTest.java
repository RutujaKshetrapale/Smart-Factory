package com.example.demo.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.example.demo.entity.Machine;
import com.example.demo.entity.Plant;

@DataJpaTest
class MachineRepositoryTest {

    @Autowired
    private MachineRepository machineRepository;

    @Autowired
    private PlantRepository plantRepository;

    // =========================
    // SAVE MACHINE
    // =========================

    @Test
    @DisplayName("Should save machine successfully")
    void shouldSaveMachineSuccessfully() {

        Plant plant =
                plantRepository.save(
                        new Plant(
                                "Pune Plant",
                                "Pune",
                                true
                        )
                );

        Machine machine = new Machine();

        machine.setName("CNC Machine 01");
        machine.setType("CNC");
        machine.setStatus("ACTIVE");
        machine.setPlant(plant);

        Machine savedMachine =
                machineRepository.save(machine);

        assertTrue(
                savedMachine.getId() > 0
        );

        assertEquals(
                "CNC Machine 01",
                savedMachine.getName()
        );

    }

    // =========================
    // FIND BY PLANT
    // =========================

    @Test
    @DisplayName("Should find machines by plant successfully")
    void shouldFindMachinesByPlantSuccessfully() {

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

        machineRepository.save(machine);

        List<Machine> result =
                machineRepository.findByPlantId(
                        plant.getId()
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
    @DisplayName("Should find machines by status successfully")
    void shouldFindMachinesByStatusSuccessfully() {

        Plant plant =
                plantRepository.save(
                        new Plant(
                                "Pune Plant",
                                "Pune",
                                true
                        )
                );

        Machine machine = new Machine();

        machine.setName("Machine 01");
        machine.setType("CNC");
        machine.setStatus("ACTIVE");
        machine.setPlant(plant);

        machineRepository.save(machine);

        List<Machine> result =
                machineRepository.findByStatusIgnoreCase(
                        "active"
                );

        assertEquals(
                1,
                result.size()
        );

    }

    // =========================
    // COUNT BY STATUS
    // =========================

    @Test
    @DisplayName("Should count machines by status")
    void shouldCountMachinesByStatus() {

        Plant plant =
                plantRepository.save(
                        new Plant(
                                "Pune Plant",
                                "Pune",
                                true
                        )
                );

        Machine machine1 = new Machine();

        machine1.setName("Machine 01");
        machine1.setType("CNC");
        machine1.setStatus("ACTIVE");
        machine1.setPlant(plant);

        Machine machine2 = new Machine();

        machine2.setName("Machine 02");
        machine2.setType("CNC");
        machine2.setStatus("ACTIVE");
        machine2.setPlant(plant);

        machineRepository.save(machine1);
        machineRepository.save(machine2);

        assertEquals(
                2,
                machineRepository
                        .countByStatusIgnoreCase(
                                "active"
                        )
        );

    }

    // =========================
    // PAGINATION
    // =========================

    @Test
    @DisplayName("Should get paginated machines successfully")
    void shouldGetPaginatedMachinesSuccessfully() {

        Plant plant =
                plantRepository.save(
                        new Plant(
                                "Pune Plant",
                                "Pune",
                                true
                        )
                );

        Machine machine = new Machine();

        machine.setName("Machine 01");
        machine.setType("CNC");
        machine.setStatus("ACTIVE");
        machine.setPlant(plant);

        machineRepository.save(machine);

        Page<Machine> result =
                machineRepository.findAll(
                        PageRequest.of(0, 10)
                );

        assertEquals(
                1,
                result.getTotalElements()
        );

    }

}