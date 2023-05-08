package com.ttt.ttt_shop.service;


import com.ttt.ttt_shop.model.dto.ProductDTO;
import com.ttt.ttt_shop.model.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {
    Page<Product> getAll(Pageable pageable);

    List<ProductDTO> getAllProducts();
    ProductDTO getProductById(Long id);
    void addProduct(ProductDTO product);
    void updateProduct(ProductDTO product);
    void deleteProductById(Long id);
    List<ProductDTO> getProductsByCategoryId(Long category_Id);
}
