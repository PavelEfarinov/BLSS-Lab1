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
    @Query(value = "select s from StoreItemInCart s where s.item.id = ?1 AND s.owner.id = ?2")
    Optional<StoreItemInCart> getCartItemByCart(long itemId, long ownerId);

    @Query(value = "select s from StoreItemInCart s where s.owner.id = :ownerId")
    List<StoreItemInCart> getCartItemByOwnerId(@Param("ownerId") long ownerId);

    @Query(value = "delete from StoreItemInCart s where s.owner.id = :ownerId")
    @Modifying
    @Transactional
    int deleteAllByOwnerId(@Param("ownerId") long ownerId);
}
