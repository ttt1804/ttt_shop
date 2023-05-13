package com.ttt.ttt_shop.repository;

import com.ttt.ttt_shop.model.entity.Product;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> getProductsByCategory_Id(Long category_Id);
    List<Product> getTop8ProductsByCategory_Id(Long category_Id, Pageable pageable);

}