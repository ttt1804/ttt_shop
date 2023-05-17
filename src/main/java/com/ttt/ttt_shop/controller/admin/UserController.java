package com.ttt.ttt_shop.controller.admin;


import com.ttt.ttt_shop.model.entity.CustomerDetail;
import com.ttt.ttt_shop.service.CustomerDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping(value = {"/admin/users"})
public class UserController {
    @Autowired
    private CustomerDetailService customerDetailService;

    @GetMapping()
    public String listProducers(Model model){
        List<CustomerDetail> customerDetails = customerDetailService.getAll();
        model.addAttribute("customerDetails", customerDetails);
        return "admin/users/user-list";
    }
}
