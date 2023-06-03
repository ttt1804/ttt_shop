package com.ttt.ttt_shop.controller.admin;

import com.ttt.ttt_shop.model.dto.CategoryDTO;
import com.ttt.ttt_shop.model.entity.Category;
import com.ttt.ttt_shop.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/admin/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping()
    public String getAll(Model model,
                         @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "5") int size) {
        List<Category> categories = new ArrayList<>();
        Pageable paging = PageRequest.of(page - 1, size);
        Page<Category> categoryPages;
        categoryPages = categoryService.getAll(paging);
        categories = categoryPages.getContent();
        model.addAttribute("categories", categories);
        model.addAttribute("currentPage", categoryPages.getNumber() + 1);
        model.addAttribute("totalItems", categoryPages.getTotalElements());
        model.addAttribute("totalPages", categoryPages.getTotalPages());
        model.addAttribute("pageSize", size);
        return "admin/categories/category-list";
    }

    @GetMapping("/add")
    public String add(Model model) {
        model.addAttribute("category", new CategoryDTO());
        return "admin/categories/category-add";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("category") @Valid CategoryDTO categoryDTO, BindingResult bindingResult, Model model) {
        if(bindingResult.hasErrors()) {
            List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError error : errors) {
                String field = error.getField();
                String errorMessage = error.getDefaultMessage();
                model.addAttribute(field + "Error", errorMessage);
            }
            return "admin/categories/category-add";
        }
        categoryService.add(categoryDTO);
        return "redirect:/admin/categories";
    }

    @PostMapping("/addForProduct")
    public String addCategoryForProduct(@ModelAttribute("category") CategoryDTO category) {
        categoryService.add(category);
        return "redirect:/admin/products/add";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, Model model) {
        CategoryDTO categoryDTO = categoryService.getCategoryById(id);
        if(categoryDTO == null){
            String error = "404";
            model.addAttribute("error", error);
            return "redirect:/admin/categories";
        }else{
            model.addAttribute("category", categoryDTO);
        }
        return "admin/categories/category-edit";
    }

    @PostMapping("/edit")
    public String editCategory(@ModelAttribute("category") @Valid CategoryDTO categoryDTO, BindingResult bindingResult, Model model) {
        if(bindingResult.hasErrors()) {
            List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError error : errors) {
                String field = error.getField();
                String errorMessage = error.getDefaultMessage();
                model.addAttribute(field + "Error", errorMessage);
            }
            return "admin/categories/category-edit";
        }
        categoryService.update(categoryDTO);
        return "redirect:/admin/categories";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id, Model model) {
        CategoryDTO categoryDTO = categoryService.getCategoryById(id);
        if(categoryDTO == null){
            String error = "Thể loại không tồn tại";
            model.addAttribute("error", error);
        }else{
            categoryService.delete(id);
        }
        return "redirect:/admin/categories";
    }
}

