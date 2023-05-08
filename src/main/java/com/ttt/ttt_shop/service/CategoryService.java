package com.ttt.ttt_shop.service;

import com.ttt.ttt_shop.model.dto.CategoryDTO;

import java.util.List;

public interface CategoryService {
    List<CategoryDTO> getAllCategories();

    CategoryDTO getCategoryById(Long id);

    void addCategory(CategoryDTO category);

    void updateCategory(CategoryDTO category);

    void deleteCategoryById(Long id);
}
