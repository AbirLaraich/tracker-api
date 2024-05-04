package com.tracker.trackerapi.api.model;
import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "product_tb")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private int numProduct;
    @Column(nullable = false)
    private String weight;
    @Column(nullable = true)
    private Date deliveryDate;
    @ManyToOne
    @JoinColumn(name = "lot_id", nullable = false)
    private Lot lot;
    @ManyToOne
    @JoinColumn(name = "distributer_id", nullable = true)
    private Distributer distributer;
    @Column(nullable = false, unique = true, columnDefinition = "TEXT")
    private String qrCode;

    public Product(int numProduct, String weight, Date deliveryDate) {
        this.numProduct = numProduct;
        this.weight = weight;
        this.deliveryDate = deliveryDate;
    }

    public Product(int numProduct, String weight, Lot lot, Distributer distributer) {
        this.numProduct = numProduct;
        this.weight = weight;
        this.lot = lot;
        this.distributer = distributer;
    }

    public Product() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getNumSiret() {
        return numProduct;
    }

    public void setNumSiret(int numProduct) {
        this.numProduct = numProduct;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }


    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public Lot getLot() {
        return lot;
    }

    public void setLot(Lot lot) {
        this.lot = lot;
    }

    public Distributer getDistributer() {
        return distributer;
    }

    public void setDistributer(Distributer distributer) {
        this.distributer = distributer;
    }

    public int getNumProduct() {
        return numProduct;
    }

    public void setNumProduct(int numProduct) {
        this.numProduct = numProduct;
    }

    public String getQrCode() {
        return qrCode;
    }
    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }
    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", numProduct=" + numProduct +
                ", weight='" + weight + '\'' +
                ", deliveryDate=" + deliveryDate +
                ", lot=" + lot +
                ", distributer=" + distributer +
                ", qrCode='" + qrCode + '\'' +
                '}';
    }
}
