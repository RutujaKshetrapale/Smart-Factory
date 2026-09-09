package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.dto.MachineRequest;
import com.example.demo.entity.Machine;
import com.example.demo.entity.Plant;
import com.example.demo.exception.BusinessValidationException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.MachineRepository;
import com.example.demo.repository.PlantRepository;

@Service
public class MachineService {

    private final MachineRepository machineRepository;
    private final PlantRepository plantRepository;
    private final DomainValidationService domainValidationService;

    public MachineService(
            MachineRepository machineRepository,
            PlantRepository plantRepository,
            DomainValidationService domainValidationService) {

        this.machineRepository = machineRepository;
        this.plantRepository = plantRepository;
        this.domainValidationService = domainValidationService;
    }

    // CREATE
    public Machine create(MachineRequest request) {

        // Validate machine status
        domainValidationService.validateMachineStatus(
                request.getStatus()
        );

        // Find plant
        Plant plant = plantRepository
                .findById(request.getPlantId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "PLANT NOT FOUND: "
                                + request.getPlantId()
                        )
                );

        // Machine cannot be created inside inactive plant
        if (!plant.isActive()) {
            throw new BusinessValidationException(
                    "Cannot create machine in an inactive plant"
            );
        }

        Machine machine = new Machine();

        machine.setName(
                request.getName().trim()
        );

        machine.setType(
                request.getType().trim()
        );

        machine.setStatus(
                request.getStatus()
                        .trim()
                        .toUpperCase()
        );

        machine.setPlant(plant);

        return machineRepository.save(machine);
    }

    // GET ALL
    public List<Machine> getAll() {

        return machineRepository.findAll();
    }

    // GET BY ID
    public Machine getById(Long id) {

        return machineRepository
                .findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "MACHINE NOT FOUND: " + id
                        )
                );
    }

    // UPDATE
    public Machine update(
            Long id,
            MachineRequest request) {

        // Validate machine status
        domainValidationService.validateMachineStatus(
                request.getStatus()
        );

        // Find existing machine
        Machine machine = machineRepository
                .findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "MACHINE NOT FOUND: " + id
                        )
                );

        // Find new plant
        Plant plant = plantRepository
                .findById(request.getPlantId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "PLANT NOT FOUND: "
                                + request.getPlantId()
                        )
                );

        // Machine cannot be assigned to inactive plant
        if (!plant.isActive()) {
            throw new BusinessValidationException(
                    "Cannot assign machine to an inactive plant"
            );
        }

        machine.setName(
                request.getName().trim()
        );

        machine.setType(
                request.getType().trim()
        );

        machine.setStatus(
                request.getStatus()
                        .trim()
                        .toUpperCase()
        );

        machine.setPlant(plant);

        return machineRepository.save(machine);
    }

    // DELETE
    public void delete(Long id) {

        if (!machineRepository.existsById(id)) {

            throw new ResourceNotFoundException(
                    "MACHINE NOT FOUND: " + id
            );
        }

        machineRepository.deleteById(id);
    }
}