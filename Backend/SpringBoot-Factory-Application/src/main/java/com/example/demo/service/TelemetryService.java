package com.example.demo.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.dto.TelemetryRequest;
import com.example.demo.entity.Machine;
import com.example.demo.entity.Telemetry;
import com.example.demo.repository.MachineRepository;
import com.example.demo.repository.TelemetryRepository;

@Service
public class TelemetryService {

    private final TelemetryRepository telemetryRepository;
    private final MachineRepository machineRepository;

    public TelemetryService(
            TelemetryRepository telemetryRepository,
            MachineRepository machineRepository) {

        this.telemetryRepository = telemetryRepository;
        this.machineRepository = machineRepository;
    }

    public Telemetry create(TelemetryRequest request) {

        Machine machine = machineRepository
                .findById(request.getMachineId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "MACHINE NOT FOUND: " + request.getMachineId()
                        ));

        Telemetry telemetry = new Telemetry();

        telemetry.setMachine(machine);
        telemetry.setTemperature(request.getTemperature());
        telemetry.setVibration(request.getVibration());
        telemetry.setPressure(request.getPressure());
        telemetry.setRpm(request.getRpm());

        telemetry.setTimestamp(LocalDateTime.now());

        return telemetryRepository.save(telemetry);
    }

    public List<Telemetry> getAll() {

        return telemetryRepository.findAll();
    }

    public Telemetry getById(Long id) {

        return telemetryRepository
                .findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "TELEMETRY NOT FOUND: " + id
                        ));
    }

    public List<Telemetry> getByMachine(Long machineId) {

        return telemetryRepository.findByMachineId(machineId);
    }

    public void delete(Long id) {

        Telemetry telemetry = telemetryRepository
                .findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "TELEMETRY NOT FOUND: " + id
                        ));

        telemetryRepository.delete(telemetry);
    }
}