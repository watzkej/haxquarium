package com.haxquarium.repository;

import com.haxquarium.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for User entities.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
    /**
     * Find a user by username.
     * 
     * @param username The username to search for
     * @return An Optional containing the user if found
     */
    Optional<User> findByUsername(String username);
    
    /**
     * Find a user by email.
     * 
     * @param email The email to search for
     * @return An Optional containing the user if found
     */
    Optional<User> findByEmail(String email);
    
    /**
     * Check if a user exists with the given username.
     * 
     * @param username The username to check
     * @return True if a user exists with the given username
     */
    boolean existsByUsername(String username);
    
    /**
     * Check if a user exists with the given email.
     * 
     * @param email The email to check
     * @return True if a user exists with the given email
     */
    boolean existsByEmail(String email);
}
