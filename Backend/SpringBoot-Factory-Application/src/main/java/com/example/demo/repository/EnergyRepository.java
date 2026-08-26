package com.example.demo.repository;

import com.example.demo.entity.Energy;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EnergyRepository extends JpaRepository<Energy, Long> {

    List<Energy> findByMachineId(Long machineId);
}