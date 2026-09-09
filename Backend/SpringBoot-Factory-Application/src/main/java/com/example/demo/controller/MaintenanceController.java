package com.example.demo.controller;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.example.demo.dto.MaintenanceRequest;
import com.example.demo.dto.PageResponse;
import com.example.demo.entity.Maintenance;
import com.example.demo.service.MaintenanceService;
import com.example.demo.util.PaginationUtil;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/maintenance")
public class MaintenanceController {

    private final MaintenanceService maintenanceService;

    public MaintenanceController(
            MaintenanceService maintenanceService) {

        this.maintenanceService = maintenanceService;
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'ENGINEER')")
    public ResponseEntity<Maintenance> create(
            @Valid @RequestBody MaintenanceRequest request) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(maintenanceService.create(request));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'ENGINEER', 'OPERATOR', 'MANAGER')")
    public ResponseEntity<?> getAll(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction) {

        if (page == null && size == null) {
            return ResponseEntity.ok(
                    maintenanceService.getAll()
            );
        }

        int currentPage = page == null ? 0 : page;
        int pageSize = size == null ? 10 : size;

        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable =
                PageRequest.of(currentPage, pageSize, sort);

        PageResponse<Maintenance> response =
                PaginationUtil.toResponse(
                        maintenanceService.getAll(pageable)
                );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'ENGINEER', 'OPERATOR', 'MANAGER')")
    public ResponseEntity<Maintenance> getById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                maintenanceService.getById(id)
        );
    }

    @GetMapping("/machine/{machineId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'ENGINEER', 'OPERATOR', 'MANAGER')")
    public ResponseEntity<?> getByMachine(
            @PathVariable Long machineId,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction) {

        if (page == null && size == null) {
            return ResponseEntity.ok(
                    maintenanceService.getByMachine(machineId)
            );
        }

        int currentPage = page == null ? 0 : page;
        int pageSize = size == null ? 10 : size;

        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable =
                PageRequest.of(currentPage, pageSize, sort);

        return ResponseEntity.ok(
                PaginationUtil.toResponse(
                        maintenanceService.getByMachine(
                                machineId,
                                pageable
                        )
                )
        );
    }

    @GetMapping("/status/{status}")
    @PreAuthorize("hasAnyRole('ADMIN', 'ENGINEER', 'OPERATOR', 'MANAGER')")
    public ResponseEntity<?> getByStatus(
            @PathVariable String status,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction) {

        if (page == null && size == null) {
            return ResponseEntity.ok(
                    maintenanceService.getByStatus(status)
            );
        }

        int currentPage = page == null ? 0 : page;
        int pageSize = size == null ? 10 : size;

        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable =
                PageRequest.of(currentPage, pageSize, sort);

        return ResponseEntity.ok(
                PaginationUtil.toResponse(
                        maintenanceService.getByStatus(
                                status,
                                pageable
                        )
                )
        );
    }

    @GetMapping("/status/{status}/sorted")
    @PreAuthorize("hasAnyRole('ADMIN', 'ENGINEER', 'OPERATOR', 'MANAGER')")
    public ResponseEntity<?> getByStatusSorted(
            @PathVariable String status,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size,
            @RequestParam(defaultValue = "scheduledDate") String sortBy,
            @RequestParam(defaultValue = "asc") String direction) {

        if (page == null && size == null) {
            return ResponseEntity.ok(
                    maintenanceService.getByStatusOrderByDate(status)
            );
        }

        int currentPage = page == null ? 0 : page;
        int pageSize = size == null ? 10 : size;

        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable =
                PageRequest.of(currentPage, pageSize, sort);

        return ResponseEntity.ok(
                PaginationUtil.toResponse(
                        maintenanceService.getByStatusOrderByDate(
                                status,
                                pageable
                        )
                )
        );
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'ENGINEER')")
    public ResponseEntity<Maintenance> update(
            @PathVariable Long id,
            @Valid @RequestBody MaintenanceRequest request) {

        return ResponseEntity.ok(
                maintenanceService.update(id, request)
        );
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(
            @PathVariable Long id) {

        maintenanceService.delete(id);

        return ResponseEntity.noContent().build();
    }
}

