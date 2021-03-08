package ru.blss.lab1.service;

import org.springframework.stereotype.Service;
import ru.blss.lab1.domain.ShoppingCart;
import ru.blss.lab1.domain.StoreItem;
import ru.blss.lab1.repository.ShoppingCartRepository;
import ru.blss.lab1.repository.StoreItemRepository;

@Service
public class ShoppingCartService {
    StoreItemRepository storeItemRepository;
    ShoppingCartRepository shoppingCartRepository;

    public ShoppingCartService(StoreItemRepository storeItemRepository, ShoppingCartRepository shoppingCartRepository) {
        this.storeItemRepository = storeItemRepository;
        this.shoppingCartRepository = shoppingCartRepository;
    }

    public void AddItemToCart(ShoppingCart cart, StoreItem item)
    {
        if(storeItemRepository.getCurrentlyAvailableById(item.getId()) > 0)
        {
            storeItemRepository.takeStoreItem(item.getId());
            cart.getAddedItems().add(item);
            shoppingCartRepository.save(cart);
        }
        else
        {
            // TODO throw proper exception
        }
    }

    public void RemoveItemFromCart(ShoppingCart cart, StoreItem item)
    {
        storeItemRepository.addStoreItem(item.getId());
        cart.getAddedItems().remove(item);
        shoppingCartRepository.save(cart);
    }
}
