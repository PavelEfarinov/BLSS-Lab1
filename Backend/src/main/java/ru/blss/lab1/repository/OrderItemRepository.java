package ru.blss.lab1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.blss.lab1.domain.OrderItem;
import ru.blss.lab1.domain.order.Order;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    @Query("select i from OrderItem i where i.order = :order")
    List<OrderItem> getAllForOrder(@Param("order")Order order);
}
