package ru.blss.lab1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.blss.lab1.domain.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
