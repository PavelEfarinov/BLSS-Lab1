package ru.blss.lab1.domain;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Entity
@Table (name = "item_in_cart")
public class StoreItemInCart {
    @Id
    @GeneratedValue
    private long id;

    @ManyToOne
    private User owner;

    @ManyToOne
    private StoreItem item;

    @NotNull
    @Min(1)
    private long quantity;

    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public StoreItem getItem() {
        return item;
    }

    public void setItem(StoreItem item) {
        this.item = item;
    }
}
