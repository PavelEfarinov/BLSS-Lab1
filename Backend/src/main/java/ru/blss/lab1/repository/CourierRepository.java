package ru.blss.lab1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.blss.lab1.domain.Courier;

import java.sql.Date;
import java.sql.Timestamp;

public interface CourierRepository extends JpaRepository<Courier, Long> {

    @Query(value = "UPDATE courier where id = ?0 set address = ?1", nativeQuery = true)
    @Modifying
    @Transactional
    int addAddress(long id, String address);

    @Query(value = "UPDATE courier where id = ?0 set  deliveryDate = ?1", nativeQuery = true)
    @Modifying
    @Transactional
    int addDeliveryDate(long id, Date date);

    @Query(value = "UPDATE courier where id = ?0 set status = ?1", nativeQuery = true)
    @Modifying
    @Transactional
    int changeStatus(long id, String status);

}
