package com.example.demo.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.dto.PlantRequest;
import com.example.demo.entity.Plant;
import com.example.demo.service.PlantService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/plants")
public class PlantController {

    private final PlantService plantService;

    public PlantController(PlantService plantService) {
        this.plantService = plantService;
    }

    // CREATE
    @PostMapping
    public ResponseEntity<Plant> create(
            @Valid @RequestBody PlantRequest request) {

        Plant plant = plantService.create(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(plant);
    }

    // READ ALL
    @GetMapping
    public ResponseEntity<List<Plant>> getAll() {

        return ResponseEntity.ok(
                plantService.getAll()
        );
    }

    // READ ONE
    @GetMapping("/{id}")
    public ResponseEntity<Plant> getById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                plantService.getById(id)
        );
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<Plant> update(
            @PathVariable Long id,
            @Valid @RequestBody PlantRequest request) {

        return ResponseEntity.ok(
                plantService.update(id, request)
        );
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Long id) {

        plantService.delete(id);

        return ResponseEntity.noContent().build();
    }
}