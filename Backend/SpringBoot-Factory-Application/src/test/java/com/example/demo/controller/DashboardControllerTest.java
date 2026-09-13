package com.example.demo.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.demo.dto.DashboardMachineStatusResponse;
import com.example.demo.dto.DashboardSummaryResponse;
import com.example.demo.service.DashboardService;

@ExtendWith(MockitoExtension.class)
class DashboardControllerTest {

    @Mock
    private DashboardService dashboardService;

    @InjectMocks
    private DashboardController dashboardController;

    private DashboardSummaryResponse summaryResponse;
    private DashboardMachineStatusResponse machineStatusResponse;

    @BeforeEach
    void setUp() {

        summaryResponse =
                new DashboardSummaryResponse(
                        5,
                        10,
                        7,
                        20,
                        3,
                        1000,
                        50,
                        5000.50
                );

        machineStatusResponse =
                new DashboardMachineStatusResponse(
                        7,
                        2,
                        1
                );
    }

    // =========================
    // GET DASHBOARD SUMMARY
    // =========================

    @Test
    @DisplayName("Should get dashboard summary successfully")
    void shouldGetDashboardSummarySuccessfully() {

        when(dashboardService.getSummary())
                .thenReturn(summaryResponse);

        DashboardSummaryResponse response =
                dashboardController.getSummary();

        assertNotNull(response);
        assertEquals(
                summaryResponse,
                response
        );
        assertEquals(
                5,
                response.getTotalPlants()
        );
        assertEquals(
                10,
                response.getTotalMachines()
        );
        assertEquals(
                7,
                response.getActiveMachines()
        );
        assertEquals(
                20,
                response.getTotalSensors()
        );
        assertEquals(
                3,
                response.getUnresolvedAlerts()
        );
        assertEquals(
                1000,
                response.getTotalProduction()
        );
        assertEquals(
                50,
                response.getRejectedProduction()
        );
        assertEquals(
                5000.50,
                response.getTotalEnergyConsumption()
        );

        verify(dashboardService, times(1))
                .getSummary();
    }

    // =========================
    // GET MACHINE STATUS
    // =========================

    @Test
    @DisplayName("Should get machine status successfully")
    void shouldGetMachineStatusSuccessfully() {

        when(dashboardService.getMachineStatus())
                .thenReturn(machineStatusResponse);

        DashboardMachineStatusResponse response =
                dashboardController.getMachineStatus();

        assertNotNull(response);
        assertEquals(
                machineStatusResponse,
                response
        );
        assertEquals(
                7,
                response.getActive()
        );
        assertEquals(
                2,
                response.getInactive()
        );
        assertEquals(
                1,
                response.getMaintenance()
        );

        verify(dashboardService, times(1))
                .getMachineStatus();
    }
}