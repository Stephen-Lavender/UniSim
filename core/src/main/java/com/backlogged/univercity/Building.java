package com.backlogged.univercity;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.List;
import java.util.Set;

public class Building {
    private int level;
    private List<BuildingInstance> upgrades;

    private Coord mapPos;

    final List<Coord> tileCoverageOffsets;

    public Building(List<BuildingInstance> upgrades, List<Coord> tileCoverageOffsets) {
        mapPos = new Coord(-1, -1);
        level = 0;
        this.upgrades = upgrades;
        this.tileCoverageOffsets = tileCoverageOffsets;
    }
    private BuildingInstance getCurrentBuildingInstance(){
        return upgrades.get(level);
    }

    public Set<BuildingType> getType(){
        return getCurrentBuildingInstance().getType();
    }

    public boolean isOfType(BuildingType type){
        return getCurrentBuildingInstance().isOfType(type);
    }

    /**
     * Retrieves the sprite associated with this building, used for rendering the
     * building on the map.
     *
     * @return The {@code Sprite} representing the building's visual appearance.
     */
    public final Sprite getSprite() {
        return getCurrentBuildingInstance().sprite;
    }

    /**
     * Retrieves the tile coverage offsets for this building. These offsets specify
     * the tiles that
     * this building occupies relative to its bottom-left most tile.
     *
     * @return An {@code ArrayList} of {@code Coord} representing the tile coverage
     *         offsets for this
     *         building.
     */
    public final List<Coord> getTileCoverageOffsets() {
        return tileCoverageOffsets;
    }

    /**
     * Sets the position of this building on the map.
     *
     * @param mapPos The {@code Coord} representing the building's position on the
     *               map grid.
     */
    public final void setMapPosition(Coord mapPos) {
        this.mapPos = mapPos;
    }

    /**
     * Retrieves the position of this building on the map.
     *
     * @return The {@code Coord} representing the building's position on the map
     *         grid.
     */
    public final Coord getMapPos() {
        return mapPos;
    }

    /**
     * Draws the building's sprite at its current map position using the specified
     * {@code
     * SpriteBatch}. This method sets the position of the sprite to the map
     * coordinates, renders it,
     * and then resets the sprite position to (0,0).
     *
     * @param batch The {@code SpriteBatch} used to draw the building's sprite.
     */
    public final void draw(SpriteBatch batch) {
        getCurrentBuildingInstance().sprite.setPosition(this.mapPos.getRow(), this.mapPos.getColumn());
        getCurrentBuildingInstance().sprite.draw(batch);
        getCurrentBuildingInstance().sprite.setPosition(0, 0);
    }

    public boolean exists(){
        return (mapPos.getRow() != -1 && mapPos.getColumn() != -1);
    }

    public Building copy(){
        Building newBuilding = new Building(upgrades, tileCoverageOffsets);
        return newBuilding;
    }


}
