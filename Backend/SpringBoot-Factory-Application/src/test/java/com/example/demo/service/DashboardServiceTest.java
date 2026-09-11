package com.example.demo.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.demo.dto.DashboardMachineStatusResponse;
import com.example.demo.dto.DashboardSummaryResponse;
import com.example.demo.entity.Alert;
import com.example.demo.entity.Energy;
import com.example.demo.entity.Production;
import com.example.demo.repository.AlertRepository;
import com.example.demo.repository.EnergyRepository;
import com.example.demo.repository.MachineRepository;
import com.example.demo.repository.PlantRepository;
import com.example.demo.repository.ProductionRepository;
import com.example.demo.repository.SensorRepository;

@ExtendWith(MockitoExtension.class)
class DashboardServiceTest {

    @Mock
    private PlantRepository plantRepository;

    @Mock
    private MachineRepository machineRepository;

    @Mock
    private SensorRepository sensorRepository;

    @Mock
    private AlertRepository alertRepository;

    @Mock
    private ProductionRepository productionRepository;

    @Mock
    private EnergyRepository energyRepository;

    @InjectMocks
    private DashboardService dashboardService;

    @BeforeEach
    void setUp() {
    }

    // =========================
    // GET DASHBOARD SUMMARY
    // =========================

    @Test
    @DisplayName("Should get dashboard summary successfully")
    void shouldGetDashboardSummary() {

        PlantRepository plantRepositoryMock = plantRepository;
        MachineRepository machineRepositoryMock = machineRepository;
        SensorRepository sensorRepositoryMock = sensorRepository;
        AlertRepository alertRepositoryMock = alertRepository;
        ProductionRepository productionRepositoryMock = productionRepository;
        EnergyRepository energyRepositoryMock = energyRepository;

        when(plantRepositoryMock.count())
                .thenReturn(5L);

        when(machineRepositoryMock.count())
                .thenReturn(10L);

        when(machineRepositoryMock
                .countByStatusIgnoreCase("ACTIVE"))
                .thenReturn(7L);

        when(sensorRepositoryMock.count())
                .thenReturn(20L);

        Alert unresolvedAlert = new Alert();
        unresolvedAlert.setResolved(false);

        Alert resolvedAlert = new Alert();
        resolvedAlert.setResolved(true);

        when(alertRepositoryMock.findAll())
                .thenReturn(
                        List.of(
                                unresolvedAlert,
                                resolvedAlert
                        )
                );

        Production production1 = new Production();
        production1.setQuantityProduced(100);
        production1.setQuantityRejected(10);

        Production production2 = new Production();
        production2.setQuantityProduced(200);
        production2.setQuantityRejected(20);

        when(productionRepositoryMock.findAll())
                .thenReturn(
                        List.of(
                                production1,
                                production2
                        )
                );

        Energy energy1 = new Energy();
        energy1.setEnergyConsumption(150.5);

        Energy energy2 = new Energy();
        energy2.setEnergyConsumption(249.5);

        when(energyRepositoryMock.findAll())
                .thenReturn(
                        List.of(
                                energy1,
                                energy2
                        )
                );

        // =========================
        // ACT
        // =========================

        DashboardSummaryResponse response =
                dashboardService.getSummary();

        // =========================
        // ASSERT
        // =========================

        assertNotNull(response);

        assertEquals(
                5L,
                response.getTotalPlants()
        );

        assertEquals(
                10L,
                response.getTotalMachines()
        );

        assertEquals(
                7L,
                response.getActiveMachines()
        );

        assertEquals(
                20L,
                response.getTotalSensors()
        );

        assertEquals(
                1L,
                response.getUnresolvedAlerts()
        );

        assertEquals(
                300L,
                response.getTotalProduction()
        );

        assertEquals(
                30L,
                response.getRejectedProduction()
        );

        assertEquals(
                400.0,
                response.getTotalEnergyConsumption()
        );

        // =========================
        // VERIFY
        // =========================

        verify(
                plantRepositoryMock,
                times(1)
        ).count();

        verify(
                machineRepositoryMock,
                times(1)
        ).count();

        verify(
                machineRepositoryMock,
                times(1)
        ).countByStatusIgnoreCase("ACTIVE");

        verify(
                sensorRepositoryMock,
                times(1)
        ).count();

        verify(
                alertRepositoryMock,
                times(1)
        ).findAll();

        verify(
                productionRepositoryMock,
                times(2)
        ).findAll();

        verify(
                energyRepositoryMock,
                times(1)
        ).findAll();
    }

    // =========================
    // GET DASHBOARD SUMMARY
    // WITH NULL PRODUCTION VALUES
    // =========================

