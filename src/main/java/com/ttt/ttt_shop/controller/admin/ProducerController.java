package com.ttt.ttt_shop.controller.admin;


import com.ttt.ttt_shop.model.dto.ProducerDTO;
import com.ttt.ttt_shop.model.entity.Producer;
import com.ttt.ttt_shop.service.ProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin/producers")
public class ProducerController {
    @Autowired
    private ProducerService producerService;

    @GetMapping()
    public String listProducers(Model model){
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
    public String addProducer(@ModelAttribute("producer") ProducerDTO producerDTO){
        producerService.addProducer(producerDTO);
        return "redirect:/admin/producers";
    }

    @PostMapping("addForProduct")
    public String addProducerForProduct(@ModelAttribute("producer") ProducerDTO producerDTO){
        producerService.addProducer(producerDTO);
        return "redirect:/admin/products/add";
    }

    @GetMapping("/edit/{id}")
    public String editProducerForm(@PathVariable("id") Long id, Model model) {
        ProducerDTO producerDTO = producerService.getProducerById(id);
        model.addAttribute("producer", producerDTO);
        return "admin/producers/producer-edit";
    }
    @PostMapping("/edit")
    public String editProducer(@ModelAttribute("producer") ProducerDTO producerDTO){
        producerService.updateProducer(producerDTO);
        return "redirect:/admin/producers";
    }

    @GetMapping("/delete/{id}")
    public String deleteProducer(@PathVariable("id") Long id){
        producerService.deleteProducerById(id);
        return "redirect:/admin/producers";
    }
}
