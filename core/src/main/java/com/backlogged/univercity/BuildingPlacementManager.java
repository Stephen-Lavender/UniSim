package com.backlogged.univercity;

import com.badlogic.gdx.maps.MapLayer;
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
    private MapLayer objectLayer;
    private List<Building> placedBuildings = new ArrayList<>();

    /**
     * Constructs a BuildingPlacementManager instance.
     *
     * @param objectLayer The layer containing terrain information within the map.
     */
    public BuildingPlacementManager(MapLayer objectLayer) {
        System.out.println(objectLayer);
        this.objectLayer = objectLayer;
    }

    public BuildingPlacementManager(){

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
//        for (var tileOffset : building.getTileCoverageOffsets()) {
//            TiledMapTileLayer.Cell terrainCell = objectLayer.getCell(column + tileOffset.getColumn(),
//                row + tileOffset.getRow());
//            if (!terrainCell.getTile().getProperties().get("canBeBuiltOn", Boolean.class)
//                || placedBuildingTiles.containsKey(tileOffset)) {
//                return false;
//            }
//        }
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
        List<Coord> placedBuildingTiles = getPlacedBuildingTiles();
        for (var tileOffset : building.getTileCoverageOffsets()) {
            if (placedBuildingTiles.contains(tileOffset.translate(new Coord(column, row)))){
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean canBeMovedToCurrentLocation(int column, int row, Building building) {
        List<Coord> placedBuildingTiles = new ArrayList<>();
        for (Building b: getPlacedBuildings()){
            if (b == building){
                continue;
            }
            for (Coord coverageOffset: b.getTileCoverageOffsets()){
                placedBuildingTiles.add(coverageOffset.translate(b.getMapPos()));
            }
        }
        for (var tileOffset : building.getTileCoverageOffsets()) {
            if (placedBuildingTiles.contains(tileOffset.translate(new Coord(column, row)))){
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
        building.setMapPosition(new Coord(column, row));
        placedBuildings.add(building);
    }

    /**
     * Retrives all the currently placed buildings.
     *
     * @return A List of the currently placed buildings.
     */
    public List<Building> getPlacedBuildings() {
        return placedBuildings.stream().filter(Building::exists).collect(Collectors.toList());
    }

    public List<Coord> getPlacedBuildingTiles(){
        List<Coord> buildingTiles = new ArrayList<>();
        for (Building building: getPlacedBuildings()){
            for (Coord coverageOffset: building.getTileCoverageOffsets()){
                buildingTiles.add(coverageOffset.translate(building.getMapPos()));
            }
        }

        return buildingTiles;
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
}
