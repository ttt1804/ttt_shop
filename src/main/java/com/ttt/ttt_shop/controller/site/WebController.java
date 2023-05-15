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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Controller
@RequestMapping(value = {"/site"})
// http://localhost:8081/site
public class WebController {

    @Autowired
    ProductService productService;


    @GetMapping("")
    public String home(Model model){
        List<Product> productsPhone = productService.getTop8ProductsByCategoryId(4L);
        List<Product> productsLaptop = productService.getTop8ProductsByCategoryId(7L);
        List<Product> productsTablet = productService.getTop8ProductsByCategoryId(14L);
        List<Product> productsWatch = productService.getTop8ProductsByCategoryId(8L);
        model.addAttribute("productsPhone", productsPhone);
        model.addAttribute("productsLaptop", productsLaptop);
        model.addAttribute("productsTablet", productsTablet);
        model.addAttribute("productsWatch", productsWatch);
        return "site/index";
    }
    @GetMapping("/products")
    public String getAll(Model model,@RequestParam(required = false) String keyword,
                         @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "9") int size) {
        List<Product> products = new ArrayList<Product>();
        Pageable paging = PageRequest.of(page - 1, size);
        Page<Product> pageProducts;

        if(keyword == null){
            pageProducts = productService.getAll(paging);
        }else{
            pageProducts = productService.getProductsWithCategoryName(keyword, paging);
            model.addAttribute("keyword", keyword);
        }

        products = pageProducts.getContent();
        model.addAttribute("products", products);
        model.addAttribute("currentPage", pageProducts.getNumber() + 1);
        model.addAttribute("totalItems", pageProducts.getTotalElements());
        model.addAttribute("totalPages", pageProducts.getTotalPages());
        model.addAttribute("pageSize", size);
        return "site/shop";
    }

    @GetMapping("/products/detail/{id}")
    public String detailProduct(@PathVariable("id") Long id, Model model){
        ProductDTO product = productService.getProductById(id);
        List<Product> products = productService.getTop8ProductsByCategoryId(product.getCategoryId());
        model.addAttribute("product", product);
        model.addAttribute("products", products);
        return "site/detail";
    }
    @GetMapping("/products/sort")
    public String getProductsSortedByPrice(Model model,
                                           @RequestParam(required = false) String keyword,
                                           @RequestParam(defaultValue = "1") int page,
                                           @RequestParam(defaultValue = "9") int size,
                                           @RequestParam String sortType) {

        List<Product> products = new ArrayList<Product>();
        Pageable paging = PageRequest.of(page - 1, size);
        Page<Product> pageProducts;

        if (sortType.equals("asc")) {
            pageProducts = productService.getProductsSortedByPriceAsc(paging);
            model.addAttribute("sortType", sortType);
        } else {
            pageProducts = productService.getProductsSortedByPriceDesc(paging);
            model.addAttribute("sortType", sortType);
        }

        products = pageProducts.getContent();
        model.addAttribute("products", products);
        model.addAttribute("currentPage", pageProducts.getNumber() + 1);
        model.addAttribute("totalItems", pageProducts.getTotalElements());
        model.addAttribute("totalPages", pageProducts.getTotalPages());
        model.addAttribute("pageSize", size);
        return "site/shop";
    }

}
