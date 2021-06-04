package ru.blss.lab1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.blss.lab1.domain.order.Order;
import ru.blss.lab1.domain.order.OrderStatus;

import javax.transaction.Transactional;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    @Query(value = "update Order o set o.address = :address where o.id = :id")
    @Modifying
    @Transactional
    int updateAddress(@Param("id") long id, @Param("address") String address);

    @Query(value = "select o from Order o where o.courier.id = :courierId")
    List<Order> getAllByCourierId(@Param("courierId") long courierId);

    @Query(value = "update Order o set o.courier.id = :courierId where o.id = :id")
    @Modifying
    @Transactional
    int setCourier(@Param("id") long id, @Param("courierId") long courierId);

    @Query(value = "update Order o set o.orderStatus = :status where o.id = :id")
    @Modifying
    @Transactional
    void updateOrderStatus(@Param("id") long id, @Param("status") OrderStatus status);

    @Query(value = "select o from Order o where o.courier.id is not null")
    List<Order> getAllFreeOrders();

    @Query(value = "select o from Order o where o.paymentStatus = 'PENDING' and o.orderStatus = 'FORMED'")
    List<Order> getAllNewUnpaidOrders();

    @Query(value = "select o from Order o where o.courier.id = ?1")
    List<Order> getAssignedOrders(long id);
}
