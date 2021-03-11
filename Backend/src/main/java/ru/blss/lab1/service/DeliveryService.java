package ru.blss.lab1.service;

import org.springframework.stereotype.Service;
import ru.blss.lab1.domain.Courier;
import ru.blss.lab1.domain.DeliveryCarFlight;
import ru.blss.lab1.domain.User;
import ru.blss.lab1.repository.CourierRepository;
import ru.blss.lab1.repository.DeliveryCarRepository;

import java.sql.Date;
import java.sql.Timestamp;

@Service
public class DeliveryService {
    private CourierRepository courierRepository;
    private DeliveryCarRepository deliveryCarRepository;

    public DeliveryService(CourierRepository courierRepository, DeliveryCarRepository deliveryCarRepository) {
        this.courierRepository = courierRepository;
        this.deliveryCarRepository = deliveryCarRepository;
    }

    public void addDeliveryInfo(String address, Date date, Courier courier) {
        courierRepository.addAddress(courier.getId(), address);
        courierRepository.addDeliveryDate(courier.getId(), date);
        courierRepository.save(courier);
    }

    public void setCourierStatus(String status, Courier courier) {
        courierRepository.changeStatus(courier.getId(), status);
        courierRepository.save(courier);
    }

    public void addDeliveryCarFlight(Timestamp begin, Timestamp end, Courier courier){
        DeliveryCarFlight deliveryCar = new DeliveryCarFlight();
        deliveryCar.setId(courier.getId());
        deliveryCarRepository.changeArrivalTime(deliveryCar.getId(), begin);
        deliveryCarRepository.changeDepartureTime(deliveryCar.getId(), end);
        deliveryCarRepository.save(deliveryCar);
    }

    public void giveCourierAccount(User user){
        Courier courier = new Courier();
        courier.setId(user.getId());
        courierRepository.save(courier);
    }

}
