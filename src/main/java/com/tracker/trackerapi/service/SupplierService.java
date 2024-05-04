package com.tracker.trackerapi.service;

import com.tracker.trackerapi.api.model.Supplier;
import com.tracker.trackerapi.api.model.dto.SupplierDto;

import java.util.List;

public interface SupplierService {
    public void createSupplier(Supplier supplier);
    public List<SupplierDto> getSuppliers();
    public Supplier getSupplierByEmail(String email);
}
