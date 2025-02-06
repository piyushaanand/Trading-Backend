package com.engineeringbureau.trading.repository;

//import com.engineeringbureau.trading.model.Order;
import com.engineeringbureau.trading.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
