package com.ttt.ttt_shop.controller.admin;

import com.ttt.ttt_shop.model.entity.CustomerDetail;
import com.ttt.ttt_shop.model.entity.User;
import com.ttt.ttt_shop.security.CustomUserDetail;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
//http://localhost:8081/admin/home
public class AdminController {
    @GetMapping("/login")
    public String login() {
        return "admin/login";
    }

    @GetMapping("/admin/index")
    public String home(Model model) {
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
