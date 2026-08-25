package com.example.demo.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @PostMapping
    public ResponseEntity<Alert> create(
            @Valid @RequestBody AlertRequest request) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(alertService.create(request));
    }

    @GetMapping
    public ResponseEntity<List<Alert>> getAll() {

        return ResponseEntity.ok(
                alertService.getAll()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<Alert> getById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                alertService.getById(id)
        );
    }

    @GetMapping("/machine/{machineId}")
    public ResponseEntity<List<Alert>> getByMachine(
            @PathVariable Long machineId) {

        return ResponseEntity.ok(
                alertService.getByMachine(machineId)
        );
    }

    @GetMapping("/unresolved")
    public ResponseEntity<List<Alert>> getUnresolved() {

        return ResponseEntity.ok(
                alertService.getUnresolved()
        );
    }

    @GetMapping("/severity/{severity}")
    public ResponseEntity<List<Alert>> getBySeverity(
            @PathVariable String severity) {

        return ResponseEntity.ok(
                alertService.getBySeverity(severity)
        );
    }

    @PatchMapping("/{id}/resolve")
    public ResponseEntity<Alert> resolve(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                alertService.resolve(id)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Long id) {

        alertService.delete(id);

        return ResponseEntity.noContent().build();
    }
}