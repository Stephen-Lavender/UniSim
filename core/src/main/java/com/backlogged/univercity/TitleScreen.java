package com.backlogged.univercity;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.TextInputListener;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.Value;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import java.util.HashMap;
import java.util.Map;

/**
 * Handles rendering title screen - first screen user sees.
 */
public class TitleScreen implements Screen {
  private Stage stage;
  private Table table;
  private Skin skin;

  private Texture bgTexture;

  private Button univerCityLogo;
  private TextButton playButton;
  private TextButton optionsButton;
  private TextButton quitButton;
  private LeaderBoard leaderBoard;

  /**
   * Sets up the title screen.
   *
   * @param game current instance of the game
   */
  public TitleScreen(Game game) {
    leaderBoard = new LeaderBoard();
    bgTexture = new Texture(Constants.BACKGROUND_PICTURE_PATH);




    // Set up UI stage, skin and table
    stage = new Stage(new ScreenViewport());
    // TODO: edit constants to use new uiskin
    skin = new Skin(Gdx.files.internal(Constants.UI_SKIN_PATH));
    table = new Table(skin);
    table.setFillParent(true);
    table.setDebug(true);

    Table leaderboardTable = new Table();
    leaderboardTable.setDebug(true);
    leaderboardTable.setFillParent(true);
    leaderboardTable.align(Align.topLeft);


    stage.addActor(table);
    table.setVisible(true);
    //chris
    //

    // Set up the title label and buttons
    univerCityLogo = new Button(skin, "univerCityLogo");
    playButton = new TextButton("PLAY", skin, "default");
    playButton.addListener(new ClickListener() {
      public void clicked(InputEvent e, float x, float y) {
        game.setScreen(new MapScreen(game));

      }
    });

    optionsButton = new TextButton("OPTIONS", skin, "default");
    optionsButton.addListener(new ClickListener() {
      public void clicked(InputEvent e, float x, float y) {
        game.setScreen(new SettingsScreen(game, game.getScreen()));
      }
    });

    quitButton = new TextButton("QUIT", skin, "redTextButton");
    quitButton.addListener(new ClickListener() {
      public void clicked(InputEvent e, float x, float y) {
        Gdx.app.exit();
      }
    });

    // Add the title label and buttons to the UI table
    table.add(univerCityLogo).top().padTop(100).width(Value.percentWidth(0.6f, table))
                .height(Value.percentWidth(0.081f, table));
    table.row();
    table.add(playButton).top().padTop(100).width(Value.percentWidth(0.3f, table))
        .height(Value.percentHeight(0.1f, table));
    table.row();
    table.add(optionsButton).top().padTop(50).width(Value.percentWidth(0.3f, table))
        .height(Value.percentHeight(0.1f, table));
    table.row();
    table.add(quitButton).top().padTop(50).width(Value.percentWidth(0.3f, table))
        .height(Value.percentHeight(0.1f, table));


    //leaderboard
    Label titleLabel = new Label("Leaderboard", skin);
    titleLabel.setAlignment(Align.center);
    titleLabel.setFontScale(1f);
    leaderboardTable.padTop(stage.getHeight() * 0.05f).padLeft(stage.getWidth() * 0.03f);
    leaderboardTable.add(titleLabel).left().width(Value.percentWidth(0.15f, leaderboardTable));
    leaderboardTable.row();
    // Add entries for 1st to 5th place
    for (Map.Entry<Integer,Pair<String,Integer>> playerScore : leaderBoard.playerScores.entrySet()) {
        if (playerScore.getKey() > 4) {
          break;
        }
        Label playerData = new Label((playerScore.getKey() + 1) + ": " + playerScore.getValue().name + " " +playerScore.getValue().score + "%"  , skin);
        playerData.setFontScale(0.7f);
        leaderboardTable.add(playerData) // Customize as needed
            .left() // Align the text to the left
            .padTop(stage.getHeight() * 0.04f) // Add padding around each entry
            .width(Value.percentWidth(0.15f, leaderboardTable)); // Adjust the width if necessary
      leaderboardTable.row(); // Move to the next row
    }

// Add the leaderboard table to the stage
stage.addActor(leaderboardTable);



        stage.addActor(leaderboardTable);


  }

  @Override
  public void show() {
    Gdx.input.setInputProcessor(stage);
    Soundtrack.play();
  }

  @Override
  public void render(float delta) {
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    stage.getBatch().begin();
    renderBackground();
    stage.getBatch().end();
    stage.act();
    stage.draw();
  }

  /**
   * Handles rendering for the background image, including scaling correctly,
   * and moving relative to the mouse position.
   */
  private void renderBackground() {
    float aspectRatio = 16f / 9f;
    float worldWidth = stage.getWidth();
    float worldHeight = stage.getHeight();
    float padding = 20;

    float height = worldHeight;
    float width = height * aspectRatio;

    if (width < worldWidth) {
      width = worldWidth;
      height = width / aspectRatio;
    }

    float correctionX = (worldWidth - width) / 2;
    float correctionY = (worldHeight - height) / 2;

    Vector2 offsetVect2 = getMouseDirection();
    float offsetX = (-offsetVect2.x * padding / 2) + correctionX;
    float offsetY = (offsetVect2.y * padding / 2) + correctionY;

    stage.getBatch().draw(bgTexture, offsetX - padding / 2, offsetY - padding / 2,
        width + padding, height + padding);

  }

  /**
   * Returns a 2-dimensional <a href="https://en.wikipedia.org/wiki/Unit_vector">
   * unit vector</a> from the centre of the screen, towards the mouse position.
   */
  private Vector2 getMouseDirection() {
    float x = Gdx.input.getX();
    float y = Gdx.input.getY();

    int centerX = (int) stage.getWidth() / 2;
    int centerY = (int) stage.getHeight() / 2;

    float diffX = centerX - x;
    float diffY = centerY - y;

    float normX = diffX / centerX;
    float normY = diffY / centerY;

    return new Vector2(normX, normY);

  }

  @Override
  public void resize(int width, int height) {
    if (width == 0 || height == 0) {
      return;
    }
    playButton.getStyle().font.getData()
        .setScale(width / Constants.TEXT_BUTTON_FONT_SCALING_FACTOR);
    quitButton.getStyle().font.getData()
        .setScale(width / Constants.TEXT_BUTTON_FONT_SCALING_FACTOR);
    stage.getViewport().update(width, height, true);
  }

  @Override
  public void pause() {

  }

  @Override
  public void resume() {

  }

  @Override
  public void hide() {

  }

  @Override
  public void dispose() {
    stage.dispose();
    skin.dispose();
    bgTexture.dispose();
  }
}
