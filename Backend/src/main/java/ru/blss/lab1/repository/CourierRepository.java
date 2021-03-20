package ru.blss.lab1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.blss.lab1.domain.Courier;

import java.sql.Date;
import java.sql.Timestamp;

public interface CourierRepository extends JpaRepository<Courier, Long> {
    @Query(value = "UPDATE courier where id = ?0 set rating = ?1", nativeQuery = true)
    @Modifying
    @Transactional
    int updateRating(long id, double rating);

}
