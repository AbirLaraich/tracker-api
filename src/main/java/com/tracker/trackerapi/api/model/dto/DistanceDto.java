package com.tracker.trackerapi.api.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DistanceDto {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("distributer")
    private DistributerDto distributer;

    @JsonProperty("supplier")
    private SupplierDto supplier;

    public DistanceDto(Long id, DistributerDto distributer, SupplierDto supplier) {
        this.id = id;
        this.distributer = distributer;
        this.supplier = supplier;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DistributerDto getDistributer() {
        return distributer;
    }

    public void setDistributer(DistributerDto distributer) {
        this.distributer = distributer;
    }

    public SupplierDto getSupplier() {
        return supplier;
    }

    public void setSupplier(SupplierDto supplier) {
        this.supplier = supplier;
    }


}
