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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/alerts")
@Tag(
        name = "Alert Management",
        description = "APIs for monitoring and managing machine alerts"
)
public class AlertController {

    private final AlertService alertService;

    public AlertController(AlertService alertService) {
        this.alertService = alertService;
    }

    @Operation(
            summary = "Create a new alert",
            description = "Creates a new alert associated with a machine."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Alert created successfully"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid alert data"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Machine or telemetry record not found"
            )
    })
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'ENGINEER')")
    public ResponseEntity<Alert> create(
            @Valid @RequestBody AlertRequest request) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(alertService.create(request));
    }

    @Operation(
            summary = "Get all alerts",
            description = "Returns all alerts. If pagination parameters are supplied, returns a paginated and sorted response."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Alerts retrieved successfully"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid pagination parameters"
            )
    })
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'ENGINEER', 'OPERATOR', 'MANAGER')")
    public ResponseEntity<?> getAll(

            @Parameter(
                    description = "Page number starting from 0",
                    example = "0"
            )
            @RequestParam(required = false) Integer page,

            @Parameter(
                    description = "Number of alerts per page",
                    example = "10"
            )
            @RequestParam(required = false) Integer size,

            @Parameter(
                    description = "Field used for sorting",
                    example = "id"
            )
            @RequestParam(defaultValue = "id") String sortBy,

            @Parameter(
                    description = "Sorting direction: asc or desc",
                    example = "asc"
            )
            @RequestParam(defaultValue = "asc") String direction) {

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

    @Operation(
            summary = "Get alert by ID",
            description = "Retrieves a specific alert using its unique ID."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Alert found successfully"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Alert not found"
            )
    })
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'ENGINEER', 'OPERATOR', 'MANAGER')")
    public ResponseEntity<Alert> getById(

            @Parameter(
                    description = "Unique ID of the alert",
                    example = "1"
            )
            @PathVariable Long id) {

        return ResponseEntity.ok(
                alertService.getById(id)
        );
    }

    @Operation(
            summary = "Get alerts by machine",
            description = "Returns alerts generated for a specific machine."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Alerts retrieved successfully"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Machine not found"
            )
    })
    @GetMapping("/machine/{machineId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'ENGINEER', 'OPERATOR', 'MANAGER')")
    public ResponseEntity<?> getByMachine(

            @Parameter(
                    description = "Unique ID of the machine",
                    example = "1"
            )
            @PathVariable Long machineId,

            @Parameter(
                    description = "Page number starting from 0",
                    example = "0"
            )
            @RequestParam(required = false) Integer page,

            @Parameter(
                    description = "Number of alerts per page",
                    example = "10"
            )
            @RequestParam(required = false) Integer size,

            @Parameter(
                    description = "Field used for sorting",
                    example = "id"
            )
            @RequestParam(defaultValue = "id") String sortBy,

            @Parameter(
                    description = "Sorting direction: asc or desc",
                    example = "asc"
            )
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

    @Operation(
            summary = "Get unresolved alerts",
            description = "Returns alerts that have not yet been resolved."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Unresolved alerts retrieved successfully"
            )
    })
    @GetMapping("/unresolved")
    @PreAuthorize("hasAnyRole('ADMIN', 'ENGINEER', 'OPERATOR', 'MANAGER')")
    public ResponseEntity<?> getUnresolved(

            @Parameter(
                    description = "Page number starting from 0",
                    example = "0"
            )
            @RequestParam(required = false) Integer page,

            @Parameter(
                    description = "Number of alerts per page",
                    example = "10"
            )
            @RequestParam(required = false) Integer size,

            @Parameter(
                    description = "Field used for sorting",
                    example = "id"
            )
            @RequestParam(defaultValue = "id") String sortBy,

            @Parameter(
                    description = "Sorting direction: asc or desc",
                    example = "asc"
            )
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

    @Operation(
            summary = "Get alerts by severity",
            description = "Returns alerts filtered by severity level."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Alerts retrieved successfully"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid severity value"
            )
    })
    @GetMapping("/severity/{severity}")
    @PreAuthorize("hasAnyRole('ADMIN', 'ENGINEER', 'OPERATOR', 'MANAGER')")
    public ResponseEntity<?> getBySeverity(

            @Parameter(
                    description = "Alert severity level",
                    example = "HIGH"
            )
            @PathVariable String severity,

            @Parameter(
                    description = "Page number starting from 0",
                    example = "0"
            )
            @RequestParam(required = false) Integer page,

            @Parameter(
                    description = "Number of alerts per page",
                    example = "10"
            )
            @RequestParam(required = false) Integer size,

            @Parameter(
                    description = "Field used for sorting",
                    example = "id"
            )
            @RequestParam(defaultValue = "id") String sortBy,

            @Parameter(
                    description = "Sorting direction: asc or desc",
                    example = "asc"
            )
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

    @Operation(
            summary = "Update an alert",
            description = "Updates an existing alert."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Alert updated successfully"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid alert data"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Alert not found"
            )
    })
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'ENGINEER')")
    public ResponseEntity<Alert> update(

            @Parameter(
                    description = "Unique ID of the alert",
                    example = "1"
            )
            @PathVariable Long id,

            @Valid @RequestBody AlertRequest request) {

        return ResponseEntity.ok(
                alertService.update(id, request)
        );
    }

    @Operation(
            summary = "Delete an alert",
            description = "Deletes an alert using its unique ID."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "Alert deleted successfully"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Alert not found"
            )
    })
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(

            @Parameter(
                    description = "Unique ID of the alert",
                    example = "1"
            )
            @PathVariable Long id) {

        alertService.delete(id);

        return ResponseEntity.noContent().build();
    }
}