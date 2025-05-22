package com.haxquarium.service;

import com.haxquarium.model.Achievement;
import com.haxquarium.model.User;
import com.haxquarium.model.UserAchievement;
import com.haxquarium.repository.AchievementRepository;
import com.haxquarium.repository.UserAchievementRepository;
import com.haxquarium.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service for managing achievements.
 */
@Service
@RequiredArgsConstructor
public class AchievementService {

    private final AchievementRepository achievementRepository;
    private final UserAchievementRepository userAchievementRepository;
    private final UserRepository userRepository;
    private final UserService userService;
    
    /**
     * Find all achievements.
     * 
     * @return A list of all achievements
     */
    public List<Achievement> findAll() {
        return achievementRepository.findAll();
    }
    
    /**
     * Find an achievement by ID.
     * 
     * @param id The ID to search for
     * @return An Optional containing the achievement if found
     */
    public Optional<Achievement> findById(Long id) {
        return achievementRepository.findById(id);
    }
    
    /**
     * Find achievements by category.
     * 
     * @param category The category to search for
     * @return A list of achievements in the given category
     */
    public List<Achievement> findByCategory(String category) {
        return achievementRepository.findByCategory(category);
    }
    
    /**
     * Find achievements by difficulty.
     * 
     * @param difficulty The difficulty to search for
     * @return A list of achievements with the given difficulty
     */
    public List<Achievement> findByDifficulty(Achievement.Difficulty difficulty) {
        return achievementRepository.findByDifficulty(difficulty);
    }
    
    /**
     * Find an achievement by flag UUID.
     * 
     * @param flagUuid The flag UUID to search for
     * @return An Optional containing the achievement if found
     */
    public Optional<Achievement> findByFlagUuid(String flagUuid) {
        return achievementRepository.findByFlagUuid(flagUuid);
    }
    
    /**
     * Get all achievements for a user.
     * 
     * @param userId The ID of the user
     * @return A list of user achievements
     */
    public List<UserAchievement> getUserAchievements(Long userId) {
        return userAchievementRepository.findByUserId(userId);
    }
    
    /**
     * Check if a user has completed an achievement.
     * 
     * @param userId The ID of the user
     * @param achievementId The ID of the achievement
     * @return True if the user has completed the achievement
     */
    public boolean hasAchievement(Long userId, Long achievementId) {
        return userAchievementRepository.existsByUserIdAndAchievementId(userId, achievementId);
    }
    
    /**
     * Award an achievement to a user.
     * 
     * @param userId The ID of the user
     * @param achievementId The ID of the achievement
     * @return The user achievement
     */
    @Transactional
    public UserAchievement awardAchievement(Long userId, Long achievementId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        
        Achievement achievement = achievementRepository.findById(achievementId)
                .orElseThrow(() -> new IllegalArgumentException("Achievement not found"));
        
        if (hasAchievement(userId, achievementId)) {
            throw new IllegalArgumentException("User already has this achievement");
        }
        
        UserAchievement userAchievement = new UserAchievement(user, achievement);
        userAchievementRepository.save(userAchievement);
        
        // Add points to the user
        userService.addPoints(userId, achievement.getPoints());
        
        return userAchievement;
    }
    
    /**
     * Award an achievement to a user by flag UUID.
     * 
     * @param userId The ID of the user
     * @param flagUuid The flag UUID of the achievement
     * @return The user achievement
     */
    @Transactional
    public UserAchievement awardAchievementByFlag(Long userId, String flagUuid) {
        Achievement achievement = achievementRepository.findByFlagUuid(flagUuid)
                .orElseThrow(() -> new IllegalArgumentException("Achievement not found"));
        
        return awardAchievement(userId, achievement.getId());
    }
    
    /**
     * Save an achievement.
     * 
     * @param achievement The achievement to save
     * @return The saved achievement
     */
    @Transactional
    public Achievement save(Achievement achievement) {
        return achievementRepository.save(achievement);
    }
    
    /**
     * Delete an achievement.
     * 
     * @param id The ID of the achievement to delete
     */
    @Transactional
    public void delete(Long id) {
        achievementRepository.deleteById(id);
    }
}
