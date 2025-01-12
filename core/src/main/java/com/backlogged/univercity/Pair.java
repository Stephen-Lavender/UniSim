package com.backlogged.univercity;

public class Pair<T, U> {
    
    //Satisfaction Score usage
    public Coord coord;
    public BuildingType type;

    public Pair(Coord coord, BuildingType type) {
        this.coord = coord;
        this.type = type;
    }

    public Coord getMapPos() {
        return this.coord;
    }
    
    public BuildingType getType() {
        return this.type;
    }

    public boolean isboostType(BuildingType BoostType) {
        if (BoostType == null) {
            return false;
        }
        else {
            return BoostType == type;   
        }
    } 
    
    //LeaderBoard usage
    public String name;
    public int score;

    public Pair(String name, int score) {
        this.name = name;
        this.score = score;
    }

    public String name() {
        return this.name;
    }
    
    public int getScore() {
        return this.score;
    }

    public String toString() {
        return this.name  + ": " + this.score;
    }
    
}