package com.example.demo.service;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.dto.ProductionRequest;
import com.example.demo.entity.Machine;
import com.example.demo.entity.Production;
import com.example.demo.repository.MachineRepository;
import com.example.demo.repository.ProductionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

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

    // CREATE
    public Production create(ProductionRequest request) {

        Machine machine = machineRepository.findById(request.getMachineId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "MACHINE NOT FOUND: " + request.getMachineId()
                        ));

        Production production = new Production();

        production.setProductName(request.getProductName());
        production.setQuantityProduced(request.getQuantityProduced());
        production.setQuantityRejected(request.getQuantityRejected());
        production.setProductionStart(request.getProductionStart());
        production.setProductionEnd(request.getProductionEnd());
        production.setStatus(request.getStatus());
        production.setMachine(machine);

        return productionRepository.save(production);
    }

    // GET ALL
    public List<Production> getAll() {

        return productionRepository.findAll();
    }

    // GET BY ID
    public Production getById(Long id) {

        return productionRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "PRODUCTION NOT FOUND: " + id
                        ));
    }

    // GET BY MACHINE
    public List<Production> getByMachineId(Long machineId) {

        return productionRepository.findByMachineId(machineId);
    }

    // GET BY STATUS
    public List<Production> getByStatus(String status) {

        return productionRepository.findByStatus(status);
    }

    // UPDATE
    public Production update(
            Long id,
            ProductionRequest request) {

        Production production = getById(id);

        Machine machine = machineRepository.findById(request.getMachineId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "MACHINE NOT FOUND: " + request.getMachineId()
                        ));

        production.setProductName(request.getProductName());
        production.setQuantityProduced(request.getQuantityProduced());
        production.setQuantityRejected(request.getQuantityRejected());
        production.setProductionStart(request.getProductionStart());
        production.setProductionEnd(request.getProductionEnd());
        production.setStatus(request.getStatus());
        production.setMachine(machine);

        return productionRepository.save(production);
    }

    // DELETE
    public void delete(Long id) {

        Production production = getById(id);

        productionRepository.delete(production);
    }
}