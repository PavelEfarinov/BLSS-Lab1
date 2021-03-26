package ru.blss.lab1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.blss.lab1.domain.StoreItemInCart;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface CartItemRepository extends JpaRepository<StoreItemInCart, Long> {
    //TODO: Remove Native SQL



    @Query(value = "SELECT * from item_in_cart where item_id = ?1 AND owner_id = ?2", nativeQuery = true)
    Optional<StoreItemInCart> getCartItemByCart(long itemId, long ownerId);

    @Query(value = "SELECT * from item_in_cart where owner_id = :owner_id", nativeQuery = true)
    List<StoreItemInCart> getCartItemByOwnerId(@Param("owner_id") long ownerId);

    @Query(value = "delete from item_in_cart where owner_id = :owner_id", nativeQuery = true)
    @Modifying
    @Transactional
    int deleteAllByOwnerId(@Param("owner_id") long ownerId);
}
