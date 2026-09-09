package com.example.demo.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Alert;

public interface AlertRepository extends JpaRepository<Alert, Long> {

    // Existing queries
    List<Alert> findByMachineId(Long machineId);

    List<Alert> findByResolvedFalse();

    List<Alert> findBySeverity(String severity);

    // Pagination
    Page<Alert> findAll(Pageable pageable);

    Page<Alert> findByMachineId(
            Long machineId,
            Pageable pageable
    );

    Page<Alert> findByResolvedFalse(
            Pageable pageable
    );

    Page<Alert> findBySeverity(
            String severity,
            Pageable pageable
    );
}