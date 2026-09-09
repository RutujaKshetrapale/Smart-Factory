package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.dto.TelemetryRequest;
import com.example.demo.entity.Machine;
import com.example.demo.entity.Telemetry;
import com.example.demo.exception.BusinessValidationException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.MachineRepository;
import com.example.demo.repository.TelemetryRepository;

@Service
public class TelemetryService {

    private final TelemetryRepository telemetryRepository;
    private final MachineRepository machineRepository;
    private final DomainValidationService domainValidationService;

    public TelemetryService(
            TelemetryRepository telemetryRepository,
            MachineRepository machineRepository,
            DomainValidationService domainValidationService) {

        this.telemetryRepository = telemetryRepository;
        this.machineRepository = machineRepository;
        this.domainValidationService = domainValidationService;
    }

    // CREATE
    public Telemetry create(
            TelemetryRequest request) {

        // Validate telemetry values
        domainValidationService.validateTelemetry(
                request.getTemperature(),
                request.getVibration(),
                request.getPressure(),
                request.getRpm()
        );

        // Find machine
        Machine machine = machineRepository
                .findById(request.getMachineId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "MACHINE NOT FOUND: "
                                + request.getMachineId()
                        )
                );

        // Offline machines should not receive telemetry
        if ("OFFLINE".equalsIgnoreCase(
                machine.getStatus())) {

            throw new BusinessValidationException(
                    "Cannot record telemetry for an offline machine"
            );
        }

        Telemetry telemetry = new Telemetry();

        telemetry.setMachine(machine);

        telemetry.setTemperature(
                request.getTemperature()
        );

        telemetry.setVibration(
                request.getVibration()
        );

        telemetry.setPressure(
                request.getPressure()
        );

        telemetry.setRpm(
                request.getRpm()
        );

        // Use timestamp supplied by telemetry source
        telemetry.setTimestamp(
                request.getTimestamp()
        );

        return telemetryRepository.save(telemetry);
    }

    // GET ALL
    public List<Telemetry> getAll() {

        return telemetryRepository.findAll();
    }

    // GET BY ID
    public Telemetry getById(Long id) {

        return telemetryRepository
                .findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "TELEMETRY NOT FOUND: " + id
                        )
                );
    }

    // GET BY MACHINE
    public List<Telemetry> getByMachine(
            Long machineId) {

        // Verify machine exists
        if (!machineRepository.existsById(machineId)) {

            throw new ResourceNotFoundException(
                    "MACHINE NOT FOUND: " + machineId
            );
        }

        return telemetryRepository
                .findByMachineId(machineId);
    }

    // DELETE
    public void delete(Long id) {

        Telemetry telemetry = getById(id);

        telemetryRepository.delete(telemetry);
    }
}