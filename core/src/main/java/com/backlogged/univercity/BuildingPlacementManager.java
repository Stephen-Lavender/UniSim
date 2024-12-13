package com.backlogged.univercity;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * BuildingPlacementManager manages what buildings are currently placed on the
 * map, whether or not
 * you can place a building at a certain location and placing a building.
 */
public class BuildingPlacementManager implements IBuildingPlacementManager {
    private final TiledMapTileLayer terrainLayer;
    private HashMap<Coord, Building> placedBuildingTiles = new HashMap<>();
    private List<Building> placedBuildings = new ArrayList<>();

    /**
     * Constructs a BuildingPlacementManager instance.
     *
     * @param terrainLayer The layer containing terrain information within the map.
     */
    public BuildingPlacementManager(TiledMapTileLayer terrainLayer) {
        this.terrainLayer = terrainLayer;
    }

    /**
     * Determines if its possible to place a building at the location of the cursor
     * by checking if any
     * of the tiles that the building will take up contain either a terrain type
     * that cant be built on
     * or another building.
     *
     * @param row      The row to start checking from.
     * @param column   The column to start checking from.
     * @param building The {@link Building} to check.
     * @return Returns true its possible and false if not.
     */
    public boolean canBePlacedAtLocation(int column, int row, Building building) {
        for (var tileOffset : building.getTileCoverageOffsets()) {
            TiledMapTileLayer.Cell terrainCell = terrainLayer.getCell(column + tileOffset.getColumn(),
                row + tileOffset.getRow());
            if (!terrainCell.getTile().getProperties().get("canBeBuiltOn", Boolean.class)
                || placedBuildingTiles.containsKey(tileOffset)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Determines if its possible to place a building at the location of the cursor
     * by checking if any
     * of the tiles that the building will take up contain another building.
     *
     * @param row      The row to start checking from.
     * @param column   The column to start checking from.
     * @param building The {@link Building} to check.
     * @return Returns true its possible and false if not.
     */
    public boolean canBePlacedAtLocationIgnoreTerrain(
        int column, int row, Building building) {
        for (var tileOffset : building.getTileCoverageOffsets()) {
            if (placedBuildingTiles.containsKey(
                new Coord(tileOffset.getColumn() + column, tileOffset.getRow() + row))) {
                return false;
            }
        }
        return true;
    }

    /**
     * Places a building at a given location.
     *
     * @param row    The row to place the building.
     * @param column The column to place the building.
     */
    public void placeBuilding(int column, int row, Building building) {
        for (var tileOffset : building.getTileCoverageOffsets()) {
            placedBuildingTiles.put(
                new Coord(column + tileOffset.getColumn(), row + tileOffset.getRow()), building);
        }
        placedBuildings.add(building);
        building.setMapPosition(new Coord(column, row));
    }

    /**
     * Retrives all the currently placed buildings.
     *
     * @return A List of the currently placed buildings.
     */
    public List<Building> getPlacedBuildings() {
        return placedBuildings.stream().filter(Building::exists).collect(Collectors.toList());
    }

    public List<Building> getPlacedBuildingTiles(){
        return placedBuildingTiles.values().stream().filter(Building::exists).collect(Collectors.toList());
    }

    /**
     * Retrives the current number of buildings.
     *
     * @return The number of buildings currently placed on the map.
     */
    public int getCount() {
        return getPlacedBuildings().size();
    }

    /**
     * Resets the count to zero and clears any placed buildings.
     */
    public void reset() {
        placedBuildingTiles.clear();
    }

    public void updateBuildings(){
        placedBuildingTiles = (HashMap<Coord, Building>) placedBuildingTiles.entrySet()
            .stream()
            .filter(entry -> entry.getValue().exists())
            .collect(Collectors.toMap(
                    Map.Entry::getKey,  // Use lambda for getKey
                    Map.Entry::getValue // Use lambda for getValue
            ));
    }
}
