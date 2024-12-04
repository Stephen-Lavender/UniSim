package com.backlogged.univercity;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import java.util.Collection;

/** Handles rendering placement feedback and currently placed buildings. */
public interface IBuildingRenderer {
  /**
   * Renders all currently placed buildings.
   *
   * @param placedBuildings Currently placed buildings to be rendered.
   * @param camera          The projection matrix for the sprite batch.
   */
  public void renderBuildings(
      Collection<Building> placedBuildings, OrthographicCamera camera);

  /**
   * Renders visual feedback for the placement area of a building, indicating
   * whether it can be
   * placed at the specified location.
   *
   * @param canBePlacedAtLocation Indicates whether the building can be placed at
   *                              the specified row
   *                              and column.
   * @param row                   The row coordinate for placement feedback to
   *                              start from.
   * @param column                The column coordinate for placement feedback ro
   *                              start from.
   * @param camera                The camera used for projection, providing the
   *                              matrix for the renderer.
   * @param building              The {@link Building} to be placed for
   *                              placement.
   */
  public void renderPlacementFeedback(
      boolean canBePlacedAtLocation,
      int row,
      int column,
      OrthographicCamera camera,
      Building building);

  /**
   * Retrieves the {@code TextureAtlas} for the building sprites.
   *
   * @return The {@code TextureAtlas} for the buildling sprites.
   */
  public TextureAtlas getAtlas();
}
