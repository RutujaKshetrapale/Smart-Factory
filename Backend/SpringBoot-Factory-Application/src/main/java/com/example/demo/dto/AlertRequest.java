package com.example.demo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class AlertRequest {

    @NotNull(message = "Machine ID is required")
    private Long machineId;

    @NotBlank(message = "Alert type is required")
    @Size(
        min = 2,
        max = 50,
        message = "Alert type must be between 2 and 50 characters"
    )
    private String type;

    @NotBlank(message = "Alert severity is required")
    @Size(
        min = 2,
        max = 30,
        message = "Alert severity must be between 2 and 30 characters"
    )
    private String severity;

    @NotBlank(message = "Alert message is required")
    @Size(
        min = 2,
        max = 500,
        message = "Alert message must be between 2 and 500 characters"
    )
    private String message;

    private boolean resolved;

    public AlertRequest() {
    }

    public Long getMachineId() {
        return machineId;
    }

    public void setMachineId(Long machineId) {
        this.machineId = machineId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSeverity() {
        return severity;
    }

    public void setSeverity(String severity) {
        this.severity = severity;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isResolved() {
        return resolved;
    }

    public void setResolved(boolean resolved) {
        this.resolved = resolved;
    }
}