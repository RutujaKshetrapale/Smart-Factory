package com.example.demo.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.example.demo.dto.AlertRequest;
import com.example.demo.entity.Alert;
import com.example.demo.service.AlertService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/alerts")
public class AlertController {

    private final AlertService alertService;

    public AlertController(AlertService alertService) {
        this.alertService = alertService;
    }

    // =========================
    // CREATE ALERT
    // ADMIN + ENGINEER
    // =========================

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'ENGINEER')")
    public ResponseEntity<Alert> create(
            @Valid @RequestBody AlertRequest request) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(alertService.create(request));
    }

    // =========================
    // GET ALL ALERTS
    // ALL ROLES
    // =========================

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'ENGINEER', 'OPERATOR', 'MANAGER')")
    public ResponseEntity<List<Alert>> getAll() {

        return ResponseEntity.ok(
                alertService.getAll()
        );
    }

    // =========================
    // GET ALERT BY ID
    // ALL ROLES
    // =========================

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'ENGINEER', 'OPERATOR', 'MANAGER')")
    public ResponseEntity<Alert> getById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                alertService.getById(id)
        );
    }

    // =========================
    // GET ALERTS BY MACHINE
    // ALL ROLES
    // =========================

    @GetMapping("/machine/{machineId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'ENGINEER', 'OPERATOR', 'MANAGER')")
    public ResponseEntity<List<Alert>> getByMachine(
            @PathVariable Long machineId) {

        return ResponseEntity.ok(
                alertService.getByMachine(machineId)
        );
    }

    // =========================
    // UPDATE ALERT
    // ADMIN + ENGINEER
    // =========================

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'ENGINEER')")
    public ResponseEntity<Alert> update(
            @PathVariable Long id,
            @Valid @RequestBody AlertRequest request) {

        return ResponseEntity.ok(
                alertService.update(id, request)
        );
    }

    // =========================
    // DELETE ALERT
    // ADMIN ONLY
    // =========================

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(
            @PathVariable Long id) {

        alertService.delete(id);

        return ResponseEntity
                .noContent()
                .build();
    }
}