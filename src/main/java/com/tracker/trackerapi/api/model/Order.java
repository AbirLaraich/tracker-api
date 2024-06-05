package com.tracker.trackerapi.api.model;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "order_tb")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "distributer_id", nullable = false)
    private Distributer distributer;

    @ManyToOne
    @JoinColumn(name = "owner", nullable = false)
    private Supplier owner;
    @Column(nullable = false)
    private Date creationDate;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status;
    @Column(nullable = false)
    private boolean inBlockchain;
    public Order() {
    }

    public Order(Distributer distributer, Supplier owner, Date creationDate, Status status, boolean inBlockchain) {
        this.distributer = distributer;
        this.owner = owner;
        this.creationDate = creationDate;
        this.status = status;
        this.inBlockchain = inBlockchain;
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Distributer getDistributer() {
        return distributer;
    }

    public void setDistributer(Distributer distributer) {
        this.distributer = distributer;
    }

    public Supplier getOwner() {
        return owner;
    }

    public void setOwner(Supplier owner) {
        this.owner = owner;
    }
    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public boolean isInBlockchain() {
        return inBlockchain;
    }

    public void setInBlockchain(boolean inBlockchain) {
        this.inBlockchain = inBlockchain;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", distributer=" + distributer +
                ", owner=" + owner +
                ", creationDate=" + creationDate +
                ", status=" + status +
                '}';
    }
}
