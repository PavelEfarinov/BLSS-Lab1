package ru.blss.lab1.domain;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Entity
@Table (name = "item_in_cart")
public class StoreItemInCart {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private User owner;

    @ManyToOne
    private StoreItem item;

    private @NotNull @Min(1) Long quantity;

    public @NotNull @Min(1) Long getQuantity() {
        return quantity;
    }

    public void setQuantity(@NotNull @Min(1) Long quantity) {
        this.quantity = quantity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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
