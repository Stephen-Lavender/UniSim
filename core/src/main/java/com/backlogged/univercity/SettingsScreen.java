package com.backlogged.univercity;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Value;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

/**
 * Handles rendering and logic for the settings screen.
 */
public class SettingsScreen implements Screen {
  private Skin skin;
  private Stage stage;
  private Table table;
  private Table buttonsTable;
  private Texture bgTexture;
  private ScrollPane scrollPane;

  private Label settingsLabel;

  private TextButton backButton;
  private TextButton quitButton;

  private Label musicEnabledLabel;
  private CheckBox musicEnabledCheckBox;

  private Label musicVolumeLabel;
  private Slider musicVolumeSlider;

  private Label soundEnabledLabel;
  private CheckBox soundEnabledCheckBox;

  private Label soundVolumeLabel;
  private Slider soundVolumeSlider;

  private Label fullScreenLabel;
  private CheckBox fullScreenCheckBox;

  private Label mouseSensitivityLabel;
  private Slider mouseSensitivitySlider;

  private Label keyboardSensitivityLabel;
  private Slider keyboardSensitivitySlider;

  private Label keyboardBindingUpLabel;
  private TextButton keyboardBindingUpButton;

  private Label keyboardBindingDownLabel;
  private TextButton keyboardBindingDownButton;

  private Label keyboardBindingLeftLabel;
  private TextButton keyboardBindingLeftButton;

  private Label keyboardBindingRightLabel;
  private TextButton keyboardBindingRightButton;

  private Label keyboardBindingZoomInLabel;
  private TextButton keyboardBindingZoomInButton;

  private Label keyboardBindingZoomOutLabel;
  private TextButton keyboardBindingZoomOutButton;

  /**
   * Sets up the settings screen.
   *
   * @param game           current instance of the game
   * @param previousScreen the screen to return to after the user is done changing
   *                       settings
   */
  public SettingsScreen(Game game, Screen previousScreen) {
    skin = new Skin(Gdx.files.internal(Constants.UI_SKIN_PATH));
    stage = new Stage(new ScreenViewport());
    table = new Table(skin);

    settingsLabel = new Label("SETTINGS", skin, "redFont");
    backButton = new TextButton("BACK", skin);
    quitButton = new TextButton("QUIT", skin, "redTextButton");
    backButton.addListener(new ClickListener() {
      public void clicked(InputEvent e, float x, float y) {
        game.setScreen(previousScreen);
      }
    });

    quitButton.addListener(new ClickListener() {
      public void clicked(InputEvent e, float x, float y) {
        Gdx.app.exit();
      }
    });
    buttonsTable = new Table(skin);
    buttonsTable.add(backButton).expandX().width(Value.percentWidth(0.3f, table))
        .height(Value.percentWidth(0.081f, table))
        .padTop(20)
        .padBottom(10);
    buttonsTable.add(quitButton).expandX().width(Value.percentWidth(0.3f, table))
        .height(Value.percentWidth(0.081f, table))
        .padTop(20)
        .padBottom(10);
    table.add(settingsLabel).spaceBottom(20).padTop(10);
    table.row();
    createScrollPane();
    table.add(scrollPane).growX().bottom();
    table.row();
    table.add(buttonsTable);
    table.setFillParent(true);
    table.setDebug(false);
    stage.addActor(table);

    bgTexture = new Texture(Constants.BACKGROUND_PICTURE_PATH);

  }

