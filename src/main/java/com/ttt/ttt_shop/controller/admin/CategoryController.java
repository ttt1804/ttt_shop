package com.ttt.ttt_shop.controller.admin;

import com.ttt.ttt_shop.model.dto.CategoryDTO;
import com.ttt.ttt_shop.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping()
    public String listCategories(Model model) {
        List<CategoryDTO> categories = categoryService.getAllCategories();
        model.addAttribute("categories", categories);
        return "admin/categories/category-list";
    }

    @GetMapping("/add")
    public String addCategoryForm(Model model) {
        model.addAttribute("category", new CategoryDTO());
        return "admin/categories/category-add";
    }

    @PostMapping("/add")
    public String addCategory(@ModelAttribute("category") CategoryDTO category) {
        categoryService.addCategory(category);
        return "redirect:/admin/categories";
    }
    @PostMapping("/addForProduct")
    public String addCategoryForProduct(@ModelAttribute("category") CategoryDTO category) {
        categoryService.addCategory(category);
//        return "redirect:/admin/categories";
        return "redirect:/admin/products/add";
    }

    @GetMapping("/edit/{id}")
    public String editCategoryForm(@PathVariable("id") Long id, Model model) {
        CategoryDTO category = categoryService.getCategoryById(id);
        model.addAttribute("category", category);
        return "admin/categories/category-edit";
    }

    @PostMapping("/edit")
    public String editCategory(@ModelAttribute("category") CategoryDTO category) {
        categoryService.updateCategory(category);
        return "redirect:/admin/categories";
    }

    @GetMapping("/delete/{id}")
    public String deleteCategory(@PathVariable("id") Long id) {
        categoryService.deleteCategoryById(id);
        return "redirect:/admin/categories";
    }
}

