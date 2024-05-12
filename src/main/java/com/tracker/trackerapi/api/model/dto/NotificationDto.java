package com.tracker.trackerapi.api.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.util.Date;

public class NotificationDto {
    @JsonProperty("id")
    private Long id;
    @JsonProperty("distributer")
    private String distributer_email;
    @JsonProperty("supplier")
    private String supplier_email;
    @JsonProperty("order")
    private long order_id;
    @JsonProperty("isRead")
    private boolean isRead;

    @JsonProperty("CreateDate")
    private LocalDateTime CreateDate;
    public NotificationDto() {
    }

    public NotificationDto(Long id, String distributer_email, String supplier_email, long order_id, boolean isRead,LocalDateTime CreateDate) {
        this.id = id;
        this.distributer_email = distributer_email;
        this.supplier_email = supplier_email;
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
    public String getDistributerEmail() {
        return distributer_email;
    }
    @JsonProperty("distributer")
    public void setDistributerEmail(String distributer_email) {
        this.distributer_email = distributer_email;
    }
    @JsonProperty("supplier")
    public String getSupplierEmail() {
        return supplier_email;
    }
    @JsonProperty("supplier")
    public void setSupplierEmail(String supplier_email) {
        this.supplier_email = supplier_email;
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
