package ru.blss.lab1.domain;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "shopping_cart")
public class ShoppingCart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;

    @ManyToOne
    private User Owner;

    @ManyToMany
    private List<StoreItem> AddedItems;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public User getOwner() {
        return Owner;
    }

    public void setOwner(User owner) {
        Owner = owner;
    }

    public List<StoreItem> getAddedItems() {
        return AddedItems;
    }

    public void setAddedItems(List<StoreItem> addedItems) {
        AddedItems = addedItems;
    }
}
