package ru.blss.lab1.domain;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Max;

@Entity
@Data
@Table(name = "courier")
public class Courier {

    @Id
    private Long id;

    private @Max(5) Double rating;

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

    public void setRating(@Max(5) Double rating) {
        this.rating = rating;
    }

}
