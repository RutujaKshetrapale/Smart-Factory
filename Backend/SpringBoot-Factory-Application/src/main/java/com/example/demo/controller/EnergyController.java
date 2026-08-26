package com.example.demo.controller;

import com.example.demo.dto.EnergyRequest;
import com.example.demo.entity.Energy;
import com.example.demo.service.EnergyService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/energy")
public class EnergyController {

    private final EnergyService energyService;

    public EnergyController(EnergyService energyService) {
        this.energyService = energyService;
    }

    // CREATE
    @PostMapping
    public ResponseEntity<Energy> create(
            @Valid @RequestBody EnergyRequest request) {

        return ResponseEntity.ok(
                energyService.create(request)
        );
    }

    // GET ALL
    @GetMapping
    public ResponseEntity<List<Energy>> getAll() {

        return ResponseEntity.ok(
                energyService.getAll()
        );
    }

    // GET BY ID
    @GetMapping("/{id}")
    public ResponseEntity<Energy> getById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                energyService.getById(id)
        );
    }

    // GET BY MACHINE
    @GetMapping("/machine/{machineId}")
    public ResponseEntity<List<Energy>> getByMachineId(
            @PathVariable Long machineId) {

        return ResponseEntity.ok(
                energyService.getByMachineId(machineId)
        );
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<Energy> update(
            @PathVariable Long id,
            @Valid @RequestBody EnergyRequest request) {

        return ResponseEntity.ok(
                energyService.update(id, request)
        );
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Long id) {

        energyService.delete(id);

        return ResponseEntity.noContent().build();
    }
}