package com.ttt.ttt_shop.controller.site;


import com.ttt.ttt_shop.model.dto.ProductDTO;
import com.ttt.ttt_shop.model.entity.Product;
import com.ttt.ttt_shop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value = {"/site"})
// http://localhost:8080/site
public class WebController {

    @Autowired
    ProductService productService;


    @GetMapping("")
    public String home(Model model){
        List<ProductDTO> productsPhone = productService.getProductsByCategoryId(4L);
        List<ProductDTO> productsLaptop = productService.getProductsByCategoryId(7L);
        List<ProductDTO> productsTablet = productService.getProductsByCategoryId(14L);
        List<ProductDTO> productsWatch = productService.getProductsByCategoryId(8L);
        model.addAttribute("productsPhone", productsPhone);
        model.addAttribute("productsLaptop", productsLaptop);
        model.addAttribute("productsTablet", productsTablet);
        model.addAttribute("productsWatch", productsWatch);
        return "site/index";
    }
    @GetMapping("/products")
    public String getAll(Model model,
                         @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "9") int size) {
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
        return "site/shop";
    }
}
