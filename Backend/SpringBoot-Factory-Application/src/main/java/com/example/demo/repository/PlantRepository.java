package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Plant;

public interface PlantRepository extends JpaRepository<Plant, Long> {

}