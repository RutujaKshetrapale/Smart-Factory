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

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/production")
public class ProductionController {

    private final ProductionService productionService;

    public ProductionController(
            ProductionService productionService) {

        this.productionService = productionService;
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'ENGINEER')")
    public ResponseEntity<Production> create(
            @Valid @RequestBody ProductionRequest request) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(productionService.create(request));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'ENGINEER', 'OPERATOR', 'MANAGER')")
    public ResponseEntity<?> getAll(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size,
            @RequestParam(defaultValue = "id") String sortBy,
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
                PageRequest.of(currentPage, pageSize, sort);

        PageResponse<Production> response =
                PaginationUtil.toResponse(
                        productionService.getAll(pageable)
                );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'ENGINEER', 'OPERATOR', 'MANAGER')")
    public ResponseEntity<Production> getById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                productionService.getById(id)
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
                    productionService.getByMachine(machineId)
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
                        productionService.getByMachine(
                                machineId,
                                pageable
                        )
                )
        );
    }

    @GetMapping("/status/{status}")
    @PreAuthorize("hasAnyRole('ADMIN', 'ENGINEER', 'OPERATOR', 'MANAGER')")
    public ResponseEntity<?> getByStatus(
            @PathVariable String status,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size,
            @RequestParam(defaultValue = "id") String sortBy,
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
                PageRequest.of(currentPage, pageSize, sort);

        return ResponseEntity.ok(
                PaginationUtil.toResponse(
                        productionService.getByStatus(
                                status,
                                pageable
                        )
                )
        );
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'ENGINEER')")
    public ResponseEntity<Production> update(
            @PathVariable Long id,
            @Valid @RequestBody ProductionRequest request) {

        return ResponseEntity.ok(
                productionService.update(id, request)
        );
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(
            @PathVariable Long id) {

        productionService.delete(id);

        return ResponseEntity.noContent().build();
    }
}
