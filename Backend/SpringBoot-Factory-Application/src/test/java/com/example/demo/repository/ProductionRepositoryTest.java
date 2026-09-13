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

import com.example.demo.entity.Machine;
import com.example.demo.entity.Plant;
import com.example.demo.entity.Production;

@DataJpaTest
class ProductionRepositoryTest {

    @Autowired
    private ProductionRepository productionRepository;

    @Autowired
    private MachineRepository machineRepository;

    @Autowired
    private PlantRepository plantRepository;

    // =========================
    // SAVE PRODUCTION
    // =========================

    @Test
    @DisplayName("Should save production successfully")
    void shouldSaveProductionSuccessfully() {

        Machine machine = createMachine();

        Production production =
                createProduction(
                        machine,
                        "RUNNING"
                );

        Production savedProduction =
                productionRepository.save(
                        production
                );

        assertEquals(
                "Product A",
                savedProduction.getProductName()
        );

        assertEquals(
                100,
                savedProduction
                        .getQuantityProduced()
        );

    }

    // =========================
    // FIND BY MACHINE
    // =========================

    @Test
    @DisplayName("Should find production by machine successfully")
    void shouldFindProductionByMachineSuccessfully() {

        Machine machine = createMachine();

        productionRepository.save(
                createProduction(
                        machine,
                        "COMPLETED"
                )
        );

        List<Production> result =
                productionRepository.findByMachineId(
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
    @DisplayName("Should find production by status successfully")
    void shouldFindProductionByStatusSuccessfully() {

        Machine machine = createMachine();

        productionRepository.save(
                createProduction(
                        machine,
                        "RUNNING"
                )
        );

        List<Production> result =
                productionRepository.findByStatus(
                        "RUNNING"
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
    @DisplayName("Should get paginated production successfully")
    void shouldGetPaginatedProductionSuccessfully() {

        Machine machine = createMachine();

        productionRepository.save(
                createProduction(
                        machine,
                        "RUNNING"
                )
        );

        Page<Production> result =
                productionRepository.findAll(
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

    private Production createProduction(
            Machine machine,
            String status) {

        Production production =
                new Production();

        production.setProductName(
                "Product A"
        );

        production.setQuantityProduced(
                100
        );

        production.setQuantityRejected(
                10
        );

        production.setProductionStart(
                LocalDateTime.now()
        );

        production.setStatus(
                status
        );

        production.setMachine(
                machine
        );

        return production;
    }

}