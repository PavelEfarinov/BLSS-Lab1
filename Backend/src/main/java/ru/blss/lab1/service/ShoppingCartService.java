package ru.blss.lab1.service;

import org.springframework.stereotype.Service;
import ru.blss.lab1.domain.StoreItem;
import ru.blss.lab1.domain.StoreItemInCart;
import ru.blss.lab1.domain.User;
import ru.blss.lab1.exception.NoMoreItemException;
import ru.blss.lab1.exception.NoSuchResourceException;
import ru.blss.lab1.repository.CartItemRepository;
import ru.blss.lab1.repository.StoreItemRepository;
import ru.blss.lab1.repository.UserRepository;

import java.util.List;
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

    public void addItemToCart(User user, StoreItem item) throws NoMoreItemException {
        Optional<Integer> available = storeItemRepository.getCurrentlyAvailableById(item.getId());
        if (!available.isPresent()) {
            throw new NoSuchResourceException("Was not able to find item with the given id " + "\"" + item.getId() + "\"");
        }
        if (available.get() > 0) {
            item = storeItemRepository.findById(item.getId()).get();
            storeItemRepository.takeStoreItem(item.getId());

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
            cartItemRepository.saveAndFlush(storeItemInCart);
        } else {
            throw new NoMoreItemException("No more items left in the store");
        }
    }

    public void removeItemFromCart(User user, StoreItem item) {

        Optional<StoreItemInCart> storeItemInCartResp = cartItemRepository.getCartItemByCart(item.getId(), user.getId());
        if(!storeItemInCartResp.isPresent())
        {
            throw new NoSuchResourceException("No such item in the cart");
        }
        StoreItemInCart storeItemInCart = storeItemInCartResp.get();
        if (storeItemInCart.getQuantity() > 1) {
            storeItemInCart.setQuantity(storeItemInCart.getQuantity() - 1);
            cartItemRepository.save(storeItemInCart);
        } else {
            cartItemRepository.delete(storeItemInCart);
        }

        storeItemRepository.addStoreItem(item.getId());
    }

    public List<StoreItemInCart> getItemsInCart(User user)
    {
        return cartItemRepository.getCartItemByOwnerId(user.getId());
    }
}
