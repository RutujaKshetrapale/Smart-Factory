package com.example.demo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class PlantRequest {

    @NotBlank(message = "Plant name is required")
    @Size(max = 100, message = "Plant name cannot exceed 100 characters")
    private String name;

    @NotBlank(message = "Plant location is required")
    @Size(max = 150, message = "Plant location cannot exceed 150 characters")
    private String location;

    public PlantRequest() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}