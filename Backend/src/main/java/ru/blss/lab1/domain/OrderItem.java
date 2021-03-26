package ru.blss.lab1.domain;

import ru.blss.lab1.domain.order.Order;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Entity
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private @NotNull @Min(1) Long quantity;

    @ManyToOne
    private StoreItem storeItem;

    @ManyToOne
    private Order order;

    public OrderItem(@NotNull @Min(1) long quantity, StoreItem storeItem, Order order) {
        this.quantity = quantity;
        this.storeItem = storeItem;
        this.order = order;
    }

    public OrderItem() {

    }

    public Order getOrders() {
        return order;
    }

    public void setOrders(Order order) {
        this.order = order;
    }

    public StoreItem getStoreItem() {
        return storeItem;
    }

    public void setStoreItem(StoreItem storeItem) {
        this.storeItem = storeItem;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @NotNull @Min(1) Long getQuantity() {
        return quantity;
    }

    public void setQuantity(@NotNull @Min(1) Long quantity) {
        this.quantity = quantity;
    }
}
