package com.backlogged.univercity;
import java.util.List;
import java.util.Random;

public class Events {

        int picker;
        Random rand = new Random();
        public void WorldEvent(List<Building> city)
        {
            picker = rand.nextInt(0, city.size());
            city.get(picker).remove();
            city.remove(picker);


        }
}
