package ru.blss.lab1.service;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ru.blss.lab1.domain.StoreItem;
import ru.blss.lab1.domain.StoreItemInCart;
import ru.blss.lab1.domain.User;
import ru.blss.lab1.domain.order.Order;
import ru.blss.lab1.exception.OrderNotFoundException;

@Service
@Slf4j
public class MessageService {
    private DeliveryService deliveryService;
    private ShoppingCartService shoppingCartService;
    private final KafkaTemplate<Long, Order> kafkaOrderTemplate;
    private final KafkaTemplate<Long, StoreItemInCart> kafkaCartItemTemplate;
    private final KafkaTemplate<Long, AddCartItemDto> kafkaAddCartItemTemplate;
    private final RoleService roleService;

    public MessageService(KafkaTemplate<Long, Order> kafkaStarshipTemplate, DeliveryService deliveryService, ShoppingCartService shoppingCartService, KafkaTemplate<Long, StoreItemInCart> kafkaCartItemTemplate, KafkaTemplate<Long, AddCartItemDto> kafkaAddCartItemTemplate, RoleService roleService) {
        this.kafkaOrderTemplate = kafkaStarshipTemplate;
        this.deliveryService = deliveryService;
        this.shoppingCartService = shoppingCartService;
        this.kafkaCartItemTemplate = kafkaCartItemTemplate;
        this.kafkaAddCartItemTemplate = kafkaAddCartItemTemplate;
        this.roleService = roleService;
    }

    public void sendToUpdateOrder(Order order) {
        kafkaOrderTemplate.send("orders-update", order);
    }

    // todo ???
    public void sendToGiveCourierRole(User user) {
        roleService.giveCourierRole(user);
    }

    public void sendToAddItemInCart(User user, StoreItem item) {
        AddCartItemDto dto = new AddCartItemDto();
        dto.userId = user.getId();
        dto.itemId = item.getId();
        kafkaAddCartItemTemplate.send("cart-add", dto);
    }

    public void sendToRemoveItemFromCart(StoreItemInCart storeItemInCart) {
        kafkaCartItemTemplate.send("cart-remove", storeItemInCart);
    }

    @KafkaListener(id = "cart-remove-consumer", topics = {"cart-remove"}, containerFactory = "singleFactory")
    public void consumeRemoveItem(StoreItemInCart dto) {
        shoppingCartService.removeItemFromCart(dto);
        log.info("=> Item " + dto.getItem().getName() + " was successfully removed.");
    }

    @KafkaListener(id = "cart-add-consumer", topics = {"cart-add"}, containerFactory = "singleFactory")
    public void consumeAddItem(AddCartItemDto dto) {
        shoppingCartService.addItemToCart(dto.userId, dto.itemId);
    }

    @KafkaListener(id = "order-consumer", topics = {"orders-update"}, containerFactory = "singleFactory")
    public void consumeOrderUpdate(Order order) throws OrderNotFoundException {
        deliveryService.updateOrderStatus(order.getId(), order.getOrderStatus());
        log.info("=> Order status for " + order.getId() + " was successfully updated.");
    }

    @KafkaListener(id = "role-consumer", topics = {"roles"}, containerFactory = "singleFactory")
    public void consumeRole(User user) {
        roleService.giveCourierRole(user);
        log.info("=> User: " + user.getUsername() + ", now has Courier role.");
    }

    @Data
    public static class AddCartItemDto {
        public long itemId;
        public long userId;
    }
}
