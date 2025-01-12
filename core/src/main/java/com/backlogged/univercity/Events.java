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

        public int ChooseEvent()
        {
            picker = rand.nextInt(1,11);

            if (picker % 2 == 0) {
               picker = Negative_event();
            }
            else
            {
               picker = Positive_Event();
            }
            return picker;
        }

        private int Negative_event()
        {
            return 2;

        }

        private int Positive_Event()
        {
            return 1;
        }
}
