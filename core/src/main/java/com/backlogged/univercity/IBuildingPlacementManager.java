package com.backlogged.univercity;

import java.util.List;
import java.util.HashMap;

/**
 * Manages the placement of buildings on the map, including checking if
 * placement is possible,
 * handling actual placement, tracking placed buildings, and resetting any
 * placed buildings and the
 * building count. This interface provides methods to check both terrain and
 * existing buildings to
 * ensure buildings are placed in valid locations.
 */
public interface IBuildingPlacementManager {
  /**
   * Places a building at the given location.
   *
   * @param row    The row to place the building.
   * @param column The column to place the building.
   */
  public void placeBuilding(int column, int row, Building building);

  /**
   * Determines if its possible to place a building at the location of the cursor
   * by checking if any
   * of the tiles that the building will take up contain either a terrain type
   * that cant be built on
   * or another building.
   *
   * @param row      The row to start checking from.
   * @param column   The column to start checking from.
   * @param building The building to check.
   * @return Returns true its possible and false if not.
   */
  public boolean canBePlacedAtLocation(int column, int row, Building building);

  /** Resets the count to zero and clears any placed buildings. */
  public void reset();

  /**
   * Retrives the current number of buildings.
   *
   * @return The number of buildings currently placed on the map.
   */
  public int getCount();

  /**
   * Retrives all the currently placed buildings.
   *
   * @return A List of the currently placed buildings.
   */
  public List<Building> getPlacedBuildings();

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
  public boolean canBePlacedAtLocationIgnoreTerrain(int column, int row , Building building);
}
