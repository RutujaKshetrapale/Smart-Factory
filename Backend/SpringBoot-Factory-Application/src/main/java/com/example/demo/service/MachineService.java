package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.dto.MachineRequest;
import com.example.demo.entity.Machine;
import com.example.demo.entity.Plant;
import com.example.demo.repository.MachineRepository;
import com.example.demo.repository.PlantRepository;

@Service
public class MachineService {

    private final MachineRepository machineRepository;
    private final PlantRepository plantRepository;

    public MachineService(
            MachineRepository machineRepository,
            PlantRepository plantRepository) {

        this.machineRepository = machineRepository;
        this.plantRepository = plantRepository;
    }

    // CREATE
    public Machine create(MachineRequest request) {

        Plant plant = plantRepository.findById(request.getPlantId())
                .orElseThrow(() ->
                        new RuntimeException(
                                "PLANT NOT FOUND: " + request.getPlantId()
                        ));

        Machine machine = new Machine();

        machine.setName(request.getName());
        machine.setType(request.getType());
        machine.setStatus(request.getStatus());
        machine.setPlant(plant);

        return machineRepository.save(machine);
    }

    // GET ALL
    public List<Machine> getAll() {

        return machineRepository.findAll();
    }

    // GET BY ID
    public Machine getById(Long id) {

        return machineRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "MACHINE NOT FOUND: " + id
                        ));
    }

    // UPDATE
    public Machine update(Long id, MachineRequest request) {

        Machine machine = machineRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "MACHINE NOT FOUND: " + id
                        ));

        Plant plant = plantRepository.findById(request.getPlantId())
                .orElseThrow(() ->
                        new RuntimeException(
                                "PLANT NOT FOUND: " + request.getPlantId()
                        ));

        machine.setName(request.getName());
        machine.setType(request.getType());
        machine.setStatus(request.getStatus());
        machine.setPlant(plant);

        return machineRepository.save(machine);
    }

    // DELETE
    public void delete(Long id) {

        Machine machine = machineRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "MACHINE NOT FOUND: " + id
                        ));

        machineRepository.delete(machine);
    }
}