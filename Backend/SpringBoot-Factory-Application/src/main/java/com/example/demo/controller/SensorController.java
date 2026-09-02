package com.example.demo.controller;

import com.example.demo.dto.SensorRequest;
import com.example.demo.entity.Sensor;
import com.example.demo.service.SensorService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sensors")
public class SensorController {

    private final SensorService sensorService;

    public SensorController(SensorService sensorService) {
        this.sensorService = sensorService;
    }

    // CREATE SENSOR
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'ENGINEER')")
    public ResponseEntity<Sensor> create(
            @Valid @RequestBody SensorRequest request) {

        Sensor sensor = sensorService.create(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(sensor);
    }

    // GET ALL SENSORS
    @GetMapping
    public ResponseEntity<List<Sensor>> getAll() {

        return ResponseEntity.ok(
                sensorService.getAll()
        );
    }

    // GET SENSOR BY ID
    @GetMapping("/{id}")
    public ResponseEntity<Sensor> getById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                sensorService.getById(id)
        );
    }

    // GET SENSORS OF A MACHINE
    @GetMapping("/machine/{machineId}")
    public ResponseEntity<List<Sensor>> getByMachineId(
            @PathVariable Long machineId) {

        return ResponseEntity.ok(
                sensorService.getByMachineId(machineId)
        );
    }

    // UPDATE SENSOR
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'ENGINEER')")
    public ResponseEntity<Sensor> update(
            @PathVariable Long id,
            @Valid @RequestBody SensorRequest request) {

        return ResponseEntity.ok(
                sensorService.update(id, request)
        );
    }

    // ACTIVATE SENSOR
    @PatchMapping("/{id}/activate")
    @PreAuthorize("hasAnyRole('ADMIN', 'ENGINEER')")
    public ResponseEntity<Sensor> activate(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                sensorService.activate(id)
        );
    }

    // DEACTIVATE SENSOR
    @PatchMapping("/{id}/deactivate")
    @PreAuthorize("hasAnyRole('ADMIN', 'ENGINEER')")
    public ResponseEntity<Sensor> deactivate(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                sensorService.deactivate(id)
        );
    }

    // DELETE SENSOR
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(
            @PathVariable Long id) {

        sensorService.delete(id);

        return ResponseEntity.noContent().build();
    }
}