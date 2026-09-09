package com.example.demo.entity;

import jakarta.persistence.*;

@Entity
@Table(
    name = "machines",
    indexes = {
        @Index(name = "idx_machine_plant", columnList = "plant_id"),
        @Index(name = "idx_machine_status", columnList = "status")
    }
)
public class Machine {

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
        length = 100
    )
    private String type;

    @Column(
        nullable = false,
        length = 30
    )
    private String status;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
        name = "plant_id",
        nullable = false,
        foreignKey = @ForeignKey(
            name = "fk_machine_plant"
        )
    )
    private Plant plant;

    public Machine() {
    }

    public Long getId() {
        return id;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Plant getPlant() {
        return plant;
    }

    public void setPlant(Plant plant) {
        this.plant = plant;
    }
}