package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Telemetry;

public interface TelemetryRepository extends JpaRepository<Telemetry, Long> {

    List<Telemetry> findByMachineId(Long machineId);

    List<Telemetry> findTop10ByMachineIdOrderByTimestampDesc(Long machineId);
}