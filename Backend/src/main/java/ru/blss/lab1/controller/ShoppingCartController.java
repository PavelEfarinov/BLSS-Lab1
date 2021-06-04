package ru.blss.lab1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.blss.lab1.domain.StoreItem;
import ru.blss.lab1.domain.StoreItemInCart;
import ru.blss.lab1.domain.User;
import ru.blss.lab1.domain.order.Order;
import ru.blss.lab1.exception.*;
import ru.blss.lab1.repository.CartItemRepository;
import ru.blss.lab1.repository.StoreItemRepository;
import ru.blss.lab1.repository.UserRepository;
import ru.blss.lab1.service.DeliveryService;
import ru.blss.lab1.service.MessageService;
import ru.blss.lab1.service.ShoppingCartService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/cart")
public class ShoppingCartController extends ApiController {
    @Autowired
    ShoppingCartService shoppingCartService;
    @Autowired
    private DeliveryService deliveryService;
    @Autowired
    StoreItemRepository storeItemRepository;
    @Autowired
    CartItemRepository cartItemRepository;

    @Autowired
    MessageService messageService;

    @PostMapping("add")
    public void addItem(@ModelAttribute("user") User user, @RequestBody StoreItem item) throws UnauthorizedUserException, NoMoreItemException {
        if (user == null) {
            throw new UnauthorizedUserException();
        }
        Optional<Integer> available = storeItemRepository.getCurrentlyAvailableById(item.getId());
        if (!available.isPresent()) {
            throw new NoSuchResourceException("Was not able to find item with the given id " + "\"" + item.getId() + "\"");
        }
        if (available.get() <= 0)
        {
            throw new NoMoreItemException("No more items left in the store");
        }
        else {
            messageService.sendToAddItemInCart(user, item);
        }
    }

    @PostMapping("remove")
    public void removeItem(@ModelAttribute("user") User user, @RequestBody StoreItem item) throws UnauthorizedUserException {
        if (user == null) {
            throw new UnauthorizedUserException();
        }
        Optional<StoreItemInCart> storeItemInCartResp = cartItemRepository.getCartItemByCart(item.getId(), user.getId());
        if (!storeItemInCartResp.isPresent()) {
            throw new NoSuchResourceException("No such item in the cart");
        }
        messageService.sendToRemoveItemFromCart(storeItemInCartResp.get());
    }

    @GetMapping("items")
    public List<StoreItemInCart> getAssignedOrders(@ModelAttribute("user") User user) throws UnauthorizedUserException {
        if (user == null) {
            throw new UnauthorizedUserException();
        }
        return shoppingCartService.getItemsInCart(user);
    }

    @PostMapping("order")
    public long addNewOrder(@RequestBody Order orderInfo, @ModelAttribute("user") User user) throws CartItemNotFoundException, UnauthorizedUserException, NoMoreItemException {
        if(orderInfo.getAddress() == null || orderInfo.getAddress().isEmpty())
        {
            throw new ValidationException("Order address should be provided");
        }
        if (orderInfo.getPaymentStatus() == null) {
            throw new ValidationException("Order payment status should be provided");
        }
        if (user == null) {
            throw new UnauthorizedUserException();
        }
        return deliveryService.addNewOrder(user, orderInfo);
    }
}
