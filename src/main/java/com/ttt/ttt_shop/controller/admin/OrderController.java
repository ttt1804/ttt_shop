package com.ttt.ttt_shop.controller.admin;


import com.ttt.ttt_shop.model.entity.Order;
import com.ttt.ttt_shop.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin/orders")
public class OrderController {

    @Autowired
    OrderService orderService;

    @GetMapping()
    public String getAll(Model model){
        List<Order> orders = orderService.getAll();
        model.addAttribute("orders", orders);
        return "admin/orders/order-list";
    }
}
