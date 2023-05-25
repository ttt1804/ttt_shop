package com.ttt.ttt_shop.service.impl;

import com.ttt.ttt_shop.model.entity.Order;
import com.ttt.ttt_shop.model.entity.User;
import com.ttt.ttt_shop.repository.OrderItemsRepository;
import com.ttt.ttt_shop.repository.OrderRepository;
import com.ttt.ttt_shop.repository.UserRepository;
import com.ttt.ttt_shop.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    OrderItemsRepository orderItemsRepository;
    @Autowired
    UserRepository userRepository;
    @Override
    public void add(Long userId, float totalPrice) {
        Order order = new Order();
        User user = userRepository.findById(userId).orElse(null);
        if(user != null){
            order.setUser(user);
        }
        order.setTotalPrice(totalPrice);
        order.setStatus(false);
        orderRepository.save(order);
    }

    @Override
    public void updateStatus(Long orderId) {
        Order order = orderRepository.findById(orderId).orElse(null);
        if(order != null){
            order.setStatus(true);
            orderRepository.save(order);
        }
    }

    @Override
    public Order findByUserIdAndStatus(Long userId, boolean status) {
       return orderRepository.findByUser_IdAndStatus(userId, status);
    }

    @Override
    public List<Order> findById(Long userId) {
        return orderRepository.findByUser_Id(userId);
    }

    @Override
    public long getTotalOrders() {
        return orderRepository.count();
    }

    @Override
    public List<Order> getAll() {
        return orderRepository.findAll();
    }

}
