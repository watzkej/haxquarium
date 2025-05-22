package com.haxquarium.service;

import com.haxquarium.model.Product;
import com.haxquarium.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service for managing products.
 */
@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    
    /**
     * Find all products.
     * 
     * @return A list of all products
     */
    public List<Product> findAll() {
        return productRepository.findAll();
    }
    
    /**
     * Find a product by ID.
     * 
     * @param id The ID to search for
     * @return An Optional containing the product if found
     */
    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }
    
    /**
     * Find products by category ID.
     * 
     * @param categoryId The category ID to search for
     * @return A list of products in the given category
     */
    public List<Product> findByCategoryId(Long categoryId) {
        return productRepository.findByCategoryId(categoryId);
    }
    
    /**
     * Search for products by name or description.
     * 
     * @param keyword The keyword to search for
     * @return A list of products matching the search
     */
    public List<Product> search(String keyword) {
        return productRepository.findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(keyword, keyword);
    }
    
    /**
     * Search for products using a vulnerable SQL query.
     * This method is intentionally vulnerable to SQL injection.
     * 
     * @param query The search query
     * @return A list of products matching the query
     */
    public List<Product> searchVulnerable(String query) {
        return productRepository.searchByQuery(query);
    }
    
    /**
     * Save a product.
     * 
     * @param product The product to save
     * @return The saved product
     */
    @Transactional
    public Product save(Product product) {
        return productRepository.save(product);
    }
    
    /**
     * Delete a product.
     * 
     * @param id The ID of the product to delete
     */
    @Transactional
    public void delete(Long id) {
        productRepository.deleteById(id);
    }
    
    /**
     * Update product stock.
     * 
     * @param id The ID of the product to update
     * @param quantity The quantity to subtract from stock
     * @return The updated product
     */
    @Transactional
    public Product updateStock(Long id, int quantity) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));
        
        if (product.getStock() < quantity) {
            throw new IllegalArgumentException("Not enough stock");
        }
        
        product.setStock(product.getStock() - quantity);
        return productRepository.save(product);
    }
}
