package com.example.demo.controller;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.example.demo.dto.EnergyRequest;
import com.example.demo.dto.PageResponse;
import com.example.demo.entity.Energy;
import com.example.demo.service.EnergyService;
import com.example.demo.util.PaginationUtil;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/energy")
@Tag(
        name = "Energy Management",
        description = "APIs for managing machine energy consumption records"
)
public class EnergyController {

    private final EnergyService energyService;

    public EnergyController(EnergyService energyService) {
        this.energyService = energyService;
    }

    @Operation(
            summary = "Create energy record",
            description = "Creates a new energy consumption record for a machine."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Energy record created successfully"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid energy data"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Machine not found"
            )
    })
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'ENGINEER')")
    public ResponseEntity<Energy> create(
            @Valid @RequestBody EnergyRequest request) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(energyService.create(request));
    }

    @Operation(
            summary = "Get all energy records",
            description = "Returns all energy consumption records with optional pagination and sorting."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Energy records retrieved successfully"
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
                    energyService.getAll()
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

        PageResponse<Energy> response =
                PaginationUtil.toResponse(
                        energyService.getAll(pageable)
                );

        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Get energy record by ID",
            description = "Retrieves an energy consumption record using its unique ID."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Energy record found successfully"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Energy record not found"
            )
    })
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'ENGINEER', 'OPERATOR', 'MANAGER')")
    public ResponseEntity<Energy> getById(

            @Parameter(
                    description = "Unique ID of the energy record",
                    example = "1"
            )
            @PathVariable Long id) {

        return ResponseEntity.ok(
                energyService.getById(id)
        );
    }

    @Operation(
            summary = "Get energy records by machine",
            description = "Returns energy consumption records associated with a specific machine."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Energy records retrieved successfully"
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
                    energyService.getByMachine(machineId)
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
                        energyService.getByMachine(
                                machineId,
                                pageable
                        )
                )
        );
    }

    @Operation(
            summary = "Update energy record",
            description = "Updates an existing energy consumption record."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Energy record updated successfully"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid energy data"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Energy record not found"
            )
    })
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'ENGINEER')")
    public ResponseEntity<Energy> update(

            @Parameter(
                    description = "Unique ID of the energy record",
                    example = "1"
            )
            @PathVariable Long id,

            @Valid @RequestBody EnergyRequest request) {

        return ResponseEntity.ok(
                energyService.update(
                        id,
                        request
                )
        );
    }

    @Operation(
            summary = "Delete energy record",
            description = "Deletes an energy consumption record using its unique ID."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "Energy record deleted successfully"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Energy record not found"
            )
    })
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(

            @Parameter(
                    description = "Unique ID of the energy record",
                    example = "1"
            )
            @PathVariable Long id) {

        energyService.delete(id);

        return ResponseEntity
                .noContent()
                .build();
    }
}