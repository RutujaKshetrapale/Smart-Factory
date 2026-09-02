package com.example.demo.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.dto.AlertRequest;
import com.example.demo.entity.Alert;
import com.example.demo.entity.Machine;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.AlertRepository;
import com.example.demo.repository.MachineRepository;

@Service
public class AlertService {

    private final AlertRepository alertRepository;

    private final MachineRepository machineRepository;

    public AlertService(
            AlertRepository alertRepository,
            MachineRepository machineRepository) {

        this.alertRepository = alertRepository;
        this.machineRepository = machineRepository;
    }

    // =========================
    // CREATE ALERT
    // =========================

    public Alert create(AlertRequest request) {

        Machine machine = machineRepository
                .findById(request.getMachineId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "MACHINE NOT FOUND: "
                                + request.getMachineId()
                        ));

        Alert alert = new Alert();

        alert.setMachine(machine);
        alert.setType(request.getType());
        alert.setMessage(request.getMessage());
        alert.setSeverity(request.getSeverity());
        alert.setResolved(request.isResolved());
        alert.setCreatedAt(LocalDateTime.now());

        return alertRepository.save(alert);
    }

    // =========================
    // GET ALL ALERTS
    // =========================

    public List<Alert> getAll() {

        return alertRepository.findAll();
    }

    // =========================
    // GET ALERT BY ID
    // =========================

    public Alert getById(Long id) {

        return alertRepository
                .findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "ALERT NOT FOUND: " + id
                        ));
    }

    // =========================
    // GET ALERTS BY MACHINE
    // =========================

    public List<Alert> getByMachine(Long machineId) {

        if (!machineRepository.existsById(machineId)) {

            throw new ResourceNotFoundException(
                    "MACHINE NOT FOUND: " + machineId
            );
        }

        return alertRepository.findByMachineId(machineId);
    }

    // =========================
    // UPDATE ALERT
    // =========================

    public Alert update(Long id, AlertRequest request) {

        Alert alert = alertRepository
                .findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "ALERT NOT FOUND: " + id
                        ));

        Machine machine = machineRepository
                .findById(request.getMachineId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "MACHINE NOT FOUND: "
                                + request.getMachineId()
                        ));

        alert.setMachine(machine);
        alert.setType(request.getType());
        alert.setMessage(request.getMessage());
        alert.setSeverity(request.getSeverity());
        alert.setResolved(request.isResolved());

        return alertRepository.save(alert);
    }

    // =========================
    // DELETE ALERT
    // =========================

    public void delete(Long id) {

        Alert alert = alertRepository
                .findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "ALERT NOT FOUND: " + id
                        ));

        alertRepository.delete(alert);
    }
}