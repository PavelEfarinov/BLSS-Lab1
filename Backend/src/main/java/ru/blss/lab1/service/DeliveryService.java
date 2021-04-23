package ru.blss.lab1.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.blss.lab1.domain.*;
import ru.blss.lab1.domain.order.Order;
import ru.blss.lab1.domain.order.OrderStatus;
import ru.blss.lab1.exception.*;
import ru.blss.lab1.repository.*;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class DeliveryService {
    @Autowired
    private CourierRepository courierRepository;
    @Autowired
    private DeliveryCarRepository deliveryCarRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderItemRepository orderItemRepository;
    @Autowired
    private CartItemRepository cartItemRepository;
    @Autowired
    private StoreItemRepository storeItemRepository;

    @Transactional
    public void addNewOrder(User user, Order order) throws CartItemNotFoundException {
        List<StoreItemInCart> storeItemInCarts = cartItemRepository.getCartItemByOwnerId(user.getId());

        if (storeItemInCarts != null && !storeItemInCarts.isEmpty()) {
            order.setClient(user);
            orderRepository.save(order);
            for (StoreItemInCart item : storeItemInCarts) {
                storeItemRepository.takeStoreItem(item.getItem().getId());
                orderItemRepository.save(new OrderItem(item.getQuantity(), item.getItem(), order));
            }

            cartItemRepository.deleteAllByOwnerId(user.getId());
        } else throw new CartItemNotFoundException("Your cart is empty.");
    }

    @Transactional
    public void setOrderCourier(long courierId, long orderId) throws CourierAlreadyExistException {
        Order order = orderRepository.getOne(orderId);
        if (order.getCourier() == null)
            orderRepository.setCourier(orderId, courierId);
        else throw new CourierAlreadyExistException("This order already has a courier");
    }

    @Transactional
    public void updateOrderStatus(long orderId, OrderStatus status) throws OrderNotFoundException {
        if (orderRepository.getOne(orderId) == null) throw new OrderNotFoundException("Order not found");
        orderRepository.updateOrderStatus(orderId, status);
    }

    @Transactional
    public void addDeliveryCarFlight(LocalDateTime begin, LocalDateTime end, long courierId, String address) throws NoPermissionException, OrderNotFoundException {
        DeliveryCarFlight deliveryCar = new DeliveryCarFlight();
        Courier courier = courierRepository.getOne(courierId);

        if (courier == null) throw new NoPermissionException("You no have courier permission to make a car flight");

        List<Order> order = orderRepository.getAllByCourierId(courierId);
        if (order != null) {
            deliveryCar.setCourier(courier);

            deliveryCar.setArrivalTime(begin);
            deliveryCar.setDepartureTime(end);

            for (Order ord : order) {
                orderRepository.updateAddress(ord.getId(), address);
            }
            deliveryCarRepository.save(deliveryCar);
        } else throw new OrderNotFoundException("Your chosen no order");
    }

    public List<Order> getAssignedOrders(long id) throws NoPermissionException {
        try {
            courierRepository.getOne(id);
        } catch (EntityNotFoundException e) {
            throw new NoPermissionException("You have no courier permissions");
        }
        return orderRepository.getAssignedOrders(id);
    }

    public List<Order> getFreeOrders(long id) throws NoPermissionException {
        try {
            courierRepository.getOne(id);
        } catch (EntityNotFoundException e) {
            throw new NoPermissionException("You have no courier permissions");
        }
        return orderRepository.getAllFreeOrders();
    }
}
