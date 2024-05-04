package com.tracker.trackerapi.api.model.mapper;

import com.tracker.trackerapi.api.model.Lot;
import com.tracker.trackerapi.api.model.Supplier;
import com.tracker.trackerapi.api.model.dto.SupplierDto;

import org.springframework.stereotype.Component;

@Component
public class SuppplierMapper {
    public static SupplierDto mapToUserDto(Supplier supplier) {
        SupplierDto supplierDto = new SupplierDto();
        supplierDto.setEmail(supplier.getEmail());
        supplierDto.setPassword(supplier.getPassword());
        supplierDto.setAdresse(supplier.getAdresse());
        return supplierDto;
    }

    public static Supplier mapToUserEntity(SupplierDto supplierDto) {
        Supplier supplier = new Supplier();
        supplier.setEmail(supplierDto.getEmail());
        supplier.setPassword(supplierDto.getPassword());
        supplier.setAdresse(supplierDto.getAdresse());
        return supplier;
    }

    public static SupplierDto mapSupplierDtoByLot(Lot lot){
     return   new SupplierDto(lot.getSupplier().getEmail(),
                lot.getSupplier().getPassword(),
                lot.getSupplier().getAdresse(),
                lot.getSupplier().getName(),
                lot.getSupplier().getSiretNumber());
    }
}
