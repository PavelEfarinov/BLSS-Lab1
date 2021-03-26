package ru.blss.lab1.service;

import org.springframework.stereotype.Service;
import ru.blss.lab1.domain.*;
import ru.blss.lab1.exception.*;
import ru.blss.lab1.repository.*;

import javax.persistence.EntityNotFoundException;
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

    public void addNewOrder(User user, Orders order) throws CartItemNotFoundException {
        List<StoreItemInCart> storeItemInCarts = cartItemRepository.getCartItemByOwnerId(user.getId());

        if (storeItemInCarts == null || !storeItemInCarts.isEmpty()) {
            order.setClient(user);
            orderRepository.save(order);
            for (StoreItemInCart item : storeItemInCarts) {
                orderItemRepository.save(new OrderItems(item.getQuantity(), item.getItem(), order));
            }

            cartItemRepository.deleteAllByOwnerId(user.getId());
        } else throw new CartItemNotFoundException("Your cart is empty.");
    }

    public void setOrderCourier(long courierId, long orderId) throws CourierAlreadyExistException {
        Orders order = orderRepository.getOne(orderId);
        if (order.getCourier() == null)
            orderRepository.setCourier(orderId, courierId);
        else throw new CourierAlreadyExistException("This order already has a courier");
    }

    public void updateOrderStatus(long orderId, String status) throws OrderNotFoundException {
        if (orderRepository.getOne(orderId) == null) throw new OrderNotFoundException("Order not found");
        orderRepository.updateOrderStatus(orderId, status);
    }

    public void addDeliveryCarFlight(Timestamp begin, Timestamp end, long courierId, String address) throws NoPermissionException, OrderNotFoundException {
        DeliveryCarFlight deliveryCar = new DeliveryCarFlight();
        Courier courier = courierRepository.getOne(courierId);

        if (courier == null) throw new NoPermissionException("You no have courier permission to make a car flight");

        List<Orders> orders = orderRepository.getAllByCourierId(courierId);
        if (orders != null) {
            deliveryCar.setCourier(courier);

            deliveryCar.setArrivalTime(begin);
            deliveryCar.setDepartureTime(end);

            for (Orders order : orders) {
                orderRepository.updateAddress(order.getId(), address);
            }
            deliveryCarRepository.save(deliveryCar);
        } else throw new OrderNotFoundException("Your chosen no orders");
    }

    public void giveCourierRole(User user) throws UnauthorizedUserException, CourierAlreadyExistException {
        if (user == null) {
            throw new UnauthorizedUserException();
        }

        if (courierRepository.getOne(user.getId()) != null) throw new CourierAlreadyExistException("You are already courier");
        Courier courier = new Courier();
        courier.setUser(user);
        courierRepository.save(courier);
    }

    public List<Orders> getAssignedOrders(long id) throws NoPermissionException {
        try {
            courierRepository.getOne(id);
        } catch (EntityNotFoundException e) {
            throw new NoPermissionException("You have no courier permissions");
        }
        return orderRepository.getAssignedOrders(id);
    }

    public List<Orders> getFreeOrders(long id) throws NoPermissionException {
        try {
            courierRepository.getOne(id);
        } catch (EntityNotFoundException e) {
            throw new NoPermissionException("You have no courier permissions");
        }
        return orderRepository.getAllFreeOrders();
    }
}
