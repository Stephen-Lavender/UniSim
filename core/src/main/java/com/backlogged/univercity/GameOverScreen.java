package com.backlogged.univercity;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane.TitlePaneLayout;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.Value;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

/**
 * Handles the rendering and logic for the game over screen.
 */
public class GameOverScreen implements Screen {
  private Skin skin;
  private Stage stage;
  private Table table;
  private Table enterName;
  private Label gameOverLabel;
  private TextButton startAgainButton;
  private TextButton quitButton;
  private Label satScoreLabel;

  /**
   * Sets up the game over screen.
   *
   * @param game the current instance of the game
   */

   
  public GameOverScreen(Game game, int finalScore) {
    skin = new Skin(Gdx.files.internal(Constants.UI_SKIN_PATH));
    stage = new Stage(new ScreenViewport());
 

    table = new Table(skin);
    table.setFillParent(true);
    table.setDebug(false);

    stage.addActor(table);
    table.setVisible(false);
    gameOverLabel = new Label("GAME OVER!", skin, "lightOrangeFont");

    
    TextField textField = new TextField("", skin);
    textField.setMessageText("Enter Name");;
    textField.scaleBy(3);
    textField.setAlignment(Align.center);
    textField.setMaxLength(15);

    startAgainButton = new TextButton("Submit", skin);
    startAgainButton.addListener(new ClickListener() {
      public void clicked(InputEvent e, float x, float y) {
        LeaderBoard leaderBoard = new LeaderBoard();
        leaderBoard.addNewScore(textField.getText(), finalScore);
        game.setScreen(new TitleScreen(game));
      }
    });

    quitButton = new TextButton("QUIT", skin, "redTextButton");
    quitButton.addListener(new ClickListener() {
      public void clicked(InputEvent e, float x, float y) {
        Gdx.app.exit();
      }
    });

    satScoreLabel = new Label(finalScore + "%",skin, "whiteFont");
    satScoreLabel.scaleBy(5);
    satScoreLabel.setAlignment(Align.center);

    



    enterName = new Table();
    enterName.setFillParent(true);
    enterName.setDebug(false);



    
    


    enterName.add(gameOverLabel).top().padTop(100).top().padTop(100).width(Value.percentWidth(0.3f, enterName))
    .height(Value.percentHeight(0.1f, enterName));
    enterName.row();
    enterName.add(satScoreLabel).top().padTop(100).top().padTop(100).width(Value.percentWidth(0.3f, enterName))
    .height(Value.percentHeight(0.1f, enterName));
    enterName.row();
    enterName.add(textField).top().padTop(100).width(Value.percentWidth(0.3f, enterName))
        .height(Value.percentHeight(0.1f, enterName));
    enterName.row();
    enterName.add(startAgainButton).top().padTop(50).width(Value.percentWidth(0.3f, enterName))
        .height(Value.percentHeight(0.1f, enterName));

    
    stage.addActor(enterName);





  }

  @Override
  public void render(float delta) {
    ScreenUtils.clear(0, 0, 0, 0);
    stage.act();
    stage.draw();
  }

  @Override
  public void resize(int width, int height) {
    if (width == 0 || height == 0) {
      return;
    }
    gameOverLabel.setFontScale(width / Constants.GAME_OVER_FONT_SCALING_FACTOR);
    startAgainButton.getStyle().font.getData()
        .setScale(width / Constants.TEXT_BUTTON_FONT_SCALING_FACTOR);
    quitButton.getStyle().font.getData()
        .setScale(width / Constants.TEXT_BUTTON_FONT_SCALING_FACTOR);
    stage.getViewport().update(width, height, true);
  }

  @Override
  public void pause() {
  }

  @Override
  public void show() {
    Soundtrack.pause();
    Sound gameOverSound = Gdx.audio.newSound(Gdx.files.internal(Constants.GAME_OVER_SOUND_PATH));
    if (GamePreferences.isSoundEnabled()) {
      gameOverSound.play(GamePreferences.getSoundVolume());
    }
    Gdx.input.setInputProcessor(stage);
  }

  @Override
  public void resume() {

  }

  @Override
  public void hide() {

  }

  @Override
  public void dispose() {

  }
}
