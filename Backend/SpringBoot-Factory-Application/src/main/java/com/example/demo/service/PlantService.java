package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.dto.PlantRequest;
import com.example.demo.entity.Plant;
import com.example.demo.repository.PlantRepository;

@Service
public class PlantService {

    private final PlantRepository plantRepository;

    public PlantService(PlantRepository plantRepository) {
        this.plantRepository = plantRepository;
    }

    // CREATE
    public Plant create(PlantRequest request) {

        Plant plant = new Plant();

        plant.setName(request.getName());
        plant.setLocation(request.getLocation());
        plant.setActive(true);

        return plantRepository.save(plant);
    }

    // READ ALL
    public List<Plant> getAll() {

        return plantRepository.findAll();
    }

    // READ ONE
    public Plant getById(Long id) {

        return plantRepository.findById(id)
                .orElseThrow(() ->
                    new ResourceNotFoundException("Plant not found: " + id)
                );
    }

    // UPDATE
    public Plant update(Long id, PlantRequest request) {

        Plant plant = plantRepository.findById(id)
                .orElseThrow(() ->
                    new ResourceNotFoundException("Plant not found: " + id)
                );

        plant.setName(request.getName());
        plant.setLocation(request.getLocation());

        return plantRepository.save(plant);
    }

    // DELETE
    public void delete(Long id) {

        Plant plant = plantRepository.findById(id)
                .orElseThrow(() ->
                    new ResourceNotFoundException("Plant not found: " + id)
                );

        plantRepository.delete(plant);
    }
}