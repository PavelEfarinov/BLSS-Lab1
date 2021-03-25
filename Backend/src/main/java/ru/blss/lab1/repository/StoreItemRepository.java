package ru.blss.lab1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.blss.lab1.domain.StoreItem;

import java.util.Optional;

public interface StoreItemRepository extends JpaRepository<StoreItem, Long> {
    @Query(value = "SELECT currently_available from store_item where id = ?1", nativeQuery = true)
    @Transactional
    Optional<Integer> getCurrentlyAvailableById(long id);

    @Query(value = "UPDATE store_item set currently_available = currently_available - 1 where id = ?1 ", nativeQuery = true)
    @Modifying
    @Transactional
    void takeStoreItem(long id);

    @Query(value = "UPDATE store_item set currently_available = currently_available + 1 where id = ?1", nativeQuery = true)
    @Modifying
    @Transactional
    void addStoreItem(long id);
}
