package com.tracker.trackerapi.api.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tracker.trackerapi.api.model.Order.*;
import com.tracker.trackerapi.api.model.Status;
import com.tracker.trackerapi.service.LotService;

import java.util.Date;
import java.util.List;

public class OrderDto {
    @JsonProperty("id")
    private Long id;
    @JsonProperty("distributer")
    private DistributerDto distributer;
    @JsonProperty("owner")
    private SupplierDto owner;
    @JsonProperty("status")
    private Status status;
    @JsonProperty("lots")
    private List<LotDto> lots;
    @JsonProperty("inBlockchain")
    private boolean inBlockchain;


    public OrderDto() {
    }

    public OrderDto(DistributerDto distributer, SupplierDto owner, Status status) {
        this.distributer = distributer;
        this.owner = owner;
        this.status = status;
    }


    public OrderDto(Long id, DistributerDto distributer, SupplierDto owner, Status status, boolean inBlockchain) {
        this.id = id;
        this.distributer = distributer;
        this.owner = owner;
        this.status = status;
        this.inBlockchain = inBlockchain;
    }


    @JsonProperty("id")
    public Long getId() {
        return id;
    }
    @JsonProperty("id")
    public void setId(Long id) {
        this.id = id;
    }

    @JsonProperty("distributer")
    public DistributerDto getDistributer() {
        return distributer;
    }
    @JsonProperty("distributer")
    public void setDistributer(DistributerDto distributer) {
        this.distributer = distributer;
    }
    @JsonProperty("owner")
    public SupplierDto getOwner() {
        return owner;
    }
    @JsonProperty("owner")
    public void setOwner(SupplierDto owner) {
        this.owner = owner;
    }
    @JsonProperty("status")
    public Status getStatus() {
        return status;
    }
    @JsonProperty("status")
    public void setStatus(Status status) {
        this.status = status;
    }
    @JsonProperty("lots")
    public List<LotDto> getLots() {
        return lots;
    }
    @JsonProperty("lots")
    public void setLots(List<LotDto> lots) {
        this.lots = lots;
    }

    public boolean isInBlockchain() {
        return inBlockchain;
    }

    public void setInBlockchain(boolean inBlockchain) {
        this.inBlockchain = inBlockchain;
    }
}
