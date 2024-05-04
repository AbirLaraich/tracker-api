package com.tracker.trackerapi.api.model;

import jakarta.persistence.*;
import org.aspectj.weaver.ast.Or;


import java.util.Date;

@Entity
@Table(name = "lot_tb")
public class Lot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String numLot;
    @Column(nullable = false)
    private String name;
    @ManyToOne
    @JoinColumn(name = "supplier_id", nullable = false)
    private Supplier supplier;
    @ManyToOne
    @JoinColumn(name = "distributer_id", nullable = true)
    private Distributer distributer;
    @Column(nullable = false)
    private Date creation_date;
    @ManyToOne
    @JoinColumn(name = "order_id", nullable = true)
    private Order order;

    public Lot(String numLot, String name, Supplier supplier, Distributer distributer, Date creation_date, Order order) {
        this.numLot = numLot;
        this.name = name;
        this.supplier = supplier;
        this.distributer = distributer;
        this.creation_date = creation_date;
        this.order = order;
    }

    public Lot(String numLot, String name, Supplier supplier, Distributer distributer, Date creation_date) {
        this.numLot = numLot;
        this.name = name;
        this.supplier = supplier;
        this.distributer = distributer;
        this.creation_date = creation_date;
    }

    public Lot() {

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

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public Distributer getDistributer() {
        return distributer;
    }

    public void setDistributer(Distributer distributer) {
        this.distributer = distributer;
    }

    public Date getCreation_date() {
        return creation_date;
    }

    public void setCreation_date(Date creation_date) {
        this.creation_date = creation_date;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    @Override
    public String toString() {
        return "Lot{" +
                "numLot='" + numLot + '\'' +
                ", name='" + name + '\'' +
                ", supplier=" + supplier +
                ", distributer=" + distributer +
                ", creation_date=" + creation_date +
                ", order=" + order +
                '}';
    }
}
