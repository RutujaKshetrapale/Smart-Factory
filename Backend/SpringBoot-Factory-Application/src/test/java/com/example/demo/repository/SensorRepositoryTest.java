package com.example.demo.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.example.demo.entity.Machine;
import com.example.demo.entity.Plant;
import com.example.demo.entity.Sensor;

@DataJpaTest
class SensorRepositoryTest {

    @Autowired
    private SensorRepository sensorRepository;

    @Autowired
    private MachineRepository machineRepository;

    @Autowired
    private PlantRepository plantRepository;

    // =========================
    // SAVE SENSOR
    // =========================

    @Test
    @DisplayName("Should save sensor successfully")
    void shouldSaveSensorSuccessfully() {

        Machine machine = createMachine();

        Sensor sensor = new Sensor();

        sensor.setName("Temperature Sensor");
        sensor.setType("TEMPERATURE");
        sensor.setUnit("C");
        sensor.setActive(true);
        sensor.setMachine(machine);

        Sensor savedSensor =
                sensorRepository.save(sensor);

        assertEquals(
                "Temperature Sensor",
                savedSensor.getName()
        );

    }

    // =========================
    // FIND BY MACHINE
    // =========================

    @Test
    @DisplayName("Should find sensors by machine successfully")
    void shouldFindSensorsByMachineSuccessfully() {

        Machine machine = createMachine();

        Sensor sensor = createSensor(
                machine,
                "Temperature Sensor"
        );

        sensorRepository.save(sensor);

        List<Sensor> result =
                sensorRepository.findByMachineId(
                        machine.getId()
                );

        assertEquals(
                1,
                result.size()
        );

    }

    // =========================
    // FIND ACTIVE
    // =========================

    @Test
    @DisplayName("Should find active sensors successfully")
    void shouldFindActiveSensorsSuccessfully() {

        Machine machine = createMachine();

        Sensor sensor =
                createSensor(
                        machine,
                        "Temperature Sensor"
                );

        sensorRepository.save(sensor);

        List<Sensor> result =
                sensorRepository.findByActiveTrue();

        assertEquals(
                1,
                result.size()
        );

    }

    // =========================
    // FIND BY TYPE
    // =========================

    @Test
    @DisplayName("Should find sensors by type successfully")
    void shouldFindSensorsByTypeSuccessfully() {

        Machine machine = createMachine();

        Sensor sensor =
                createSensor(
                        machine,
                        "Temperature Sensor"
                );

        sensorRepository.save(sensor);

        List<Sensor> result =
                sensorRepository.findByTypeIgnoreCase(
                        "temperature"
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
    @DisplayName("Should get paginated sensors successfully")
    void shouldGetPaginatedSensorsSuccessfully() {

        Machine machine = createMachine();

        sensorRepository.save(
                createSensor(
                        machine,
                        "Temperature Sensor"
                )
        );

        sensorRepository.save(
                createSensor(
                        machine,
                        "Pressure Sensor"
                )
        );

        Page<Sensor> result =
                sensorRepository.findAll(
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

    private Sensor createSensor(
            Machine machine,
            String name) {

        Sensor sensor = new Sensor();

        sensor.setName(name);
        sensor.setType(
                name.contains("Temperature")
                        ? "TEMPERATURE"
                        : "PRESSURE"
        );
        sensor.setUnit(
                name.contains("Temperature")
                        ? "C"
                        : "bar"
        );
        sensor.setActive(true);
        sensor.setMachine(machine);

        return sensor;
    }

}