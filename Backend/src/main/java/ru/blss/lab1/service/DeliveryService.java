package ru.blss.lab1.service;

import org.springframework.stereotype.Service;
import ru.blss.lab1.domain.*;
import ru.blss.lab1.repository.*;

import java.sql.Timestamp;
import java.util.List;

@Service
public class DeliveryService {
    private CourierRepository courierRepository;
    private DeliveryCarRepository deliveryCarRepository;
    private OrderRepository orderRepository;
    private OrderItemRepository orderItemRepository;
    private CartItemRepository cartItemRepository;

    public DeliveryService(CourierRepository courierRepository, DeliveryCarRepository deliveryCarRepository, OrderRepository orderRepository, OrderItemRepository orderItemRepository, CartItemRepository cartItemRepository) {
        this.courierRepository = courierRepository;
        this.deliveryCarRepository = deliveryCarRepository;
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.cartItemRepository = cartItemRepository;
    }

    public void addNewOrder(User user, String address, String paymentStatus){
        Orders order = new Orders();
        List<StoreItemInCart> storeItemInCarts = cartItemRepository.getCartItemByOwnerId(user.getId());

        if (!storeItemInCarts.isEmpty()) {
            order.setAddress(address);
            order.setPaymentStatus(paymentStatus);
            order.setClient(user);
            orderRepository.save(order);
            for (StoreItemInCart item : storeItemInCarts) {
                orderItemRepository.save(new OrderItems(item.getQuantity(), item.getItem(), order));
            }
        }
    }

    public void setOrderCourier(long courierId, long orderId){
        Orders order = orderRepository.getOne(orderId);
        if (order.getCourier() == null)
            orderRepository.setCourier(orderId, courierId);
    }

    public void updateOrderStatus(long orderId, String status){
        orderRepository.updateOrderStatus(orderId, status);
    }

    public void addDeliveryCarFlight(Timestamp begin, Timestamp end, long courierId, String address){
        DeliveryCarFlight deliveryCar = new DeliveryCarFlight();
        Courier courier = courierRepository.getOne(courierId);
        List<Orders> orders = orderRepository.getAllByCourierId(courierId);
        if (courier != null && orders != null)
        {
            deliveryCar.setCourier(courier);

            deliveryCar.setArrivalTime(begin);
            deliveryCar.setDepartureTime(end);

            for (Orders order: orders) {
                orderRepository.updateAddress(order.getId(), address);
            }
            deliveryCarRepository.save(deliveryCar);
        }
    }

    public void giveCourierRole(User user){
        Courier courier = new Courier();
        courier.setUser(user);
        courierRepository.save(courier);
    }

}
