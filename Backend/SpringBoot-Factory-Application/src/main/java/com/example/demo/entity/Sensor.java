package com.example.demo.entity;

import jakarta.persistence.*;

@Entity
@Table(
    name = "sensors",
    indexes = {
        @Index(name = "idx_sensor_machine", columnList = "machine_id"),
        @Index(name = "idx_sensor_active", columnList = "active")
    }
)
public class Sensor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(
        nullable = false,
        length = 100
    )
    private String name;

    @Column(
        nullable = false,
        length = 50
    )
    private String type;

    @Column(
        nullable = false,
        length = 20
    )
    private String unit;

    @Column(
        nullable = false
    )
    private boolean active;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
        name = "machine_id",
        nullable = false,
        foreignKey = @ForeignKey(
            name = "fk_sensor_machine"
        )
    )
    private Machine machine;

    public Sensor() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Machine getMachine() {
        return machine;
    }

    public void setMachine(Machine machine) {
        this.machine = machine;
    }
}