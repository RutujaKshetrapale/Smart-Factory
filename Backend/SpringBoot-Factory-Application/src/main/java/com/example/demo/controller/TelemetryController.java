package com.example.demo.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @PostMapping
    public ResponseEntity<Telemetry> create(
            @Valid @RequestBody TelemetryRequest request) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(telemetryService.create(request));
    }

    @GetMapping
    public ResponseEntity<List<Telemetry>> getAll() {

        return ResponseEntity.ok(
                telemetryService.getAll()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<Telemetry> getById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                telemetryService.getById(id)
        );
    }

    @GetMapping("/machine/{machineId}")
    public ResponseEntity<List<Telemetry>> getByMachine(
            @PathVariable Long machineId) {

        return ResponseEntity.ok(
                telemetryService.getByMachine(machineId)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Long id) {

        telemetryService.delete(id);

        return ResponseEntity.noContent().build();
    }
}