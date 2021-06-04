package ru.blss.lab1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import ru.blss.lab1.domain.StoreItem;

import javax.transaction.Transactional;
import java.util.Optional;

@Transactional
public interface StoreItemRepository extends JpaRepository<StoreItem, Long> {
    @Query(value = "select s.currentlyAvailable from StoreItem s where s.id = ?1")
    Optional<Integer> getCurrentlyAvailableById(long id);

    @Query(value = "update StoreItem s set s.currentlyAvailable = s.currentlyAvailable - 1 where s.id = ?1 ")
    @Modifying
    void takeStoreItem(long id);

    @Query(value = "update StoreItem s set s.currentlyAvailable = s.currentlyAvailable + 1 where s.id = ?1")
    @Modifying
    void addStoreItem(long id);

    @Query(value = "update StoreItem s set s.currentlyAvailable = s.currentlyAvailable - ?2 where s.id = ?1 ")
    @Modifying
    void takeStoreItemQuantity(long id, long quantity);

    @Query(value = "update StoreItem s set s.currentlyAvailable = s.currentlyAvailable + ?2 where s.id = ?1")
    @Modifying
    void addStoreItemQuantity(long id, long quantity);

}
