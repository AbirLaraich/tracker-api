package com.tracker.trackerapi.service;

import com.tracker.trackerapi.api.model.Supplier;
import com.tracker.trackerapi.api.model.dto.SupplierDto;
import com.tracker.trackerapi.exeption.HttpErrorException;
import com.tracker.trackerapi.repository.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SupplierServiceImpl implements SupplierService {
    private final SupplierRepository supplierRepository;

    @Autowired
    public SupplierServiceImpl(SupplierRepository supplierRepository) {
        this.supplierRepository = supplierRepository;
    }

    @Override
    public void createSupplier(Supplier supplier) {
        try {
            supplierRepository.save(supplier);
        } catch (Exception e) {
            throw new HttpErrorException("Error occurred while saving the user.", HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }

    @Override
    public List<SupplierDto> getSuppliers() {
        List<Supplier> suppliers = this.supplierRepository.findAll();
        return suppliers.stream()
                .map(supplier -> new SupplierDto
                        (supplier.getEmail(),
                                supplier.getPassword(),
                                supplier.getAdresse(),
                                supplier.getName(),
                                supplier.getSiretNumber()))
                .collect(Collectors.toList());


    }

    @Override
    public Supplier getSupplierByEmail(String email) {
        return this.supplierRepository.findSupplierByEmail(email);
    }
}
