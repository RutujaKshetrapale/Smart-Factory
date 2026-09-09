package com.example.demo.controller;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.example.demo.dto.MachineRequest;
import com.example.demo.dto.PageResponse;
import com.example.demo.entity.Machine;
import com.example.demo.exception.BusinessValidationException;
import com.example.demo.service.MachineService;
import com.example.demo.util.PaginationUtil;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/machines")
public class MachineController {

    private final MachineService machineService;

    public MachineController(
            MachineService machineService) {

        this.machineService = machineService;
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'ENGINEER')")
    public ResponseEntity<Machine> create(
            @Valid @RequestBody MachineRequest request) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(machineService.create(request));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'ENGINEER', 'OPERATOR', 'MANAGER')")
    public ResponseEntity<PageResponse<Machine>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction) {

        return ResponseEntity.ok(
                PaginationUtil.toResponse(
                        machineService.getAll(
                                createPageable(
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
    public ResponseEntity<Machine> getById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                machineService.getById(id)
        );
    }

    @GetMapping("/plant/{plantId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'ENGINEER', 'OPERATOR', 'MANAGER')")
    public ResponseEntity<PageResponse<Machine>> getByPlant(
            @PathVariable Long plantId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction) {

        return ResponseEntity.ok(
                PaginationUtil.toResponse(
                        machineService.getByPlant(
                                plantId,
                                createPageable(
                                        page,
                                        size,
                                        sortBy,
                                        direction
                                )
                        )
                )
        );
    }

    @GetMapping("/status/{status}")
    @PreAuthorize("hasAnyRole('ADMIN', 'ENGINEER', 'OPERATOR', 'MANAGER')")
    public ResponseEntity<PageResponse<Machine>> getByStatus(
            @PathVariable String status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction) {

        return ResponseEntity.ok(
                PaginationUtil.toResponse(
                        machineService.getByStatus(
                                status,
                                createPageable(
                                        page,
                                        size,
                                        sortBy,
                                        direction
                                )
                        )
                )
        );
    }

    @GetMapping("/plant/{plantId}/status/{status}")
    @PreAuthorize("hasAnyRole('ADMIN', 'ENGINEER', 'OPERATOR', 'MANAGER')")
    public ResponseEntity<PageResponse<Machine>>
    getByPlantAndStatus(
            @PathVariable Long plantId,
            @PathVariable String status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction) {

        return ResponseEntity.ok(
                PaginationUtil.toResponse(
                        machineService.getByPlantAndStatus(
                                plantId,
                                status,
                                createPageable(
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
    public ResponseEntity<PageResponse<Machine>> searchByName(
            @RequestParam String name,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "name") String sortBy,
            @RequestParam(defaultValue = "asc") String direction) {

        return ResponseEntity.ok(
                PaginationUtil.toResponse(
                        machineService.searchByName(
                                name,
                                createPageable(
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
    public ResponseEntity<Machine> update(
            @PathVariable Long id,
            @Valid @RequestBody MachineRequest request) {

        return ResponseEntity.ok(
                machineService.update(id, request)
        );
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(
            @PathVariable Long id) {

        machineService.delete(id);

        return ResponseEntity.noContent().build();
    }

    private Pageable createPageable(
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