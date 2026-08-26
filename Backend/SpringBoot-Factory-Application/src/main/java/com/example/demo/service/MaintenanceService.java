package com.example.demo.service;

import java.time.LocalDate;
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

    public Maintenance create(MaintenanceRequest request) {

        Machine machine = machineRepository
                .findById(request.getMachineId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Machine not found: "
                                + request.getMachineId()
                        ));

        Maintenance maintenance = new Maintenance();

        maintenance.setMachine(machine);
        maintenance.setType(request.getType());
        maintenance.setDescription(request.getDescription());
        maintenance.setScheduledDate(request.getScheduledDate());
        maintenance.setTechnician(request.getTechnician());

        maintenance.setStatus(request.getStatus());
        maintenance.setCompletedDate(request.getCompletedDate());

        maintenance.setCreatedAt(LocalDateTime.now());

        return maintenanceRepository.save(maintenance);
    }

    public List<Maintenance> getAll() {

        return maintenanceRepository.findAll();
    }

    public Maintenance getById(Long id) {

        return maintenanceRepository
                .findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Maintenance not found: " + id
                        ));
    }

    public List<Maintenance> getByMachine(Long machineId) {

        return maintenanceRepository.findByMachineId(machineId);
    }

    public List<Maintenance> getPending() {

        return maintenanceRepository
                .findByStatusOrderByScheduledDateAsc("PENDING");
    }

    public Maintenance complete(Long id) {

        Maintenance maintenance = getById(id);

        maintenance.setStatus("COMPLETED");
        maintenance.setCompletedDate(LocalDate.now());

        return maintenanceRepository.save(maintenance);
    }

    public void delete(Long id) {

        Maintenance maintenance = getById(id);

        maintenanceRepository.delete(maintenance);
    }
}