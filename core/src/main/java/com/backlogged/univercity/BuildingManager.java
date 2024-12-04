package com.backlogged.univercity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import java.util.ArrayList;
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

    List<JSONBuilding> buildings;
    List<BuildingInfo> buildingInfos;

    public void generateBuildingBlueprints() {
        buildings = new ArrayList<>();
        JSONBuilding accommodationBuilding1 = new JSONBuilding();
        JSONBuilding cafeteriaBuilding1 = new JSONBuilding();
        JSONBuilding courseBuilding1 = new JSONBuilding();
        JSONBuilding recreationalBuilding1 = new JSONBuilding();

        accommodationBuilding1.setType(BuildingType.ACCOMMODATION);
        accommodationBuilding1.setAtlasRegion("square");
        accommodationBuilding1.setTileCoverageOffsets(List.of(
            new Coord[] {
                new Coord(0, 0),
                new Coord(0, 1),
                new Coord(1, 0),
                new Coord(1, 1)
            }
        ));

        cafeteriaBuilding1.setType(BuildingType.CAFETERIA);
        cafeteriaBuilding1.setAtlasRegion("circle");
        cafeteriaBuilding1.setTileCoverageOffsets(List.of(
            new Coord[] {
                new Coord(0, 0),
                new Coord(0, 1),
                new Coord(1, 0),
                new Coord(1, 1)
            }
        ));

        courseBuilding1.setType(BuildingType.COURSE);
        courseBuilding1.setAtlasRegion("rhombus");
        courseBuilding1.setTileCoverageOffsets(List.of(
            new Coord[] {
                new Coord(0, 0),
                new Coord(0, 1),
                new Coord(1, 0),
                new Coord(1, 1)
            }
        ));

        recreationalBuilding1.setType(BuildingType.RECREATIONAL);
        recreationalBuilding1.setAtlasRegion("hex");
        recreationalBuilding1.setTileCoverageOffsets(List.of(
            new Coord[] {
                new Coord(0, 0),
                new Coord(0, 1),
                new Coord(1, 0),
                new Coord(1, 1)
            }
        ));

        buildings.add(accommodationBuilding1);
        buildings.add(cafeteriaBuilding1);
        buildings.add(courseBuilding1);
        buildings.add(recreationalBuilding1);
    }

    public void generateBuildingInfosFromBlueprints(TextureAtlas buildingAtlas, float unitScale){
        buildingInfos = new ArrayList<>();

        for (JSONBuilding building: buildings){
            Sprite sprite = buildingAtlas.createSprite(building.atlasRegion);
            sprite.setScale(unitScale);
            sprite.setOrigin(0, 0);
            buildingInfos.add(new BuildingInfo(building.type, building.tileCoverageOffsets, building.info, sprite ));
        }
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
      return new Building(buildingInfos.get(indexInBlueprintArray));
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
    generateBuildingBlueprints();
    generateBuildingInfosFromBlueprints(renderer.getAtlas(), unitScale);

    System.out.println(buildings.size());
    System.out.println(buildingInfos.size());
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
  public List<JSONBuilding> getBuildings() {
    return buildings;
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
  private void placeBuilding(int row, int column) {
    placementManager.placeBuilding(row, column, buildingToBePlaced);
    var countForBuildingTypePlaced = buildingCounts.get(buildingToBePlaced.getType());
    buildingCounts.put(buildingToBePlaced.getType(), ++countForBuildingTypePlaced);
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
          placeBuilding(currentRow, currentColumn);
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
      currentRow = (int) worldCoordinates.x;
      currentColumn = (int) worldCoordinates.y;
      canBePlacedAtCurrentLocation = placementManager.canBePlacedAtLocationIgnoreTerrain(
          currentRow, currentColumn, buildingToBePlaced);
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
  public void render() {

    renderer.renderBuildings(placementManager.getPlacedBuildings(), camera);
    if (isChoosingLocation) {
      renderer.renderPlacementFeedback(
          canBePlacedAtCurrentLocation, currentRow, currentColumn, camera, buildingToBePlaced);
    }
  }
}
