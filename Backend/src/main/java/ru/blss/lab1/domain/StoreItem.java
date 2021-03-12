package ru.blss.lab1.domain;

import com.sun.istack.NotNull;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "store_item")
public class StoreItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long Id;

    @NotNull
    @NotEmpty
    private String Name;

    @NotNull
    @NotEmpty
    private String Description;

    @NotNull
    @Min(0)
    private int CurrentlyAvailable;

    public long getId() {
        return Id;
    }

    public void setId(long id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public int getCurrentlyAvailable() {
        return CurrentlyAvailable;
    }

    public void setCurrentlyAvailable(int currentlyAvailable) {
        CurrentlyAvailable = currentlyAvailable;
    }
}
