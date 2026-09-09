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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/maintenance")
@Tag(
        name = "Maintenance Management",
        description = "APIs for managing machine maintenance records"
)
public class MaintenanceController {

    private final MaintenanceService maintenanceService;

    public MaintenanceController(
            MaintenanceService maintenanceService) {

        this.maintenanceService = maintenanceService;
    }

    @Operation(
            summary = "Create maintenance record",
            description = "Creates a new maintenance record for a machine."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Maintenance record created successfully"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid maintenance data"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Machine not found"
            )
    })
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'ENGINEER')")
    public ResponseEntity<Maintenance> create(
            @Valid @RequestBody MaintenanceRequest request) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(maintenanceService.create(request));
    }

    @Operation(
            summary = "Get all maintenance records",
            description = "Returns all maintenance records with optional pagination and sorting."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Maintenance records retrieved successfully"
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
                    description = "Number of records per page",
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
                    maintenanceService.getAll()
            );
        }

        int currentPage = page == null ? 0 : page;
        int pageSize = size == null ? 10 : size;

        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable =
                PageRequest.of(
                        currentPage,
                        pageSize,
                        sort
                );

        PageResponse<Maintenance> response =
                PaginationUtil.toResponse(
                        maintenanceService.getAll(pageable)
                );

        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Get maintenance record by ID",
            description = "Retrieves a maintenance record using its unique ID."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Maintenance record found successfully"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Maintenance record not found"
            )
    })
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'ENGINEER', 'OPERATOR', 'MANAGER')")
    public ResponseEntity<Maintenance> getById(

            @Parameter(
                    description = "Unique ID of the maintenance record",
                    example = "1"
            )
            @PathVariable Long id) {

        return ResponseEntity.ok(
                maintenanceService.getById(id)
        );
    }

    @Operation(
            summary = "Get maintenance records by machine",
            description = "Returns maintenance records associated with a specific machine."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Maintenance records retrieved successfully"
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
                    description = "Number of records per page",
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
                    maintenanceService.getByMachine(machineId)
            );
        }

        int currentPage = page == null ? 0 : page;
        int pageSize = size == null ? 10 : size;

        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable =
                PageRequest.of(
                        currentPage,
                        pageSize,
                        sort
                );

        return ResponseEntity.ok(
                PaginationUtil.toResponse(
                        maintenanceService.getByMachine(
                                machineId,
                                pageable
                        )
                )
        );
    }

    @Operation(
            summary = "Get maintenance records by status",
            description = "Returns maintenance records filtered by their current status."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Maintenance records retrieved successfully"
            )
    })
    @GetMapping("/status/{status}")
    @PreAuthorize("hasAnyRole('ADMIN', 'ENGINEER', 'OPERATOR', 'MANAGER')")
    public ResponseEntity<?> getByStatus(

            @Parameter(
                    description = "Maintenance status",
                    example = "SCHEDULED"
            )
            @PathVariable String status,

            @Parameter(
                    description = "Page number starting from 0",
                    example = "0"
            )
            @RequestParam(required = false) Integer page,

            @Parameter(
                    description = "Number of records per page",
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
                    maintenanceService.getByStatus(status)
            );
        }

        int currentPage = page == null ? 0 : page;
        int pageSize = size == null ? 10 : size;

        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable =
                PageRequest.of(
                        currentPage,
                        pageSize,
                        sort
                );

        return ResponseEntity.ok(
                PaginationUtil.toResponse(
                        maintenanceService.getByStatus(
                                status,
                                pageable
                        )
                )
        );
    }

    @Operation(
            summary = "Get maintenance records by status with date sorting",
            description = "Returns maintenance records filtered by status and sorted using the selected field."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Maintenance records retrieved successfully"
            )
    })
    @GetMapping("/status/{status}/sorted")
    @PreAuthorize("hasAnyRole('ADMIN', 'ENGINEER', 'OPERATOR', 'MANAGER')")
    public ResponseEntity<?> getByStatusSorted(

            @Parameter(
                    description = "Maintenance status",
                    example = "SCHEDULED"
            )
            @PathVariable String status,

            @Parameter(
                    description = "Page number starting from 0",
                    example = "0"
            )
            @RequestParam(required = false) Integer page,

            @Parameter(
                    description = "Number of records per page",
                    example = "10"
            )
            @RequestParam(required = false) Integer size,

            @Parameter(
                    description = "Field used for sorting",
                    example = "scheduledDate"
            )
            @RequestParam(defaultValue = "scheduledDate") String sortBy,

            @Parameter(
                    description = "Sorting direction: asc or desc",
                    example = "asc"
            )
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
                PageRequest.of(
                        currentPage,
                        pageSize,
                        sort
                );

        return ResponseEntity.ok(
                PaginationUtil.toResponse(
                        maintenanceService.getByStatusOrderByDate(
                                status,
                                pageable
                        )
                )
        );
    }

    @Operation(
            summary = "Update maintenance record",
            description = "Updates an existing maintenance record."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Maintenance record updated successfully"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid maintenance data"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Maintenance record not found"
            )
    })
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'ENGINEER')")
    public ResponseEntity<Maintenance> update(

            @Parameter(
                    description = "Unique ID of the maintenance record",
                    example = "1"
            )
            @PathVariable Long id,

            @Valid @RequestBody MaintenanceRequest request) {

        return ResponseEntity.ok(
                maintenanceService.update(
                        id,
                        request
                )
        );
    }

    @Operation(
            summary = "Delete maintenance record",
            description = "Deletes a maintenance record using its unique ID."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "Maintenance record deleted successfully"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Maintenance record not found"
            )
    })
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(

            @Parameter(
                    description = "Unique ID of the maintenance record",
                    example = "1"
            )
            @PathVariable Long id) {

        maintenanceService.delete(id);

        return ResponseEntity
                .noContent()
                .build();
    }
}