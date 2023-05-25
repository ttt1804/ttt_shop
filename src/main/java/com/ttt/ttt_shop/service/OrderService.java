package com.ttt.ttt_shop.service;


import com.ttt.ttt_shop.model.entity.Order;

import java.util.List;

public interface OrderService {

    void add(Long userId, float totalPrice);
    void updateStatus(Long orderId);
    Order findByUserIdAndStatus(Long userId, boolean status);
    List<Order> findById(Long userId);
    long getTotalOrders();
    List<Order> getAll();



}
