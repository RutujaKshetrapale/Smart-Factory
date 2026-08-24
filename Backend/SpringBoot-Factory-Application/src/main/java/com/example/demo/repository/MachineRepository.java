package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Machine;

public interface MachineRepository extends JpaRepository<Machine, Long> {

}