package com.example.demo.controller;

import java.time.LocalDateTime;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.example.demo.dto.PageResponse;
import com.example.demo.dto.TelemetryRequest;
import com.example.demo.entity.Telemetry;
import com.example.demo.exception.BusinessValidationException;
import com.example.demo.service.TelemetryService;
import com.example.demo.util.PaginationUtil;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/telemetry")
public class TelemetryController {

    private final TelemetryService telemetryService;

    public TelemetryController(
            TelemetryService telemetryService) {

        this.telemetryService = telemetryService;
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'ENGINEER', 'OPERATOR')")
    public ResponseEntity<Telemetry> create(
            @Valid @RequestBody TelemetryRequest request) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(telemetryService.create(request));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'ENGINEER', 'OPERATOR', 'MANAGER')")
    public ResponseEntity<PageResponse<Telemetry>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "timestamp") String sortBy,
            @RequestParam(defaultValue = "desc") String direction) {

        return ResponseEntity.ok(
                PaginationUtil.toResponse(
                        telemetryService.getAll(
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
    public ResponseEntity<Telemetry> getById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                telemetryService.getById(id)
        );
    }

    @GetMapping("/machine/{machineId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'ENGINEER', 'OPERATOR', 'MANAGER')")
    public ResponseEntity<PageResponse<Telemetry>> getByMachine(
            @PathVariable Long machineId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "timestamp") String sortBy,
            @RequestParam(defaultValue = "desc") String direction) {

        return ResponseEntity.ok(
                PaginationUtil.toResponse(
                        telemetryService.getByMachine(
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

    @GetMapping("/machine/{machineId}/date-range")
    @PreAuthorize("hasAnyRole('ADMIN', 'ENGINEER', 'OPERATOR', 'MANAGER')")
    public ResponseEntity<PageResponse<Telemetry>>
    getByMachineAndDateRange(
            @PathVariable Long machineId,
            @RequestParam LocalDateTime start,
            @RequestParam LocalDateTime end,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "timestamp") String sortBy,
            @RequestParam(defaultValue = "desc") String direction) {

        validateDates(start, end);

        return ResponseEntity.ok(
                PaginationUtil.toResponse(
                        telemetryService
                                .getByMachineAndDateRange(
                                        machineId,
                                        start,
                                        end,
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

    @GetMapping("/date-range")
    @PreAuthorize("hasAnyRole('ADMIN', 'ENGINEER', 'OPERATOR', 'MANAGER')")
    public ResponseEntity<PageResponse<Telemetry>>
    getByDateRange(
            @RequestParam LocalDateTime start,
            @RequestParam LocalDateTime end,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "timestamp") String sortBy,
            @RequestParam(defaultValue = "desc") String direction) {

        validateDates(start, end);

        return ResponseEntity.ok(
                PaginationUtil.toResponse(
                        telemetryService.getByDateRange(
                                start,
                                end,
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

    @GetMapping("/machine/{machineId}/latest")
    @PreAuthorize("hasAnyRole('ADMIN', 'ENGINEER', 'OPERATOR', 'MANAGER')")
    public ResponseEntity<?> getLatestByMachine(
            @PathVariable Long machineId) {

        return ResponseEntity.ok(
                telemetryService.getLatestByMachine(
                        machineId
                )
        );
    }

    private Pageable pageable(
            int page,
            int size,
            String sortBy,
            String direction) {

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

        Sort.Direction sortDirection =
                direction.equalsIgnoreCase("asc")
                        ? Sort.Direction.ASC
                        : Sort.Direction.DESC;

        return PageRequest.of(
                page,
                size,
                Sort.by(sortDirection, sortBy)
        );
    }

    private void validateDates(
            LocalDateTime start,
            LocalDateTime end) {

        if (start == null || end == null) {
            throw new BusinessValidationException(
                    "Start time and end time are required"
            );
        }

        if (end.isBefore(start)) {
            throw new BusinessValidationException(
                    "End time cannot be before start time"
            );
        }
    }
}