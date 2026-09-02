package com.example.demo.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.example.demo.dto.TelemetryRequest;
import com.example.demo.entity.Telemetry;
import com.example.demo.service.TelemetryService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/telemetry")
public class TelemetryController {

    private final TelemetryService telemetryService;

    public TelemetryController(TelemetryService telemetryService) {
        this.telemetryService = telemetryService;
    }

    // =========================
    // CREATE TELEMETRY
    // ENGINEER + OPERATOR + ADMIN
    // =========================

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'ENGINEER', 'OPERATOR')")
    public ResponseEntity<Telemetry> create(
            @Valid @RequestBody TelemetryRequest request) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(telemetryService.create(request));
    }

    // =========================
    // GET ALL TELEMETRY
    // ALL ROLES
    // =========================

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'ENGINEER', 'OPERATOR', 'MANAGER')")
    public ResponseEntity<List<Telemetry>> getAll() {

        return ResponseEntity.ok(
                telemetryService.getAll()
        );
    }

    // =========================
    // GET TELEMETRY BY ID
    // ALL ROLES
    // =========================

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'ENGINEER', 'OPERATOR', 'MANAGER')")
    public ResponseEntity<Telemetry> getById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                telemetryService.getById(id)
        );
    }

    // =========================
    // GET TELEMETRY BY MACHINE
    // ALL ROLES
    // =========================

    @GetMapping("/machine/{machineId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'ENGINEER', 'OPERATOR', 'MANAGER')")
    public ResponseEntity<List<Telemetry>> getByMachine(
            @PathVariable Long machineId) {

        return ResponseEntity.ok(
                telemetryService.getByMachine(machineId)
        );
    }

    // =========================
    // DELETE TELEMETRY
    // ADMIN ONLY
    // =========================

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(
            @PathVariable Long id) {

        telemetryService.delete(id);

        return ResponseEntity.noContent().build();
    }
}