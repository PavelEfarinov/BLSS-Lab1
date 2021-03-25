package ru.blss.lab1.domain;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "orders")
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    private Courier courier;

    @ManyToOne
    private User client;

    @NotNull
    @NotEmpty
    private String address;

    @Column(nullable = false)
    @NotNull
    @NotEmpty
    private Timestamp formedDate;

    @NotNull
    @NotEmpty
    private String paymentStatus;

    @Column(nullable = false)
    @NotNull
    @NotEmpty
    private String orderStatus;

    @PrePersist
    void preInsert() {
        if (this.formedDate == null)
            this.formedDate = new Timestamp(new Date().getTime());

        if (this.orderStatus == null)
            this.orderStatus = "formed";
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Timestamp getFormedDate() {
        return formedDate;
    }

    public void setFormedDate(Timestamp formedDate) {
        this.formedDate = formedDate;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Courier getCourier() {
        return courier;
    }

    public void setCourier(Courier courier) {
        this.courier = courier;
    }

    public User getClient() {
        return client;
    }

    public void setClient(User client) {
        this.client = client;
    }
}
