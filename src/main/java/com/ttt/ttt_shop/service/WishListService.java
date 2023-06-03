package com.ttt.ttt_shop.service;

import com.ttt.ttt_shop.model.entity.WishList;

import java.util.List;

public interface WishListService {
    void add(Long userId, Long productId);
    List<WishList> getByUserId(Long userId);
    int getWishListCountByUserId(Long userId);

    void deleteById(Long id);

    boolean checkWishList(Long userId, Long productId);
    void deleteByUserIdAndProductId(Long userId, Long productId);
}
