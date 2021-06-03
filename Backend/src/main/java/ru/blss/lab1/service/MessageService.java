package ru.blss.lab1.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ru.blss.lab1.domain.User;
import ru.blss.lab1.domain.order.Order;
import ru.blss.lab1.exception.OrderNotFoundException;

@Service
@Slf4j
public class MessageService {
    private DeliveryService deliveryService;
    private final KafkaTemplate<Long, Order> kafkaOrderTemplate;
    private final RoleService roleService;

    public MessageService(KafkaTemplate<Long, Order> kafkaStarshipTemplate, DeliveryService deliveryService, RoleService roleService) {
        this.kafkaOrderTemplate = kafkaStarshipTemplate;
        this.deliveryService = deliveryService;
        this.roleService = roleService;
    }

    public void sendToUpdateOrder(Order order) {
        kafkaOrderTemplate.send("orders-update", order);
    }

    public void sendToGiveCourierRole(User user){
        roleService.giveCourierRole(user);
    }

    @KafkaListener(id = "order-consumer", topics = {"orders-update"}, containerFactory = "singleFactory")
    public void consumeOrderUpdate(Order order) throws OrderNotFoundException {
        deliveryService.updateOrderStatus(order.getId(), order.getOrderStatus());
        log.info("=> Order status for " + order.getId() + " was successfully update.");
    }

    @KafkaListener(id = "role-consumer", topics = {"roles"}, containerFactory = "singleFactory")
    public void consumeRole(User user) {
        roleService.giveCourierRole(user);
        log.info("=> User: " + user.getUsername() + ", now has Courier role.");
    }
}
