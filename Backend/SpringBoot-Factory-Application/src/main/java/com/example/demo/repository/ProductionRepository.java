package com.example.demo.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Production;

public interface ProductionRepository
        extends JpaRepository<Production, Long> {

    // Existing queries
    List<Production> findByMachineId(Long machineId);

    List<Production> findByStatus(String status);

    // Pagination
    Page<Production> findAll(Pageable pageable);

    Page<Production> findByMachineId(
            Long machineId,
            Pageable pageable
    );

    Page<Production> findByStatus(
            String status,
            Pageable pageable
    );
}