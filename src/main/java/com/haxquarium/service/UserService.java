package com.haxquarium.service;

import com.haxquarium.model.User;
import com.haxquarium.model.UserRole;
import com.haxquarium.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service for managing users.
 */
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    
    /**
     * Find all users.
     * 
     * @return A list of all users
     */
    public List<User> findAll() {
        return userRepository.findAll();
    }
    
    /**
     * Find a user by ID.
     * 
     * @param id The ID to search for
     * @return An Optional containing the user if found
     */
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }
    
    /**
     * Find a user by username.
     * 
     * @param username The username to search for
     * @return An Optional containing the user if found
     */
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
    
    /**
     * Find a user by email.
     * 
     * @param email The email to search for
     * @return An Optional containing the user if found
     */
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
    
    /**
     * Check if a user exists with the given username.
     * 
     * @param username The username to check
     * @return True if a user exists with the given username
     */
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }
    
    /**
     * Check if a user exists with the given email.
     * 
     * @param email The email to check
     * @return True if a user exists with the given email
     */
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }
    
    /**
     * Register a new user.
     * 
     * @param user The user to register
     * @return The registered user
     */
    @Transactional
    public User register(User user) {
        // Encode the password
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        
        // Add the default role
        UserRole role = new UserRole();
        role.setRole("ROLE_USER");
        user.addRole(role);
        
        // Save the user
        return userRepository.save(user);
    }
    
    /**
     * Update a user.
     * 
     * @param user The user to update
     * @return The updated user
     */
    @Transactional
    public User update(User user) {
        return userRepository.save(user);
    }
    
    /**
     * Delete a user.
     * 
     * @param id The ID of the user to delete
     */
    @Transactional
    public void delete(Long id) {
        userRepository.deleteById(id);
    }
    
    /**
     * Add points to a user.
     * 
     * @param userId The ID of the user to add points to
     * @param points The number of points to add
     * @return The updated user
     */
    @Transactional
    public User addPoints(Long userId, int points) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        
        user.setPoints(user.getPoints() + points);
        return userRepository.save(user);
    }
}
