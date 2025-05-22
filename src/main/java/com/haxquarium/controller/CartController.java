package com.haxquarium.controller;

import com.haxquarium.model.CartItem;
import com.haxquarium.model.User;
import com.haxquarium.service.CartService;
import com.haxquarium.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

/**
 * Controller for cart-related operations.
 */
@Controller
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;
    private final UserService userService;
    
    /**
     * Display the cart.
     * 
     * @param authentication The authentication object
     * @param model The model to add attributes to
     * @return The name of the view to render
     */
    @GetMapping
    public String viewCart(Authentication authentication, Model model) {
        User user = userService.findByUsername(authentication.getName())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        
        List<CartItem> cartItems = cartService.getCartItems(user.getId());
        BigDecimal total = cartService.calculateTotal(user.getId());
        
        model.addAttribute("cartItems", cartItems);
        model.addAttribute("total", total);
        
        return "cart/view";
    }
    
    /**
     * Add a product to the cart.
     * 
     * @param productId The ID of the product
     * @param quantity The quantity to add
     * @param authentication The authentication object
     * @return A redirect to the cart
     */
    @PostMapping("/add")
    public String addToCart(@RequestParam Long productId, @RequestParam(defaultValue = "1") int quantity,
                           Authentication authentication) {
        User user = userService.findByUsername(authentication.getName())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        
        cartService.addToCart(user.getId(), productId, quantity);
        
        return "redirect:/cart";
    }
    
    /**
     * Update the quantity of a cart item.
     * 
     * @param cartItemId The ID of the cart item
     * @param quantity The new quantity
     * @return A redirect to the cart
     */
    @PostMapping("/update")
    public String updateQuantity(@RequestParam Long cartItemId, @RequestParam int quantity) {
        cartService.updateQuantity(cartItemId, quantity);
        return "redirect:/cart";
    }
    
    /**
     * Remove a cart item.
     * 
     * @param cartItemId The ID of the cart item
     * @return A redirect to the cart
     */
    @PostMapping("/remove")
    public String removeFromCart(@RequestParam Long cartItemId) {
        cartService.removeFromCart(cartItemId);
        return "redirect:/cart";
    }
    
    /**
     * Clear the cart.
     * 
     * @param authentication The authentication object
     * @return A redirect to the cart
     */
    @PostMapping("/clear")
    public String clearCart(Authentication authentication) {
        User user = userService.findByUsername(authentication.getName())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        
        cartService.clearCart(user.getId());
        
        return "redirect:/cart";
    }
}
