package com.backlogged.univercity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.List;

/**
 * Manages building placement etc. within the game and Handles input, rendering,
 * and state
 * transitions for buildings.
 */
public class BuildingManager {
  /** Represents the states of building interactions. */
  private enum BuildingState {
    NOT_BUILDING,
    BUILDING,
    DELETING,
    MOVING
  }

    List<Building> buildingBlueprints;

    public void generateBuildingBlueprints(TextureAtlas textureAtlas, float unitScale) {
        buildingBlueprints = new ArrayList<>();

        List<BuildingInstance> upgrades = new ArrayList<>();
        BuildingInstance accommodationBuilding1 = new BuildingInstance("square");
        BuildingInstance accommodationBuilding2 = new BuildingInstance("square");
        BuildingInstance accommodationBuilding3 = new BuildingInstance("circle");
        accommodationBuilding1.addType(BuildingType.ACCOMMODATION);
        accommodationBuilding2.addType(BuildingType.ACCOMMODATION);
        accommodationBuilding3.addType(BuildingType.ACCOMMODATION);
        accommodationBuilding3.addType(BuildingType.CAFETERIA);


        accommodationBuilding1.setSprite(textureAtlas, unitScale);
        accommodationBuilding2.setSprite(textureAtlas, unitScale);
        accommodationBuilding2.setSpriteColour(Color.BLACK);
        accommodationBuilding3.setSprite(textureAtlas, unitScale * 2);
        accommodationBuilding3.setSpriteColour(Color.FIREBRICK);
        upgrades.add(accommodationBuilding1);
        upgrades.add(accommodationBuilding2);
        upgrades.add(accommodationBuilding3);

        Building accommodationBuilding = new Building(upgrades, List.of(
            new Coord[] {
                new Coord(0, 0),
                new Coord(0, 1),
                new Coord(1, 0),
                new Coord(1, 1)
            }
        ));


        upgrades = new ArrayList<>();
        BuildingInstance cafeteriaBuilding1 = new BuildingInstance("circle");
        cafeteriaBuilding1.addType(BuildingType.CAFETERIA);

        cafeteriaBuilding1.setSprite(textureAtlas, unitScale);

        upgrades.add(cafeteriaBuilding1);
        Building cafeteriaBuilding = new Building(upgrades, List.of(
            new Coord[] {
                new Coord(0, 0),
                new Coord(0, 1),
                new Coord(1, 0),
                new Coord(1, 1)
            }
        ));

        upgrades = new ArrayList<>();
        BuildingInstance courseBuilding1 = new BuildingInstance("rhombus");
        courseBuilding1.addType(BuildingType.COURSE);

        courseBuilding1.setSprite(textureAtlas, unitScale);

        upgrades.add(courseBuilding1);
        Building courseBuilding = new Building(upgrades, List.of(
            new Coord[] {
                new Coord(0, 0),
                new Coord(0, 1),
                new Coord(1, 0),
                new Coord(1, 1)
            }
        ));

        upgrades = new ArrayList<>();
        BuildingInstance recreationalBuilding1 = new BuildingInstance("hex");
        recreationalBuilding1.addType(BuildingType.RECREATIONAL);

        recreationalBuilding1.setSprite(textureAtlas, unitScale);

        upgrades.add(recreationalBuilding1);
        Building recreationalBuilding = new Building(upgrades, List.of(
            new Coord[] {
                new Coord(0, 0),
                new Coord(0, 1),
                new Coord(1, 0),
                new Coord(1, 1)
            }
        ));

        buildingBlueprints.add(accommodationBuilding);
        buildingBlueprints.add(cafeteriaBuilding);
        buildingBlueprints.add(courseBuilding);
        buildingBlueprints.add(recreationalBuilding);
    }

  /** Factory for creating building instances. */
  private class BuildingFactory {
    /**
     * Instantiates a Building based on the specified class name.
     *
     *
     *
     * @return New Building instance.
     * @throws IllegalArgumentException If the building class cannot be
     *                                  instantiated.
     */
    public Building createBuilding(int indexInBlueprintArray) {
      return buildingBlueprints.get(indexInBlueprintArray).copy();
    }
  }

  BuildingFactory buildingFactory = new BuildingFactory();
  private IBuildingRenderer renderer;
  private IBuildingPlacementManager placementManager;
  private boolean isChoosingLocation = false;
  private Building buildingToBePlaced;
  private int currentRow;
  private int currentColumn;
  private BuildingState buildingState = BuildingState.NOT_BUILDING;
  private OrthographicCamera camera;

  private HashMap<BuildingType, Integer> buildingCounts = new HashMap<>();
  private boolean canBePlacedAtCurrentLocation;

