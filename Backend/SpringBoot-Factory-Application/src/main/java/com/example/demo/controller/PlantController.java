package com.example.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.dto.PlantRequest;
import com.example.demo.entity.Plant;
import com.example.demo.service.PlantService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/plants")
@Tag(
        name = "Plant Management",
        description = "APIs for managing manufacturing plants"
)
public class PlantController {

    private final PlantService plantService;

    public PlantController(PlantService plantService) {
        this.plantService = plantService;
    }

    @Operation(
            summary = "Create a new plant",
            description = "Creates a new manufacturing plant in the Smart Factory system."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Plant created successfully"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid plant data"
            )
    })
    @PostMapping
    public ResponseEntity<Plant> create(
            @Valid @RequestBody PlantRequest request) {

        Plant plant = plantService.create(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(plant);
    }

    @Operation(
            summary = "Get plant by ID",
            description = "Retrieves a manufacturing plant using its unique ID."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Plant found successfully"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Plant not found"
            )
    })
    @GetMapping("/{id}")
    public ResponseEntity<Plant> getById(

            @Parameter(
                    description = "Unique ID of the plant",
                    example = "1"
            )
            @PathVariable Long id) {

        return ResponseEntity.ok(
                plantService.getById(id)
        );
    }
}