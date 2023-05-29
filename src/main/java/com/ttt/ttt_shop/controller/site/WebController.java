package com.ttt.ttt_shop.controller.site;

import com.ttt.ttt_shop.model.dto.ProductDTO;
import com.ttt.ttt_shop.model.dto.UserDTO;
import com.ttt.ttt_shop.model.entity.Product;
import com.ttt.ttt_shop.model.entity.User;
import com.ttt.ttt_shop.repository.UserRepository;
import com.ttt.ttt_shop.service.ProductService;
import com.ttt.ttt_shop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Controller
@RequestMapping(value = {"/site"})
// http://localhost:8081/site
public class WebController {

    @Autowired
    ProductService productService;

    @Autowired
    UserService userService;

    @Autowired
    JavaMailSender emailSender;
    @Autowired
    PasswordEncoder passwordEncoder;

    @GetMapping("")
    public String home(Model model){
        List<Product> productsPhone = productService.getTop8ProductsByCategoryId(4L);
        List<Product> productsLaptop = productService.getTop8ProductsByCategoryId(7L);
        List<Product> productsTablet = productService.getTop8ProductsByCategoryId(14L);
        List<Product> productsWatch = productService.getTop8ProductsByCategoryId(8L);

        Long countPhone = productService.countProductsByCategoryName("Phone");
        Long countLaptop = productService.countProductsByCategoryName("Laptop");
        Long countTablet = productService.countProductsByCategoryName("Tablet");
        Long countWatch = productService.countProductsByCategoryName("Watch");

        model.addAttribute("countPhone", countPhone);
        model.addAttribute("countLaptop", countLaptop);
        model.addAttribute("countTablet", countTablet);
        model.addAttribute("countWatch", countWatch);

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

    @GetMapping("/register")
    public String register(Model model){
        model.addAttribute("user", new UserDTO());
        return "/site/register";
    }
    @PostMapping("/register")
    public String register(@ModelAttribute("user") UserDTO userDTO, Model model) {
        Random random = new Random();
        int min = 100000;
        int max = 999999;
        int code = random.nextInt(max - min + 1) + min;
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo("trantan1804@gmail.com");
        message.setSubject("Mã kích hoạt tài khoản");
        message.setText("" + code);
        userDTO.setVerificationCode(passwordEncoder.encode(String.valueOf(code)));
        emailSender.send(message);
        userService.register(userDTO);
        model.addAttribute("user", userDTO);
        return "/site/verify";
    }

    @PostMapping("/verify")
    public String verify(@ModelAttribute("user") UserDTO userDTO){
        UserDTO user = userService.findByUserName(userDTO.getUsername());
        if(user != null){
            String verificationCode = userDTO.getVerificationCode();
            if(passwordEncoder.matches(verificationCode, user.getVerificationCode())){
                userService.verify(userDTO);
            }
        }
        return "redirect:/login";
    }
}
