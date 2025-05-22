package com.haxquarium.repository;

import com.haxquarium.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for Order entities.
 */
@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    
    /**
     * Find orders by user ID.
     * 
     * @param userId The user ID to search for
     * @return A list of orders for the given user
     */
    List<Order> findByUserId(Long userId);
    
    /**
     * Find orders by status.
     * 
     * @param status The status to search for
     * @return A list of orders with the given status
     */
    List<Order> findByStatus(Order.OrderStatus status);
    
    /**
     * Find orders by user ID and status.
     * 
     * @param userId The user ID to search for
     * @param status The status to search for
     * @return A list of orders for the given user with the given status
     */
    List<Order> findByUserIdAndStatus(Long userId, Order.OrderStatus status);
}