  /**
   * Constructs a building manager instance.
   *
   * @param unitScale        Scale factor to match with tiled map renderer
   *                         projection matrix
   * @param renderer         Renderer for displaying buildings.
   * @param placementManager Manager for handling building placements.
   */
  public BuildingManager(
      float unitScale, IBuildingRenderer renderer, IBuildingPlacementManager placementManager) {
    if (renderer == null || placementManager == null) {
      throw new IllegalArgumentException("renderer and placement manager MUST both be initialized");
    }
    generateBuildingBlueprints(renderer.getAtlas(), unitScale);
    initBuildingCounters();
    this.renderer = renderer;
    this.placementManager = placementManager;
  }

  /** Initialises counters for all building types to zero. */
  private void initBuildingCounters() {
    for (BuildingType buildingType : BuildingType.values()) {
      buildingCounts.put(buildingType, 0);
    }
  }

  /**
   * Transitions the BuildingManager to building mode if not currently building.
   */
  public void setBuildingState() {
    if (buildingState == BuildingState.NOT_BUILDING) {
      buildingState = BuildingState.BUILDING;
    }
  }

  /**
   * Retrieves all available building information.
   *
   * @return Set of map entries associating building names with BuildingInfo data.
   */
  public List<Building> getBuildingBlueprints() {
    return buildingBlueprints;
  }

  /** Resets building placement state. */
  private void resetState() {
    buildingToBePlaced = null;
    isChoosingLocation = false;
  }

  /**
   * Returns the count of each type of building currently placed.
   *
   * @return A string of all building types in the form: BuildingType : Count.
   */
  public String getBuildingTypeCounts() {
    return buildingCounts.toString();
  }

  /**
   * Places the building at the specified coordinates.
   *
   * @param row    Row coordinate for placement.
   * @param column Column coordinate for placement.
   */
  private void placeBuilding(int column, int row) {
    placementManager.placeBuilding(column, row, buildingToBePlaced);

    for (BuildingType type: buildingCounts.keySet()){
        if (buildingToBePlaced.isOfType(type)){
            buildingCounts.put(type, buildingCounts.get(type) + 1);
        }
    }

    resetState();
  }

  /**
   * Initiates location choosing state for placing a building.
   *
   * @param
   */
  public void chooseLocationOfBuilding(int indexInBlueprintArray) {
      buildingToBePlaced = buildingFactory.createBuilding(indexInBlueprintArray);
      isChoosingLocation = true;
  }

  /**
   * Sets the camera used for projecting world coordinates.
   *
   * @param camera Camera instance to set.
   */
  public void setCamera(OrthographicCamera camera) {
    this.camera = camera;
  }

  /**
   * Converts screen coordinates to world coordinates, adjusted for the camera's
   * perspective.
   *
   * @return Vector2 representing world coordinates of the cursor position.
   */
  private Vector2 getWorldCoordinates() {
    var cursorPos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
    var worldCoordinates = camera.unproject(cursorPos);
    return new Vector2(worldCoordinates.x, worldCoordinates.y);
  }

  /** Processes input based on the current building state. */
  public void handleInput() {
    switch (buildingState) {
      case NOT_BUILDING: {
      }
        break;
      case BUILDING: {
        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT) && canBePlacedAtCurrentLocation) {
          placeBuilding(currentColumn, currentRow);
          resetState();
          buildingState = BuildingState.NOT_BUILDING;
        }
      }
        break;
      case DELETING:
        {
        // TODO: UNIMPLEMENTED
        }
        break;
      case MOVING:
        {
        // TODO: UNIMPLEMENTEDt
        }
        break;
      default:
        break;
    }
    if (buildingState != BuildingState.NOT_BUILDING && Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
      resetState();
      buildingState = BuildingState.NOT_BUILDING;
    }
  }

  /**
   * Updates the BuildingManager, updating the row and column based on cursor
   * location if placing a
   * building.
   */
  public void update() {
    if (isChoosingLocation) {
      var worldCoordinates = getWorldCoordinates();
      currentColumn = (int) worldCoordinates.x;
      currentRow = (int) worldCoordinates.y;
      canBePlacedAtCurrentLocation = placementManager.canBePlacedAtLocationIgnoreTerrain(
          currentColumn, currentRow, buildingToBePlaced);
    }
  }

  /** Resets the building placement manager, clearing all placed buildings. */
  public void reset() {
    placementManager.reset();
  }

  /**
   * Gets the total count of buildings currently placed.
   *
   * @return Integer count of placed buildings.
   */
  public int getBuildingCount() {
    return placementManager.getCount();
  }

  /**
   * Renders buildings, and the placement squares if {@code buildingState ==
   * BuildingState.BUILDING}.
   */

  public HashMap<Coord, Building> getMapBuildings(){
      return placementManager.getBuildingMap();
  }

  public List<Building> getPlacedBuildings(){
      return placementManager.getPlacedBuildings();
  }
  public void render() {

    renderer.renderBuildings(placementManager.getPlacedBuildings(), camera);
    if (isChoosingLocation) {
      renderer.renderPlacementFeedback(
          canBePlacedAtCurrentLocation, currentColumn, currentRow, camera, buildingToBePlaced);
    }
  }
}
