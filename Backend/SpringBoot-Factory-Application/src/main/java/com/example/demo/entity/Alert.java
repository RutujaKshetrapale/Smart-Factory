package com.example.demo.entity;

import java.time.LocalDateTime;

import jakarta.persistence.*;

@Entity
@Table(
    name = "alerts",
    indexes = {
        @Index(
            name = "idx_alert_machine",
            columnList = "machine_id"
        ),
        @Index(
            name = "idx_alert_severity",
            columnList = "severity"
        ),
        @Index(
            name = "idx_alert_resolved",
            columnList = "resolved"
        ),
        @Index(
            name = "idx_alert_created_at",
            columnList = "created_at"
        )
    }
)
public class Alert {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(
        nullable = false,
        length = 50
    )
    private String type;

    @Column(
        nullable = false,
        length = 30
    )
    private String severity;

    @Column(
        nullable = false,
        length = 500
    )
    private String message;

    @Column(
        nullable = false
    )
    private boolean resolved = false;

    @Column(
        name = "created_at",
        nullable = false
    )
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
        name = "machine_id",
        nullable = false,
        foreignKey = @ForeignKey(
            name = "fk_alert_machine"
        )
    )
    private Machine machine;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
        name = "telemetry_id",
        foreignKey = @ForeignKey(
            name = "fk_alert_telemetry"
        )
    )
    private Telemetry telemetry;

    public Alert() {
    }

    public Long getId() {
        return id;
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Machine getMachine() {
        return machine;
    }

    public void setMachine(Machine machine) {
        this.machine = machine;
    }

    public Telemetry getTelemetry() {
        return telemetry;
    }

    public void setTelemetry(Telemetry telemetry) {
        this.telemetry = telemetry;
    }
}