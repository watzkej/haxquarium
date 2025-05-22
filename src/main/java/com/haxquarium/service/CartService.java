package com.haxquarium.service;

import com.haxquarium.model.CartItem;
import com.haxquarium.model.Product;
import com.haxquarium.model.User;
import com.haxquarium.repository.CartItemRepository;
import com.haxquarium.repository.ProductRepository;
import com.haxquarium.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * Service for managing shopping carts.
 */
@Service
@RequiredArgsConstructor
public class CartService {

    private final CartItemRepository cartItemRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    
    /**
     * Get all cart items for a user.
     * 
     * @param userId The ID of the user
     * @return A list of cart items
     */
    public List<CartItem> getCartItems(Long userId) {
        return cartItemRepository.findByUserId(userId);
    }
    
    /**
     * Add a product to the cart.
     * 
     * @param userId The ID of the user
     * @param productId The ID of the product
     * @param quantity The quantity to add
     * @return The cart item
     */
    @Transactional
    public CartItem addToCart(Long userId, Long productId, int quantity) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));
        
        Optional<CartItem> existingItem = cartItemRepository.findByUserIdAndProductId(userId, productId);
        
        if (existingItem.isPresent()) {
            CartItem item = existingItem.get();
            item.setQuantity(item.getQuantity() + quantity);
            return cartItemRepository.save(item);
        } else {
            CartItem newItem = new CartItem(user, product, quantity);
            return cartItemRepository.save(newItem);
        }
    }
    
    /**
     * Update the quantity of a cart item.
     * 
     * @param cartItemId The ID of the cart item
     * @param quantity The new quantity
     * @return The updated cart item
     */
    @Transactional
    public CartItem updateQuantity(Long cartItemId, int quantity) {
        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new IllegalArgumentException("Cart item not found"));
        
        cartItem.setQuantity(quantity);
        return cartItemRepository.save(cartItem);
    }
    
    /**
     * Remove a cart item.
     * 
     * @param cartItemId The ID of the cart item to remove
     */
    @Transactional
    public void removeFromCart(Long cartItemId) {
        cartItemRepository.deleteById(cartItemId);
    }
    
    /**
     * Clear the cart for a user.
     * 
     * @param userId The ID of the user
     */
    @Transactional
    public void clearCart(Long userId) {
        cartItemRepository.deleteByUserId(userId);
    }
    
    /**
     * Calculate the total price of the cart.
     * 
     * @param userId The ID of the user
     * @return The total price
     */
    public BigDecimal calculateTotal(Long userId) {
        List<CartItem> cartItems = cartItemRepository.findByUserId(userId);
        
        return cartItems.stream()
                .map(item -> item.getProduct().getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
