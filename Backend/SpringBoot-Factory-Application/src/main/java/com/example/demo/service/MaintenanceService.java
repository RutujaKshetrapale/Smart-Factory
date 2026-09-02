package com.example.demo.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.dto.MaintenanceRequest;
import com.example.demo.entity.Machine;
import com.example.demo.entity.Maintenance;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.MachineRepository;
import com.example.demo.repository.MaintenanceRepository;

@Service
public class MaintenanceService {

    private final MaintenanceRepository maintenanceRepository;
    private final MachineRepository machineRepository;

    public MaintenanceService(
            MaintenanceRepository maintenanceRepository,
            MachineRepository machineRepository) {

        this.maintenanceRepository = maintenanceRepository;
        this.machineRepository = machineRepository;
    }

    // =========================
    // CREATE MAINTENANCE
    // =========================

    public Maintenance create(MaintenanceRequest request) {

        Machine machine = machineRepository
                .findById(request.getMachineId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "MACHINE NOT FOUND: "
                                + request.getMachineId()
                        ));

        Maintenance maintenance = new Maintenance();

        maintenance.setMachine(machine);
        maintenance.setType(request.getType());
        maintenance.setDescription(request.getDescription());
        maintenance.setScheduledDate(request.getScheduledDate());
        maintenance.setCompletedDate(request.getCompletedDate());
        maintenance.setStatus(request.getStatus());
        maintenance.setTechnician(request.getTechnician());
        maintenance.setCreatedAt(LocalDateTime.now());

        return maintenanceRepository.save(maintenance);
    }

    // =========================
    // GET ALL MAINTENANCE
    // =========================

    public List<Maintenance> getAll() {

        return maintenanceRepository.findAll();
    }

    // =========================
    // GET MAINTENANCE BY ID
    // =========================

    public Maintenance getById(Long id) {

        return maintenanceRepository
                .findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "MAINTENANCE NOT FOUND: " + id
                        ));
    }

    // =========================
    // GET MAINTENANCE BY MACHINE
    // =========================

    public List<Maintenance> getByMachine(Long machineId) {

        if (!machineRepository.existsById(machineId)) {

            throw new ResourceNotFoundException(
                    "MACHINE NOT FOUND: " + machineId
            );
        }

        return maintenanceRepository.findByMachineId(machineId);
    }

    // =========================
    // GET MAINTENANCE BY STATUS
    // =========================

    public List<Maintenance> getByStatus(String status) {

        return maintenanceRepository.findByStatus(status);
    }

    // =========================
    // GET BY STATUS
    // ORDERED BY DATE
    // =========================

    public List<Maintenance> getByStatusOrderByDate(String status) {

        return maintenanceRepository
                .findByStatusOrderByScheduledDateAsc(status);
    }

    // =========================
    // UPDATE MAINTENANCE
    // =========================

    public Maintenance update(
            Long id,
            MaintenanceRequest request) {

        Maintenance maintenance = maintenanceRepository
                .findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "MAINTENANCE NOT FOUND: " + id
                        ));

        Machine machine = machineRepository
                .findById(request.getMachineId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "MACHINE NOT FOUND: "
                                + request.getMachineId()
                        ));

        maintenance.setMachine(machine);
        maintenance.setType(request.getType());
        maintenance.setDescription(request.getDescription());
        maintenance.setScheduledDate(request.getScheduledDate());
        maintenance.setCompletedDate(request.getCompletedDate());
        maintenance.setStatus(request.getStatus());
        maintenance.setTechnician(request.getTechnician());

        return maintenanceRepository.save(maintenance);
    }

    // =========================
    // DELETE MAINTENANCE
    // =========================

    public void delete(Long id) {

        Maintenance maintenance = maintenanceRepository
                .findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "MAINTENANCE NOT FOUND: " + id
                        ));

        maintenanceRepository.delete(maintenance);
    }
}