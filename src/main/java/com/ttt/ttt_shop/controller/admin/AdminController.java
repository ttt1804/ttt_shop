package com.ttt.ttt_shop.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
//http://localhost:8080/admin/home
public class AdminController {
    @GetMapping("/login")
    public String login() {
        return "admin/login";
    }

    @GetMapping("/admin/index")
    public String home(Model model) {
        return "admin/index";
    }

    @GetMapping("/site/customer")
    public String homeUser(Model model) {
        return "site/customer/index";
    }
}
