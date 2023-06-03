package com.ttt.ttt_shop.repository;

import com.ttt.ttt_shop.model.entity.WishList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WishListRepository extends JpaRepository<WishList, Long> {
    List<WishList> findByUser_Id(Long userId);
    int countByUserId(long userId);
    WishList findByUser_IdAndAndProductId(Long userId, Long productId);
    boolean existsByUserIdAndProductId(long userId, long productId);

}
