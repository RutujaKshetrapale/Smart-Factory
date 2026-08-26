package com.example.demo.controller;

import com.example.demo.dto.ProductionRequest;
import com.example.demo.entity.Production;
import com.example.demo.service.ProductionService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/production")
public class ProductionController {

    private final ProductionService productionService;

    public ProductionController(
            ProductionService productionService) {

        this.productionService = productionService;
    }

    // CREATE
    @PostMapping
    public ResponseEntity<Production> create(
            @Valid @RequestBody ProductionRequest request) {

        return ResponseEntity.ok(
                productionService.create(request)
        );
    }

    // GET ALL
    @GetMapping
    public ResponseEntity<List<Production>> getAll() {

        return ResponseEntity.ok(
                productionService.getAll()
        );
    }

    // GET BY ID
    @GetMapping("/{id}")
    public ResponseEntity<Production> getById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                productionService.getById(id)
        );
    }

    // GET BY MACHINE
    @GetMapping("/machine/{machineId}")
    public ResponseEntity<List<Production>> getByMachineId(
            @PathVariable Long machineId) {

        return ResponseEntity.ok(
                productionService.getByMachineId(machineId)
        );
    }

    // GET BY STATUS
    @GetMapping("/status/{status}")
    public ResponseEntity<List<Production>> getByStatus(
            @PathVariable String status) {

        return ResponseEntity.ok(
                productionService.getByStatus(status)
        );
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<Production> update(
            @PathVariable Long id,
            @Valid @RequestBody ProductionRequest request) {

        return ResponseEntity.ok(
                productionService.update(id, request)
        );
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Long id) {

        productionService.delete(id);

        return ResponseEntity.noContent().build();
    }
}