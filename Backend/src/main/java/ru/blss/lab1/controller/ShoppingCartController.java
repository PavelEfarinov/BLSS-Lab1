package ru.blss.lab1.controller;

import org.springframework.web.bind.annotation.*;
import ru.blss.lab1.domain.StoreItem;
import ru.blss.lab1.domain.StoreItemInCart;
import ru.blss.lab1.domain.User;
import ru.blss.lab1.exception.NoMoreItemException;
import ru.blss.lab1.exception.UnauthorizedUserException;
import ru.blss.lab1.service.ShoppingCartService;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class ShoppingCartController extends ApiController {

    ShoppingCartService shoppingCartService;

    public ShoppingCartController(ShoppingCartService shoppingCartService) {
        this.shoppingCartService = shoppingCartService;
    }

    @PostMapping("cart/add")
    public void addItem(@ModelAttribute("user") User user, @RequestBody StoreItem item) throws UnauthorizedUserException, NoMoreItemException {
        if (user == null) {
            throw new UnauthorizedUserException();
        }


        shoppingCartService.addItemToCart(user, item);
    }

    @PostMapping("cart/remove")
    public void removeItem(@ModelAttribute("user") User user, @RequestBody StoreItem item) throws UnauthorizedUserException {
        if (user == null) {
            throw new UnauthorizedUserException();
        }
        shoppingCartService.removeItemFromCart(user, item);
    }

    @GetMapping("cart/items")
    public List<StoreItemInCart> getAssignedOrders(@ModelAttribute("user") User user) throws UnauthorizedUserException {
        if (user == null) {
            throw new UnauthorizedUserException();
        }
        return shoppingCartService.getItemsInCart(user);
    }

}
