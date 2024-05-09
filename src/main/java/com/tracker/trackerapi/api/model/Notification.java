package com.tracker.trackerapi.api.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

@Entity
@Table(name = "notification_tb")
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long notif_id;
    @ManyToOne
    @JoinColumn(name = "distributor_id", nullable = false)
    private Distributer distributor;

    @ManyToOne
    @JoinColumn(name = "supplier_id", nullable = false)
    private Supplier supplier;
    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;
    private boolean isRead;

    private LocalDateTime CreateDate;

    public Notification() {
    }

    public Notification(Distributer distributor, Supplier supplier, Order order, boolean isRead , LocalDateTime date) {
        this.distributor = distributor;
        this.supplier = supplier;
        this.order = order;
        this.isRead = isRead;
        this.CreateDate = date;
    }

    public long getNotif_id() {
        return notif_id;
    }

    public void setNotif_id(long notif_id) {
        this.notif_id = notif_id;
    }

    public Distributer getDistributor() {
        return distributor;
    }

    public void setDistributor(Distributer distributor) {
        this.distributor = distributor;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }

    public LocalDateTime getCreateDate() {
        return CreateDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        CreateDate = createDate;
    }
}
