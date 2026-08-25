package com.example.demo.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.dto.AlertRequest;
import com.example.demo.entity.Alert;
import com.example.demo.entity.Machine;
import com.example.demo.entity.Telemetry;
import com.example.demo.repository.AlertRepository;
import com.example.demo.repository.MachineRepository;
import com.example.demo.repository.TelemetryRepository;

@Service
public class AlertService {

    private final AlertRepository alertRepository;
    private final MachineRepository machineRepository;
    private final TelemetryRepository telemetryRepository;

    public AlertService(
            AlertRepository alertRepository,
            MachineRepository machineRepository,
            TelemetryRepository telemetryRepository) {

        this.alertRepository = alertRepository;
        this.machineRepository = machineRepository;
        this.telemetryRepository = telemetryRepository;
    }

    public Alert create(AlertRequest request) {

        Machine machine = machineRepository
                .findById(request.getMachineId())
                .orElseThrow(() ->
                        new RuntimeException(
                                "MACHINE NOT FOUND: " + request.getMachineId()
                        ));

        Alert alert = new Alert();

        alert.setMachine(machine);
        alert.setType(request.getType());
        alert.setSeverity(request.getSeverity());
        alert.setMessage(request.getMessage());
        alert.setResolved(false);
        alert.setCreatedAt(LocalDateTime.now());

        if (request.getTelemetryId() != null) {

            Telemetry telemetry = telemetryRepository
                    .findById(request.getTelemetryId())
                    .orElseThrow(() ->
                            new RuntimeException(
                                    "TELEMETRY NOT FOUND: "
                                    + request.getTelemetryId()
                            ));

            alert.setTelemetry(telemetry);
        }

        return alertRepository.save(alert);
    }

    public List<Alert> getAll() {

        return alertRepository.findAll();
    }

    public Alert getById(Long id) {

        return alertRepository
                .findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "ALERT NOT FOUND: " + id
                        ));
    }

    public List<Alert> getByMachine(Long machineId) {

        return alertRepository.findByMachineId(machineId);
    }

    public List<Alert> getUnresolved() {

        return alertRepository.findByResolvedFalse();
    }

    public List<Alert> getBySeverity(String severity) {

        return alertRepository.findBySeverity(severity);
    }

    public Alert resolve(Long id) {

        Alert alert = alertRepository
                .findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "ALERT NOT FOUND: " + id
                        ));

        alert.setResolved(true);

        return alertRepository.save(alert);
    }

    public void delete(Long id) {

        Alert alert = alertRepository
                .findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "ALERT NOT FOUND: " + id
                        ));

        alertRepository.delete(alert);
    }
}