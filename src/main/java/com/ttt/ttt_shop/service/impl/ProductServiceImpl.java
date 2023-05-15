package com.ttt.ttt_shop.service.impl;

import com.ttt.ttt_shop.model.dto.ProductDTO;
import com.ttt.ttt_shop.model.entity.Category;
import com.ttt.ttt_shop.model.entity.Producer;
import com.ttt.ttt_shop.model.entity.Product;
import com.ttt.ttt_shop.repository.CategoryRepository;
import com.ttt.ttt_shop.repository.ProducerRepository;
import com.ttt.ttt_shop.repository.ProductRepository;
import com.ttt.ttt_shop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProducerRepository producerRepository;

    @Override
    public Page<Product> getAll(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    @Override
    public List<ProductDTO> getAllProducts() {
        List<Product> products = productRepository.findAll();
        List<ProductDTO> productDTOs = new ArrayList<>();
        for (Product product : products) {
            ProductDTO productDTO = new ProductDTO();
            productDTO.setId(product.getId());
            productDTO.setName(product.getName());
            productDTO.setPrice(product.getPrice());
            productDTO.setDescription(product.getDescription());
            productDTO.setImage(product.getImage());
            productDTO.setCategoryId(product.getCategory().getId());
            productDTO.setCategoryName(product.getCategory().getName());
            productDTOs.add(productDTO);
        }
        return productDTOs;
    }

    @Override
    public List<Product> getTop8ProductsByCategoryId(Long categoryId) {
        Pageable pageable = PageRequest.of(0, 8);
        return productRepository.getTop8ProductsByCategory_Id(categoryId, pageable);
    }


    @Override
    public ProductDTO getProductById(Long id) {
        Optional<Product> product = productRepository.findById(id);
        if (product.isPresent()) {
            ProductDTO productDTO = new ProductDTO();
            productDTO.setId(product.get().getId());
            productDTO.setName(product.get().getName());
            productDTO.setPrice(product.get().getPrice());
            productDTO.setQuantity(product.get().getQuantity());
            productDTO.setDescription(product.get().getDescription());
            productDTO.setImage(product.get().getImage());
            productDTO.setCategoryId(product.get().getCategory().getId());
            productDTO.setCategoryName(product.get().getCategory().getName());
            productDTO.setProducerId(product.get().getProducer().getId());
            productDTO.setProducerName(product.get().getProducer().getName());
            return productDTO;
        } else {
            return null;
        }
    }

    @Override
    public void addProduct(ProductDTO productDTO) {
        Product product = new Product();
        product.setName(productDTO.getName());
        product.setPrice(productDTO.getPrice());
        product.setQuantity(productDTO.getQuantity());
        product.setDescription(productDTO.getDescription());
        product.setImage(productDTO.getImage());
        Category category = categoryRepository.findById(productDTO.getCategoryId()).orElse(null);
        if (category != null) {
            product.setCategory(category);
        }
        Producer producer = producerRepository.findById(productDTO.getProducerId()).orElse(null);
        if(producer != null){
            product.setProducer(producer);
        }
        productRepository.save(product);
    }

    @Override
    public void updateProduct(ProductDTO productDTO) {
        Optional<Product> product = productRepository.findById(productDTO.getId());
        if (product.isPresent()) {
            Product p = product.get();
            p.setName(productDTO.getName());
            p.setPrice(productDTO.getPrice());
            p.setDescription(productDTO.getDescription());
            p.setImage(productDTO.getImage());
            Category category = categoryRepository.findById(productDTO.getCategoryId()).orElse(null);
            if (category != null) {
                p.setCategory(category);
            }
            productRepository.save(p);
        }
    }

    @Override
    public void deleteProductById(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public List<ProductDTO> getProductsByCategoryId(Long category_Id) {
        List<Product> products = productRepository.getProductsByCategory_Id(category_Id);
        List<ProductDTO> productDTOs = new ArrayList<>();
        for (Product product : products) {
            ProductDTO productDTO = new ProductDTO();
            productDTO.setId(product.getId());
            productDTO.setName(product.getName());
            productDTO.setPrice(product.getPrice());
            productDTO.setDescription(product.getDescription());
            productDTO.setImage(product.getImage());
            productDTO.setCategoryId(product.getCategory().getId());
            productDTO.setCategoryName(product.getCategory().getName());
            productDTOs.add(productDTO);
        }
        return productDTOs;
    }

    @Override
    public Page<Product> getAllByName(String keyword, Pageable pageable) {
        return productRepository.findByNameContainingIgnoreCase(keyword, pageable);
    }

    @Override
    public Page<Product> getProductsWithCategoryName(String categoryName, Pageable pageable) {
        return productRepository.findByCategory_Name(categoryName, pageable);
    }

    @Override
    public Page<Product> getProductsSortedByPriceAsc(Pageable pageable) {
        return productRepository.findAllByOrderByPriceAsc(pageable);
    }

    @Override
    public Page<Product> getProductsSortedByPriceDesc(Pageable pageable) {
        return productRepository.findAllByOrderByPriceDesc(pageable);
    }

}
