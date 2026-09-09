package com.example.demo.dto;

public class DashboardSummaryResponse {

    private long totalPlants;
    private long totalMachines;
    private long activeMachines;
    private long totalSensors;
    private long unresolvedAlerts;
    private long totalProduction;
    private long rejectedProduction;
    private double totalEnergyConsumption;

    public DashboardSummaryResponse() {
    }

    public DashboardSummaryResponse(
            long totalPlants,
            long totalMachines,
            long activeMachines,
            long totalSensors,
            long unresolvedAlerts,
            long totalProduction,
            long rejectedProduction,
            double totalEnergyConsumption) {

        this.totalPlants = totalPlants;
        this.totalMachines = totalMachines;
        this.activeMachines = activeMachines;
        this.totalSensors = totalSensors;
        this.unresolvedAlerts = unresolvedAlerts;
        this.totalProduction = totalProduction;
        this.rejectedProduction = rejectedProduction;
        this.totalEnergyConsumption = totalEnergyConsumption;
    }

    public long getTotalPlants() {
        return totalPlants;
    }

    public void setTotalPlants(long totalPlants) {
        this.totalPlants = totalPlants;
    }

    public long getTotalMachines() {
        return totalMachines;
    }

    public void setTotalMachines(long totalMachines) {
        this.totalMachines = totalMachines;
    }

    public long getActiveMachines() {
        return activeMachines;
    }

    public void setActiveMachines(long activeMachines) {
        this.activeMachines = activeMachines;
    }

    public long getTotalSensors() {
        return totalSensors;
    }

    public void setTotalSensors(long totalSensors) {
        this.totalSensors = totalSensors;
    }

    public long getUnresolvedAlerts() {
        return unresolvedAlerts;
    }

    public void setUnresolvedAlerts(long unresolvedAlerts) {
        this.unresolvedAlerts = unresolvedAlerts;
    }

    public long getTotalProduction() {
        return totalProduction;
    }

    public void setTotalProduction(long totalProduction) {
        this.totalProduction = totalProduction;
    }

    public long getRejectedProduction() {
        return rejectedProduction;
    }

    public void setRejectedProduction(long rejectedProduction) {
        this.rejectedProduction = rejectedProduction;
    }

    public double getTotalEnergyConsumption() {
        return totalEnergyConsumption;
    }

    public void setTotalEnergyConsumption(double totalEnergyConsumption) {
        this.totalEnergyConsumption = totalEnergyConsumption;
    }
}