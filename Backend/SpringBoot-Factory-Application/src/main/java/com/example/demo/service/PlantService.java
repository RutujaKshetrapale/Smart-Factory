package com.example.demo.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.demo.dto.PlantRequest;
import com.example.demo.entity.Plant;
import com.example.demo.exception.BusinessValidationException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.PlantRepository;

@Service
public class PlantService {

    private final PlantRepository plantRepository;

    public PlantService(PlantRepository plantRepository) {
        this.plantRepository = plantRepository;
    }

    public Plant create(PlantRequest request) {

        if (plantRepository.existsByNameIgnoreCase(
                request.getName().trim())) {

            throw new BusinessValidationException(
                    "Plant with this name already exists"
            );
        }

        Plant plant = new Plant();

        plant.setName(request.getName().trim());
        plant.setLocation(request.getLocation().trim());
        plant.setActive(true);

        return plantRepository.save(plant);
    }

    public List<Plant> getAll() {
        return plantRepository.findAll();
    }

    public Page<Plant> getAll(Pageable pageable) {
        return plantRepository.findAll(pageable);
    }

    public Plant getById(Long id) {

        return plantRepository
                .findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "PLANT NOT FOUND: " + id
                        ));
    }

    public List<Plant> getActive() {
        return plantRepository.findByActiveTrue();
    }

    public Page<Plant> getActive(Pageable pageable) {
        return plantRepository.findByActiveTrue(pageable);
    }

    public List<Plant> getInactive() {
        return plantRepository.findByActiveFalse();
    }

    public Page<Plant> getInactive(Pageable pageable) {
        return plantRepository.findByActiveFalse(pageable);
    }

    public List<Plant> searchByName(String name) {
        return plantRepository
                .findByNameContainingIgnoreCase(name);
    }

    public Page<Plant> searchByName(
            String name,
            Pageable pageable) {

        return plantRepository
                .findByNameContainingIgnoreCase(
                        name,
                        pageable
                );
    }

    public List<Plant> searchByLocation(String location) {
        return plantRepository
                .findByLocationContainingIgnoreCase(location);
    }

    public Page<Plant> searchByLocation(
            String location,
            Pageable pageable) {

        return plantRepository
                .findByLocationContainingIgnoreCase(
                        location,
                        pageable
                );
    }

    public Plant update(
            Long id,
            PlantRequest request) {

        Plant plant = getById(id);

        if (!plant.getName().equalsIgnoreCase(
                request.getName().trim())
                && plantRepository.existsByNameIgnoreCase(
                        request.getName().trim())) {

            throw new BusinessValidationException(
                    "Plant with this name already exists"
            );
        }

        plant.setName(request.getName().trim());
        plant.setLocation(request.getLocation().trim());

        return plantRepository.save(plant);
    }

    public void delete(Long id) {

        Plant plant = getById(id);

        plantRepository.delete(plant);
    }
}