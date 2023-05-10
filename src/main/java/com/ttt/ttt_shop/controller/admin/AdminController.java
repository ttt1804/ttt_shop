package com.ttt.ttt_shop.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
//http://localhost:8080/admin/home
public class AdminController {
    @GetMapping("/login")
    public String login() {
        return "admin/login";
    }
//    @PostMapping("/processLogin")
//    public String doLogin() {
//        return "admin/home";
//    }
    @GetMapping("/admin/index")
    public String home(Model model) {
        return "admin/index";
    }
}
