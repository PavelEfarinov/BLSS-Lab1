package ru.blss.lab1.domain;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Entity
@Table(name = "delivery_car_flight")
public class DeliveryCarFlight {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    @NotEmpty
    private Timestamp arrival_time;

    @NotNull
    @NotEmpty
    private Timestamp departure_time;

    @ManyToOne
    private Courier courier;

    public DeliveryCarFlight(){}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Timestamp getArrivalTime() {
        return arrival_time;
    }

    public void setArrivalTime(Timestamp arrival_time) {
        this.arrival_time = arrival_time;
    }

    public Timestamp getDepartureTime() {
        return departure_time;
    }

    public void setDepartureTime(Timestamp departure_time) {
        this.departure_time = departure_time;
    }

    public Courier getCourier() {
        return courier;
    }

    public void setCourier(Courier courier) {
        this.courier = courier;
    }
}
