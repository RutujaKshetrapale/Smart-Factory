package com.example.demo.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Energy;

public interface EnergyRepository
        extends JpaRepository<Energy, Long> {

    // Existing query
    List<Energy> findByMachineId(Long machineId);

    // Pagination
    Page<Energy> findAll(Pageable pageable);

    Page<Energy> findByMachineId(
            Long machineId,
            Pageable pageable
    );
}