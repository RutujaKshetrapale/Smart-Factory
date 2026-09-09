package com.example.demo.service;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.example.demo.exception.BusinessValidationException;

@Service
public class DomainValidationService {

    public void validateMachineStatus(String status) {

        if (status == null || status.isBlank()) {
            throw new BusinessValidationException(
                    "Machine status is required"
            );
        }

        String normalized = status.trim().toUpperCase();

        if (!normalized.equals("RUNNING")
                && !normalized.equals("IDLE")
                && !normalized.equals("STOPPED")
                && !normalized.equals("MAINTENANCE")
                && !normalized.equals("OFFLINE")) {

            throw new BusinessValidationException(
                    "Invalid machine status: " + status
            );
        }
    }

    public void validateProductionStatus(String status) {

        if (status == null || status.isBlank()) {
            throw new BusinessValidationException(
                    "Production status is required"
            );
        }

        String normalized = status.trim().toUpperCase();

        if (!normalized.equals("PLANNED")
                && !normalized.equals("RUNNING")
                && !normalized.equals("COMPLETED")
                && !normalized.equals("CANCELLED")) {

            throw new BusinessValidationException(
                    "Invalid production status: " + status
            );
        }
    }

    public void validateMaintenanceStatus(String status) {

        if (status == null || status.isBlank()) {
            throw new BusinessValidationException(
                    "Maintenance status is required"
            );
        }

        String normalized = status.trim().toUpperCase();

        if (!normalized.equals("SCHEDULED")
                && !normalized.equals("IN_PROGRESS")
                && !normalized.equals("COMPLETED")
                && !normalized.equals("CANCELLED")) {

            throw new BusinessValidationException(
                    "Invalid maintenance status: " + status
            );
        }
    }

    public void validateMaintenanceDates(
            LocalDate scheduledDate,
            LocalDate completedDate) {

        if (scheduledDate == null) {
            throw new BusinessValidationException(
                    "Scheduled date is required"
            );
        }

        if (completedDate != null
                && completedDate.isBefore(scheduledDate)) {

            throw new BusinessValidationException(
                    "Completed date cannot be before scheduled date"
            );
        }
    }

    public void validateProductionDates(
            LocalDateTime start,
            LocalDateTime end) {

        if (start == null) {
            throw new BusinessValidationException(
                    "Production start time is required"
            );
        }

        if (end != null && end.isBefore(start)) {

            throw new BusinessValidationException(
                    "Production end time cannot be before production start time"
            );
        }
    }

    public void validateProductionQuantities(
            Integer produced,
            Integer rejected) {

        if (produced == null || produced < 0) {
            throw new BusinessValidationException(
                    "Quantity produced cannot be negative"
            );
        }

        if (rejected == null || rejected < 0) {
            throw new BusinessValidationException(
                    "Quantity rejected cannot be negative"
            );
        }

        if (rejected > produced) {
            throw new BusinessValidationException(
                    "Quantity rejected cannot exceed quantity produced"
            );
        }
    }

    public void validateTelemetry(
            Double temperature,
            Double vibration,
            Double pressure,
            Double rpm) {

        if (temperature == null
                || vibration == null
                || pressure == null
                || rpm == null) {

            throw new BusinessValidationException(
                    "All telemetry values are required"
            );
        }

        if (vibration < 0) {
            throw new BusinessValidationException(
                    "Vibration cannot be negative"
            );
        }

        if (pressure < 0) {
            throw new BusinessValidationException(
                    "Pressure cannot be negative"
            );
        }

        if (rpm < 0) {
            throw new BusinessValidationException(
                    "RPM cannot be negative"
            );
        }

        if (temperature < -100 || temperature > 200) {
            throw new BusinessValidationException(
                    "Temperature must be between -100°C and 200°C"
            );
        }
    }

    public void validateEnergy(Double energyConsumption) {

        if (energyConsumption == null) {
            throw new BusinessValidationException(
                    "Energy consumption is required"
            );
        }

        if (energyConsumption < 0) {
            throw new BusinessValidationException(
                    "Energy consumption cannot be negative"
            );
        }
    }

    public void validateAlertSeverity(String severity) {

        if (severity == null || severity.isBlank()) {
            throw new BusinessValidationException(
                    "Alert severity is required"
            );
        }

        String normalized = severity.trim().toUpperCase();

        if (!normalized.equals("LOW")
                && !normalized.equals("MEDIUM")
                && !normalized.equals("HIGH")
                && !normalized.equals("CRITICAL")) {

            throw new BusinessValidationException(
                    "Invalid alert severity: " + severity
            );
        }
    }
}