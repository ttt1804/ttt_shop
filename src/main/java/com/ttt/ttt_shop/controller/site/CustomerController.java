package com.ttt.ttt_shop.controller.site;

import com.ttt.ttt_shop.model.entity.Cart;
import com.ttt.ttt_shop.model.entity.Product;
import com.ttt.ttt_shop.security.CustomUserDetail;
import com.ttt.ttt_shop.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;


@Controller
@RequestMapping(value = {"/customer"})

public class CustomerController {

    @Autowired
    CartService cartService;

    @GetMapping("/cart/{product_id}")
    public String addCart(@PathVariable("product_id") Long productId, @AuthenticationPrincipal CustomUserDetail userDetails){
            cartService.addCart(userDetails.getUser().getId(), productId);
        return "redirect:/customer/cart";
    }

    @GetMapping("/cart")
    public String getAll(Model model, @AuthenticationPrincipal CustomUserDetail userDetails){
        List<Cart> carts = cartService.getByUserId(userDetails.getUser().getId());
        float totalPrice = 0;
        float shippingPrice = 0;
        for(Cart cart : carts){
            totalPrice += cart.getProduct().getPrice();
        }
        model.addAttribute("totalPrice", totalPrice);
        model.addAttribute("shippingPrice", shippingPrice);
        model.addAttribute("carts", carts);
        return "site/customer/cart";
    }

    @GetMapping("/cart/delete/{cart_id}")
    public String deleteCartItem(@PathVariable("cart_id") Long cartId){
        cartService.deleteById(cartId);
        return "redirect:/customer/cart";
    }




}
