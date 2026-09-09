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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/telemetry")
@Tag(
        name = "Telemetry Management",
        description = "APIs for collecting and retrieving machine telemetry data"
)
public class TelemetryController {

    private final TelemetryService telemetryService;

    public TelemetryController(
            TelemetryService telemetryService) {

        this.telemetryService = telemetryService;
    }

    @Operation(
            summary = "Create telemetry data",
            description = "Records a new telemetry reading for a machine."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Telemetry data created successfully"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid telemetry data"
            )
    })
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'ENGINEER', 'OPERATOR')")
    public ResponseEntity<Telemetry> create(
            @Valid @RequestBody TelemetryRequest request) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(telemetryService.create(request));
    }

    @Operation(
            summary = "Get all telemetry data",
            description = "Returns paginated and sorted telemetry records."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Telemetry records retrieved successfully"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid pagination parameters"
            )
    })
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'ENGINEER', 'OPERATOR', 'MANAGER')")
    public ResponseEntity<PageResponse<Telemetry>> getAll(

            @Parameter(
                    description = "Page number starting from 0",
                    example = "0"
            )
            @RequestParam(defaultValue = "0") int page,

            @Parameter(
                    description = "Number of records per page",
                    example = "10"
            )
            @RequestParam(defaultValue = "10") int size,

            @Parameter(
                    description = "Field used for sorting",
                    example = "timestamp"
            )
            @RequestParam(defaultValue = "timestamp") String sortBy,

            @Parameter(
                    description = "Sorting direction: asc or desc",
                    example = "desc"
            )
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

    @Operation(
            summary = "Get telemetry by ID",
            description = "Retrieves a specific telemetry record using its unique ID."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Telemetry record found successfully"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Telemetry record not found"
            )
    })
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'ENGINEER', 'OPERATOR', 'MANAGER')")
    public ResponseEntity<Telemetry> getById(

            @Parameter(
                    description = "Unique ID of the telemetry record",
                    example = "1"
            )
            @PathVariable Long id) {

        return ResponseEntity.ok(
                telemetryService.getById(id)
        );
    }

    @Operation(
            summary = "Get telemetry by machine",
            description = "Returns telemetry records belonging to a specific machine."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Telemetry records retrieved successfully"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Machine not found"
            )
    })
    @GetMapping("/machine/{machineId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'ENGINEER', 'OPERATOR', 'MANAGER')")
    public ResponseEntity<PageResponse<Telemetry>> getByMachine(

            @Parameter(
                    description = "Unique ID of the machine",
                    example = "1"
            )
            @PathVariable Long machineId,

            @Parameter(
                    description = "Page number starting from 0",
                    example = "0"
            )
            @RequestParam(defaultValue = "0") int page,

            @Parameter(
                    description = "Number of records per page",
                    example = "10"
            )
            @RequestParam(defaultValue = "10") int size,

            @Parameter(
                    description = "Field used for sorting",
                    example = "timestamp"
            )
            @RequestParam(defaultValue = "timestamp") String sortBy,

            @Parameter(
                    description = "Sorting direction: asc or desc",
                    example = "desc"
            )
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

    @Operation(
            summary = "Get machine telemetry by date range",
            description = "Returns telemetry records for a specific machine between the supplied start and end timestamps."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Telemetry records retrieved successfully"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid date range or pagination parameters"
            )
    })
    @GetMapping("/machine/{machineId}/date-range")
    @PreAuthorize("hasAnyRole('ADMIN', 'ENGINEER', 'OPERATOR', 'MANAGER')")
    public ResponseEntity<PageResponse<Telemetry>>
    getByMachineAndDateRange(

            @Parameter(
                    description = "Unique ID of the machine",
                    example = "1"
            )
            @PathVariable Long machineId,

            @Parameter(
                    description = "Start timestamp",
                    example = "2026-09-01T00:00:00"
            )
            @RequestParam LocalDateTime start,

            @Parameter(
                    description = "End timestamp",
                    example = "2026-09-01T23:59:59"
            )
            @RequestParam LocalDateTime end,

            @Parameter(
                    description = "Page number starting from 0",
                    example = "0"
            )
            @RequestParam(defaultValue = "0") int page,

            @Parameter(
                    description = "Number of records per page",
                    example = "10"
            )
            @RequestParam(defaultValue = "10") int size,

            @Parameter(
                    description = "Field used for sorting",
                    example = "timestamp"
            )
            @RequestParam(defaultValue = "timestamp") String sortBy,

            @Parameter(
                    description = "Sorting direction: asc or desc",
                    example = "desc"
            )
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

    @Operation(
            summary = "Get telemetry by date range",
            description = "Returns telemetry records recorded between the supplied start and end timestamps."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Telemetry records retrieved successfully"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid date range or pagination parameters"
            )
    })
    @GetMapping("/date-range")
    @PreAuthorize("hasAnyRole('ADMIN', 'ENGINEER', 'OPERATOR', 'MANAGER')")
    public ResponseEntity<PageResponse<Telemetry>>
    getByDateRange(

            @Parameter(
                    description = "Start timestamp",
                    example = "2026-09-01T00:00:00"
            )
            @RequestParam LocalDateTime start,

            @Parameter(
                    description = "End timestamp",
                    example = "2026-09-01T23:59:59"
            )
            @RequestParam LocalDateTime end,

            @Parameter(
                    description = "Page number starting from 0",
                    example = "0"
            )
            @RequestParam(defaultValue = "0") int page,

            @Parameter(
                    description = "Number of records per page",
                    example = "10"
            )
            @RequestParam(defaultValue = "10") int size,

            @Parameter(
                    description = "Field used for sorting",
                    example = "timestamp"
            )
            @RequestParam(defaultValue = "timestamp") String sortBy,

            @Parameter(
                    description = "Sorting direction: asc or desc",
                    example = "desc"
            )
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

    @Operation(
            summary = "Get latest telemetry for a machine",
            description = "Retrieves the most recent telemetry reading recorded for a specific machine."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Latest telemetry retrieved successfully"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "No telemetry found for the machine"
            )
    })
    @GetMapping("/machine/{machineId}/latest")
    @PreAuthorize("hasAnyRole('ADMIN', 'ENGINEER', 'OPERATOR', 'MANAGER')")
    public ResponseEntity<?> getLatestByMachine(

            @Parameter(
                    description = "Unique ID of the machine",
                    example = "1"
            )
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