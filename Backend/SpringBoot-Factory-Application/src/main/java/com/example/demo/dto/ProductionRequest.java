package com.example.demo.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public class ProductionRequest {

    @NotBlank
    private String productName;

    @NotNull
    @Min(0)
    private Integer quantityProduced;

    @NotNull
    @Min(0)
    private Integer quantityRejected;

    @NotNull
    private LocalDateTime productionStart;

    private LocalDateTime productionEnd;

    @NotBlank
    private String status;

    @NotNull
    private Long machineId;

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