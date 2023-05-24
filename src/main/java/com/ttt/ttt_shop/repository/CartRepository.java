package com.ttt.ttt_shop.repository;

import com.ttt.ttt_shop.model.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    Cart findByUser_IdAndProduct_Id(Long userId, Long productId);
    List<Cart> getCartsByUser_Id(Long userId);
}
