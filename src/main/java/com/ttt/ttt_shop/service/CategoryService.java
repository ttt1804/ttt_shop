package com.ttt.ttt_shop.service;

import com.ttt.ttt_shop.model.dto.CategoryDTO;
import com.ttt.ttt_shop.model.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CategoryService {
    List<CategoryDTO> getAll();

    Page<Category> getAll(Pageable pageable);

    CategoryDTO getCategoryById(Long id);

    void add(CategoryDTO categoryDTO);

    void update(CategoryDTO categoryDTO);

    void delete(Long id);
}
