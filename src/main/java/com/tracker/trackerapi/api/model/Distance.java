package com.tracker.trackerapi.api.model;

import jakarta.persistence.*;

@Entity
@Table(name = "distance_tb")
public class Distance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "distributor_id", nullable = false)
    private Distributer distributor;

    @ManyToOne
    @JoinColumn(name = "supplier_id", nullable = false)
    private Supplier supplier;
    private double distance;

    public Distance() {

    }
    public Distance(Distributer distributor, Supplier supplier, double distance) {
        this.distributor = distributor;
        this.supplier = supplier;
        this.distance = distance;
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

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }


    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
