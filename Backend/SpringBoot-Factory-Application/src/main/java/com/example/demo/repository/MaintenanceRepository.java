package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Maintenance;

public interface MaintenanceRepository
        extends JpaRepository<Maintenance, Long> {

    List<Maintenance> findByMachineId(Long machineId);

    List<Maintenance> findByStatus(String status);

    List<Maintenance> findByStatusOrderByScheduledDateAsc(String status);
}