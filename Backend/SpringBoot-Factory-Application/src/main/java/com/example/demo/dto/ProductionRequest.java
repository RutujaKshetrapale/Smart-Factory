package com.example.demo.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class ProductionRequest {

    @NotBlank(message = "Product name is required")
    @Size(
        min = 2,
        max = 100,
        message = "Product name must be between 2 and 100 characters"
    )
    private String productName;

    @NotNull(message = "Quantity produced is required")
    @Min(
        value = 0,
        message = "Quantity produced cannot be negative"
    )
    private Integer quantityProduced;

    @NotNull(message = "Quantity rejected is required")
    @Min(
        value = 0,
        message = "Quantity rejected cannot be negative"
    )
    private Integer quantityRejected;

    @NotNull(message = "Production start time is required")
    private LocalDateTime productionStart;

    private LocalDateTime productionEnd;

    @NotBlank(message = "Production status is required")
    @Size(
        min = 2,
        max = 30,
        message = "Production status cannot exceed 30 characters"
    )
    private String status;

    @NotNull(message = "Machine ID is required")
    private Long machineId;

    public ProductionRequest() {
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Integer getQuantityProduced() {
        return quantityProduced;
    }

    public void setQuantityProduced(Integer quantityProduced) {
        this.quantityProduced = quantityProduced;
    }

    public Integer getQuantityRejected() {
        return quantityRejected;
    }

    public void setQuantityRejected(Integer quantityRejected) {
        this.quantityRejected = quantityRejected;
    }

    public LocalDateTime getProductionStart() {
        return productionStart;
    }

    public void setProductionStart(LocalDateTime productionStart) {
        this.productionStart = productionStart;
    }

    public LocalDateTime getProductionEnd() {
        return productionEnd;
    }

    public void setProductionEnd(LocalDateTime productionEnd) {
        this.productionEnd = productionEnd;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getMachineId() {
        return machineId;
    }

    public void setMachineId(Long machineId) {
        this.machineId = machineId;
    }
}