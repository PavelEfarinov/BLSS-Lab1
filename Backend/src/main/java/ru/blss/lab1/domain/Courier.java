package ru.blss.lab1.domain;

import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.sql.Date;

@Entity
@Table(name = "courier")
public class Courier {

    @Id
    private long id;

    @Max(5)
    private double rating;

    @OneToOne
    @MapsId
    @JoinColumn(name = "id")
    private User user;

    public Courier(){}

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getRate() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

}
