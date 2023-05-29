package com.ttt.ttt_shop.service;

import com.ttt.ttt_shop.model.dto.CategoryDTO;

import java.util.List;

public interface CategoryService {
    List<CategoryDTO> getAll();

    CategoryDTO getCategoryById(Long id);

    void add(CategoryDTO categoryDTO);

    void update(CategoryDTO categoryDTO);

    void delete(Long id);
}
