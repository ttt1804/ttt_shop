package com.ttt.ttt_shop.repository;


import com.ttt.ttt_shop.model.entity.Order;
import org.aspectj.weaver.ast.Or;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    Order findByUser_IdAndStatus(Long user_id, String status);

    List<Order> findByUser_Id(Long userId);

    Page<Order> findByUser_Id(Long userId, Pageable pageable);

    List<Order> findTop10ByOrderByOrderDateAsc();
}
