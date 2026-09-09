package com.example.demo.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    public Machine create(MachineRequest request) {

        Plant plant = plantRepository
                .findById(request.getPlantId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "PLANT NOT FOUND: "
                                + request.getPlantId()
                        ));

        domainValidationService.validateMachineStatus(
                request.getStatus()
        );

        Machine machine = new Machine();

        machine.setName(request.getName().trim());
        machine.setType(request.getType().trim());
        machine.setStatus(
                request.getStatus().trim().toUpperCase()
        );
        machine.setPlant(plant);

        return machineRepository.save(machine);
    }

    public List<Machine> getAll() {
        return machineRepository.findAll();
    }

    public Page<Machine> getAll(Pageable pageable) {
        return machineRepository.findAll(pageable);
    }

    public Machine getById(Long id) {

        return machineRepository
                .findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "MACHINE NOT FOUND: " + id
                        ));
    }

    public List<Machine> getByPlant(Long plantId) {

        if (!plantRepository.existsById(plantId)) {
            throw new ResourceNotFoundException(
                    "PLANT NOT FOUND: " + plantId
            );
        }

        return machineRepository.findByPlantId(plantId);
    }

    public Page<Machine> getByPlant(
            Long plantId,
            Pageable pageable) {

        if (!plantRepository.existsById(plantId)) {
            throw new ResourceNotFoundException(
                    "PLANT NOT FOUND: " + plantId
            );
        }

        return machineRepository.findByPlantId(
                plantId,
                pageable
        );
    }

    public List<Machine> getByStatus(String status) {

        domainValidationService.validateMachineStatus(status);

        return machineRepository
                .findByStatusIgnoreCase(status.trim());
    }

    public Page<Machine> getByStatus(
            String status,
            Pageable pageable) {

        domainValidationService.validateMachineStatus(status);

        return machineRepository.findByStatusIgnoreCase(
                status.trim(),
                pageable
        );
    }

    public List<Machine> getByPlantAndStatus(
            Long plantId,
            String status) {

        if (!plantRepository.existsById(plantId)) {
            throw new ResourceNotFoundException(
                    "PLANT NOT FOUND: " + plantId
            );
        }

        domainValidationService.validateMachineStatus(status);

        return machineRepository
                .findByPlantIdAndStatusIgnoreCase(
                        plantId,
                        status.trim()
                );
    }

    public Page<Machine> getByPlantAndStatus(
            Long plantId,
            String status,
            Pageable pageable) {

        if (!plantRepository.existsById(plantId)) {
            throw new ResourceNotFoundException(
                    "PLANT NOT FOUND: " + plantId
            );
        }

        domainValidationService.validateMachineStatus(status);

        return machineRepository
                .findByPlantIdAndStatusIgnoreCase(
                        plantId,
                        status.trim(),
                        pageable
                );
    }

    public List<Machine> searchByName(String name) {
        return machineRepository
                .findByNameContainingIgnoreCase(name);
    }

    public Page<Machine> searchByName(
            String name,
            Pageable pageable) {

        return machineRepository
                .findByNameContainingIgnoreCase(
                        name,
                        pageable
                );
    }

    public Machine update(
            Long id,
            MachineRequest request) {

        Machine machine = getById(id);

        Plant plant = plantRepository
                .findById(request.getPlantId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "PLANT NOT FOUND: "
                                + request.getPlantId()
                        ));

        domainValidationService.validateMachineStatus(
                request.getStatus()
        );

        machine.setName(request.getName().trim());
        machine.setType(request.getType().trim());
        machine.setStatus(
                request.getStatus().trim().toUpperCase()
        );
        machine.setPlant(plant);

        return machineRepository.save(machine);
    }

    public void delete(Long id) {

        Machine machine = getById(id);

        machineRepository.delete(machine);
    }
}