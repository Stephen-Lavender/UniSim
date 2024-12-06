package com.backlogged.univercity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Disposable;
import java.util.Collection;

/**
 * Building Renderer handles rendering valid and invalid loactions for a
 * building that is about to
 * be placed and buildings that are already placed.
 */
public class BuildingRenderer implements Disposable, IBuildingRenderer {
  private SpriteBatch batch = new SpriteBatch();
  private ShapeRenderer shapeRenderer = new ShapeRenderer();
  private TextureAtlas buildingAtlas;
  private boolean debugGridEnabled = false;

  /**
   * Contructs a BuildingRenderer.
   *
   * @param buildingAtlas This is the atlas that contains all the building sprites
   *                      that are able to
   *                      be rendered if placed on the map.
   */
  public BuildingRenderer(TextureAtlas buildingAtlas) {
    this.buildingAtlas = buildingAtlas;
  }

  /**
   * Enables or disables the debug grid.
   *
   * @param shouldEnable Boolean for enabling or disabling the grid.
   */
  public void enableDebugGrid(boolean shouldEnable) {
    debugGridEnabled = shouldEnable;
  }

  /**
   * Draws a debug grid for visually checking the alignment of buildings on the
   * map.
   *
   * @param camera The projection Matrix for the shapeRenderer
   */
  public void drawDebugGrid(OrthographicCamera camera) {
    shapeRenderer.setProjectionMatrix(camera.combined);
    shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
    shapeRenderer.setColor(Color.BLACK);
    var h = Gdx.graphics.getHeight();
    var w = Gdx.graphics.getWidth();

    for (float y = 0; y <= h; y++) {
      shapeRenderer.line(0, y, w, y);
    }
    for (float x = 0; x <= w; x++) {
      shapeRenderer.line(x, 0, x, h);
    }

    shapeRenderer.end();
  }

  /**
   * Renders visual feedback for the placement area of a building, indicating
   * whether it can be
   * placed at the specified location. If valid, ticks are rendered in green;
   * otherwise, crosses are
   * rendered in red.
   *
   * @param canBePlacedAtLocation Indicates whether the building can be placed at
   *                              the specified row
   *                              and column.
   * @param row                   The row coordinate for placement feedback to
   *                              start from.
   * @param column                The column coordinate for placement feedback ro
   *                              start from.
   * @param camera                The camera used for projection, providing the
   *                              matrix for the shape renderer.
   * @param building              The {@link Building} to be placed for
   *                              placement.
   */
  public void renderPlacementFeedback(
      boolean canBePlacedAtLocation,
      int column,
      int row,
      OrthographicCamera camera,
      Building building) {
    shapeRenderer.setProjectionMatrix(camera.combined);
    shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
    Gdx.gl.glLineWidth(5);
    if (canBePlacedAtLocation) {
      shapeRenderer.setColor(Color.GREEN);

      for (var tileOffset : building.getTileCoverageOffsets()) {
        int offsetX = column + tileOffset.getColumn();
        int offsetY = row + tileOffset.getRow();
        shapeRenderer.line(0.f + offsetX, 0.5f + offsetY, 0.25f + offsetX, 0.f + offsetY);
        shapeRenderer.line(0.25f + offsetX, 0.f + offsetY, 1.f + offsetX, 1.f + offsetY);
      }

    } else {
      shapeRenderer.setColor(Color.RED);
      for (var tileOffset : building.getTileCoverageOffsets()) {
        int offsetX = row + tileOffset.getRow();
        int offsetY = row + tileOffset.getRow();
        shapeRenderer.line(0.f + offsetX, 1.f + offsetY, 1.f + offsetX, 0.f + offsetY);
        shapeRenderer.line(0.f + offsetX, 0.f + offsetY, 1.f + offsetX, 1.f + offsetY);
      }
    }
    shapeRenderer.end();
    Gdx.gl.glLineWidth(1);
  }

  /**
   * Renders all currently placed buildings.
   *
   * @param placedBuildings Currently placed buildings to be rendered.
   * @param camera          The projection matrix for the sprite batch.
   */
  public void renderBuildings(
      Collection<Building> placedBuildings, OrthographicCamera camera) {
    batch.setProjectionMatrix(camera.combined);
    batch.begin();
    for (var building : placedBuildings) {
      building.draw(batch);
    }
    batch.end();
    if (debugGridEnabled) {
      drawDebugGrid(camera);
    }
  }

  /**
   * Retrieves the {@code TextureAtlas} for the building sprites.
   *
   * @return The {@code TextureAtlas} for the buildling sprites.
   */
  public TextureAtlas getAtlas() {
    return buildingAtlas;
  }

  /** Releases GPU resources used by the BuildingRenderer. */
  public void dispose() {
    if (buildingAtlas != null) {
      buildingAtlas.dispose();
    }
    batch.dispose();
    shapeRenderer.dispose();
  }
}
