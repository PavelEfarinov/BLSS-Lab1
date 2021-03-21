package ru.blss.lab1.service;

import org.springframework.stereotype.Service;
import ru.blss.lab1.domain.Courier;
import ru.blss.lab1.domain.DeliveryCarFlight;
import ru.blss.lab1.domain.Orders;
import ru.blss.lab1.domain.User;
import ru.blss.lab1.repository.CourierRepository;
import ru.blss.lab1.repository.DeliveryCarRepository;
import ru.blss.lab1.repository.OrderRepository;

import java.sql.Timestamp;

@Service
public class DeliveryService {
    private CourierRepository courierRepository;
    private DeliveryCarRepository deliveryCarRepository;
    private OrderRepository orderRepository;

    public DeliveryService(CourierRepository courierRepository, DeliveryCarRepository deliveryCarRepository, OrderRepository orderRepository) {
        this.courierRepository = courierRepository;
        this.deliveryCarRepository = deliveryCarRepository;
        this.orderRepository = orderRepository;
    }

    public void addNewOrder(String address, String paymentStatus){
        Orders order = new Orders();
        order.setAddress(address);
        order.setPaymentStatus(paymentStatus);
        orderRepository.save(order);
    }

    public void setOrderCourier(long courierId, long orderId){
        Orders order = orderRepository.getOne(orderId);
        if (order.getCourier().getId() != null)
            orderRepository.setCourier(orderId, courierId);
    }

    public void updateOrderStatus(long orderId, String status){
        orderRepository.updateOrderStatus(orderId, status);
    }

    public void addDeliveryCarFlight(Timestamp begin, Timestamp end, long courierId){
        DeliveryCarFlight deliveryCar = new DeliveryCarFlight();
        deliveryCar.setId(courierId);
        deliveryCarRepository.updateArrivalTime(deliveryCar.getId(), begin);
        deliveryCarRepository.updateDepartureTime(deliveryCar.getId(), end);
        deliveryCarRepository.save(deliveryCar);
    }

    public void giveCourierRole(User user){
        Courier courier = new Courier();
        courier.setUser(user);
        courierRepository.save(courier);
    }

}