    @Test
    @DisplayName("Should handle null production values")
    void shouldHandleNullProductionValues() {

        when(plantRepository.count())
                .thenReturn(2L);

        when(machineRepository.count())
                .thenReturn(4L);

        when(machineRepository
                .countByStatusIgnoreCase("ACTIVE"))
                .thenReturn(2L);

        when(sensorRepository.count())
                .thenReturn(8L);

        when(alertRepository.findAll())
                .thenReturn(List.of());

        Production production =
                new Production();

        production.setQuantityProduced(null);
        production.setQuantityRejected(null);

        when(productionRepository.findAll())
                .thenReturn(
                        List.of(production)
                );

        when(energyRepository.findAll())
                .thenReturn(List.of());

        DashboardSummaryResponse response =
                dashboardService.getSummary();

        assertNotNull(response);

        assertEquals(
                0L,
                response.getTotalProduction()
        );

        assertEquals(
                0L,
                response.getRejectedProduction()
        );

        assertEquals(
                0.0,
                response.getTotalEnergyConsumption()
        );

        verify(
                productionRepository,
                times(2)
        ).findAll();
    }

    // =========================
    // GET DASHBOARD SUMMARY
    // WITH NULL ENERGY VALUE
    // =========================

    @Test
    @DisplayName("Should handle null energy consumption")
    void shouldHandleNullEnergyConsumption() {

        when(plantRepository.count())
                .thenReturn(1L);

        when(machineRepository.count())
                .thenReturn(1L);

        when(machineRepository
                .countByStatusIgnoreCase("ACTIVE"))
                .thenReturn(1L);

        when(sensorRepository.count())
                .thenReturn(1L);

        when(alertRepository.findAll())
                .thenReturn(List.of());

        when(productionRepository.findAll())
                .thenReturn(List.of());

        Energy energy =
                new Energy();

        energy.setEnergyConsumption(null);

        when(energyRepository.findAll())
                .thenReturn(
                        List.of(energy)
                );

        DashboardSummaryResponse response =
                dashboardService.getSummary();

        assertNotNull(response);

        assertEquals(
                0.0,
                response.getTotalEnergyConsumption()
        );

        verify(
                energyRepository,
                times(1)
        ).findAll();
    }

    // =========================
    // GET MACHINE STATUS
    // =========================

    @Test
    @DisplayName("Should get machine status successfully")
    void shouldGetMachineStatus() {

        when(machineRepository
                .countByStatusIgnoreCase("ACTIVE"))
                .thenReturn(5L);

        when(machineRepository
                .countByStatusIgnoreCase("INACTIVE"))
                .thenReturn(3L);

        when(machineRepository
                .countByStatusIgnoreCase("MAINTENANCE"))
                .thenReturn(2L);

        // =========================
        // ACT
        // =========================

        DashboardMachineStatusResponse response =
                dashboardService.getMachineStatus();

        // =========================
        // ASSERT
        // =========================

        assertNotNull(response);

        assertEquals(
                5L,
                response.getActive()
        );

        assertEquals(
                3L,
                response.getInactive()
        );

        assertEquals(
                2L,
                response.getMaintenance()
        );

        // =========================
        // VERIFY
        // =========================

        verify(
                machineRepository,
                times(1)
        ).countByStatusIgnoreCase("ACTIVE");

        verify(
                machineRepository,
                times(1)
        ).countByStatusIgnoreCase("INACTIVE");

        verify(
                machineRepository,
                times(1)
        ).countByStatusIgnoreCase("MAINTENANCE");
    }

    // =========================
    // GET MACHINE STATUS
    // WHEN ALL COUNTS ARE ZERO
    // =========================

    @Test
    @DisplayName("Should return zero machine status counts")
    void shouldReturnZeroMachineStatusCounts() {

        when(machineRepository
                .countByStatusIgnoreCase("ACTIVE"))
                .thenReturn(0L);

        when(machineRepository
                .countByStatusIgnoreCase("INACTIVE"))
                .thenReturn(0L);

        when(machineRepository
                .countByStatusIgnoreCase("MAINTENANCE"))
                .thenReturn(0L);

        DashboardMachineStatusResponse response =
                dashboardService.getMachineStatus();

        assertNotNull(response);

        assertEquals(
                0L,
                response.getActive()
        );

        assertEquals(
                0L,
                response.getInactive()
        );

        assertEquals(
                0L,
                response.getMaintenance()
        );

        verify(
                machineRepository,
                times(1)
        ).countByStatusIgnoreCase("ACTIVE");

        verify(
                machineRepository,
                times(1)
        ).countByStatusIgnoreCase("INACTIVE");

        verify(
                machineRepository,
                times(1)
        ).countByStatusIgnoreCase("MAINTENANCE");
    }
}