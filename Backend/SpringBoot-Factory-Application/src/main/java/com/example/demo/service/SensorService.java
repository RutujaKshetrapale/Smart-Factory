package com.example.demo.service;

import com.example.demo.dto.SensorRequest;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.entity.Machine;
import com.example.demo.entity.Sensor;
import com.example.demo.repository.MachineRepository;
import com.example.demo.repository.SensorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

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

        Machine machine = machineRepository.findById(request.getMachineId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "MACHINE NOT FOUND: " + request.getMachineId()
                        ));

        Sensor sensor = new Sensor();

        sensor.setName(request.getName());
        sensor.setType(request.getType());
        sensor.setUnit(request.getUnit());

        if (request.getActive() != null) {
            sensor.setActive(request.getActive());
        } else {
            sensor.setActive(true);
        }

        sensor.setMachine(machine);

        return sensorRepository.save(sensor);
    }

    // GET ALL
    public List<Sensor> getAll() {

        return sensorRepository.findAll();
    }

    // GET BY ID
    public Sensor getById(Long id) {

        return sensorRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "SENSOR NOT FOUND: " + id
                        ));
    }

    // GET BY MACHINE
    public List<Sensor> getByMachineId(Long machineId) {

        return sensorRepository.findByMachineId(machineId);
    }

    // UPDATE
    public Sensor update(Long id, SensorRequest request) {

        Sensor sensor = getById(id);

        Machine machine = machineRepository.findById(request.getMachineId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "MACHINE NOT FOUND: " + request.getMachineId()
                        ));

        sensor.setName(request.getName());
        sensor.setType(request.getType());
        sensor.setUnit(request.getUnit());
        sensor.setMachine(machine);

        if (request.getActive() != null) {
            sensor.setActive(request.getActive());
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