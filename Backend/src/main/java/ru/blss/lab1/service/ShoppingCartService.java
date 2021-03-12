package ru.blss.lab1.service;

import org.springframework.stereotype.Service;
import ru.blss.lab1.domain.StoreItem;
import ru.blss.lab1.domain.StoreItemInCart;
import ru.blss.lab1.domain.User;
import ru.blss.lab1.repository.CartItemRepository;
import ru.blss.lab1.repository.StoreItemRepository;
import ru.blss.lab1.repository.UserRepository;

import java.util.Optional;

@Service
public class ShoppingCartService {
    StoreItemRepository storeItemRepository;
    UserRepository userRepository;
    CartItemRepository cartItemRepository;

    public ShoppingCartService(StoreItemRepository storeItemRepository, UserRepository userRepository, CartItemRepository cartItemRepository) {
        this.storeItemRepository = storeItemRepository;
        this.userRepository = userRepository;
        this.cartItemRepository = cartItemRepository;
    }

    public void AddItemToCart(User user, StoreItem item) {
        if (storeItemRepository.getCurrentlyAvailableById(item.getId()) > 0) {
            item = storeItemRepository.findById(item.getId()).get();
            storeItemRepository.takeStoreItem(item.getId());

            Optional<StoreItemInCart> storeItemInCartResponse = cartItemRepository.getCartItemByCart(item.getId(), user.getId());
            StoreItemInCart storeItemInCart;
            if(!storeItemInCartResponse.isPresent())
            {
                storeItemInCart = new StoreItemInCart();

                storeItemInCart.setItem(item);
                storeItemInCart.setOwner(user);
            }
            else
            {
                storeItemInCart = storeItemInCartResponse.get();
            }

            storeItemInCart.setQuantity(storeItemInCart.getQuantity() + 1);

            cartItemRepository.saveAndFlush(storeItemInCart);
        } else {
            // TODO throw proper exception
        }
    }

    public void RemoveItemFromCart(User user, StoreItem item) {
        StoreItemInCart storeItemInCart = cartItemRepository.getCartItemByCart(item.getId(), user.getId()).get();

        if(storeItemInCart.getQuantity() > 1)
        {
            storeItemInCart.setQuantity(storeItemInCart.getQuantity() - 1);
            cartItemRepository.save(storeItemInCart);
        }
        else {
            cartItemRepository.delete(storeItemInCart);
        }

        storeItemRepository.addStoreItem(item.getId());
    }
}
