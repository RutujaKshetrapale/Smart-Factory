package com.example.demo.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.example.demo.dto.ProductionRequest;
import com.example.demo.entity.Production;
import com.example.demo.service.ProductionService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/production")
public class ProductionController {

    private final ProductionService productionService;

    public ProductionController(
            ProductionService productionService) {

        this.productionService = productionService;
    }

    // =========================
    // CREATE PRODUCTION
    // ADMIN + ENGINEER
    // =========================

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'ENGINEER')")
    public ResponseEntity<Production> create(
            @Valid @RequestBody ProductionRequest request) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(productionService.create(request));
    }

    // =========================
    // GET ALL PRODUCTIONS
    // ALL ROLES
    // =========================

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'ENGINEER', 'OPERATOR', 'MANAGER')")
    public ResponseEntity<List<Production>> getAll() {

        return ResponseEntity.ok(
                productionService.getAll()
        );
    }

    // =========================
    // GET PRODUCTION BY ID
    // ALL ROLES
    // =========================

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'ENGINEER', 'OPERATOR', 'MANAGER')")
    public ResponseEntity<Production> getById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                productionService.getById(id)
        );
    }

    // =========================
    // GET BY MACHINE
    // ALL ROLES
    // =========================

    @GetMapping("/machine/{machineId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'ENGINEER', 'OPERATOR', 'MANAGER')")
    public ResponseEntity<List<Production>> getByMachine(
            @PathVariable Long machineId) {

        return ResponseEntity.ok(
                productionService.getByMachine(machineId)
        );
    }

    // =========================
    // GET BY STATUS
    // ALL ROLES
    // =========================

    @GetMapping("/status/{status}")
    @PreAuthorize("hasAnyRole('ADMIN', 'ENGINEER', 'OPERATOR', 'MANAGER')")
    public ResponseEntity<List<Production>> getByStatus(
            @PathVariable String status) {

        return ResponseEntity.ok(
                productionService.getByStatus(status)
        );
    }

    // =========================
    // UPDATE PRODUCTION
    // ADMIN + ENGINEER
    // =========================

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'ENGINEER')")
    public ResponseEntity<Production> update(
            @PathVariable Long id,
            @Valid @RequestBody ProductionRequest request) {

        return ResponseEntity.ok(
                productionService.update(id, request)
        );
    }

    // =========================
    // DELETE PRODUCTION
    // ADMIN ONLY
    // =========================

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(
            @PathVariable Long id) {

        productionService.delete(id);

        return ResponseEntity
                .noContent()
                .build();
    }
}