package com.ttt.ttt_shop.service;


import com.ttt.ttt_shop.model.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface OrderService {

    void add(Long userId, float totalPrice);
    void updateStatus(Long orderId, String status);
    Order findByUserIdAndStatus(Long userId, String status);
    Order findById(Long id);
    Page<Order> findByUserID(Long userId, Pageable pageable);
    long getTotalOrders();
    Page<Order> getAll(Pageable pageable);

    List<Order> findTop10ByOrderByOrderDateAsc();



}
