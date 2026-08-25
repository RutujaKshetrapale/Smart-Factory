package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Alert;

public interface AlertRepository extends JpaRepository<Alert, Long> {

    List<Alert> findByMachineId(Long machineId);

    List<Alert> findByResolvedFalse();

    List<Alert> findBySeverity(String severity);
}