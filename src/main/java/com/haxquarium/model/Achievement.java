package com.haxquarium.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

/**
 * Entity class representing an achievement in the system.
 */
@Entity
@Table(name = "achievements")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Achievement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String name;
    
    @Column(columnDefinition = "TEXT")
    private String description;
    
    private int points;
    
    @Column(nullable = false)
    private String category;
    
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Difficulty difficulty;
    
    @Column(nullable = false, unique = true)
    private String flagUuid;
    
    @OneToMany(mappedBy = "achievement", cascade = CascadeType.ALL)
    private Set<UserAchievement> userAchievements = new HashSet<>();
    
    /**
     * Enum representing the difficulty of an achievement.
     */
    public enum Difficulty {
        EASY,
        MEDIUM,
        HARD
    }
}
