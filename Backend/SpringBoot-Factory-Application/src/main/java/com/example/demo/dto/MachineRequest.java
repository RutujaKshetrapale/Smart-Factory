package com.example.demo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class MachineRequest {

    @NotBlank
    private String name;

    @NotBlank
    private String type;

    @NotBlank
    private String status;

    @NotNull
    private Long plantId;

    public MachineRequest() {
    }

    public MachineRequest(String name, String type, String status, Long plantId) {
        this.name = name;
        this.type = type;
        this.status = status;
        this.plantId = plantId;
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