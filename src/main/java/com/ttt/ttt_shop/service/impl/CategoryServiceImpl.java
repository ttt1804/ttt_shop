package com.ttt.ttt_shop.service.impl;

import com.ttt.ttt_shop.model.dto.CategoryDTO;
import com.ttt.ttt_shop.model.entity.Category;
import com.ttt.ttt_shop.repository.CategoryRepository;
import com.ttt.ttt_shop.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<CategoryDTO> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public CategoryDTO getCategoryById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found with id " + id));
        return convertToDTO(category);
    }

    @Override
    public void addCategory(CategoryDTO category) {
        categoryRepository.save(convertToEntity(category));
    }

    @Override
    public void updateCategory(CategoryDTO category) {
        Category existingCategory = categoryRepository.findById(category.getId())
                .orElseThrow(() -> new RuntimeException("Category not found with id " + category.getId()));
        existingCategory.setName(category.getName());
        categoryRepository.save(existingCategory);
    }

    @Override
    public void deleteCategoryById(Long id) {
        categoryRepository.deleteById(id);
    }

    private CategoryDTO convertToDTO(Category category) {
        return new CategoryDTO(category.getId(), category.getName());
    }

    private Category convertToEntity(CategoryDTO category) {
        Category entity = new Category();
        entity.setId(category.getId());
        entity.setName(category.getName());
        return entity;
    }

}
