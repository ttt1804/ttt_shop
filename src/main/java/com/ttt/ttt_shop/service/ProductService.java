package com.ttt.ttt_shop.service;


import com.ttt.ttt_shop.model.dto.ProductDTO;
import com.ttt.ttt_shop.model.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {
    Page<Product> getAll(Pageable pageable);

    List<ProductDTO> getAllProducts();
    List<Product> getTop8ProductsByCategoryId(Long categoryId);
    ProductDTO getProductById(Long id);
    void add(ProductDTO product);
    void update(ProductDTO product);
    void delete(Long id);
    List<ProductDTO> getProductsByCategoryId(Long category_Id);

    Page<Product> getAllByName(String keyword,Pageable pageable);
    Page<Product> getProductsWithCategoryName(String categoryName,Pageable pageable);

    Page<Product> getProductsSortedByPriceAsc(Pageable pageable);
    Page<Product> getProductsSortedByPriceDesc(Pageable pageable);

     long countProductsByCategoryName(String categoryName);

     long getTotalProducts();

     Boolean updateQuantity(Long productId, int quantity);

}
