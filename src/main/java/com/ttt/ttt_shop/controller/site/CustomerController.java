package com.ttt.ttt_shop.controller.site;

import com.ttt.ttt_shop.model.dto.CustomerDetailDTO;
import com.ttt.ttt_shop.model.entity.*;
import com.ttt.ttt_shop.security.CustomUserDetail;
import com.ttt.ttt_shop.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.text.ParseException;
import java.util.ArrayList;
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
    @Autowired
    ProductService productService;
    @Autowired
    WishListService wishListService;

    @GetMapping("/cart/{product_id}")
    public String addCart(@PathVariable("product_id") Long productId, @AuthenticationPrincipal CustomUserDetail userDetails){
        if(wishListService.checkWishList(userDetails.getUser().getId(), productId)){
            wishListService.deleteByUserIdAndProductId(userDetails.getUser().getId(), productId);
        }
        cartService.addCart(userDetails.getUser().getId(), productId);
        return "redirect:/customer/cart";
    }

    @GetMapping("/wish-list/{product_id}")
    public String addWishList(@PathVariable("product_id") Long productId, @AuthenticationPrincipal CustomUserDetail userDetails){
        wishListService.add(userDetails.getUser().getId(), productId);
        return "redirect:/customer/wish-list";
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
            totalPrice += (cart.getProduct().getPrice() - cart.getProduct().getPrice() * cart.getProduct().getDiscount()/100) * cart.getQuantity();
        }
        model.addAttribute("totalPrice", totalPrice);
        model.addAttribute("shippingPrice", shippingPrice);
        model.addAttribute("carts", carts);
        return "site/customer/cart";
    }
    @GetMapping("/wish-list")
    public String getAllWishList(Model model, @AuthenticationPrincipal CustomUserDetail userDetails){
        List<WishList> wishLists = wishListService.getByUserId(userDetails.getUser().getId());
        model.addAttribute("wishLists", wishLists);
        return "/site/customer/wish-list";
    }

    @GetMapping("/cart/delete/{cart_id}")
    public String deleteCartItem(@PathVariable("cart_id") Long cartId){
        cartService.deleteById(cartId);
        return "redirect:/customer/cart";
    }

    @GetMapping("/wish-list/delete/{wishList_id}")
    public String deleteWishListItem(@PathVariable("wishList_id") Long wishListId ){
        wishListService.deleteById(wishListId);
        return "redirect:/customer/wish-list";
    }
    @ModelAttribute("cartQuantity")
    public int getCartQuantity(HttpSession session,@AuthenticationPrincipal CustomUserDetail userDetails){
        int cartQuantity = cartService.getCartCountByUserId(userDetails.getUser().getId());
        session.setAttribute("cartQuantity", cartQuantity);
        return cartQuantity;
    }
    @ModelAttribute("wishListQuantity")
    public int getWishListQuantity(HttpSession session,@AuthenticationPrincipal CustomUserDetail userDetails){
        int wishListQuantity = wishListService.getWishListCountByUserId(userDetails.getUser().getId());
        session.setAttribute("wishListQuantity", wishListQuantity);
        return wishListQuantity;
    }

    @GetMapping("/checkout")
    public String checkOut(@AuthenticationPrincipal CustomUserDetail userDetails, Model model){
        Long userId = userDetails.getUser().getId();
        List<Cart> carts = cartService.getByUserId(userId);
        if(carts.size() != 0){
            float totalPrice = 0;
            for(Cart cart : carts){
                totalPrice += (cart.getProduct().getPrice() - cart.getProduct().getPrice() * cart.getProduct().getDiscount()/100) * cart.getQuantity();
            }
            orderService.add(userId, totalPrice);

            Order orderJustAdd = orderService.findByUserIdAndStatus(userId, "Ordering");
            if(orderJustAdd != null){
                for(Cart cart : carts){
                    Product product = cart.getProduct();
                    orderItemsService.add(orderJustAdd.getId(), product.getId(), cart.getQuantity());
                    Long productId = cart.getProduct().getId();
                    int quantity = cart.getQuantity();
                    if(productService.updateQuantity(productId, quantity)){
                        cartService.deleteById(cart.getId());
                    }else{
                        String error = "Error";
                    }
                }
                orderService.updateStatus(orderJustAdd.getId(), "Just_Confirmed");
            }
            Order order = orderService.findByUserIdAndStatus(userId, "Just_Confirmed");
            orderService.updateStatus(order.getId(), "Confirm");
            model.addAttribute("totalPrice", totalPrice);
            model.addAttribute("carts", carts);
            model.addAttribute("customerDetail", userDetails.getUser().getCustomerDetail());
        }
        return "site/customer/bill";
    }
    @GetMapping("/bill")
    public String bill(@AuthenticationPrincipal CustomUserDetail userDetails, Model model){
        CustomerDetail customerDetail = userDetails.getUser().getCustomerDetail();
        Long userId = userDetails.getUser().getId();
        float totalPrice;
        Order order = orderService.findByUserIdAndStatus(userId, "Just_Confirmed");
        orderService.updateStatus(order.getId(), "Confirm");
        totalPrice = order.getTotalPrice();
        List<OrderItem> orderItems = order.getOrderItems();

        model.addAttribute("orderItems", orderItems);
        model.addAttribute("customerDetail", customerDetail);
        return "site/customer/bill";
    }
    
    @GetMapping("/purchased")
    public String purchased(Model model, @AuthenticationPrincipal CustomUserDetail userDetails,
                            @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "5") int size){
        List<Order> orders = new ArrayList<>();
        Pageable paging = PageRequest.of(page - 1, size);
        Page<Order> pageOrders;
        pageOrders = orderService.findByUserID(userDetails.getUser().getId(), paging);
        orders = pageOrders.getContent();
        model.addAttribute("orders", orders);
        model.addAttribute("currentPage", pageOrders.getNumber() + 1);
        model.addAttribute("totalItems", pageOrders.getTotalElements());
        model.addAttribute("totalPages", pageOrders.getTotalPages());
        model.addAttribute("pageSize", size);
        
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
        userDetails.setUser(user);
        return "redirect:/customer";
    }

}
