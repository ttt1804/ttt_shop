package com.ttt.ttt_shop.service;


public interface OrderItemsService {
    void add(Long orderId, Long productId, int quantity);
}
