package ru.blss.lab1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.blss.lab1.domain.DeliveryCarFlight;

import java.sql.Timestamp;

public interface DeliveryCarRepository extends JpaRepository<DeliveryCarFlight, Long> {
    @Query(value = "UPDATE delivery_car where id = ?0 set arrival_time = ?1", nativeQuery = true)
    @Modifying
    @Transactional
    int changeArrivalTime(long id, Timestamp time);

    @Query(value = "UPDATE delivery_car where id = ?0 set departure_time = ?1", nativeQuery = true)
    @Modifying
    @Transactional
    int changeDepartureTime(long id, Timestamp time);


}
