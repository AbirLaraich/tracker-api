package com.tracker.trackerapi.api.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public class ProductDto {

    @JsonProperty("numProduct")
    private int numProduct;

    @JsonProperty("weight")
    private String weight;

    @JsonProperty("numLot")
    private String numLot ;
    @JsonProperty("distributer")
    private DistributerDto distributer;
    @JsonProperty("delivery_date")
    private Date delivery_date;
    @JsonProperty("qrCode")
    private String qrCode;
    public ProductDto(int numProduct, String weight, String numLot, DistributerDto distributer, Date delivery_date) {
        this.numProduct = numProduct;
        this.weight = weight;
        this.numLot = numLot;
        this.distributer = distributer;
        this.delivery_date = delivery_date;
    }

    public ProductDto(int numProduct, String weight, String numLot, DistributerDto distributer, Date delivery_date, String qrCode) {
        this.numProduct = numProduct;
        this.weight = weight;
        this.numLot = numLot;
        this.distributer = distributer;
        this.delivery_date = delivery_date;
        this.qrCode = qrCode;
    }
    public ProductDto() {
    }

    public ProductDto(int numProduct, String weight) {
        this.numProduct = numProduct;
        this.weight = weight;
    }

    @JsonProperty("numProduct")
    public int getNumProduct() {
        return numProduct;
    }

    @JsonProperty("numProduct")
    public void setNumProduct(int numProduct) {
        this.numProduct = numProduct;
    }

    @JsonProperty("weight")
    public String getWeight() {
        return weight;
    }

    @JsonProperty("weight")
    public void setWeight(String weight) {
        this.weight = weight;
    }

    @JsonProperty("numLot")
    public String getNumLot() {
        return numLot;
    }

    @JsonProperty("numLot")
    public void setNumLot(String numLot) {
        this.numLot = numLot;
    }

    @JsonProperty("distributer")
    public DistributerDto getDistributer() {
        return distributer;
    }

    @JsonProperty("distributer")
    public void setDistributer(DistributerDto distributer) {
        this.distributer = distributer;
    }

    @JsonProperty("delivery_date")

    public Date getDelivery_date() {
        return delivery_date;
    }

    @JsonProperty("delivery_date")

    public void setDelivery_date(Date delivery_date) {
        this.delivery_date = delivery_date;
    }

    public String getQrCode() {
        return qrCode;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }

}

