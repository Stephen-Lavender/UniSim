package com.backlogged.univercity;
import java.util.List;
import java.util.Random;

public class Events {

        int picker;
        Random rand = new Random();
        public boolean WorldEvent(List<Building> buildings) {
            if (buildings.isEmpty()){
                return false;
            }
            picker = rand.nextInt(0, buildings.size());
            buildings.get(picker).remove();
            buildings.remove(picker);
            return true;
        }
}
