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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/machines")
@Tag(
        name = "Machine Management",
        description = "APIs for managing factory machines"
)
public class MachineController {

    private final MachineService machineService;

    public MachineController(
            MachineService machineService) {

        this.machineService = machineService;
    }

    @Operation(
            summary = "Create a new machine",
            description = "Creates a new machine and associates it with a plant."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Machine created successfully"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid machine data"
            )
    })
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'ENGINEER')")
    public ResponseEntity<Machine> create(
            @Valid @RequestBody MachineRequest request) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(machineService.create(request));
    }

    @Operation(
            summary = "Get all machines",
            description = "Returns a paginated and sorted list of all machines."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Machines retrieved successfully"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid pagination parameters"
            )
    })
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'ENGINEER', 'OPERATOR', 'MANAGER')")
    public ResponseEntity<PageResponse<Machine>> getAll(

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
                    example = "name"
            )
            @RequestParam(defaultValue = "id") String sortBy,

            @Parameter(
                    description = "Sorting direction: asc or desc",
                    example = "asc"
            )
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

    @Operation(
            summary = "Get machine by ID",
            description = "Retrieves a specific machine using its unique ID."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Machine found successfully"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Machine not found"
            )
    })
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'ENGINEER', 'OPERATOR', 'MANAGER')")
    public ResponseEntity<Machine> getById(

            @Parameter(
                    description = "Unique ID of the machine",
                    example = "1"
            )
            @PathVariable Long id) {

        return ResponseEntity.ok(
                machineService.getById(id)
        );
    }

    @Operation(
            summary = "Get machines by plant",
            description = "Returns all machines belonging to a specific plant."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Machines retrieved successfully"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Plant not found"
            )
    })
    @GetMapping("/plant/{plantId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'ENGINEER', 'OPERATOR', 'MANAGER')")
    public ResponseEntity<PageResponse<Machine>> getByPlant(

            @Parameter(
                    description = "Unique ID of the plant",
                    example = "1"
            )
            @PathVariable Long plantId,

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
                    example = "name"
            )
            @RequestParam(defaultValue = "id") String sortBy,

            @Parameter(
                    description = "Sorting direction",
                    example = "asc"
            )
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

    @Operation(
            summary = "Get machines by status",
            description = "Returns machines filtered by their current status."
    )
    @GetMapping("/status/{status}")
    @PreAuthorize("hasAnyRole('ADMIN', 'ENGINEER', 'OPERATOR', 'MANAGER')")
    public ResponseEntity<PageResponse<Machine>> getByStatus(

            @Parameter(
                    description = "Machine status",
                    example = "ACTIVE"
            )
            @PathVariable String status,

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
                    example = "name"
            )
            @RequestParam(defaultValue = "id") String sortBy,

            @Parameter(
                    description = "Sorting direction",
                    example = "asc"
            )
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

    @Operation(
            summary = "Get machines by plant and status",
            description = "Returns machines belonging to a plant and matching the specified status."
    )
    @GetMapping("/plant/{plantId}/status/{status}")
    @PreAuthorize("hasAnyRole('ADMIN', 'ENGINEER', 'OPERATOR', 'MANAGER')")
    public ResponseEntity<PageResponse<Machine>>
    getByPlantAndStatus(

            @Parameter(
                    description = "Unique ID of the plant",
                    example = "1"
            )
            @PathVariable Long plantId,

            @Parameter(
                    description = "Machine status",
                    example = "ACTIVE"
            )
            @PathVariable String status,

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
                    example = "name"
            )
            @RequestParam(defaultValue = "id") String sortBy,

            @Parameter(
                    description = "Sorting direction",
                    example = "asc"
            )
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

    @Operation(
            summary = "Search machines by name",
            description = "Searches machines using a case-insensitive partial name match."
    )
    @GetMapping("/search/name")
    @PreAuthorize("hasAnyRole('ADMIN', 'ENGINEER', 'OPERATOR', 'MANAGER')")
    public ResponseEntity<PageResponse<Machine>> searchByName(

            @Parameter(
                    description = "Machine name or part of the name",
                    example = "CNC"
            )
            @RequestParam String name,

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
                    example = "name"
            )
            @RequestParam(defaultValue = "name") String sortBy,

            @Parameter(
                    description = "Sorting direction",
                    example = "asc"
            )
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

    @Operation(
            summary = "Update a machine",
            description = "Updates the details of an existing machine."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Machine updated successfully"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid machine data"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Machine not found"
            )
    })
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'ENGINEER')")
    public ResponseEntity<Machine> update(

            @Parameter(
                    description = "Unique ID of the machine",
                    example = "1"
            )
            @PathVariable Long id,

            @Valid @RequestBody MachineRequest request) {

        return ResponseEntity.ok(
                machineService.update(id, request)
        );
    }

    @Operation(
            summary = "Delete a machine",
            description = "Deletes an existing machine from the Smart Factory system."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "Machine deleted successfully"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Machine not found"
            )
    })
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(

            @Parameter(
                    description = "Unique ID of the machine",
                    example = "1"
            )
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