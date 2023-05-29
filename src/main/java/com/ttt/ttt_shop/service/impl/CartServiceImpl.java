package com.ttt.ttt_shop.service.impl;

import com.ttt.ttt_shop.model.dto.CartDTO;
import com.ttt.ttt_shop.model.entity.Cart;
import com.ttt.ttt_shop.model.entity.Product;
import com.ttt.ttt_shop.model.entity.User;
import com.ttt.ttt_shop.repository.CartRepository;
import com.ttt.ttt_shop.repository.ProductRepository;
import com.ttt.ttt_shop.repository.UserRepository;
import com.ttt.ttt_shop.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    CartRepository cartRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ProductRepository productRepository;


    @Override
    public void addCart(Long userId, Long productId) {
        Cart cart = cartRepository.findByUser_IdAndProduct_Id(userId, productId);
        if (cart == null) {
            cart = new Cart();
            User user = userRepository.findById(userId).orElse(null);
            if (user != null) {
                cart.setUser(user);
            }
            Product product = productRepository.findById(productId).orElse(null);
            if (product != null) {
                cart.setProduct(product);
            }
            cart.setQuantity(1);
        } else {
            int newQuantity = cart.getQuantity() + 1;
            cart.setQuantity(newQuantity);
        }
        cartRepository.save(cart);
    }

    @Override
    public List<Cart> getByUserId(Long userId) {
        return cartRepository.getCartsByUser_Id(userId);
    }

    @Override
    public void deleteById(Long userId) {
        cartRepository.deleteById(userId);
    }

    @Override
    public void changeQuantity(Long productId, String changeType) {
        Cart cart = cartRepository.findById(productId).orElse(null);
        if(cart != null){
            if(changeType.equals("increase")){
                int newQuantity = cart.getQuantity() + 1;
                cart.setQuantity(newQuantity);
            }
            if(changeType.equals("reduce")){
                int newQuantity = cart.getQuantity() - 1;
                cart.setQuantity(newQuantity);
            }
        }
        cartRepository.save(cart);

    }

}
