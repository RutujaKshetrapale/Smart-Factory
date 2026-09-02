package com.example.demo.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

    // =========================
    // CREATE MAINTENANCE
    // ADMIN + ENGINEER
    // =========================

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'ENGINEER')")
    public ResponseEntity<Maintenance> create(
            @Valid @RequestBody MaintenanceRequest request) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(maintenanceService.create(request));
    }

    // =========================
    // GET ALL MAINTENANCE
    // ALL ROLES
    // =========================

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'ENGINEER', 'OPERATOR', 'MANAGER')")
    public ResponseEntity<List<Maintenance>> getAll() {

        return ResponseEntity.ok(
                maintenanceService.getAll()
        );
    }

    // =========================
    // GET MAINTENANCE BY ID
    // ALL ROLES
    // =========================

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'ENGINEER', 'OPERATOR', 'MANAGER')")
    public ResponseEntity<Maintenance> getById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                maintenanceService.getById(id)
        );
    }

    // =========================
    // GET BY MACHINE
    // ALL ROLES
    // =========================

    @GetMapping("/machine/{machineId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'ENGINEER', 'OPERATOR', 'MANAGER')")
    public ResponseEntity<List<Maintenance>> getByMachine(
            @PathVariable Long machineId) {

        return ResponseEntity.ok(
                maintenanceService.getByMachine(machineId)
        );
    }

    // =========================
    // GET BY STATUS
    // ALL ROLES
    // =========================

    @GetMapping("/status/{status}")
    @PreAuthorize("hasAnyRole('ADMIN', 'ENGINEER', 'OPERATOR', 'MANAGER')")
    public ResponseEntity<List<Maintenance>> getByStatus(
            @PathVariable String status) {

        return ResponseEntity.ok(
                maintenanceService.getByStatus(status)
        );
    }

    // =========================
    // GET BY STATUS
    // ORDERED BY DATE
    // ALL ROLES
    // =========================

    @GetMapping("/status/{status}/scheduled")
    @PreAuthorize("hasAnyRole('ADMIN', 'ENGINEER', 'OPERATOR', 'MANAGER')")
    public ResponseEntity<List<Maintenance>> getByStatusOrderByDate(
            @PathVariable String status) {

        return ResponseEntity.ok(
                maintenanceService
                        .getByStatusOrderByDate(status)
        );
    }

    // =========================
    // UPDATE MAINTENANCE
    // ADMIN + ENGINEER
    // =========================

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'ENGINEER')")
    public ResponseEntity<Maintenance> update(
            @PathVariable Long id,
            @Valid @RequestBody MaintenanceRequest request) {

        return ResponseEntity.ok(
                maintenanceService.update(id, request)
        );
    }

    // =========================
    // DELETE MAINTENANCE
    // ADMIN ONLY
    // =========================

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(
            @PathVariable Long id) {

        maintenanceService.delete(id);

        return ResponseEntity
                .noContent()
                .build();
    }
}