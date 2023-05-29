package com.ttt.ttt_shop.service.impl;

import com.ttt.ttt_shop.model.dto.CategoryDTO;
import com.ttt.ttt_shop.model.entity.Category;
import com.ttt.ttt_shop.repository.CategoryRepository;
import com.ttt.ttt_shop.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<CategoryDTO> getAll() {
        List<Category> categories = categoryRepository.findAll();
        List<CategoryDTO> categoryDTOs = new ArrayList<>();
        for (Category category : categories) {
            CategoryDTO categoryDTO = convertToDTO(category);
            categoryDTOs.add(categoryDTO);
        }
        return categoryDTOs;
    }

    @Override
    public CategoryDTO getCategoryById(Long id) {
        Category category = categoryRepository.findById(id).orElse(null);
        if(category == null){
            return null;
        }else{
            return convertToDTO(category);
        }
    }

    @Override
    public void add(CategoryDTO categoryDTO) {
        categoryRepository.save(convertToEntity(categoryDTO));
    }

    @Override
    public void update(CategoryDTO categoryDTO) {
        Category category = categoryRepository.findById(categoryDTO.getId()).orElse(null);
        category.setName(categoryDTO.getName());
        categoryRepository.save(category);
    }

    @Override
    public void delete(Long id) {
        categoryRepository.deleteById(id);
    }

    private CategoryDTO convertToDTO(Category category) {
        return new CategoryDTO(category.getId(), category.getName());
    }

    private Category convertToEntity(CategoryDTO categoryDTO) {
        Category entity = new Category();
        entity.setId(categoryDTO.getId());
        entity.setName(categoryDTO.getName());
        return entity;
    }

}
