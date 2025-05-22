package com.haxquarium.repository;

import com.haxquarium.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for Category entities.
 */
@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    
    /**
     * Find a category by name.
     * 
     * @param name The name to search for
     * @return An Optional containing the category if found
     */
    Optional<Category> findByName(String name);
}