  /**
   * Creates the scroll pane and fills it with all the settings options.
   */
  private void createScrollPane() {
    Table preferencesTable = new Table(skin);
    preferencesTable.setDebug(false);
    musicEnabledLabel = new Label("MUSIC ENABLED", skin, "lightOrangeFont");
    musicEnabledCheckBox = new CheckBox("", skin);
    musicEnabledCheckBox.setChecked(GamePreferences.isMusicEnabled());
    musicEnabledCheckBox.addListener(
        new ClickListener() {
          public void clicked(InputEvent e, float x, float y) {
            GamePreferences.setMusicEnabled(musicEnabledCheckBox.isChecked());
            if (musicEnabledCheckBox.isChecked()) {
              Soundtrack.play();
            } else {
              Soundtrack.pause();
            }
          }
        });

    musicVolumeLabel = new Label("MUSIC VOLUME", skin, "lightOrangeFont");
    musicVolumeSlider = new Slider(0, 1, 0.1f, false, skin);
    musicVolumeSlider.setValue(GamePreferences.getMusicVolume());
    musicVolumeSlider.addListener(new ChangeListener() {
      public void changed(ChangeEvent event, Actor actor) {
        GamePreferences.setMusicVolume(musicVolumeSlider.getValue());
        Soundtrack.play();
      }
    });

    soundEnabledLabel = new Label("SOUND ENABLED", skin, "lightOrangeFont");
    soundEnabledCheckBox = new CheckBox("", skin);
    soundEnabledCheckBox.setChecked(GamePreferences.isSoundEnabled());
    soundEnabledCheckBox.addListener(new ClickListener() {
      public void clicked(InputEvent e, float x, float y) {
        GamePreferences.setSoundEnabled(soundEnabledCheckBox.isChecked());
      }
    });

    soundVolumeLabel = new Label("SOUND VOLUME", skin, "lightOrangeFont");
    soundVolumeSlider = new Slider(0, 1, 0.1f, false, skin);
    soundVolumeSlider.setValue(GamePreferences.getSoundVolume());
    soundVolumeSlider.addListener(new ChangeListener() {
      public void changed(ChangeEvent event, Actor actor) {
        GamePreferences.setSoundVolume(soundVolumeSlider.getValue());
      }
    });

    fullScreenLabel = new Label("FULLSCREEN", skin, "lightOrangeFont");
    fullScreenCheckBox = new CheckBox("", skin);
    fullScreenCheckBox.setChecked(GamePreferences.isFullscreen());
    fullScreenCheckBox.addListener(new ClickListener() {
      public void clicked(InputEvent e, float x, float y) {
        GamePreferences.setFullscreen(fullScreenCheckBox.isChecked());
        if (fullScreenCheckBox.isChecked()) {
          Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
        } else {
          Gdx.graphics.setWindowedMode(1280, 720);
        }
      }
    });

    mouseSensitivityLabel = new Label("MOUSE SENSITIVITY", skin, "lightOrangeFont");
    mouseSensitivitySlider = new Slider(0.1f, 2f, 0.1f, false, skin);
    mouseSensitivitySlider.setValue(GamePreferences.getMouseSensitivity());
    mouseSensitivitySlider.addListener(new ChangeListener() {
      public void changed(ChangeEvent event, Actor actor) {
        GamePreferences.setMouseSensitivity(mouseSensitivitySlider.getValue());
      }
    });

    keyboardSensitivityLabel = new Label("KEYBOARD SENSITIVITY", skin, "lightOrangeFont");
    keyboardSensitivitySlider = new Slider(0.1f, 2f, 0.1f, false, skin);
    keyboardSensitivitySlider.setValue(GamePreferences.getKeyboardSensitivity());
    keyboardSensitivitySlider.addListener(new ChangeListener() {
      public void changed(ChangeEvent event, Actor actor) {
        GamePreferences.setKeyboardSensitivity(keyboardSensitivitySlider.getValue());
      }
    });

    keyboardBindingUpLabel = new Label("UP KEY", skin, "lightOrangeFont");
    keyboardBindingUpButton = new TextButton(
        Input.Keys.toString(GamePreferences.getKeyboardBindingUp()), skin);
    keyboardBindingUpButton.addListener(
        new ClickListener() {
          public void clicked(InputEvent e, float x, float y) {
            // add a listener that waits for the next key press and sets it as the up key
            keyboardBindingUpButton.setText("...");
            Gdx.input.setInputProcessor(
                new InputAdapter() {
                  public boolean keyDown(int keycode) {
                    GamePreferences.setKeyboardBindingUp(keycode);
                    keyboardBindingUpButton.setText(Input.Keys.toString(keycode));
                    Gdx.input.setInputProcessor(stage);
                    return false;
                  }
                });
          }
        });

    keyboardBindingDownLabel = new Label("DOWN KEY", skin, "lightOrangeFont");
    keyboardBindingDownButton = new TextButton(
        Input.Keys.toString(GamePreferences.getKeyboardBindingDown()), skin);
    keyboardBindingDownButton.addListener(
        new ClickListener() {
          public void clicked(InputEvent e, float x, float y) {
            // add a listener that waits for the next key press and sets it as the down key
            keyboardBindingDownButton.setText("...");
            Gdx.input.setInputProcessor(
                new InputAdapter() {
                  public boolean keyDown(int keycode) {
                    GamePreferences.setKeyboardBindingDown(keycode);
                    keyboardBindingDownButton.setText(Input.Keys.toString(keycode));
                    Gdx.input.setInputProcessor(stage);
                    return false;
                  }
                });
          }
        });

    keyboardBindingLeftLabel = new Label("LEFT KEY", skin, "lightOrangeFont");
    keyboardBindingLeftButton = new TextButton(
        Input.Keys.toString(GamePreferences.getKeyboardBindingLeft()), skin);
    keyboardBindingLeftButton.addListener(
        new ClickListener() {
          public void clicked(InputEvent e, float x, float y) {
            // add a listener that waits for the next key press and sets it as the left key
            keyboardBindingLeftButton.setText("...");
            Gdx.input.setInputProcessor(
                new InputAdapter() {
                  public boolean keyDown(int keycode) {
                    GamePreferences.setKeyboardBindingLeft(keycode);
                    keyboardBindingLeftButton.setText(Input.Keys.toString(keycode));
                    Gdx.input.setInputProcessor(stage);
                    return false;
                  }
                });
          }
        });

    keyboardBindingRightLabel = new Label("RIGHT KEY", skin, "lightOrangeFont");
    keyboardBindingRightButton = new TextButton(
        Input.Keys.toString(GamePreferences.getKeyboardBindingRight()), skin);
    keyboardBindingRightButton.addListener(
        new ClickListener() {
          public void clicked(InputEvent e, float x, float y) {
            // add a listener that waits for the next key press and sets it as the right key
            keyboardBindingRightButton.setText("...");
            Gdx.input.setInputProcessor(
                new InputAdapter() {
                  public boolean keyDown(int keycode) {
                    GamePreferences.setKeyboardBindingRight(keycode);
                    keyboardBindingRightButton.setText(Input.Keys.toString(keycode));
                    Gdx.input.setInputProcessor(stage);
                    return false;
                  }
                });
          }
        });

    keyboardBindingZoomInLabel = new Label("ZOOM IN KEY", skin, "lightOrangeFont");
    keyboardBindingZoomInButton = new TextButton(
        Input.Keys.toString(GamePreferences.getKeyboardBindingZoomIn()), skin);
    keyboardBindingZoomInButton.addListener(
        new ClickListener() {
          public void clicked(InputEvent e, float x, float y) {
            // add a listener that waits for the next key press and sets it as the zoom in
            // key
            keyboardBindingZoomInButton.setText("...");
            Gdx.input.setInputProcessor(
                new InputAdapter() {
                  public boolean keyDown(int keycode) {
                    GamePreferences.setKeyboardBindingZoomIn(keycode);
                    keyboardBindingZoomInButton.setText(Input.Keys.toString(keycode));
                    Gdx.input.setInputProcessor(stage);
                    return false;
                  }
                });
          }
        });

    keyboardBindingZoomOutLabel = new Label("ZOOM OUT KEY", skin, "lightOrangeFont");
    keyboardBindingZoomOutButton = new TextButton(
        Input.Keys.toString(GamePreferences.getKeyboardBindingZoomOut()), skin);
    keyboardBindingZoomOutButton.addListener(
        new ClickListener() {
          public void clicked(InputEvent e, float x, float y) {
            // add a listener that waits for the next key press and sets it as the zoom out
            // key
            keyboardBindingZoomOutButton.setText("...");
            Gdx.input.setInputProcessor(
                new InputAdapter() {
                  public boolean keyDown(int keycode) {
                    GamePreferences.setKeyboardBindingZoomOut(keycode);
                    keyboardBindingZoomOutButton.setText(Input.Keys.toString(keycode));
                    Gdx.input.setInputProcessor(stage);
                    return false;
                  }
                });
          }
        });

    preferencesTable.add(musicEnabledLabel).space(20);
    preferencesTable.add(musicEnabledCheckBox).space(20);
    preferencesTable.row();
    preferencesTable.add(musicVolumeLabel).space(20);
    preferencesTable.add(musicVolumeSlider)
        .width(Value.percentWidth(.3f, preferencesTable))
        .fill()
        .space(20);
    preferencesTable.row();
    preferencesTable.add(soundEnabledLabel).space(20);
    preferencesTable.add(soundEnabledCheckBox).space(20);
    preferencesTable.row();
    preferencesTable.add(soundVolumeLabel).space(20);
    preferencesTable.add(soundVolumeSlider)
        .width(Value.percentWidth(.3f, preferencesTable))
        .fill()
        .space(20);
    preferencesTable.row();
    preferencesTable.add(fullScreenLabel).space(20);
    preferencesTable.add(fullScreenCheckBox).space(20);
    preferencesTable.row();
    preferencesTable.add(mouseSensitivityLabel).space(20);
    preferencesTable.add(mouseSensitivitySlider)
        .width(Value.percentWidth(.3f, preferencesTable))
        .fill()
        .space(20);
    preferencesTable.row();
    preferencesTable.add(keyboardSensitivityLabel).space(20);
    preferencesTable.add(keyboardSensitivitySlider)
        .width(Value.percentWidth(.3f, preferencesTable))
        .space(20);
    preferencesTable.row();
    preferencesTable.add(keyboardBindingUpLabel).space(20);
    preferencesTable.add(keyboardBindingUpButton).space(20)
        .width(Value.percentWidth(0.1f, table))
        .height(Value.percentWidth(0.032f, table));
    preferencesTable.row();
    preferencesTable.add(keyboardBindingDownLabel).space(20);
    preferencesTable.add(keyboardBindingDownButton).space(20)
        .width(Value.percentWidth(0.1f, table))
        .height(Value.percentWidth(0.032f, table));
    preferencesTable.row();
    preferencesTable.add(keyboardBindingLeftLabel).space(20);
    preferencesTable.add(keyboardBindingLeftButton).space(20)
        .width(Value.percentWidth(0.1f, table))
        .height(Value.percentWidth(0.032f, table));
    preferencesTable.row();
    preferencesTable.add(keyboardBindingRightLabel).space(20);
    preferencesTable.add(keyboardBindingRightButton).space(20)
        .width(Value.percentWidth(0.1f, table))
        .height(Value.percentWidth(0.032f, table));
    preferencesTable.row();
    preferencesTable.add(keyboardBindingZoomInLabel).space(20);
    preferencesTable.add(keyboardBindingZoomInButton).space(20)
        .width(Value.percentWidth(0.1f, table))
        .height(Value.percentWidth(0.032f, table));
    preferencesTable.row();
    preferencesTable.add(keyboardBindingZoomOutLabel).space(20);
    preferencesTable.add(keyboardBindingZoomOutButton).space(20)
        .width(Value.percentWidth(0.1f, table))
        .height(Value.percentWidth(0.032f, table));
    scrollPane = new ScrollPane(preferencesTable);
    scrollPane.setScrollingDisabled(true, false);
    scrollPane.setDebug(false);
    scrollPane.setFlickScroll(false);
    stage.setScrollFocus(scrollPane);
  }

