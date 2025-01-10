package com.backlogged.univercity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Represents the Achievements system in a game.
 */
public class Achievements {
    Collection<Building> buildings;
    long satScore;

    public void updateData(List<Building> buildings, long satScore) {
        this.buildings = buildings;
        this.satScore = satScore;
    }

    public void checkall() {
        for (Achievement Achievement: achievements) {
            if (!Achievement.unlocked) {
                switch (Achievement.name) {
                    case "Building Master":
                        if(this.buildings.size() >= 1) {
                            Achievement.unlocked = true;
                            System.out.println("BuildingMaster unlocked");
                        }
                        break;
                
                    default:
                        break;
                }
            }
        }
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

        @Override
        public String toString() {
            return "Achievement{" +
                   "name='" + name + '\'' +
                   ", description='" + description + '\'' +
                   ", unlocked=" + unlocked +
                   '}';
        }
    }

    private List<Achievement> achievements;

    // Constructor
    public Achievements(Collection<Building> buildings, long satScore) {
        this.buildings = buildings;
        this.satScore = satScore;

        achievements = new ArrayList<>();
        achievements.add(new Achievement("Building Master","Max buildings placed"));

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

    @Override
    public String toString() {
        return "Achievements{" +
               "achievements=" + achievements +
               '}';
    }


}
