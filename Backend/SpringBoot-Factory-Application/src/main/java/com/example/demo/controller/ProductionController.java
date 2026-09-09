package com.example.demo.controller;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.example.demo.dto.PageResponse;
import com.example.demo.dto.ProductionRequest;
import com.example.demo.entity.Production;
import com.example.demo.service.ProductionService;
import com.example.demo.util.PaginationUtil;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/production")
@Tag(
        name = "Production Management",
        description = "APIs for managing factory production records"
)
public class ProductionController {

    private final ProductionService productionService;

    public ProductionController(
            ProductionService productionService) {

        this.productionService = productionService;
    }

    @Operation(
            summary = "Create production record",
            description = "Creates a new production record for a machine."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Production record created successfully"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid production data"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Machine not found"
            )
    })
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'ENGINEER')")
    public ResponseEntity<Production> create(
            @Valid @RequestBody ProductionRequest request) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(productionService.create(request));
    }

    @Operation(
            summary = "Get all production records",
            description = "Returns all production records with optional pagination and sorting."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Production records retrieved successfully"
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
                    productionService.getAll()
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

        PageResponse<Production> response =
                PaginationUtil.toResponse(
                        productionService.getAll(pageable)
                );

        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Get production record by ID",
            description = "Retrieves a production record using its unique ID."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Production record found successfully"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Production record not found"
            )
    })
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'ENGINEER', 'OPERATOR', 'MANAGER')")
    public ResponseEntity<Production> getById(

            @Parameter(
                    description = "Unique ID of the production record",
                    example = "1"
            )
            @PathVariable Long id) {

        return ResponseEntity.ok(
                productionService.getById(id)
        );
    }

    @Operation(
            summary = "Get production records by machine",
            description = "Returns production records associated with a specific machine."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Production records retrieved successfully"
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
                    productionService.getByMachine(machineId)
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
                        productionService.getByMachine(
                                machineId,
                                pageable
                        )
                )
        );
    }

    @Operation(
            summary = "Get production records by status",
            description = "Returns production records filtered by production status."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Production records retrieved successfully"
            )
    })
    @GetMapping("/status/{status}")
    @PreAuthorize("hasAnyRole('ADMIN', 'ENGINEER', 'OPERATOR', 'MANAGER')")
    public ResponseEntity<?> getByStatus(

            @Parameter(
                    description = "Production status",
                    example = "COMPLETED"
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
                    productionService.getByStatus(status)
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
                        productionService.getByStatus(
                                status,
                                pageable
                        )
                )
        );
    }

    @Operation(
            summary = "Update production record",
            description = "Updates an existing production record."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Production record updated successfully"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid production data"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Production record not found"
            )
    })
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'ENGINEER')")
    public ResponseEntity<Production> update(

            @Parameter(
                    description = "Unique ID of the production record",
                    example = "1"
            )
            @PathVariable Long id,

            @Valid @RequestBody ProductionRequest request) {

        return ResponseEntity.ok(
                productionService.update(
                        id,
                        request
                )
        );
    }

    @Operation(
            summary = "Delete production record",
            description = "Deletes a production record using its unique ID."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "Production record deleted successfully"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Production record not found"
            )
    })
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(

            @Parameter(
                    description = "Unique ID of the production record",
                    example = "1"
            )
            @PathVariable Long id) {

        productionService.delete(id);

        return ResponseEntity
                .noContent()
                .build();
    }
}