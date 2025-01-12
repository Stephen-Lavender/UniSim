import com.backlogged.univercity.*;
import com.badlogic.gdx.backends.headless.HeadlessApplicationConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class BuildingTests{
    private Building building;
    @BeforeEach
    void setUp(){
        List<BuildingInstance> upgrades = new ArrayList<>();
        BuildingInstance building1 = new BuildingInstance("square");
        BuildingInstance building2 = new BuildingInstance("square");
        BuildingInstance building3 = new BuildingInstance("circle");
        building1.addType(BuildingType.ACCOMMODATION);
        building2.addType(BuildingType.ACCOMMODATION);
        building3.addType(BuildingType.ACCOMMODATION);
        building3.addType(BuildingType.CAFETERIA);
        upgrades.add(building1);
        upgrades.add(building2);
        upgrades.add(building3);
        building = new Building(upgrades, List.of(
            new Coord[]{
                new Coord(0, 0),
                new Coord(0, 1),
                new Coord(1, 0),
                new Coord(1, 1)
            }
        ));
    }

    @Test
    @DisplayName("Testing Upgrade")
    void testUpgrades(){
        assertEquals(0, building.getLevel());
        assertFalse(building.isOfType(BuildingType.CAFETERIA));
        assertTrue(building.isOfType(BuildingType.ACCOMMODATION));
        building.upgrade();
        assertEquals(1, building.getLevel());
        assertFalse(building.isOfType(BuildingType.CAFETERIA));
        assertTrue(building.isOfType(BuildingType.ACCOMMODATION));
        building.upgrade();
        assertFalse(building.isOfType(BuildingType.RECREATIONAL));
        assertTrue(building.isOfType(BuildingType.CAFETERIA));
        assertTrue(building.isOfType(BuildingType.ACCOMMODATION));
    }
}
