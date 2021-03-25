package ru.blss.lab1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.blss.lab1.domain.Orders;
import ru.blss.lab1.domain.User;

import java.sql.Timestamp;
import java.util.List;

public interface OrderRepository extends JpaRepository<Orders, Long> {
    @Query(value = "UPDATE orders set address = :address where id = :id", nativeQuery = true)
    @Modifying
    @Transactional
    int updateAddress(@Param("id") long id, @Param("address") String address);

    @Query(value = "select * from orders where courier_id = :courierId", nativeQuery = true)
    List<Orders> getAllByCourierId(@Param("courierId") long courierId);

    @Query(value = "UPDATE orders set courier_id = :courierId where id = :id", nativeQuery = true)
    @Modifying
    @Transactional
    int setCourier(@Param("id") long id, @Param("courierId") long courierId);

    @Query(value = "update orders set order_status = :status where id = :id", nativeQuery = true)
    @Modifying
    @Transactional
    void updateOrderStatus(@Param("id") long id, @Param("status") String status);

    @Query(value = "select * from orders where courier_id not noll", nativeQuery = true)
    List<Orders> getAllFreeOrders();

    @Query(value = "select * from orders where courier_id = ?1", nativeQuery = true)
    List<Orders> getAssignedOrders(long id);
}
