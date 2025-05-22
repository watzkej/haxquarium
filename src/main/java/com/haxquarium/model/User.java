package com.haxquarium.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * Entity class representing a user in the system.
 */
@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String email;

    private String firstName;
    
    private String lastName;
    
    private boolean enabled = true;
    
    private int points = 0;
    
    @CreationTimestamp
    private LocalDateTime createdAt;
    
    @UpdateTimestamp
    private LocalDateTime updatedAt;
    
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<UserRole> roles = new HashSet<>();
    
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<CartItem> cartItems = new HashSet<>();
    
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<Order> orders = new HashSet<>();
    
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<UserAchievement> achievements = new HashSet<>();
    
    /**
     * Add a role to the user.
     * 
     * @param role The role to add
     */
    public void addRole(UserRole role) {
        roles.add(role);
        role.setUser(this);
    }
    
    /**
     * Add an achievement to the user.
     * 
     * @param achievement The achievement to add
     */
    public void addAchievement(UserAchievement achievement) {
        achievements.add(achievement);
        achievement.setUser(this);
    }
    
    /**
     * Add a cart item to the user's cart.
     * 
     * @param cartItem The cart item to add
     */
    public void addCartItem(CartItem cartItem) {
        cartItems.add(cartItem);
        cartItem.setUser(this);
    }
    
    /**
     * Add an order to the user's orders.
     * 
     * @param order The order to add
     */
    public void addOrder(Order order) {
        orders.add(order);
        order.setUser(this);
    }
}
