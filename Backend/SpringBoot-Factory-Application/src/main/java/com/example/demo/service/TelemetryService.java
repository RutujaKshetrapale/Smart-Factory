package com.example.demo.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.demo.dto.TelemetryRequest;
import com.example.demo.entity.Machine;
import com.example.demo.entity.Telemetry;
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

    public Telemetry create(TelemetryRequest request) {

        Machine machine = machineRepository
                .findById(request.getMachineId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "MACHINE NOT FOUND: "
                                + request.getMachineId()
                        ));

        domainValidationService.validateTelemetry(
                request.getTemperature(),
                request.getVibration(),
                request.getPressure(),
                request.getRpm()
        );

        Telemetry telemetry = new Telemetry();

        telemetry.setMachine(machine);
        telemetry.setTemperature(request.getTemperature());
        telemetry.setVibration(request.getVibration());
        telemetry.setPressure(request.getPressure());
        telemetry.setRpm(request.getRpm());
        telemetry.setTimestamp(request.getTimestamp());

        return telemetryRepository.save(telemetry);
    }

    public List<Telemetry> getAll() {
        return telemetryRepository.findAll();
    }

    public Page<Telemetry> getAll(Pageable pageable) {
        return telemetryRepository.findAll(pageable);
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

        validateMachine(machineId);

        return telemetryRepository.findByMachineId(machineId);
    }

    public Page<Telemetry> getByMachine(
            Long machineId,
            Pageable pageable) {

        validateMachine(machineId);

        return telemetryRepository.findByMachineId(
                machineId,
                pageable
        );
    }

    public List<Telemetry> getByMachineAndDateRange(
            Long machineId,
            LocalDateTime start,
            LocalDateTime end) {

        validateMachine(machineId);

        return telemetryRepository
                .findByMachineIdAndTimestampBetween(
                        machineId,
                        start,
                        end
                );
    }

    public Page<Telemetry> getByMachineAndDateRange(
            Long machineId,
            LocalDateTime start,
            LocalDateTime end,
            Pageable pageable) {

        validateMachine(machineId);

        return telemetryRepository
                .findByMachineIdAndTimestampBetween(
                        machineId,
                        start,
                        end,
                        pageable
                );
    }

    public List<Telemetry> getByDateRange(
            LocalDateTime start,
            LocalDateTime end) {

        return telemetryRepository
                .findByTimestampBetween(start, end);
    }

    public Page<Telemetry> getByDateRange(
            LocalDateTime start,
            LocalDateTime end,
            Pageable pageable) {

        return telemetryRepository
                .findByTimestampBetween(
                        start,
                        end,
                        pageable
                );
    }

    public List<Telemetry> getLatestByMachine(
            Long machineId) {

        validateMachine(machineId);

        return telemetryRepository
                .findTop10ByMachineIdOrderByTimestampDesc(
                        machineId
                );
    }

    private void validateMachine(Long machineId) {

        if (!machineRepository.existsById(machineId)) {
            throw new ResourceNotFoundException(
                    "MACHINE NOT FOUND: " + machineId
            );
        }
    }
}