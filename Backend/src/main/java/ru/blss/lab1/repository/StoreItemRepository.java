package ru.blss.lab1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.blss.lab1.domain.StoreItem;

import java.util.Optional;

public interface StoreItemRepository extends JpaRepository<StoreItem, Long> {
    @Query(value = "select s.currentlyAvailable from StoreItem s where s.id = ?1")
    @Transactional
    Optional<Integer> getCurrentlyAvailableById(long id);

    @Query(value = "update StoreItem s set s.currentlyAvailable = s.currentlyAvailable - 1 where s.id = ?1 ")
    @Modifying
    @Transactional
    void takeStoreItem(long id);

    @Query(value = "update StoreItem s set s.currentlyAvailable = s.currentlyAvailable + 1 where s.id = ?1")
    @Modifying
    @Transactional
    void addStoreItem(long id);
}
