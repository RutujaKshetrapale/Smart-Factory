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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/sensors")
@Tag(
        name = "Sensor Management",
        description = "APIs for managing factory sensors"
)
public class SensorController {

    private final SensorService sensorService;

    public SensorController(
            SensorService sensorService) {

        this.sensorService = sensorService;
    }

    @Operation(
            summary = "Create a new sensor",
            description = "Creates a new sensor and associates it with a machine."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Sensor created successfully"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid sensor data"
            )
    })
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'ENGINEER')")
    public ResponseEntity<Sensor> create(
            @Valid @RequestBody SensorRequest request) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(sensorService.create(request));
    }

    @Operation(
            summary = "Get all sensors",
            description = "Returns a paginated and sorted list of all sensors."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Sensors retrieved successfully"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid pagination parameters"
            )
    })
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'ENGINEER', 'OPERATOR', 'MANAGER')")
    public ResponseEntity<PageResponse<Sensor>> getAll(

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

    @Operation(
            summary = "Get sensor by ID",
            description = "Retrieves a specific sensor using its unique ID."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Sensor found successfully"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Sensor not found"
            )
    })
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'ENGINEER', 'OPERATOR', 'MANAGER')")
    public ResponseEntity<Sensor> getById(

            @Parameter(
                    description = "Unique ID of the sensor",
                    example = "1"
            )
            @PathVariable Long id) {

        return ResponseEntity.ok(
                sensorService.getById(id)
        );
    }

    @Operation(
            summary = "Get sensors by machine",
            description = "Returns all sensors associated with a specific machine."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Sensors retrieved successfully"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Machine not found"
            )
    })
    @GetMapping("/machine/{machineId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'ENGINEER', 'OPERATOR', 'MANAGER')")
    public ResponseEntity<PageResponse<Sensor>> getByMachine(

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

    @Operation(
            summary = "Get active sensors",
            description = "Returns all currently active sensors."
    )
    @GetMapping("/active")
    @PreAuthorize("hasAnyRole('ADMIN', 'ENGINEER', 'OPERATOR', 'MANAGER')")
    public ResponseEntity<PageResponse<Sensor>> getActive(

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

    @Operation(
            summary = "Get inactive sensors",
            description = "Returns all currently inactive sensors."
    )
    @GetMapping("/inactive")
    @PreAuthorize("hasAnyRole('ADMIN', 'ENGINEER', 'OPERATOR', 'MANAGER')")
    public ResponseEntity<PageResponse<Sensor>> getInactive(

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

    @Operation(
            summary = "Get sensors by type",
            description = "Returns sensors filtered by their sensor type."
    )
    @GetMapping("/type/{type}")
    @PreAuthorize("hasAnyRole('ADMIN', 'ENGINEER', 'OPERATOR', 'MANAGER')")
    public ResponseEntity<PageResponse<Sensor>> getByType(

            @Parameter(
                    description = "Sensor type",
                    example = "TEMPERATURE"
            )
            @PathVariable String type,

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

    @Operation(
            summary = "Get sensors by machine and type",
            description = "Returns sensors belonging to a machine and matching the specified type."
    )
    @GetMapping("/machine/{machineId}/type/{type}")
    @PreAuthorize("hasAnyRole('ADMIN', 'ENGINEER', 'OPERATOR', 'MANAGER')")
    public ResponseEntity<PageResponse<Sensor>>
    getByMachineAndType(

            @Parameter(
                    description = "Unique ID of the machine",
                    example = "1"
            )
            @PathVariable Long machineId,

            @Parameter(
                    description = "Sensor type",
                    example = "TEMPERATURE"
            )
            @PathVariable String type,

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

    @Operation(
            summary = "Search sensors by name",
            description = "Searches sensors using a case-insensitive partial name match."
    )
    @GetMapping("/search/name")
    @PreAuthorize("hasAnyRole('ADMIN', 'ENGINEER', 'OPERATOR', 'MANAGER')")
    public ResponseEntity<PageResponse<Sensor>> searchByName(

            @Parameter(
                    description = "Sensor name or part of the name",
                    example = "Temperature"
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
                    description = "Sorting direction: asc or desc",
                    example = "asc"
            )
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

    @Operation(
            summary = "Update a sensor",
            description = "Updates the details of an existing sensor."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Sensor updated successfully"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid sensor data"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Sensor not found"
            )
    })
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'ENGINEER')")
    public ResponseEntity<Sensor> update(

            @Parameter(
                    description = "Unique ID of the sensor",
                    example = "1"
            )
            @PathVariable Long id,

            @Valid @RequestBody SensorRequest request) {

        return ResponseEntity.ok(
                sensorService.update(id, request)
        );
    }

    @Operation(
            summary = "Delete a sensor",
            description = "Deletes an existing sensor from the Smart Factory system."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "Sensor deleted successfully"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Sensor not found"
            )
    })
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(

            @Parameter(
                    description = "Unique ID of the sensor",
                    example = "1"
            )
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