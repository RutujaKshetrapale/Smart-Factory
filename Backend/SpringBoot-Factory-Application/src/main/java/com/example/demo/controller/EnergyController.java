package com.example.demo.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.example.demo.dto.EnergyRequest;
import com.example.demo.entity.Energy;
import com.example.demo.service.EnergyService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/energy")
public class EnergyController {

    private final EnergyService energyService;

    public EnergyController(EnergyService energyService) {

        this.energyService = energyService;
    }

    // =========================
    // CREATE ENERGY RECORD
    // ADMIN + ENGINEER
    // =========================

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'ENGINEER')")
    public ResponseEntity<Energy> create(
            @Valid @RequestBody EnergyRequest request) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(energyService.create(request));
    }

    // =========================
    // GET ALL ENERGY RECORDS
    // ALL ROLES
    // =========================

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'ENGINEER', 'OPERATOR', 'MANAGER')")
    public ResponseEntity<List<Energy>> getAll() {

        return ResponseEntity.ok(
                energyService.getAll()
        );
    }

    // =========================
    // GET ENERGY BY ID
    // ALL ROLES
    // =========================

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'ENGINEER', 'OPERATOR', 'MANAGER')")
    public ResponseEntity<Energy> getById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                energyService.getById(id)
        );
    }

    // =========================
    // GET ENERGY BY MACHINE
    // ALL ROLES
    // =========================

    @GetMapping("/machine/{machineId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'ENGINEER', 'OPERATOR', 'MANAGER')")
    public ResponseEntity<List<Energy>> getByMachine(
            @PathVariable Long machineId) {

        return ResponseEntity.ok(
                energyService.getByMachine(machineId)
        );
    }

    // =========================
    // UPDATE ENERGY RECORD
    // ADMIN + ENGINEER
    // =========================

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'ENGINEER')")
    public ResponseEntity<Energy> update(
            @PathVariable Long id,
            @Valid @RequestBody EnergyRequest request) {

        return ResponseEntity.ok(
                energyService.update(id, request)
        );
    }

    // =========================
    // DELETE ENERGY RECORD
    // ADMIN ONLY
    // =========================

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(
            @PathVariable Long id) {

        energyService.delete(id);

        return ResponseEntity
                .noContent()
                .build();
    }
}