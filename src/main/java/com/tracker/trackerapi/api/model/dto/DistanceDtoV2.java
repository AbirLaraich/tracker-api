package com.tracker.trackerapi.api.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DistanceDtoV2 {

    @JsonProperty("distributer")
    private String distributerAdresse;

    @JsonProperty("supplier")
    private String supplierAdresse;

    public DistanceDtoV2(String distributerAdresse, String supplierAdresse) {
        this.distributerAdresse = distributerAdresse;
        this.supplierAdresse = supplierAdresse;
    }

    public String getDistributerAdresse() {
        return distributerAdresse;
    }

    public void setDistributerAdresse(String distributerAdresse) {
        this.distributerAdresse = distributerAdresse;
    }

    public String getSupplierAdresse() {
        return supplierAdresse;
    }

    public void setSupplierAdresse(String supplierAdresse) {
        this.supplierAdresse = supplierAdresse;
    }
}
