package com.backlogged.univercity;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


    


public class SatisfactionScore {
    int score = 0;
    List<Coord> sceneryCoords = new ArrayList<>();
    Collection<Building> buildings;

    //simple class to hold coordinates and type of each building

    public BuildingType getBoostType(BuildingType type){
        switch (type) {
            case ACCOMMODATION:
                return BuildingType.COURSE;
            case CAFETERIA:
                return BuildingType.RECREATIONAL;
            case COURSE:
                return BuildingType.CAFETERIA;
            case RECREATIONAL:
                return null;
            default:
                return null;
        }
    }



    public void updateSatisfaction(Collection<Building> buildings) {
        List<Double> scores = new ArrayList<>();
        double total = 0;
        this.buildings = buildings;


        
        for (Pair<Coord,BuildingType> pair : convertToPairList(buildings)) {
            double test2 = calc_Satisfaction(convertToPairList(buildings), pair);
            scores.add(test2);
            total += test2;
        }
        
        // immposed building limit: 20x * (100 - ((x^1.4) - 15)) / 100
        
        
        int x = buildings.size();

        if (x >=5){
            double multiplier = (100 - (100 * (Math.exp(0.0231 * x) - 1)) + 12)/100;
            total = total*multiplier;
        }
        if (total < 0) {
            total = 0;
        }
        this.score = (int) total/2;        

    }

    //returns the score of a building
    public double getScore(Building building) {
        double total = 0;
        for (Pair<Coord,BuildingType> pair : convertToPairList(building)) {
            double test2 = calc_Satisfaction(convertToPairList(buildings), pair);
            total += test2;
        }
        return total;
    }

    public List<Pair<Coord, BuildingType>> convertToPairList(Collection<Building> buildings) {
        
        List<Pair<Coord, BuildingType>> pairList = new ArrayList<>();

        for (Building building : buildings) {
            for (BuildingType type :building.getType()) {                
                pairList.add(new Pair<>(building.getMapPos(),type));
            }
        }
        return pairList;
    }

    public List<Pair<Coord, BuildingType>> convertToPairList(Building building) {
        
        List<Pair<Coord, BuildingType>> pairList = new ArrayList<>();

            for (BuildingType type :building.getType()) {                
                pairList.add(new Pair<>(building.getMapPos(),type));
        }
        return pairList;
    }
    
    public double calcDistanceClosestBoostBuilding(List<Pair<Coord,BuildingType>> buildings, Pair<Coord,BuildingType> BuildingPair) {
        double distance = 1000;
            for (Pair<Coord,BuildingType> building : buildings) {
                if (!building.getMapPos().equals(BuildingPair.getMapPos()) && building.isboostType(getBoostType(BuildingPair.getType()))) {
                    if (distanceCalc(BuildingPair.getMapPos(), building.getMapPos()) < distance) {
                        distance = distanceCalc(BuildingPair.getMapPos(), building.getMapPos());
                    }
                }
            }

        return Math.round(distance);
    }

    public double calcDistanceClosestScenery(Pair<Coord,BuildingType> BuildingPair) {
        double distance = 1000;
        sceneryCoords.add(new Coord(1, 1));
        for (Coord scenery : sceneryCoords) {
            if (distanceCalc(BuildingPair.getMapPos(),scenery) < distance)  {
                distance = distanceCalc(BuildingPair.getMapPos(),scenery);
            }
        }
        return Math.round(distance);
    }

    public double calcDistanceClosestBuilding(List<Pair<Coord,BuildingType>> buildings, Pair<Coord,BuildingType> BuildingPair) {
        double distance = 1000;
        for (Pair<Coord,BuildingType> building : buildings) {
            if (!building.getMapPos().equals(BuildingPair.getMapPos()) && !building.isboostType(getBoostType(BuildingPair.getType()))) {
                if (distanceCalc(BuildingPair.getMapPos(), building.getMapPos()) < distance) {
                    distance = distanceCalc(BuildingPair.getMapPos(), building.getMapPos());
                }
            }
        }
        return Math.round(distance);

    }


    public double distanceCalc(Coord locA, Coord locB) {        
        return Math.sqrt(Math.pow(locB.getColumn() - locA.getColumn(), 2) + Math.pow(locB.getRow() - locA.getRow(), 2));
    }

    public double calc_Satisfaction(List<Pair<Coord,BuildingType>> buildings,Pair<Coord,BuildingType> currentBuilding) {
        
        double distanceClosestBoost = 1000;
        double distanceClosestBuilding = calcDistanceClosestBuilding(buildings, currentBuilding);
        if (currentBuilding.getType() == BuildingType.RECREATIONAL) {
            distanceClosestBoost = calcDistanceClosestScenery(currentBuilding);
           }
        else {
            distanceClosestBoost = calcDistanceClosestBoostBuilding(buildings, currentBuilding);
        }
        if (distanceClosestBuilding > 10) {
            distanceClosestBuilding = 10;
        }
        if (distanceClosestBoost > 10) {
            distanceClosestBoost = 10;
        }
        //does this formula -2/5(x^2) + 4(x) + (bonus)
        double result = (-2.0 / 5.0) * Math.pow(distanceClosestBuilding, 2) + 4 * distanceClosestBuilding + calc_bonus(currentBuilding.getType(), distanceClosestBoost);

        return result;
    }

    //Calculates bonus for score based on what type of building it is
    public double calc_bonus(BuildingType type, double distanceBoost) {
        double bonusVal = 0;
        switch (type) {
            case ACCOMMODATION:
                bonusVal = (10 - distanceBoost);
                break;
            case CAFETERIA:
                bonusVal = (10 - distanceBoost);
                break;
            case COURSE:
                bonusVal = (10 - distanceBoost);
                break;
            case RECREATIONAL:
                bonusVal = (10 - distanceBoost);
                break;
            default:
                bonusVal = 0;
                break;
        }

        return bonusVal;


    }


}
