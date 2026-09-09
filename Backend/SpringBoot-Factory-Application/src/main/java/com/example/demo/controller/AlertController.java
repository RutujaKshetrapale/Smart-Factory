package com.example.demo.controller;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.example.demo.dto.AlertRequest;
import com.example.demo.dto.PageResponse;
import com.example.demo.entity.Alert;
import com.example.demo.service.AlertService;
import com.example.demo.util.PaginationUtil;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/alerts")
public class AlertController {

    private final AlertService alertService;

    public AlertController(AlertService alertService) {
        this.alertService = alertService;
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'ENGINEER')")
    public ResponseEntity<Alert> create(
            @Valid @RequestBody AlertRequest request) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(alertService.create(request));
    }

    // Normal GET
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'ENGINEER', 'OPERATOR', 'MANAGER')")
    public ResponseEntity<?> getAll(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction) {

        // If pagination parameters are not supplied,
        // return the normal list.
        if (page == null && size == null) {
            return ResponseEntity.ok(alertService.getAll());
        }

        int currentPage = page == null ? 0 : page;
        int pageSize = size == null ? 10 : size;

        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable =
                PageRequest.of(currentPage, pageSize, sort);

        PageResponse<Alert> response =
                PaginationUtil.toResponse(
                        alertService.getAll(pageable)
                );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'ENGINEER', 'OPERATOR', 'MANAGER')")
    public ResponseEntity<Alert> getById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                alertService.getById(id)
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
                    alertService.getByMachine(machineId)
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
                        alertService.getByMachine(
                                machineId,
                                pageable
                        )
                )
        );
    }

    @GetMapping("/unresolved")
    @PreAuthorize("hasAnyRole('ADMIN', 'ENGINEER', 'OPERATOR', 'MANAGER')")
    public ResponseEntity<?> getUnresolved(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction) {

        if (page == null && size == null) {
            return ResponseEntity.ok(
                    alertService.getUnresolved()
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
                        alertService.getUnresolved(pageable)
                )
        );
    }

    @GetMapping("/severity/{severity}")
    @PreAuthorize("hasAnyRole('ADMIN', 'ENGINEER', 'OPERATOR', 'MANAGER')")
    public ResponseEntity<?> getBySeverity(
            @PathVariable String severity,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction) {

        if (page == null && size == null) {
            return ResponseEntity.ok(
                    alertService.getBySeverity(severity)
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
                        alertService.getBySeverity(
                                severity,
                                pageable
                        )
                )
        );
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'ENGINEER')")
    public ResponseEntity<Alert> update(
            @PathVariable Long id,
            @Valid @RequestBody AlertRequest request) {

        return ResponseEntity.ok(
                alertService.update(id, request)
        );
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(
            @PathVariable Long id) {

        alertService.delete(id);

        return ResponseEntity.noContent().build();
    }
}
