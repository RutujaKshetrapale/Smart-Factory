package com.example.demo.entity;

import jakarta.persistence.*;

@Entity
@Table(
    name = "plants",
    indexes = {
        @Index(name = "idx_plant_location", columnList = "location"),
        @Index(name = "idx_plant_active", columnList = "active")
    }
)
public class Plant {

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
        length = 150
    )
    private String location;

    @Column(
        nullable = false
    )
    private boolean active;

    public Plant() {
    }

    public Plant(
            String name,
            String location,
            boolean active) {

        this.name = name;
        this.location = location;
        this.active = active;
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}