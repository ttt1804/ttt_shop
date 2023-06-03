package com.ttt.ttt_shop.controller.admin;


import com.ttt.ttt_shop.model.entity.Order;
import com.ttt.ttt_shop.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/admin/orders")
public class OrderController {

    @Autowired
    OrderService orderService;

    @GetMapping()
    public String getAll(Model model,
    @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "5") int size){
        List<Order> orders = new ArrayList<>();
        Pageable paging = PageRequest.of(page - 1, size);
        Page<Order> orderPage;
        orderPage = orderService.getAll(paging);
        orders = orderPage.getContent();
        model.addAttribute("currentPage", orderPage.getNumber() + 1);
        model.addAttribute("totalItems", orderPage.getTotalElements());
        model.addAttribute("totalPages", orderPage.getTotalPages());
        model.addAttribute("pageSize", size);
        model.addAttribute("orders", orders);
        return "admin/orders/order-list";
    }
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, Model model) {
        Order order = orderService.findById(id);
        model.addAttribute("order", order);
        return "admin/orders/order-edit";
    }

    @PostMapping("/edit")
    public String edit(@ModelAttribute("order") Order order,Model model) {
        orderService.updateStatus(order.getId(), order.getStatus());
        return "redirect:/admin/orders";
    }
}
