package com.ttt.ttt_shop.controller.admin;

import com.ttt.ttt_shop.model.dto.ProductDTO;
import com.ttt.ttt_shop.model.entity.Product;
import com.ttt.ttt_shop.repository.ProductRepository;
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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

//http://localhost:8080/admin/products
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
    public String getAll(Model model,
                         @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "5") int size) {
        List<Product> products = new ArrayList<Product>();
        Pageable paging = PageRequest.of(page - 1, size);
        Page<Product> pageProducts;
        pageProducts = productService.getAll(paging);
        products = pageProducts.getContent();
        model.addAttribute("products", products);
        model.addAttribute("currentPage", pageProducts.getNumber() + 1);
        model.addAttribute("totalItems", pageProducts.getTotalElements());
        model.addAttribute("totalPages", pageProducts.getTotalPages());
        model.addAttribute("pageSize", size);
        return "admin/products/list";
    }

    @GetMapping("/add")
    public String addProductForm(Model model) {
        model.addAttribute("product", new ProductDTO());
        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("producers", producerService.getAll());
        return "admin/products/add";
    }

    @PostMapping("/add")
    public String addProduct(@ModelAttribute("productDTO") ProductDTO productDTO, @RequestParam("image") MultipartFile image) {
        try {
            String fileName = filesStorageService.save(image);
            productDTO.setImage(fileName);
            productService.addProduct(productDTO);
        } catch (Exception e) {
            return e.getMessage();
        }
        return "redirect:/admin/products";
    }

    @GetMapping("/edit/{id}")
    public String editProductForm(@PathVariable("id") Long id, Model model) {
        ProductDTO product = productService.getProductById(id);
        model.addAttribute("product", product);
        model.addAttribute("categories", categoryService.getAllCategories());
        return "admin/products/edit";
    }

    @PostMapping("/edit")
    public String editProduct(@ModelAttribute("product") ProductDTO productDTO, @RequestParam("image") MultipartFile image) {
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
            productService.updateProduct(productDTO);
        } catch (Exception e) {
            return e.getMessage();
        }
        return "redirect:/admin/products";
    }


    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable("id") Long id) {
        ProductDTO product = productService.getProductById(id);
        String imageFilename = product.getImage();
        productService.deleteProductById(id);
        filesStorageService.delete(imageFilename);
        return "redirect:/admin/products";
    }
}
