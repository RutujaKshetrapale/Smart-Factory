package com.example.demo.controller;

import com.example.demo.dto.SensorRequest;
import com.example.demo.entity.Sensor;
import com.example.demo.service.SensorService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sensors")
public class SensorController {

    private final SensorService sensorService;

    public SensorController(SensorService sensorService) {
        this.sensorService = sensorService;
    }

    // CREATE
    @PostMapping
    public ResponseEntity<Sensor> create(
            @Valid @RequestBody SensorRequest request) {

        return ResponseEntity.ok(
                sensorService.create(request)
        );
    }

    // GET ALL
    @GetMapping
    public ResponseEntity<List<Sensor>> getAll() {

        return ResponseEntity.ok(
                sensorService.getAll()
        );
    }

    // GET BY ID
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

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<Sensor> update(
            @PathVariable Long id,
            @Valid @RequestBody SensorRequest request) {

        return ResponseEntity.ok(
                sensorService.update(id, request)
        );
    }

    // ACTIVATE
    @PatchMapping("/{id}/activate")
    public ResponseEntity<Sensor> activate(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                sensorService.activate(id)
        );
    }

    // DEACTIVATE
    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<Sensor> deactivate(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                sensorService.deactivate(id)
        );
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Long id) {

        sensorService.delete(id);

        return ResponseEntity.noContent().build();
    }
}