package com.example.demo.entity;

import java.time.LocalDateTime;

import jakarta.persistence.*;

@Entity
@Table(name = "telemetry")
public class Telemetry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double temperature;

    private Double vibration;

    private Double pressure;

    private Double rpm;

    private Double energyConsumption;

    private LocalDateTime timestamp;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "machine_id", nullable = false)
    private Machine machine;

    public Telemetry() {
    }

    public Long getId() {
        return id;
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

    public Double getEnergyConsumption() {
        return energyConsumption;
    }

    public void setEnergyConsumption(Double energyConsumption) {
        this.energyConsumption = energyConsumption;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public Machine getMachine() {
        return machine;
    }

    public void setMachine(Machine machine) {
        this.machine = machine;
    }
}