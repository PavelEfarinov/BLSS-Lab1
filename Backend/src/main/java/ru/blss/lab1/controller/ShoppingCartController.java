package ru.blss.lab1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.blss.lab1.domain.StoreItem;
import ru.blss.lab1.domain.StoreItemInCart;
import ru.blss.lab1.domain.User;
import ru.blss.lab1.domain.order.Order;
import ru.blss.lab1.exception.CartItemNotFoundException;
import ru.blss.lab1.exception.NoMoreItemException;
import ru.blss.lab1.exception.UnauthorizedUserException;
import ru.blss.lab1.exception.ValidationException;
import ru.blss.lab1.service.DeliveryService;
import ru.blss.lab1.service.ShoppingCartService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/cart")
public class ShoppingCartController extends ApiController {
    @Autowired
    ShoppingCartService shoppingCartService;
    @Autowired
    private DeliveryService deliveryService;

    @PostMapping("add")
    public void addItem(@ModelAttribute("user") User user, @RequestBody StoreItem item) throws UnauthorizedUserException, NoMoreItemException {
        if (user == null) {
            throw new UnauthorizedUserException();
        }

        shoppingCartService.addItemToCart(user, item);
    }

    @PostMapping("remove")
    public void removeItem(@ModelAttribute("user") User user, @RequestBody StoreItem item) throws UnauthorizedUserException {
        if (user == null) {
            throw new UnauthorizedUserException();
        }
        shoppingCartService.removeItemFromCart(user, item);
    }

    @GetMapping("items")
    public List<StoreItemInCart> getAssignedOrders(@ModelAttribute("user") User user) throws UnauthorizedUserException {
        if (user == null) {
            throw new UnauthorizedUserException();
        }
        return shoppingCartService.getItemsInCart(user);
    }

    @PostMapping("order")
    public void addNewOrder(@RequestBody Order orderInfo, @ModelAttribute("user") User user) throws CartItemNotFoundException, UnauthorizedUserException {
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
        deliveryService.addNewOrder(user, orderInfo);
    }
}
