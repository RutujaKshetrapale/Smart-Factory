package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Plant;

public interface PlantRepository extends JpaRepository<Plant, Long> {

    List<Plant> findByActiveTrue();

    List<Plant> findByActiveFalse();

    List<Plant> findByNameContainingIgnoreCase(String name);

    List<Plant> findByLocationContainingIgnoreCase(String location);

    Optional<Plant> findByNameIgnoreCase(String name);

    boolean existsByNameIgnoreCase(String name);

    long countByActiveTrue();

    long countByActiveFalse();

    // Pagination
    Page<Plant> findAll(Pageable pageable);

    Page<Plant> findByActiveTrue(Pageable pageable);

    Page<Plant> findByActiveFalse(Pageable pageable);

    Page<Plant> findByNameContainingIgnoreCase(
            String name,
            Pageable pageable
    );

    Page<Plant> findByLocationContainingIgnoreCase(
            String location,
            Pageable pageable
    );
}