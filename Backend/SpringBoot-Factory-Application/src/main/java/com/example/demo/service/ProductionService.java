package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.dto.ProductionRequest;
import com.example.demo.entity.Machine;
import com.example.demo.entity.Production;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.MachineRepository;
import com.example.demo.repository.ProductionRepository;

@Service
public class ProductionService {

    private final ProductionRepository productionRepository;

    private final MachineRepository machineRepository;

    public ProductionService(
            ProductionRepository productionRepository,
            MachineRepository machineRepository) {

        this.productionRepository = productionRepository;

        this.machineRepository = machineRepository;
    }

    // =========================
    // CREATE PRODUCTION
    // =========================

    public Production create(ProductionRequest request) {

        Machine machine = machineRepository
                .findById(request.getMachineId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "MACHINE NOT FOUND: "
                                + request.getMachineId()
                        ));

        Production production = new Production();

        production.setMachine(machine);

        production.setProductName(
                request.getProductName()
        );

        production.setQuantityProduced(
                request.getQuantityProduced()
        );

        production.setQuantityRejected(
                request.getQuantityRejected()
        );

        production.setProductionStart(
                request.getProductionStart()
        );

        production.setProductionEnd(
                request.getProductionEnd()
        );

        production.setStatus(
                request.getStatus()
        );

        return productionRepository.save(production);
    }

    // =========================
    // GET ALL PRODUCTIONS
    // =========================

    public List<Production> getAll() {

        return productionRepository.findAll();
    }

    // =========================
    // GET PRODUCTION BY ID
    // =========================

    public Production getById(Long id) {

        return productionRepository
                .findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "PRODUCTION NOT FOUND: " + id
                        ));
    }

    // =========================
    // GET PRODUCTIONS BY MACHINE
    // =========================

    public List<Production> getByMachine(Long machineId) {

        if (!machineRepository.existsById(machineId)) {

            throw new ResourceNotFoundException(
                    "MACHINE NOT FOUND: " + machineId
            );
        }

        return productionRepository
                .findByMachineId(machineId);
    }

    // =========================
    // GET PRODUCTIONS BY STATUS
    // =========================

    public List<Production> getByStatus(String status) {

        return productionRepository
                .findByStatus(status);
    }

    // =========================
    // UPDATE PRODUCTION
    // =========================

    public Production update(
            Long id,
            ProductionRequest request) {

        Production production = productionRepository
                .findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "PRODUCTION NOT FOUND: " + id
                        ));

        Machine machine = machineRepository
                .findById(request.getMachineId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "MACHINE NOT FOUND: "
                                + request.getMachineId()
                        ));

        production.setMachine(machine);

        production.setProductName(
                request.getProductName()
        );

        production.setQuantityProduced(
                request.getQuantityProduced()
        );

        production.setQuantityRejected(
                request.getQuantityRejected()
        );

        production.setProductionStart(
                request.getProductionStart()
        );

        production.setProductionEnd(
                request.getProductionEnd()
        );

        production.setStatus(
                request.getStatus()
        );

        return productionRepository.save(production);
    }

    // =========================
    // DELETE PRODUCTION
    // =========================

    public void delete(Long id) {

        Production production = productionRepository
                .findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "PRODUCTION NOT FOUND: " + id
                        ));

        productionRepository.delete(production);
    }
}