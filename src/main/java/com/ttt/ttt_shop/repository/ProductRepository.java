package com.ttt.ttt_shop.repository;

import com.ttt.ttt_shop.model.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> getProductsByCategory_Id(Long category_Id);
    List<Product> getTop8ProductsByCategory_Id(Long category_Id, Pageable pageable);
    Page<Product> findByNameContainingIgnoreCase(String keyword, Pageable pageable);
    Page<Product> findByCategory_Name(String categoryName, Pageable pageable);
    Page<Product> findAllByOrderByPriceAsc(Pageable pageable);
    Page<Product> findAllByOrderByPriceDesc(Pageable pageable);
    @Query("SELECT COUNT(p) FROM Product p WHERE p.category.name = :categoryName")
    long countByCategoryName(@Param("categoryName") String categoryName);

}