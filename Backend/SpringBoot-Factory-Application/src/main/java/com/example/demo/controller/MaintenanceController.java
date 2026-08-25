package com.example.demo.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.dto.MaintenanceRequest;
import com.example.demo.entity.Maintenance;
import com.example.demo.service.MaintenanceService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/maintenance")
public class MaintenanceController {

    private final MaintenanceService maintenanceService;

    public MaintenanceController(
            MaintenanceService maintenanceService) {

        this.maintenanceService = maintenanceService;
    }

    @PostMapping
    public ResponseEntity<Maintenance> create(
            @Valid @RequestBody MaintenanceRequest request) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(maintenanceService.create(request));
    }

    @GetMapping
    public ResponseEntity<List<Maintenance>> getAll() {

        return ResponseEntity.ok(
                maintenanceService.getAll()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<Maintenance> getById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                maintenanceService.getById(id)
        );
    }

    @GetMapping("/machine/{machineId}")
    public ResponseEntity<List<Maintenance>> getByMachine(
            @PathVariable Long machineId) {

        return ResponseEntity.ok(
                maintenanceService.getByMachine(machineId)
        );
    }

    @GetMapping("/pending")
    public ResponseEntity<List<Maintenance>> getPending() {

        return ResponseEntity.ok(
                maintenanceService.getPending()
        );
    }

    @PatchMapping("/{id}/complete")
    public ResponseEntity<Maintenance> complete(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                maintenanceService.complete(id)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Long id) {

        maintenanceService.delete(id);

        return ResponseEntity.noContent().build();
    }
}