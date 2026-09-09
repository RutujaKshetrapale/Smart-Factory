package com.example.demo.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.*;

@Entity
@Table(
    name = "maintenance",
    indexes = {
        @Index(
            name = "idx_maintenance_machine",
            columnList = "machine_id"
        ),
        @Index(
            name = "idx_maintenance_status",
            columnList = "status"
        ),
        @Index(
            name = "idx_maintenance_scheduled",
            columnList = "scheduled_date"
        )
    }
)
public class Maintenance {

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
        length = 500
    )
    private String description;

    @Column(
        name = "scheduled_date",
        nullable = false
    )
    private LocalDate scheduledDate;

    @Column(
        name = "completed_date"
    )
    private LocalDate completedDate;

    @Column(
        nullable = false,
        length = 30
    )
    private String status;

    @Column(
        nullable = false,
        length = 100
    )
    private String technician;

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
            name = "fk_maintenance_machine"
        )
    )
    private Machine machine;

    public Maintenance() {
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getScheduledDate() {
        return scheduledDate;
    }

    public void setScheduledDate(LocalDate scheduledDate) {
        this.scheduledDate = scheduledDate;
    }

    public LocalDate getCompletedDate() {
        return completedDate;
    }

    public void setCompletedDate(LocalDate completedDate) {
        this.completedDate = completedDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTechnician() {
        return technician;
    }

    public void setTechnician(String technician) {
        this.technician = technician;
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
}