  @Override
  public void show() {
    Gdx.input.setInputProcessor(stage);
    Soundtrack.play();
  }

  @Override
  public void render(float delta) {
    ScreenUtils.clear(255, 255, 255, 255);
    stage.getBatch().begin();
    renderBackground();
    stage.getBatch().end();
    stage.act(delta);
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
    stage.getViewport().update(width, height, true);
    backButton.getStyle().font.getData()
        .setScale(width / Constants.TEXT_BUTTON_FONT_SCALING_FACTOR);
    quitButton.getStyle().font.getData()
        .setScale(width / Constants.TEXT_BUTTON_FONT_SCALING_FACTOR);
    settingsLabel.setFontScale(width / Constants.SETTINGS_TITLE_FONT_SCALING_FACTOR);
    musicEnabledLabel.setFontScale(width / Constants.SETTINGS_LABEL_FONT_SCALING_FACTOR);
    musicVolumeLabel.setFontScale(width / Constants.SETTINGS_LABEL_FONT_SCALING_FACTOR);
    soundEnabledLabel.setFontScale(width / Constants.SETTINGS_LABEL_FONT_SCALING_FACTOR);
    soundVolumeLabel.setFontScale(width / Constants.SETTINGS_LABEL_FONT_SCALING_FACTOR);
    fullScreenLabel.setFontScale(width / Constants.SETTINGS_LABEL_FONT_SCALING_FACTOR);
    mouseSensitivityLabel.setFontScale(width / Constants.SETTINGS_LABEL_FONT_SCALING_FACTOR);
    keyboardSensitivityLabel.setFontScale(width / Constants.SETTINGS_LABEL_FONT_SCALING_FACTOR);
    keyboardBindingUpLabel.setFontScale(width / Constants.SETTINGS_LABEL_FONT_SCALING_FACTOR);
    keyboardBindingDownLabel.setFontScale(width / Constants.SETTINGS_LABEL_FONT_SCALING_FACTOR);
    keyboardBindingLeftLabel.setFontScale(width / Constants.SETTINGS_LABEL_FONT_SCALING_FACTOR);
    keyboardBindingRightLabel.setFontScale(width / Constants.SETTINGS_LABEL_FONT_SCALING_FACTOR);
    keyboardBindingZoomInLabel.setFontScale(width / Constants.SETTINGS_LABEL_FONT_SCALING_FACTOR);
    keyboardBindingZoomOutLabel.setFontScale(width / Constants.SETTINGS_LABEL_FONT_SCALING_FACTOR);
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
