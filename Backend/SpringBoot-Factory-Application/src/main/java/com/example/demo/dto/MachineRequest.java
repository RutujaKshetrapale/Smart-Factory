package com.example.demo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class MachineRequest {

    @NotBlank(message = "Machine name is required")
    @Size(
        min = 2,
        max = 100,
        message = "Machine name must be between 2 and 100 characters"
    )
    private String name;

    @NotBlank(message = "Machine type is required")
    @Size(
        min = 2,
        max = 100,
        message = "Machine type must be between 2 and 100 characters"
    )
    private String type;

    @NotBlank(message = "Machine status is required")
    @Size(
        min = 2,
        max = 30,
        message = "Machine status cannot exceed 30 characters"
    )
    private String status;

    @NotNull(message = "Plant ID is required")
    private Long plantId;

    public MachineRequest() {
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getPlantId() {
        return plantId;
    }

    public void setPlantId(Long plantId) {
        this.plantId = plantId;
    }
}