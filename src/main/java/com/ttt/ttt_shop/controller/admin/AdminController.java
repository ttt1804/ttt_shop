package com.ttt.ttt_shop.controller.admin;

import com.ttt.ttt_shop.model.entity.CustomerDetail;
import com.ttt.ttt_shop.model.entity.User;
import com.ttt.ttt_shop.repository.OrderRepository;
import com.ttt.ttt_shop.repository.ProductRepository;
import com.ttt.ttt_shop.repository.UserRepository;
import com.ttt.ttt_shop.security.CustomUserDetail;
import com.ttt.ttt_shop.service.OrderService;
import com.ttt.ttt_shop.service.ProductService;
import com.ttt.ttt_shop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
//http://localhost:8081/admin/home
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
    public String homeUser(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();


            // Lấy đối tượng User nếu bạn đã triển khai CustomUserDetail
            CustomUserDetail customUserDetail = (CustomUserDetail) userDetails;
            User user = customUserDetail.getUser();

            CustomerDetail customerDetail = user.getCustomerDetail();


            // Truyền thông tin người dùng vào Model để hiển thị trên giao diện người dùng
            model.addAttribute("user", user);
            model.addAttribute("customerDetail", customerDetail);
            // ... thêm thông tin khác vào Model

            return "site/customer/customer-info";
        } else {
            // Người dùng chưa đăng nhập hoặc không có thông tin người dùng
            return "redirect:/login";
        }
    }
}
