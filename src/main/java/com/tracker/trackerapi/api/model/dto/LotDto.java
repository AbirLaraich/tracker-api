package com.tracker.trackerapi.api.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.aspectj.weaver.ast.Or;

import java.util.Date;

public class LotDto {

    @JsonProperty("id")
    private Long id;
    @JsonProperty("numLot")
    private String numLot;
    @JsonProperty("name")
    private String name;
    @JsonProperty("supplier")
    private SupplierDto supplier;
    @JsonProperty("distributer")
    private DistributerDto distributer;
    @JsonProperty("creation_date")
    private Date creation_date;
    @JsonProperty("order")
    private Long order;

    public LotDto(String numLot, String name, SupplierDto supplier, DistributerDto distributer, Date creation_date, Long order) {
        this.numLot = numLot;
        this.name = name;
        this.supplier = supplier;
        this.distributer = distributer;
        this.creation_date = creation_date;
        this.order = order;
    }

    public LotDto(String numLot, String name, SupplierDto supplier, DistributerDto distributer, Date creation_date) {
        this.numLot = numLot;
        this.name = name;
        this.supplier = supplier;
        this.distributer = distributer;
        this.creation_date = creation_date;
    }
    public LotDto(String numLot, String name,Date creation_date){
        this.numLot = numLot;
        this.name = name;
        this.creation_date = creation_date;

    }

    public LotDto() {
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getNumLot() {
        return numLot;
    }

    public void setNumLot(String numLot) {
        this.numLot = numLot;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SupplierDto getSupplier() {
        return supplier;
    }

    public void setSupplier(SupplierDto supplier) {
        this.supplier = supplier;
    }

    public DistributerDto getDistributer() {
        return distributer;
    }

    public void setDistributer(DistributerDto distributer) {
        this.distributer = distributer;
    }

    public Date getCreation_date() {
        return creation_date;
    }

    public void setCreation_date(Date creation_date) {
        this.creation_date = creation_date;
    }

    public Long getOrder() {
        return order;
    }

    public void setOrder(Long order) {
        this.order = order;
    }

    @Override
    public String toString() {
        return "LotDto{" +
                "numLot='" + numLot + '\'' +
                ", name='" + name + '\'' +
                ", supplier=" + supplier +
                ", distributer=" + distributer +
                ", creation_date=" + creation_date +
                ", order=" + order +
                '}';
    }
}
