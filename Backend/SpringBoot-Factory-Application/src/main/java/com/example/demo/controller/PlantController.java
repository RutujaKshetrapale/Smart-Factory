package com.example.demo.controller;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.example.demo.dto.PageResponse;
import com.example.demo.dto.PlantRequest;
import com.example.demo.entity.Plant;
import com.example.demo.exception.BusinessValidationException;
import com.example.demo.service.PlantService;
import com.example.demo.util.PaginationUtil;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/plants")
public class PlantController {

    private final PlantService plantService;

    public PlantController(PlantService plantService) {
        this.plantService = plantService;
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'ENGINEER')")
    public ResponseEntity<Plant> create(
            @Valid @RequestBody PlantRequest request) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(plantService.create(request));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'ENGINEER', 'OPERATOR', 'MANAGER')")
    public ResponseEntity<PageResponse<Plant>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction) {

        Pageable pageable = createPageable(
                page, size, sortBy, direction);

        return ResponseEntity.ok(
                PaginationUtil.toResponse(
                        plantService.getAll(pageable)
                )
        );
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'ENGINEER', 'OPERATOR', 'MANAGER')")
    public ResponseEntity<Plant> getById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                plantService.getById(id)
        );
    }

    @GetMapping("/active")
    @PreAuthorize("hasAnyRole('ADMIN', 'ENGINEER', 'OPERATOR', 'MANAGER')")
    public ResponseEntity<PageResponse<Plant>> getActive(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction) {

        Pageable pageable = createPageable(
                page, size, sortBy, direction);

        return ResponseEntity.ok(
                PaginationUtil.toResponse(
                        plantService.getActive(pageable)
                )
        );
    }

    @GetMapping("/inactive")
    @PreAuthorize("hasAnyRole('ADMIN', 'ENGINEER', 'OPERATOR', 'MANAGER')")
    public ResponseEntity<PageResponse<Plant>> getInactive(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction) {

        Pageable pageable = createPageable(
                page, size, sortBy, direction);

        return ResponseEntity.ok(
                PaginationUtil.toResponse(
                        plantService.getInactive(pageable)
                )
        );
    }

    @GetMapping("/search/name")
    @PreAuthorize("hasAnyRole('ADMIN', 'ENGINEER', 'OPERATOR', 'MANAGER')")
    public ResponseEntity<PageResponse<Plant>> searchByName(
            @RequestParam String name,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "name") String sortBy,
            @RequestParam(defaultValue = "asc") String direction) {

        Pageable pageable = createPageable(
                page, size, sortBy, direction);

        return ResponseEntity.ok(
                PaginationUtil.toResponse(
                        plantService.searchByName(
                                name,
                                pageable
                        )
                )
        );
    }

    @GetMapping("/search/location")
    @PreAuthorize("hasAnyRole('ADMIN', 'ENGINEER', 'OPERATOR', 'MANAGER')")
    public ResponseEntity<PageResponse<Plant>> searchByLocation(
            @RequestParam String location,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "location") String sortBy,
            @RequestParam(defaultValue = "asc") String direction) {

        Pageable pageable = createPageable(
                page, size, sortBy, direction);

        return ResponseEntity.ok(
                PaginationUtil.toResponse(
                        plantService.searchByLocation(
                                location,
                                pageable
                        )
                )
        );
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'ENGINEER')")
    public ResponseEntity<Plant> update(
            @PathVariable Long id,
            @Valid @RequestBody PlantRequest request) {

        return ResponseEntity.ok(
                plantService.update(id, request)
        );
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(
            @PathVariable Long id) {

        plantService.delete(id);

        return ResponseEntity.noContent().build();
    }

    private Pageable createPageable(
            int page,
            int size,
            String sortBy,
            String direction) {

        validatePagination(page, size);

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

    private void validatePagination(
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