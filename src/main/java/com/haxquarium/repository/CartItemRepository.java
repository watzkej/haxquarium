package com.haxquarium.repository;

import com.haxquarium.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for CartItem entities.
 */
@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    
    /**
     * Find cart items by user ID.
     * 
     * @param userId The user ID to search for
     * @return A list of cart items for the given user
     */
    List<CartItem> findByUserId(Long userId);
    
    /**
     * Find a cart item by user ID and product ID.
     * 
     * @param userId The user ID to search for
     * @param productId The product ID to search for
     * @return An Optional containing the cart item if found
     */
    Optional<CartItem> findByUserIdAndProductId(Long userId, Long productId);
    
    /**
     * Delete all cart items for the given user.
     * 
     * @param userId The user ID to delete cart items for
     */
    void deleteByUserId(Long userId);
}
