package com.haxquarium.repository;

import com.haxquarium.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for Product entities.
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    
    /**
     * Find products by category ID.
     * 
     * @param categoryId The category ID to search for
     * @return A list of products in the given category
     */
    List<Product> findByCategoryId(Long categoryId);
    
    /**
     * Find products by name containing the given string (case insensitive).
     * 
     * @param name The name to search for
     * @return A list of products with names containing the given string
     */
    List<Product> findByNameContainingIgnoreCase(String name);
    
    /**
     * Find products by description containing the given string (case insensitive).
     * 
     * @param description The description to search for
     * @return A list of products with descriptions containing the given string
     */
    List<Product> findByDescriptionContainingIgnoreCase(String description);
    
    /**
     * Search for products by name or description containing the given string (case insensitive).
     * 
     * @param keyword The keyword to search for
     * @return A list of products with names or descriptions containing the given string
     */
    List<Product> findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(String keyword, String sameKeyword);
    
    /**
     * Vulnerable SQL query for demonstration purposes.
     * This method is intentionally vulnerable to SQL injection.
     * 
     * @param query The search query
     * @return A list of products matching the query
     */
    @Query(value = "SELECT * FROM products WHERE name LIKE '%' || ?1 || '%' OR description LIKE '%' || ?1 || '%'", nativeQuery = true)
    List<Product> searchByQuery(String query);
}
