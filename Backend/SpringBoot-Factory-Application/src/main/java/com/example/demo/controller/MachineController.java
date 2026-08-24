package com.example.demo.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    // CREATE MACHINE
    @PostMapping
    public ResponseEntity<Machine> create(
            @Valid @RequestBody MachineRequest request) {

        Machine machine = machineService.create(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(machine);
    }

    // GET ALL MACHINES
    @GetMapping
    public ResponseEntity<List<Machine>> getAll() {

        return ResponseEntity.ok(
                machineService.getAll()
        );
    }

    // GET MACHINE BY ID
    @GetMapping("/{id}")
    public ResponseEntity<Machine> getById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                machineService.getById(id)
        );
    }

    // UPDATE MACHINE
    @PutMapping("/{id}")
    public ResponseEntity<Machine> update(
            @PathVariable Long id,
            @Valid @RequestBody MachineRequest request) {

        return ResponseEntity.ok(
                machineService.update(id, request)
        );
    }

    // DELETE MACHINE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Long id) {

        machineService.delete(id);

        return ResponseEntity.noContent().build();
    }
}