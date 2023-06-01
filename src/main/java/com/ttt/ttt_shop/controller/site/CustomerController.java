package com.ttt.ttt_shop.controller.site;

import com.ttt.ttt_shop.model.dto.CustomerDetailDTO;
import com.ttt.ttt_shop.model.entity.Cart;
import com.ttt.ttt_shop.model.entity.Order;
import com.ttt.ttt_shop.model.entity.Product;
import com.ttt.ttt_shop.model.entity.User;
import com.ttt.ttt_shop.security.CustomUserDetail;
import com.ttt.ttt_shop.service.CartService;
import com.ttt.ttt_shop.service.OrderItemsService;
import com.ttt.ttt_shop.service.OrderService;
import com.ttt.ttt_shop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.ParseException;
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
    @Autowired
    UserService userService;

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

    @PostMapping("update-info")
    public String updateInfo(@ModelAttribute("customerDetail") @Valid CustomerDetailDTO customerDetailDTO, BindingResult bindingResult, @AuthenticationPrincipal CustomUserDetail userDetails, Model model) throws ParseException {
        if(bindingResult.hasErrors()){
            List<FieldError> errors = bindingResult.getFieldErrors();
            for(FieldError error : errors){
                String field = error.getField();
                String errorMessage = error.getDefaultMessage();
                model.addAttribute(field + "Error", errorMessage);
            }
            model.addAttribute("user", userDetails.getUser());
            model.addAttribute("customerDetail", userDetails.getUser().getCustomerDetail());
            return "site/customer/customer-info";
        }
        User user =  userService.updateInfo(userDetails.getUser().getId(), customerDetailDTO);
        model.addAttribute("user", user);
        model.addAttribute("customerDetail", user.getCustomerDetail());
        return "site/customer/customer-info";
    }

}
