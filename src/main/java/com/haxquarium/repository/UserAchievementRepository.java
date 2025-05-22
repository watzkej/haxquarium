package com.haxquarium.repository;

import com.haxquarium.model.UserAchievement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for UserAchievement entities.
 */
@Repository
public interface UserAchievementRepository extends JpaRepository<UserAchievement, Long> {
    
    /**
     * Find user achievements by user ID.
     * 
     * @param userId The user ID to search for
     * @return A list of user achievements for the given user
     */
    List<UserAchievement> findByUserId(Long userId);
    
    /**
     * Find a user achievement by user ID and achievement ID.
     * 
     * @param userId The user ID to search for
     * @param achievementId The achievement ID to search for
     * @return An Optional containing the user achievement if found
     */
    Optional<UserAchievement> findByUserIdAndAchievementId(Long userId, Long achievementId);
    
    /**
     * Check if a user has completed an achievement.
     * 
     * @param userId The user ID to check
     * @param achievementId The achievement ID to check
     * @return True if the user has completed the achievement
     */
    boolean existsByUserIdAndAchievementId(Long userId, Long achievementId);
}
