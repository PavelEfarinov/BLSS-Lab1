package ru.blss.lab1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.blss.lab1.domain.Orders;
import ru.blss.lab1.domain.User;

import java.sql.Timestamp;

public interface OrderRepository extends JpaRepository<Orders, Long> {
    @Query(value = "UPDATE orders where id = ?0 set address = ?1", nativeQuery = true)
    @Modifying
    @Transactional
    int updateAddress(long id, String address);

    @Query(value = "UPDATE orders where id = ?0 set courier_id = ?1", nativeQuery = true)
    @Modifying
    @Transactional
    int setCourier(long id, long courierId);

    @Query(value = "UPDATE orders where id = ?0 set payment_status = ?1", nativeQuery = true)
    @Modifying
    @Transactional
    int setPaymentStatus(long id, String status);

    @Query(value = "UPDATE orders where id = ?0 set order_status = ?1", nativeQuery = true)
    @Modifying
    @Transactional
    int updateOrderStatus(long id, String status);
}
