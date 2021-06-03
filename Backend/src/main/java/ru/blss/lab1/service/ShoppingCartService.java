package ru.blss.lab1.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.blss.lab1.domain.StoreItem;
import ru.blss.lab1.domain.StoreItemInCart;
import ru.blss.lab1.domain.User;
import ru.blss.lab1.repository.CartItemRepository;
import ru.blss.lab1.repository.StoreItemRepository;
import ru.blss.lab1.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ShoppingCartService {
    @Autowired
    StoreItemRepository storeItemRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    CartItemRepository cartItemRepository;

    public void addItemToCart(long userId, long itemId) {
        User user = userRepository.getOne(userId);
        StoreItem item = storeItemRepository.findById(itemId).get();

        Optional<StoreItemInCart> storeItemInCartResponse = cartItemRepository.getCartItemByCart(item.getId(), user.getId());
        StoreItemInCart storeItemInCart;
        if (!storeItemInCartResponse.isPresent()) {
            storeItemInCart = new StoreItemInCart();
            storeItemInCart.setItem(item);
            storeItemInCart.setOwner(user);
        } else {
            storeItemInCart = storeItemInCartResponse.get();
        }

        storeItemInCart.setQuantity(storeItemInCart.getQuantity() + 1);
        cartItemRepository.save(storeItemInCart);
    }

    public void removeItemFromCart(StoreItemInCart item) {
        if (item.getQuantity() > 1) {
            item.setQuantity(item.getQuantity() - 1);
            cartItemRepository.save(item);
        } else {
            cartItemRepository.delete(item);
        }
    }

    public List<StoreItemInCart> getItemsInCart(User user) {
        return cartItemRepository.getCartItemByOwnerId(user.getId());
    }
}
