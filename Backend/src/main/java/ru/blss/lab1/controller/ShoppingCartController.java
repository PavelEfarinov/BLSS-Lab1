package ru.blss.lab1.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.blss.lab1.domain.StoreItem;
import ru.blss.lab1.domain.User;
import ru.blss.lab1.exception.NoMoreItemException;
import ru.blss.lab1.exception.UnauthorizedUserException;
import ru.blss.lab1.service.ShoppingCartService;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/1")
public class ShoppingCartController extends ApiController {

    ShoppingCartService shoppingCartService;

    public ShoppingCartController(ShoppingCartService shoppingCartService) {
        this.shoppingCartService = shoppingCartService;
    }

    @PostMapping("cart/add")
    public void addItem(HttpServletRequest request, @RequestBody StoreItem item) throws UnauthorizedUserException, NoMoreItemException {
        User user = getUser(request);
        if (user == null) {
            throw new UnauthorizedUserException();
        }
        shoppingCartService.AddItemToCart(user, item);
    }

    @PostMapping("cart/remove")
    public void removeItem(HttpServletRequest request, @RequestBody StoreItem item) throws UnauthorizedUserException {
        User user = getUser(request);
        if (user == null) {
            throw new UnauthorizedUserException();
        }
        shoppingCartService.RemoveItemFromCart(user, item);
    }

    //todo get current cart
}
