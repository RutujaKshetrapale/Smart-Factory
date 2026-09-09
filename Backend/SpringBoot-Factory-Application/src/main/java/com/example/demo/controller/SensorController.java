package com.example.demo.controller;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.example.demo.dto.PageResponse;
import com.example.demo.dto.SensorRequest;
import com.example.demo.entity.Sensor;
import com.example.demo.exception.BusinessValidationException;
import com.example.demo.service.SensorService;
import com.example.demo.util.PaginationUtil;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/sensors")
public class SensorController {

    private final SensorService sensorService;

    public SensorController(
            SensorService sensorService) {

        this.sensorService = sensorService;
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'ENGINEER')")
    public ResponseEntity<Sensor> create(
            @Valid @RequestBody SensorRequest request) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(sensorService.create(request));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'ENGINEER', 'OPERATOR', 'MANAGER')")
    public ResponseEntity<PageResponse<Sensor>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction) {

        return ResponseEntity.ok(
                PaginationUtil.toResponse(
                        sensorService.getAll(
                                pageable(
                                        page,
                                        size,
                                        sortBy,
                                        direction
                                )
                        )
                )
        );
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'ENGINEER', 'OPERATOR', 'MANAGER')")
    public ResponseEntity<Sensor> getById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                sensorService.getById(id)
        );
    }

    @GetMapping("/machine/{machineId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'ENGINEER', 'OPERATOR', 'MANAGER')")
    public ResponseEntity<PageResponse<Sensor>> getByMachine(
            @PathVariable Long machineId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction) {

        return ResponseEntity.ok(
                PaginationUtil.toResponse(
                        sensorService.getByMachine(
                                machineId,
                                pageable(
                                        page,
                                        size,
                                        sortBy,
                                        direction
                                )
                        )
                )
        );
    }

    @GetMapping("/active")
    @PreAuthorize("hasAnyRole('ADMIN', 'ENGINEER', 'OPERATOR', 'MANAGER')")
    public ResponseEntity<PageResponse<Sensor>> getActive(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction) {

        return ResponseEntity.ok(
                PaginationUtil.toResponse(
                        sensorService.getActive(
                                pageable(
                                        page,
                                        size,
                                        sortBy,
                                        direction
                                )
                        )
                )
        );
    }

    @GetMapping("/inactive")
    @PreAuthorize("hasAnyRole('ADMIN', 'ENGINEER', 'OPERATOR', 'MANAGER')")
    public ResponseEntity<PageResponse<Sensor>> getInactive(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction) {

        return ResponseEntity.ok(
                PaginationUtil.toResponse(
                        sensorService.getInactive(
                                pageable(
                                        page,
                                        size,
                                        sortBy,
                                        direction
                                )
                        )
                )
        );
    }

    @GetMapping("/type/{type}")
    @PreAuthorize("hasAnyRole('ADMIN', 'ENGINEER', 'OPERATOR', 'MANAGER')")
    public ResponseEntity<PageResponse<Sensor>> getByType(
            @PathVariable String type,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction) {

        return ResponseEntity.ok(
                PaginationUtil.toResponse(
                        sensorService.getByType(
                                type,
                                pageable(
                                        page,
                                        size,
                                        sortBy,
                                        direction
                                )
                        )
                )
        );
    }

    @GetMapping("/machine/{machineId}/type/{type}")
    @PreAuthorize("hasAnyRole('ADMIN', 'ENGINEER', 'OPERATOR', 'MANAGER')")
    public ResponseEntity<PageResponse<Sensor>>
    getByMachineAndType(
            @PathVariable Long machineId,
            @PathVariable String type,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction) {

        return ResponseEntity.ok(
                PaginationUtil.toResponse(
                        sensorService.getByMachineAndType(
                                machineId,
                                type,
                                pageable(
                                        page,
                                        size,
                                        sortBy,
                                        direction
                                )
                        )
                )
        );
    }

    @GetMapping("/search/name")
    @PreAuthorize("hasAnyRole('ADMIN', 'ENGINEER', 'OPERATOR', 'MANAGER')")
    public ResponseEntity<PageResponse<Sensor>> searchByName(
            @RequestParam String name,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "name") String sortBy,
            @RequestParam(defaultValue = "asc") String direction) {

        return ResponseEntity.ok(
                PaginationUtil.toResponse(
                        sensorService.searchByName(
                                name,
                                pageable(
                                        page,
                                        size,
                                        sortBy,
                                        direction
                                )
                        )
                )
        );
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'ENGINEER')")
    public ResponseEntity<Sensor> update(
            @PathVariable Long id,
            @Valid @RequestBody SensorRequest request) {

        return ResponseEntity.ok(
                sensorService.update(id, request)
        );
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(
            @PathVariable Long id) {

        sensorService.delete(id);

        return ResponseEntity.noContent().build();
    }

    private Pageable pageable(
            int page,
            int size,
            String sortBy,
            String direction) {

        validate(page, size);

        Sort.Direction sortDirection =
                direction.equalsIgnoreCase("desc")
                        ? Sort.Direction.DESC
                        : Sort.Direction.ASC;

        return PageRequest.of(
                page,
                size,
                Sort.by(sortDirection, sortBy)
        );
    }

    private void validate(
            int page,
            int size) {

        if (page < 0) {
            throw new BusinessValidationException(
                    "Page cannot be negative"
            );
        }

        if (size < 1 || size > 100) {
            throw new BusinessValidationException(
                    "Size must be between 1 and 100"
            );
        }
    }
}