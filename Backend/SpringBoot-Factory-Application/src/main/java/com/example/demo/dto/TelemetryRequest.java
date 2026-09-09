package com.example.demo.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

public class TelemetryRequest {

    @NotNull(message = "Temperature is required")
    @DecimalMin(
        value = "-100.0",
        message = "Temperature cannot be below -100°C"
    )
    private Double temperature;

    @NotNull(message = "Vibration is required")
    @DecimalMin(
        value = "0.0",
        message = "Vibration cannot be negative"
    )
    private Double vibration;

    @NotNull(message = "Pressure is required")
    @DecimalMin(
        value = "0.0",
        message = "Pressure cannot be negative"
    )
    private Double pressure;

    @NotNull(message = "RPM is required")
    @DecimalMin(
        value = "0.0",
        message = "RPM cannot be negative"
    )
    private Double rpm;

    @NotNull(message = "Timestamp is required")
    private LocalDateTime timestamp;

    @NotNull(message = "Machine ID is required")
    private Long machineId;

    public TelemetryRequest() {
    }

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

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public Long getMachineId() {
        return machineId;
    }

    public void setMachineId(Long machineId) {
        this.machineId = machineId;
    }
}