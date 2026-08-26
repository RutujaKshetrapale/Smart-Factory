package com.example.demo.service;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.dto.EnergyRequest;
import com.example.demo.entity.Energy;
import com.example.demo.entity.Machine;
import com.example.demo.repository.EnergyRepository;
import com.example.demo.repository.MachineRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EnergyService {

    private final EnergyRepository energyRepository;
    private final MachineRepository machineRepository;

    public EnergyService(
            EnergyRepository energyRepository,
            MachineRepository machineRepository) {

        this.energyRepository = energyRepository;
        this.machineRepository = machineRepository;
    }

    // CREATE
    public Energy create(EnergyRequest request) {

        Machine machine = machineRepository.findById(request.getMachineId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "MACHINE NOT FOUND: " + request.getMachineId()
                        ));

        Energy energy = new Energy();

        energy.setEnergyConsumption(request.getEnergyConsumption());
        energy.setRecordedAt(request.getRecordedAt());
        energy.setMachine(machine);

        return energyRepository.save(energy);
    }

    // GET ALL
    public List<Energy> getAll() {

        return energyRepository.findAll();
    }

    // GET BY ID
    public Energy getById(Long id) {

        return energyRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "ENERGY RECORD NOT FOUND: " + id
                        ));
    }

    // GET BY MACHINE
    public List<Energy> getByMachineId(Long machineId) {

        return energyRepository.findByMachineId(machineId);
    }

    // UPDATE
    public Energy update(Long id, EnergyRequest request) {

        Energy energy = getById(id);

        Machine machine = machineRepository.findById(request.getMachineId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "MACHINE NOT FOUND: " + request.getMachineId()
                        ));

        energy.setEnergyConsumption(request.getEnergyConsumption());
        energy.setRecordedAt(request.getRecordedAt());
        energy.setMachine(machine);

        return energyRepository.save(energy);
    }

    // DELETE
    public void delete(Long id) {

        Energy energy = getById(id);

        energyRepository.delete(energy);
    }
}