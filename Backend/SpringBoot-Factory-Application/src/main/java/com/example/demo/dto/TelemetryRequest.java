package com.example.demo.dto;

import jakarta.validation.constraints.NotNull;

public class TelemetryRequest {

    @NotNull
    private Double temperature;

    @NotNull
    private Double vibration;

    @NotNull
    private Double pressure;

    @NotNull
    private Double rpm;

    @NotNull
    private Long machineId;

    public Double getTemperature() {
        return temperature;
    }

    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }

    public Double getVibration() {
        return vibration;
    }

    public void setVibration(Double vibration) {
        this.vibration = vibration;
    }

    public Double getPressure() {
        return pressure;
    }

    public void setPressure(Double pressure) {
        this.pressure = pressure;
    }

    public Double getRpm() {
        return rpm;
    }

    public void setRpm(Double rpm) {
        this.rpm = rpm;
    }

    public Long getMachineId() {
        return machineId;
    }

    public void setMachineId(Long machineId) {
        this.machineId = machineId;
    }
}