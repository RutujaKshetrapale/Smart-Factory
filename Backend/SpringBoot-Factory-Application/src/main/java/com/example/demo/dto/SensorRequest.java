package com.example.demo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class SensorRequest {

    @NotBlank(message = "Sensor name is required")
    @Size(
        min = 2,
        max = 100,
        message = "Sensor name must be between 2 and 100 characters"
    )
    private String name;

    @NotBlank(message = "Sensor type is required")
    @Size(
        min = 2,
        max = 50,
        message = "Sensor type must be between 2 and 50 characters"
    )
    private String type;

    @NotBlank(message = "Sensor unit is required")
    @Size(
        min = 1,
        max = 20,
        message = "Sensor unit must be between 1 and 20 characters"
    )
    private String unit;

    @NotNull(message = "Machine ID is required")
    private Long machineId;

    private Boolean active;

    public SensorRequest() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Long getMachineId() {
        return machineId;
    }

    public void setMachineId(Long machineId) {
        this.machineId = machineId;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}