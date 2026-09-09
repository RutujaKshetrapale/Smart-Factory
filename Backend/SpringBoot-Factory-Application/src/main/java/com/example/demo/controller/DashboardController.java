package com.example.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.DashboardMachineStatusResponse;
import com.example.demo.dto.DashboardSummaryResponse;
import com.example.demo.service.DashboardService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/dashboard")
@Tag(
        name = "Dashboard",
        description = "APIs for retrieving Smart Factory dashboard information"
)
public class DashboardController {

    private final DashboardService dashboardService;

    public DashboardController(
            DashboardService dashboardService) {

        this.dashboardService = dashboardService;
    }

    @Operation(
            summary = "Get dashboard summary",
            description = "Returns overall Smart Factory statistics including plants, machines, sensors, alerts, production and energy consumption."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Dashboard summary retrieved successfully"
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Authentication is required"
            )
    })
    @GetMapping("/summary")
    public DashboardSummaryResponse getSummary() {

        return dashboardService.getSummary();
    }

    @Operation(
            summary = "Get machine status summary",
            description = "Returns the number of active, inactive and maintenance machines."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Machine status summary retrieved successfully"
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Authentication is required"
            )
    })
    @GetMapping("/machine-status")
    public DashboardMachineStatusResponse getMachineStatus() {

        return dashboardService.getMachineStatus();
    }
}