package com.ttt.ttt_shop.service.impl;

import com.ttt.ttt_shop.model.entity.Order;
import com.ttt.ttt_shop.model.entity.OrderItem;
import com.ttt.ttt_shop.model.entity.Product;
import com.ttt.ttt_shop.repository.OrderItemsRepository;
import com.ttt.ttt_shop.repository.OrderRepository;
import com.ttt.ttt_shop.repository.ProducerRepository;
import com.ttt.ttt_shop.repository.ProductRepository;
import com.ttt.ttt_shop.service.OrderItemsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class OrderItemsServiceImpl implements OrderItemsService {

    @Autowired
    OrderItemsRepository orderItemsRepository;
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    ProductRepository productRepository;

    @Override
    public void add(Long orderId, Long productId, int quantity) {
        OrderItem orderItem = new OrderItem();
        Order order = orderRepository.findById(orderId).orElse(null);
        if(order != null){
            orderItem.setOrder(order);
        }
        Product product = productRepository.findById(productId).orElse(null);
        if(product != null){
            orderItem.setProduct(product);
            orderItem.setQuantity(quantity);
            orderItem.setPrice(quantity * product.getPrice());
        }
        orderItemsRepository.save(orderItem);

    }
}
