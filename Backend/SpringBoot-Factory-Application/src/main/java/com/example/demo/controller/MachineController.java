package com.example.demo.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.example.demo.dto.MachineRequest;
import com.example.demo.entity.Machine;
import com.example.demo.service.MachineService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/machines")
public class MachineController {

    private final MachineService machineService;

    public MachineController(MachineService machineService) {
        this.machineService = machineService;
    }

    // =========================
    // CREATE MACHINE
    // ADMIN ONLY
    // =========================

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Machine> create(
            @Valid @RequestBody MachineRequest request) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(machineService.create(request));
    }

    // =========================
    // GET ALL MACHINES
    // ALL AUTHENTICATED ROLES
    // =========================

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'ENGINEER', 'OPERATOR', 'MANAGER')")
    public ResponseEntity<List<Machine>> getAll() {

        return ResponseEntity.ok(
                machineService.getAll()
        );
    }

    // =========================
    // GET MACHINE BY ID
    // ALL AUTHENTICATED ROLES
    // =========================

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'ENGINEER', 'OPERATOR', 'MANAGER')")
    public ResponseEntity<Machine> getById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                machineService.getById(id)
        );
    }

    // =========================
    // UPDATE MACHINE
    // ADMIN ONLY
    // =========================

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Machine> update(
            @PathVariable Long id,
            @Valid @RequestBody MachineRequest request) {

        return ResponseEntity.ok(
                machineService.update(id, request)
        );
    }

    // =========================
    // DELETE MACHINE
    // ADMIN ONLY
    // =========================

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(
            @PathVariable Long id) {

        machineService.delete(id);

        return ResponseEntity.noContent().build();
    }
}