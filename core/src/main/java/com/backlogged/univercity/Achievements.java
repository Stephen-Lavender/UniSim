package com.backlogged.univercity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * Represents the Achievements system in a game.
 */
public class Achievements {
    private List<Building> buildings;
    private long satScore;
    private List<Achievement> achievements;

    public String getAchievementDesc(String name) {
        for (Achievement achievement : achievements) {

            if (achievement.name.equals(name)) {
                return achievement.description;
            }
        }
        return "";
    }


    
    public void updateData(List<Building> buildings, long satScore) {
        this.buildings = buildings;
        this.satScore = satScore;
    }

    //runs all the checks for achievements
    public String checkall() {    
        for (Achievement Achievement: achievements) {
            if (!Achievement.unlocked) {
                switch (Achievement.name) {
                    case "Building Novice":
                        if(this.buildings.size() >= 1) {
                            Achievement.unlocked = true;
                            return Achievement.name;
                        }
                        break;
                        
                    case "Happy Campus I":
                        if (this.satScore>25) {
                            return Achievement.name;
                        }
                    case "Happy Campus II":
                        if (this.satScore>50) {
                            return Achievement.name;
                        }
                    case "Happy Campus III":
                        if (this.satScore>75) {
                            return Achievement.name;
                        }
                    case "Happy Campus IIII":
                        if (this.satScore>1000) {
                            return Achievement.name;
                        }
                    case "Building pro":
                    if(this.buildings.size() >= 10) {
                        Achievement.unlocked = true;
                        return Achievement.name;
                    }
                    break;
                    case "Building No11vice":
                    if(this.buildings.size() >= 1) {
                        Achievement.unlocked = true;
                        return Achievement.name;
                    }
                    break;
                    case "Building No111vice":
                    if(this.buildings.size() >= 1) {
                        Achievement.unlocked = true;
                        return Achievement.name;
                    }
                    break;
                default:
                    break;
                }
            }
        }
        return "";
    }


    // Inner class representing a single Achievement
    public static class Achievement {
        private String name;
        private String description;
        private boolean unlocked;

        public Achievement(String name, String description) {
            this.name = name;
            this.description = description;
            this.unlocked = false;
        }

        public String getName() {
            return name;
        }

        public String getDescription() {
            return description;
        }

        public boolean isUnlocked() {
            return unlocked;
        }

        public void unlock() {
            this.unlocked = true;
        }
    }

    // Constructor
    public Achievements() {
        this.buildings = new ArrayList<>();
        this.satScore = 0;

        achievements = new ArrayList<>();
        achievements.add(new Achievement("Building Novice","Place one building"));
        achievements.add(new Achievement("Happy Campus I","25% Satisfaction score"));
        achievements.add(new Achievement("Happy Campus II","50% Satisfaction score"));
        achievements.add(new Achievement("Happy Campus III","75% Satisfaction score"));
        achievements.add(new Achievement("Happy Campus IIII","100% Satisfaction score"));
        achievements.add(new Achievement("Building Pro","Place 10 buildings"));
    }

    // Unlock an achievement by name
    public boolean unlockAchievement(String name) {
        for (Achievement achievement : achievements) {
            if (achievement.getName().equalsIgnoreCase(name) && !achievement.isUnlocked()) {
                achievement.unlock();
                return true;
            }
        }
        return false;
    }

    // Get a list of all achievements
    public List<Achievement> getAllAchievements() {
        return achievements;
    }

    // Get a list of unlocked achievements
    public List<Achievement> getUnlockedAchievements() {
        List<Achievement> unlockedAchievements = new ArrayList<>();
        for (Achievement achievement : achievements) {
            if (achievement.isUnlocked()) {
                unlockedAchievements.add(achievement);
            }
        }
        return unlockedAchievements;
    }

    // Get a list of locked achievements
    public List<Achievement> getLockedAchievements() {
        List<Achievement> lockedAchievements = new ArrayList<>();
        for (Achievement achievement : achievements) {
            if (!achievement.isUnlocked()) {
                lockedAchievements.add(achievement);
            }
        }
        return lockedAchievements;
    }
}
