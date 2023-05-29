package com.ttt.ttt_shop.controller.site;

import com.ttt.ttt_shop.model.entity.Cart;
import com.ttt.ttt_shop.model.entity.Order;
import com.ttt.ttt_shop.model.entity.Product;
import com.ttt.ttt_shop.security.CustomUserDetail;
import com.ttt.ttt_shop.service.CartService;
import com.ttt.ttt_shop.service.OrderItemsService;
import com.ttt.ttt_shop.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@RequestMapping(value = {"/customer"})

public class CustomerController {

    @Autowired
    CartService cartService;
    @Autowired
    OrderService orderService;
    @Autowired
    OrderItemsService orderItemsService;

    @GetMapping("/cart/{product_id}")
    public String addCart(@PathVariable("product_id") Long productId, @AuthenticationPrincipal CustomUserDetail userDetails){
            cartService.addCart(userDetails.getUser().getId(), productId);
        return "redirect:/customer/cart";
    }
    @GetMapping("/cart/change/{product_id}")
    public String changeQuantity(@PathVariable("product_id") Long productId,@RequestParam String changeType){
        cartService.changeQuantity(productId, changeType);
        return "redirect:/customer/cart";
    }

    @GetMapping("/cart")
    public String getAll(Model model, @AuthenticationPrincipal CustomUserDetail userDetails){
        List<Cart> carts = cartService.getByUserId(userDetails.getUser().getId());
        float totalPrice = 0;
        float shippingPrice = 0;
        for(Cart cart : carts){
            totalPrice += cart.getProduct().getPrice() * cart.getQuantity();
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

    @GetMapping("/checkout")
    public String checkOut(@AuthenticationPrincipal CustomUserDetail userDetails){
        Long userId = userDetails.getUser().getId();
        List<Cart> carts = cartService.getByUserId(userId);
        if(carts.size() != 0){
            float totalPrice = 0;
            for(Cart cart : carts){
                totalPrice += cart.getProduct().getPrice() * cart.getQuantity();
            }
            orderService.add(userId, totalPrice);

            Order orderJustAdd = orderService.findByUserIdAndStatus(userId, false);
            if(orderJustAdd != null){
                for(Cart cart : carts){
                    Product product = cart.getProduct();
                    orderItemsService.add(orderJustAdd.getId(), product.getId(), cart.getQuantity());
                    cartService.deleteById(cart.getId());
                }
                orderService.updateStatus(orderJustAdd.getId());
            }
        }
        return "redirect:/customer/cart";
    }
    
    @GetMapping("/purchased")
    public String purchased(Model model, @AuthenticationPrincipal CustomUserDetail userDetails){
        List<Order> orders = orderService.findById(userDetails.getUser().getId());
        model.addAttribute("orders", orders);
        return "/site/customer/purchased";
    }

}
