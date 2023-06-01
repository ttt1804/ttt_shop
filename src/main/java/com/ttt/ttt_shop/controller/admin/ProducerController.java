package com.ttt.ttt_shop.controller.admin;


import com.ttt.ttt_shop.model.dto.ProducerDTO;
import com.ttt.ttt_shop.model.entity.Producer;
import com.ttt.ttt_shop.service.ProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/admin/producers")
public class ProducerController {
    @Autowired
    private ProducerService producerService;

    @GetMapping()
    public String getAll(Model model){
        List<Producer> producers = producerService.getAll();
        model.addAttribute("producers", producers);
        return "admin/producers/producer-list";
    }
    @GetMapping("add")
    public String addProducerForm(Model model){
        model.addAttribute("producer", new ProducerDTO());
        return "admin/producers/producer-add";
    }
    @PostMapping("add")
    public String add(@ModelAttribute("producer") @Valid ProducerDTO producerDTO, BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()) {
            List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError error : errors) {
                String field = error.getField();
                String errorMessage = error.getDefaultMessage();
                model.addAttribute(field + "Error", errorMessage);
            }
            return "admin/producers/producer-add";
        }
        producerService.add(producerDTO);
        return "redirect:/admin/producers";
    }

    @PostMapping("addForProduct")
    public String addProducerForProduct(@ModelAttribute("producer") ProducerDTO producerDTO){
        producerService.add(producerDTO);
        return "redirect:/admin/products/add";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, Model model) {
        ProducerDTO producerDTO = producerService.getProducerById(id);
        if(producerDTO == null){
            String error = "404";
            model.addAttribute("error", error);
            return "redirect:/admin/producers";
        }else{
            model.addAttribute("producer", producerDTO);
        }
        return "admin/producers/producer-edit";
    }
    @PostMapping("/edit")
    public String edit(@ModelAttribute("producer") @Valid ProducerDTO producerDTO, BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()) {
            List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError error : errors) {
                String field = error.getField();
                String errorMessage = error.getDefaultMessage();
                model.addAttribute(field + "Error", errorMessage);
            }
            return "admin/producers/producer-edit";
        }
        producerService.update(producerDTO);
        return "redirect:/admin/producers";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id, Model model){
        ProducerDTO producerDTO = producerService.getProducerById(id);
        if(producerDTO == null){
            String error = "Nhà cung cấp không tồn tại";
            model.addAttribute("error", error);
        }else{
            producerService.deleteProducerById(id);
        }
        return "redirect:/admin/producers";
    }
}
