package com.example.demo.entity;

import java.time.LocalDateTime;

import jakarta.persistence.*;

@Entity
@Table(
    name = "production",
    indexes = {
        @Index(
            name = "idx_production_machine",
            columnList = "machine_id"
        ),
        @Index(
            name = "idx_production_status",
            columnList = "status"
        ),
        @Index(
            name = "idx_production_start",
            columnList = "production_start"
        )
    }
)
public class Production {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(
        nullable = false,
        length = 100
    )
    private String productName;

    @Column(
        nullable = false
    )
    private Integer quantityProduced;

    @Column(
        nullable = false
    )
    private Integer quantityRejected;

    @Column(
        nullable = false
    )
    private LocalDateTime productionStart;

    @Column(
        name = "production_end"
    )
    private LocalDateTime productionEnd;

    @Column(
        nullable = false,
        length = 30
    )
    private String status;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
        name = "machine_id",
        nullable = false,
        foreignKey = @ForeignKey(
            name = "fk_production_machine"
        )
    )
    private Machine machine;

    public Production() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public Machine getMachine() {
        return machine;
    }

    public void setMachine(Machine machine) {
        this.machine = machine;
    }
}