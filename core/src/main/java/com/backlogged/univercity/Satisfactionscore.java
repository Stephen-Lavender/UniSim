package com.backlogged.univercity;


public class SatisfactionScore {


    public int calc_satisfaction(int distance_closest_building, int distance_to_boost) {

        if (distance_closest_building > 10) {
            distance_closest_building = 10;
        }
        if (distance_to_boost > 5) {
            distance_to_boost = 5;
        }

        double result = (-2.0 / 5.0) * Math.pow(distance_closest_building, 2) + 4 * distance_closest_building + (10 - 2 * distance_to_boost);

        return distance_closest_building;
    }
//(-2/5(x^2) + 4(x)) + A(10-2B)


}
