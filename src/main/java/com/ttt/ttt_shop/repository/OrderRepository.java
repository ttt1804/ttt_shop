package com.ttt.ttt_shop.repository;


import com.ttt.ttt_shop.model.entity.Order;
import org.aspectj.weaver.ast.Or;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    Order findByUser_IdAndStatus(Long userId, Boolean status);
}
