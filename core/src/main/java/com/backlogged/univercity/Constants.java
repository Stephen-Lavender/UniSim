package com.backlogged.univercity;

/**
 * This Class acts as a source for all constants used in the game.
 * The constructor has the 'private' access modifier to restrict
 * instantiation from other classes in the package.
 */
public final class Constants {

  private Constants() {
    // restricts instantiation of class
  }

  /*
   * The total in-game time elapse is 3 years and total real-time
   * elapse is 5 minutes, so each month is 8.33 seconds of real time.
   */
  public static final float ONE_MONTH = 8.33f;

  /**
   * Font scaling factor for the Title label.
   */
  public static final float TITLE_FONT_SCALING_FACTOR = 1300f;
  /**
   * Font scaling factor for the Game Over label.
   */
  public static final float GAME_OVER_FONT_SCALING_FACTOR = 700f;

  /**
   * Font scaling factor for TextButtons.
   */
  public static final float TEXT_BUTTON_FONT_SCALING_FACTOR = 1200f;

  /**
   * Font scaling factor for the Settings title label.
   */
  public static final float SETTINGS_TITLE_FONT_SCALING_FACTOR = 1000f;

  /**
   * Font scaling factor for the labels in the settings screen.
   */
  public static final float SETTINGS_LABEL_FONT_SCALING_FACTOR = 1500f;

  /**
   * Path to the background picture.
   */
  public static final String BACKGROUND_PICTURE_PATH = "mapBackgroundBlur.png";

  /**
   * Path to the UI skin.
   */
  public static final String UI_SKIN_PATH = "uiskin.json";

  /**
   * Path to the preferences file.
   */
  public static final String PREFS_NAME = "preferences";

  /**
   * Default mouse sensitivity value.
   */
  public static final float DEFAULT_MOUSE_SENSITIVITY = 0.025f;

  /**
   * Default keyboard sensitivity value.
   */
  public static final float DEFAULT_KEYBOARD_SENSITIVITY = 0.02f;

  /**
   * Path to the game map.
   */
  public static final String MAP_PATH = "Univer-City.tmx";
  /**
   * Path to the game soundtrack.
   */
  public static final String SOUNDTRACK_PATH = "backgroundsong.wav";

  /**
   * Path to the game over sound.
   */
  public static final String GAME_OVER_SOUND_PATH = "gameover.mp3";

  public static final String PLAYER_SCORES_PATH = "PlayerScores.txt";

}
