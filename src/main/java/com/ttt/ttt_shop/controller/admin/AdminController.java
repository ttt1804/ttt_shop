package com.ttt.ttt_shop.controller.admin;

import com.ttt.ttt_shop.model.dto.CustomerDetailDTO;
import com.ttt.ttt_shop.model.entity.CustomerDetail;
import com.ttt.ttt_shop.model.entity.User;
import com.ttt.ttt_shop.security.CustomUserDetail;
import com.ttt.ttt_shop.service.OrderService;
import com.ttt.ttt_shop.service.ProductService;
import com.ttt.ttt_shop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
//http://localhost:8080/admin/home
public class AdminController {
    @Autowired
    ProductService productService;
    @Autowired
    OrderService orderService;
    @Autowired
    UserService userService;
    @GetMapping("/login")
    public String login() {
        return "admin/login";
    }

    @GetMapping("/admin/index")
    public String home(Model model) {
        long totalProducts = productService.getTotalProducts();
        long totalOrders = orderService.getTotalOrders();
        long totalUsers = userService.getTotalCustomers();
        model.addAttribute("totalProducts", totalProducts);
        model.addAttribute("totalOrders", totalOrders);
        model.addAttribute("totalUsers", totalUsers);
        return "admin/index";
    }

    @GetMapping("/customer")
    public String homeUser(Model model, @AuthenticationPrincipal CustomUserDetail userDetails) {
        if(userDetails != null){
            User user = userDetails.getUser();
            CustomerDetail customerDetail = user.getCustomerDetail();
            if(customerDetail == null){
                CustomerDetailDTO customerDetailDTO = new CustomerDetailDTO();
                model.addAttribute("user", user);
                model.addAttribute("customerDetail", customerDetailDTO);
                return "site/customer/customer-info";
            }
            model.addAttribute("user", user);
            model.addAttribute("customerDetail", customerDetail);
            return "site/customer/customer-info";
        } else {
            return "redirect:/login";
        }
    }
}
