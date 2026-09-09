package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.dto.SensorRequest;
import com.example.demo.entity.Machine;
import com.example.demo.entity.Sensor;
import com.example.demo.exception.BusinessValidationException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.MachineRepository;
import com.example.demo.repository.SensorRepository;

@Service
public class SensorService {

    private final SensorRepository sensorRepository;
    private final MachineRepository machineRepository;

    public SensorService(
            SensorRepository sensorRepository,
            MachineRepository machineRepository) {

        this.sensorRepository = sensorRepository;
        this.machineRepository = machineRepository;
    }

    // CREATE
    public Sensor create(SensorRequest request) {

        Machine machine = machineRepository
                .findById(request.getMachineId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "MACHINE NOT FOUND: "
                                + request.getMachineId()
                        )
                );

        // Sensors cannot be attached to offline machines
        if ("OFFLINE".equalsIgnoreCase(
                machine.getStatus())) {

            throw new BusinessValidationException(
                    "Cannot attach sensor to an offline machine"
            );
        }

        Sensor sensor = new Sensor();

        sensor.setName(
                request.getName().trim()
        );

        sensor.setType(
                request.getType().trim()
        );

        sensor.setUnit(
                request.getUnit().trim()
        );

        // Default sensor status = ACTIVE
        sensor.setActive(
                request.getActive() == null
                        || request.getActive()
        );

        sensor.setMachine(machine);

        return sensorRepository.save(sensor);
    }

    // GET ALL
    public List<Sensor> getAll() {

        return sensorRepository.findAll();
    }

    // GET BY ID
    public Sensor getById(Long id) {

        return sensorRepository
                .findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "SENSOR NOT FOUND: " + id
                        )
                );
    }

    // GET BY MACHINE
    public List<Sensor> getByMachineId(
            Long machineId) {

        // Verify machine exists
        if (!machineRepository.existsById(machineId)) {

            throw new ResourceNotFoundException(
                    "MACHINE NOT FOUND: " + machineId
            );
        }

        return sensorRepository
                .findByMachineId(machineId);
    }

    // UPDATE
    public Sensor update(
            Long id,
            SensorRequest request) {

        Sensor sensor = getById(id);

        Machine machine = machineRepository
                .findById(request.getMachineId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "MACHINE NOT FOUND: "
                                + request.getMachineId()
                        )
                );

        // Sensors cannot be attached to offline machines
        if ("OFFLINE".equalsIgnoreCase(
                machine.getStatus())) {

            throw new BusinessValidationException(
                    "Cannot attach sensor to an offline machine"
            );
        }

        sensor.setName(
                request.getName().trim()
        );

        sensor.setType(
                request.getType().trim()
        );

        sensor.setUnit(
                request.getUnit().trim()
        );

        sensor.setMachine(machine);

        if (request.getActive() != null) {

            sensor.setActive(
                    request.getActive()
            );
        }

        return sensorRepository.save(sensor);
    }

    // ACTIVATE
    public Sensor activate(Long id) {

        Sensor sensor = getById(id);

        sensor.setActive(true);

        return sensorRepository.save(sensor);
    }

    // DEACTIVATE
    public Sensor deactivate(Long id) {

        Sensor sensor = getById(id);

        sensor.setActive(false);

        return sensorRepository.save(sensor);
    }

    // DELETE
    public void delete(Long id) {

        Sensor sensor = getById(id);

        sensorRepository.delete(sensor);
    }
}