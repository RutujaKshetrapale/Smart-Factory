package com.example.demo.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.dto.EnergyRequest;
import com.example.demo.entity.Energy;
import com.example.demo.entity.Machine;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.EnergyRepository;
import com.example.demo.repository.MachineRepository;

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

    // =========================
    // CREATE ENERGY RECORD
    // =========================

    public Energy create(EnergyRequest request) {

        Machine machine = machineRepository
                .findById(request.getMachineId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "MACHINE NOT FOUND: "
                                + request.getMachineId()
                        ));

        Energy energy = new Energy();

        energy.setMachine(machine);
        energy.setEnergyConsumption(
                request.getEnergyConsumption()
        );
        energy.setRecordedAt(LocalDateTime.now());

        return energyRepository.save(energy);
    }

    // =========================
    // GET ALL ENERGY RECORDS
    // =========================

    public List<Energy> getAll() {

        return energyRepository.findAll();
    }

    // =========================
    // GET ENERGY BY ID
    // =========================

    public Energy getById(Long id) {

        return energyRepository
                .findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "ENERGY RECORD NOT FOUND: " + id
                        ));
    }

    // =========================
    // GET ENERGY BY MACHINE
    // =========================

    public List<Energy> getByMachine(Long machineId) {

        if (!machineRepository.existsById(machineId)) {

            throw new ResourceNotFoundException(
                    "MACHINE NOT FOUND: " + machineId
            );
        }

        return energyRepository.findByMachineId(machineId);
    }

    // =========================
    // UPDATE ENERGY RECORD
    // =========================

    public Energy update(
            Long id,
            EnergyRequest request) {

        Energy energy = energyRepository
                .findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "ENERGY RECORD NOT FOUND: " + id
                        ));

        Machine machine = machineRepository
                .findById(request.getMachineId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "MACHINE NOT FOUND: "
                                + request.getMachineId()
                        ));

        energy.setMachine(machine);

        energy.setEnergyConsumption(
                request.getEnergyConsumption()
        );

        return energyRepository.save(energy);
    }

    // =========================
    // DELETE ENERGY RECORD
    // =========================

    public void delete(Long id) {

        Energy energy = energyRepository
                .findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "ENERGY RECORD NOT FOUND: " + id
                        ));

        energyRepository.delete(energy);
    }
}