package ru.blss.lab1.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.blss.lab1.domain.ShoppingCart;
import ru.blss.lab1.domain.StoreItem;
import ru.blss.lab1.domain.User;
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
    public void addItem(@RequestBody ShoppingCartRequestDTO request) {
        shoppingCartService.AddItemToCart(request.cart, request.item);
    }

    @PostMapping("cart/remove")
    public void removeItem(@RequestBody ShoppingCartRequestDTO request) {
        shoppingCartService.RemoveItemFromCart(request.cart, request.item);
    }

    @PostMapping("cart/new")
    public void createCart(HttpServletRequest httpServletRequest) {
        User user = getUser(httpServletRequest);
        shoppingCartService.CreateNewShoppingCart(user);
    }

    static class ShoppingCartRequestDTO {
        private ShoppingCart cart;
        private StoreItem item;

        public ShoppingCart getCart() {
            return cart;
        }

        public void setCart(ShoppingCart cart) {
            this.cart = cart;
        }

        public StoreItem getItem() {
            return item;
        }

        public void setItem(StoreItem item) {
            this.item = item;
        }
    }
}
