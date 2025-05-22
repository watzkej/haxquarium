package com.haxquarium.repository;

import com.haxquarium.model.Achievement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for Achievement entities.
 */
@Repository
public interface AchievementRepository extends JpaRepository<Achievement, Long> {
    
    /**
     * Find achievements by category.
     * 
     * @param category The category to search for
     * @return A list of achievements in the given category
     */
    List<Achievement> findByCategory(String category);
    
    /**
     * Find achievements by difficulty.
     * 
     * @param difficulty The difficulty to search for
     * @return A list of achievements with the given difficulty
     */
    List<Achievement> findByDifficulty(Achievement.Difficulty difficulty);
    
    /**
     * Find an achievement by flag UUID.
     * 
     * @param flagUuid The flag UUID to search for
     * @return An Optional containing the achievement if found
     */
    Optional<Achievement> findByFlagUuid(String flagUuid);
}
