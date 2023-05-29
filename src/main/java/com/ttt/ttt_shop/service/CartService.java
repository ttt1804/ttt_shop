package com.ttt.ttt_shop.service;


import com.ttt.ttt_shop.model.entity.Cart;

import java.util.List;

public interface CartService {
    void addCart(Long userId, Long productId);
    List<Cart> getByUserId(Long userId);
    void deleteById(Long userId);
    void changeQuantity(Long productId, String changeType);
}
