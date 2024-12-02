package com.backlogged.univercity;

import com.badlogic.gdx.graphics.g2d.Sprite;
import java.util.ArrayList;

/**
 * Encapsulates essential information about a building, including its type, tile
 * coverage,
 * descriptive information, and associated sprite. This information is used to
 * render and categorize
 * buildings on a map.
 */
public class BuildingInfo {
  final BuildingType type;
  final ArrayList<Coord> tileCoverageOffsets;
  final String info;
  final Sprite buildingSprite;

  /**
   * Constructs a {@code BuildingInfo} instance with specified type, tile
   * coverage, description, and
   * sprite.
   *
   * @param type                An array of strings categorizing the type(s) of
   *                            this building.
   * @param tileCoverageOffsets A list of {@code Coord} objects representing the
   *                            tile offsets
   *                            covered by this building.
   * @param info                A string containing information about the
   *                            building.
   * @param buildingSprite      The {@code Sprite} object used to render this
   *                            building.
   */
  public BuildingInfo(
      BuildingType type, ArrayList<Coord> tileCoverageOffsets, String info, Sprite buildingSprite) {
    this.type = type;
    this.tileCoverageOffsets = tileCoverageOffsets;
    this.info = info;
    this.buildingSprite = buildingSprite;
  }
}
