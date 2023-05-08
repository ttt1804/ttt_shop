package com.ttt.ttt_shop.controller.site;


import com.ttt.ttt_shop.model.dto.ProductDTO;
import com.ttt.ttt_shop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
    public String shop(Model model){
        List<ProductDTO> products = productService.getAllProducts();
        model.addAttribute("products", products);
        return "site/shop";
    }
}
