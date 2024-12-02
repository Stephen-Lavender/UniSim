package com.backlogged.univercity;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;

/**
 * Represents an abstract base class for buildings that can be placed on a map.
 * This class provides
 * core properties and behaviors for a building, such as its type, sprite, tile
 * coverage, and map
 * position. Concrete building classes should extend this class to implement
 * specific building
 * functionalities.
 */
public class AbstractBuilding {

  private BuildingInfo buildingInfo;
  private Coord mapPos;

  /**
   * Constructs an {@code AbstractBuilding} instance with the specified building
   * information.
   *
   * @param buildingInfo Information about the building, including its type,
   *                     sprite, tile coverage,
   *                     and additional info.
   */
  public AbstractBuilding(BuildingInfo buildingInfo) {
    this.buildingInfo = buildingInfo;
  }

  /**
   * Retrieves the type(s) of this building.
   *
   * @return An array of strings representing the type categories of the building.
   */
  public final BuildingType getType() {
    return buildingInfo.type;
  }

  /**
   * Retrieves the sprite associated with this building, used for rendering the
   * building on the map.
   *
   * @return The {@code Sprite} representing the building's visual appearance.
   */
  public final Sprite getSprite() {
    return buildingInfo.buildingSprite;
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
  public final ArrayList<Coord> getTileCoverageOffsets() {
    return buildingInfo.tileCoverageOffsets;
  }

  /**
   * Retrieves information about this building.
   *
   * @return A string containing additional information about the building.
   */
  public final String getInfo() {
    return buildingInfo.info;
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
    buildingInfo.buildingSprite.setPosition(this.mapPos.getRow(), this.mapPos.getColumn());
    buildingInfo.buildingSprite.draw(batch);
    buildingInfo.buildingSprite.setPosition(0, 0);
  }
}
