package com.example.demo.repository;

import com.example.demo.entity.Production;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductionRepository extends JpaRepository<Production, Long> {

    List<Production> findByMachineId(Long machineId);

    List<Production> findByStatus(String status);
}