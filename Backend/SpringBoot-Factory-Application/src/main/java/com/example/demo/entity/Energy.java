package com.example.demo.entity;

import java.time.LocalDateTime;

import jakarta.persistence.*;

@Entity
@Table(
    name = "energy",
    indexes = {
        @Index(
            name = "idx_energy_machine",
            columnList = "machine_id"
        ),
        @Index(
            name = "idx_energy_recorded_at",
            columnList = "recorded_at"
        ),
        @Index(
            name = "idx_energy_machine_recorded",
            columnList = "machine_id,recorded_at"
        )
    }
)
public class Energy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(
        name = "energy_consumption",
        nullable = false,
        precision = 12,
        scale = 3
    )
    private Double energyConsumption;

    @Column(
        name = "recorded_at",
        nullable = false
    )
    private LocalDateTime recordedAt;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
        name = "machine_id",
        nullable = false,
        foreignKey = @ForeignKey(
            name = "fk_energy_machine"
        )
    )
    private Machine machine;

    public Energy() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getEnergyConsumption() {
        return energyConsumption;
    }

    public void setEnergyConsumption(
            Double energyConsumption) {

        this.energyConsumption = energyConsumption;
    }

    public LocalDateTime getRecordedAt() {
        return recordedAt;
    }

    public void setRecordedAt(
            LocalDateTime recordedAt) {

        this.recordedAt = recordedAt;
    }

    public Machine getMachine() {
        return machine;
    }

    public void setMachine(Machine machine) {
        this.machine = machine;
    }
}