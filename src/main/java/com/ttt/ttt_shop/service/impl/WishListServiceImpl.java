package com.ttt.ttt_shop.service.impl;

import com.ttt.ttt_shop.model.entity.Product;
import com.ttt.ttt_shop.model.entity.User;
import com.ttt.ttt_shop.model.entity.WishList;
import com.ttt.ttt_shop.repository.ProductRepository;
import com.ttt.ttt_shop.repository.UserRepository;
import com.ttt.ttt_shop.repository.WishListRepository;
import com.ttt.ttt_shop.service.WishListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WishListServiceImpl implements WishListService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    WishListRepository wishListRepository;

    @Override
    public void add(Long userId, Long productId) {
        User user = userRepository.findById(userId).orElse(null);
        Product product = productRepository.findById(productId).orElse(null);
        if(user != null && product != null && !wishListRepository.existsByUserIdAndProductId(userId, productId)){
            WishList wishList = new WishList();
            wishList.setUser(user);
            wishList.setProduct(product);
            wishListRepository.save(wishList);
        }
    }

    @Override
    public List<WishList> getByUserId(Long userId) {
        return wishListRepository.findByUser_Id(userId);
    }

    @Override
    public int getWishListCountByUserId(Long userId) {
        return wishListRepository.countByUserId(userId);
    }

    @Override
    public void deleteById(Long id) {
        wishListRepository.deleteById(id);
    }

    @Override
    public boolean checkWishList(Long userId, Long productId) {
        User user = userRepository.findById(userId).orElse(null);
        Product product = productRepository.findById(productId).orElse(null);
        if(user != null && product != null && wishListRepository.existsByUserIdAndProductId(userId, productId)){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public void deleteByUserIdAndProductId(Long userId, Long productId) {
        WishList wishList = wishListRepository.findByUser_IdAndAndProductId(userId, productId);
        wishListRepository.delete(wishList);
    }
}
