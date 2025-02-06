package com.engineeringbureau.trading.repository;

import com.engineeringbureau.trading.model.Order;
//import com.engineeringbureau.trading.service.OrderService;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUserId(Long userId);
}
