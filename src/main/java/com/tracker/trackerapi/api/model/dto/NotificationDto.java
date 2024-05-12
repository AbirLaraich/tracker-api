package com.tracker.trackerapi.api.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.util.Date;

public class NotificationDto {
    @JsonProperty("id")
    private Long id;
    @JsonProperty("distributer")
    private DistributerDto distributer;
    @JsonProperty("supplier")
    private SupplierDto supplier;
    @JsonProperty("order")
    private long order_id;
    @JsonProperty("isRead")
    private boolean isRead;

    @JsonProperty("CreateDate")
    private LocalDateTime CreateDate;
    public NotificationDto() {
    }

    public NotificationDto(Long id, DistributerDto distributer, SupplierDto supplier, long order_id, boolean isRead,LocalDateTime CreateDate) {
        this.id = id;
        this.distributer = distributer;
        this.supplier = supplier;
        this.order_id = order_id;
        this.isRead = isRead;
        this.CreateDate =CreateDate ;
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
    @JsonProperty("supplier")
    public SupplierDto getSupplier() {
        return supplier;
    }
    @JsonProperty("supplier")
    public void setSupplierEmail(SupplierDto supplier) {
        this.supplier = supplier;
    }
    @JsonProperty("order")
    public long getOrder_id() {
        return order_id;
    }
    @JsonProperty("order")
    public void setOrder_id(long order_id) {
        this.order_id = order_id;
    }
    @JsonProperty("isRead")
    public boolean isRead() {
        return isRead;
    }
    @JsonProperty("isRead")
    public void setRead(boolean read) {
        isRead = read;
    }
}
