package com.ttt.ttt_shop.controller.site;

import com.ttt.ttt_shop.model.dto.ProductDTO;
import com.ttt.ttt_shop.model.dto.UserDTO;
import com.ttt.ttt_shop.model.entity.Product;
import com.ttt.ttt_shop.service.ProductService;
import com.ttt.ttt_shop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Controller
@RequestMapping(value = {"/site"})
// http://localhost:8080/site
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
    public String register(@ModelAttribute("user") @Valid UserDTO userDTO, BindingResult bindingResult, Model model) {
        if(userService.findByEmail(userDTO.getEmail())){
            String exitMailError = "Email đã tồn tại";
            model.addAttribute("exitMailError", exitMailError);
            return "/site/register";
        }
        if(userService.findByUserNameDTO(userDTO.getUsername())){
            String exitUserNameError = "Username đã tồn tại";
            model.addAttribute("exitUserNameError", exitUserNameError);
            return "/site/register";
        }
        if(!userDTO.getRe_password().equals(userDTO.getPassword())){
            String passwordIncorrect = "Mật khẩu không khớp";
            model.addAttribute("passwordIncorrect", passwordIncorrect);
            return "/site/register";
        }
        if (bindingResult.hasErrors()) {
            List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError error : errors) {
                String field = error.getField();
                String errorMessage = error.getDefaultMessage();
                model.addAttribute(field + "Error", errorMessage);
            }
            return "/site/register";
        }
            Random random = new Random();
            int min = 100000;
            int max = 999999;
            int code = random.nextInt(max - min + 1) + min;
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo("trantan1804@gmail.com");
            message.setSubject("Mã kích hoạt tài khoản");
            message.setText("" + code);
            userDTO.setVerificationCode(passwordEncoder.encode(String.valueOf(code)));
             userService.register(userDTO);
            emailSender.send(message);
            String success = "Đăng ký tài khoản thành công, vui lòng nhập mã kích hoạt!";
            model.addAttribute("success", success);
            model.addAttribute("user", userDTO);
            return "/site/verify";
    }

    @PostMapping("/verify")
    public String verify(@ModelAttribute("user") UserDTO userDTO, Model model){
        UserDTO user = userService.findByUserName(userDTO.getUsername());
        if(user != null){
            String verificationCode = userDTO.getVerificationCode();
            if(passwordEncoder.matches(verificationCode, user.getVerificationCode())){
                userService.verify(userDTO);
                String success = "Kích hoạt tài khoản thành công!";
                model.addAttribute("success", success);
                return "admin/login";
            }else{
                String error = "Mã xác minh không đúng. Vui lòng kiểm tra lại";
                model.addAttribute("user", userDTO);
                model.addAttribute("error", error);
                return "/site/verify";
            }
        }
        String errorVerify = "Kích hoạt tài khoản thất bại!";
        model.addAttribute("errorVerify", errorVerify);
        return "admin/login";
    }

    @GetMapping("/forgot-password")
    public String forgotPassword(Model model){
        UserDTO userDTO  = new UserDTO();
        model.addAttribute("user", userDTO);
        return "/site/forgot-password";
    }
    @PostMapping("/forgot-password")
    public String forgotPassword(@ModelAttribute("user") UserDTO userDTO, Model model) {
        if(userService.findUserByUsernameAndEmail(userDTO.getUsername(), userDTO.getEmail()) == null){
            String error = "Tài khoản không tồn tại";
            model.addAttribute("error", error);
            return "/site/forgot-password";
        }
        Random random = new Random();
        int min = 100000;
        int max = 999999;
        int code = random.nextInt(max - min + 1) + min;
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo("trantan1804@gmail.com");
        message.setSubject("Mã xác minh tài khoản");
        message.setText("" + code);
        userService.updateVerificationCode(userDTO.getUsername(), userDTO.getEmail(), passwordEncoder.encode(String.valueOf(code)));
        emailSender.send(message);
        String success = "Vui lòng nhập mã xác minh!";
        model.addAttribute("success", success);
        model.addAttribute("user", userDTO);
        return "/site/reset-password";
    }
    @PostMapping("/reset-password")
    public String resetPassword(@ModelAttribute("user") UserDTO userDTO, Model model){
        if(!userDTO.getRe_password().equals(userDTO.getPassword())){
            String passwordIncorrect = "Mật khẩu không khớp";
            model.addAttribute("passwordIncorrect", passwordIncorrect);
            return "/site/reset-password";
        }
        UserDTO user = userService.findByUserName(userDTO.getUsername());
        if(user != null){
            String verificationCode = userDTO.getVerificationCode();
            if(passwordEncoder.matches(verificationCode, user.getVerificationCode())){
                userService.resetPassword(user.getUsername(), userDTO.getPassword());
                String success = "Đổi mật khẩu thành công!";
                model.addAttribute("success", success);
                return "admin/login";
            }else{
                String error = "Mã xác minh không đúng. Vui lòng kiểm tra lại";
                model.addAttribute("user", userDTO);
                model.addAttribute("error", error);
                return "/site/reset-password";
            }
        }
        String errorResetPassword = "Đổi mật khẩu thất bại!";
        model.addAttribute("errorResetPassword", errorResetPassword);
        return "admin/login";
    }
}
