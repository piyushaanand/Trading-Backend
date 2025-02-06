package com.engineeringbureau.trading.service;


import com.engineeringbureau.trading.domain.OrderType;
import com.engineeringbureau.trading.model.Coin;
import com.engineeringbureau.trading.model.Order;
import com.engineeringbureau.trading.model.OrderItem;
import com.engineeringbureau.trading.model.User;

import java.util.List;

public interface OrderService {
    Order createOrder(User user, OrderItem orderItem, OrderType orderType);
    Order getOrderById(Long orderId) throws Exception;
    List<Order> getAllOrderOfUser(Long userId,OrderType orderType, String assetSymbol );
    Order processOrder(Coin coin, double quantity, OrderType orderType, User user) throws Exception;


}
