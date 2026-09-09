package com.example.demo.dto;

public class DashboardMachineStatusResponse {

    private long active;
    private long inactive;
    private long maintenance;

    public DashboardMachineStatusResponse() {
    }

    public DashboardMachineStatusResponse(
            long active,
            long inactive,
            long maintenance) {

        this.active = active;
        this.inactive = inactive;
        this.maintenance = maintenance;
    }

    public long getActive() {
        return active;
    }

    public void setActive(long active) {
        this.active = active;
    }

    public long getInactive() {
        return inactive;
    }

    public void setInactive(long inactive) {
        this.inactive = inactive;
    }

    public long getMaintenance() {
        return maintenance;
    }

    public void setMaintenance(long maintenance) {
        this.maintenance = maintenance;
    }
}

