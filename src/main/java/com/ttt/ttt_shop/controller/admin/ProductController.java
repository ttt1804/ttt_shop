package com.ttt.ttt_shop.controller.admin;

import com.ttt.ttt_shop.model.dto.ProductDTO;
import com.ttt.ttt_shop.model.entity.Product;
import com.ttt.ttt_shop.service.CategoryService;
import com.ttt.ttt_shop.service.FileStorageService;
import com.ttt.ttt_shop.service.ProducerService;
import com.ttt.ttt_shop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/admin/products")
public class ProductController {

    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ProducerService producerService;
    @Autowired
    FileStorageService filesStorageService;
    @GetMapping
    public String getAll(Model model, @RequestParam(required = false) String keyword,
                         @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "5") int size) {
        List<Product> products = new ArrayList<>();
        Pageable paging = PageRequest.of(page - 1, size);
        Page<Product> pageProducts;

        if(keyword == null){
            pageProducts = productService.getAll(paging);
        }else{
            pageProducts = productService.getAllByName(keyword, paging);
            model.addAttribute("keyword", keyword);
        }

        products = pageProducts.getContent();
        model.addAttribute("products", products);
        model.addAttribute("currentPage", pageProducts.getNumber() + 1);
        model.addAttribute("totalItems", pageProducts.getTotalElements());
        model.addAttribute("totalPages", pageProducts.getTotalPages());
        model.addAttribute("pageSize", size);
        return "admin/products/product-list";
    }

    @GetMapping("/add")
    public String add(Model model) {
        model.addAttribute("product", new ProductDTO());
        model.addAttribute("categories", categoryService.getAll());
        model.addAttribute("producers", producerService.getAll());
        return "admin/products/product-add";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("productDTO") @Valid  ProductDTO productDTO,BindingResult bindingResult, @RequestParam("image") MultipartFile image, Model model) {
        if(bindingResult.hasErrors()) {
            List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError error : errors) {
                String field = error.getField();
                String errorMessage = error.getDefaultMessage();
                model.addAttribute(field + "Error", errorMessage);
            }
            return "admin/products/product-add";
        }
            String fileName = filesStorageService.save(image);
            productDTO.setImage(fileName);
            productService.add(productDTO);
            return "redirect:/admin/products";
    }

    @GetMapping("/edit/{id}")
    public String editProductForm(@PathVariable("id") Long id, Model model) {
        ProductDTO product = productService.getProductById(id);
        model.addAttribute("product", product);
        model.addAttribute("categories", categoryService.getAll());
        model.addAttribute("producers", producerService.getAll());
        return "admin/products/product-edit";
    }

    @PostMapping("/edit")
    public String edit(@ModelAttribute("product")  @Valid  ProductDTO productDTO,BindingResult bindingResult, @RequestParam("image") MultipartFile image, Model model) {
        if(bindingResult.hasErrors()) {
            List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError error : errors) {
                String field = error.getField();
                String errorMessage = error.getDefaultMessage();
                model.addAttribute(field + "Error", errorMessage);
            }
            model.addAttribute("product", productDTO);
            model.addAttribute("categories", categoryService.getAll());
            model.addAttribute("producers", producerService.getAll());
            return "admin/products/product-edit";
        }
        try {
            if (!image.isEmpty()) {
                // Nếu người dùng chọn file ảnh mới
                ProductDTO oldProduct = productService.getProductById(productDTO.getId());
                String imageFilename = oldProduct.getImage();
                filesStorageService.delete(imageFilename);
                String fileName = filesStorageService.save(image);
                productDTO.setImage(fileName);
            } else {
                // Nếu người dùng không chọn file ảnh mới
                ProductDTO oldProduct = productService.getProductById(productDTO.getId());
                productDTO.setImage(oldProduct.getImage());
            }
            productService.update(productDTO);
        } catch (Exception e) {
            return e.getMessage();
        }
        return "redirect:/admin/products";
    }


    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id) {
        ProductDTO product = productService.getProductById(id);
        String imageFilename = product.getImage();
        productService.delete(id);
        filesStorageService.delete(imageFilename);
        return "redirect:/admin/products";
    }
}
