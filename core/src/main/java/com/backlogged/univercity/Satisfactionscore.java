package com.backlogged.univercity;


public class Satisfactionscore {


    public double calc_Satisfaction(int distanceClosestBuilding, int distanceBoost, BuildingType type) {

        if (distanceClosestBuilding > 10) {
            distanceClosestBuilding = 10;
        }
        if (distanceBoost > 5) {
            distanceBoost = 5;
        }

        double result = (-2.0 / 5.0) * Math.pow(distanceClosestBuilding, 2) + 4 * distanceClosestBuilding + calc_bonus(type, distanceBoost);

        return result;
    }


    public int calc_bonus(BuildingType type, int distanceBoost) {
        int bonusVal = 0;
        switch (type) {
            case ACCOMMODATION:
                bonusVal = (10 - 2 * distanceBoost);
                break;
            case CAFETERIA:
                bonusVal = (10 - 2 * distanceBoost);
                break;
            case COURSE:
                bonusVal = (10 - 2 * distanceBoost);
                break;
            case RECREATIONAL:
                bonusVal = (10 - 2 * distanceBoost);
                break;
            default:
                bonusVal = 0;
                break;
        }

        return bonusVal;


    }
//(-2/5(x^2) + 4(x)) + A(10-2B)


}
