package com.backlogged.univercity;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

/**
 * Handles input and rendering of the UI, map and buildings.
 */
public class MapScreen implements Screen {

    /**
     * Size of one tile (currently 16px x 16px).
     */
    private final float UNIT_SCALE = 1 / 16f;

    private final Game game;
    private final TiledMap map;
    private final OrthogonalTiledMapRenderer renderer;
    private final OrthographicCamera camera;
    private final Skin skin;
    private final Stage stage;
    private final Table table;
    private Table achievementTable;
    private Label displayAchievement;

    private final Table popUpTable;
    private final TextButton timerLabel;
    private final Button pauseButton;
    private final Button settingsButton;
    private final Button pauseOverlay;
    private final InGameTimer timer;
    private boolean mouseDown;
    private boolean dragging;
    private float oldMouseX;
    private float oldMouseY;
    private TextButton satisfactionLabel;
    private Label displayAchievementDesc;
    private Achievements achievements = new Achievements();
    private float timestamp = -1;

    // Buildings
    private final Button accommodationButton;
    private final Button recreationalBuilding1;
    private final Button recreationalBuilding2;
    private final Button courseBuilding;
    private final Button cafateriaBuilding;
    private final BuildingManager buildingManager;
    private final TextButton buildingCounterLabel;
    private final TextTooltip detailedBuildingCounter;
    private Events world;
    private int eventcount;
    private Building selectedBuilding;





    /**
     * Setup the main game window (map).
     *
     * @param game the current instance of game
     */
    public MapScreen(Game game) {
        this.game = game;
        world = new Events();
        eventcount = 0;
        map = new TmxMapLoader().load(Constants.MAP_PATH);
        renderer = new OrthogonalTiledMapRenderer(map, UNIT_SCALE);
        camera = new OrthographicCamera();
        float width = Gdx.graphics.getWidth();
        float height = Gdx.graphics.getHeight();
        camera.setToOrtho(false, width * UNIT_SCALE, (width * UNIT_SCALE) * (height / width));

        timer = new InGameTimer(5);
        var buildingRenderer = new BuildingRenderer(new TextureAtlas(
            Gdx.files.internal("buildings/buildings.atlas")));
        buildingManager = new BuildingManager(UNIT_SCALE, buildingRenderer,
            new BuildingPlacementManager(map.getLayers().get("OOB Layer")));
        stage = new Stage(new ScreenViewport());
        skin = new Skin(Gdx.files.internal(Constants.UI_SKIN_PATH));

        timerLabel = new TextButton("5:00", skin, "semesterTimerTextButton");
        buildingCounterLabel = new TextButton("5:00", skin, "buildingCountTextButton");
        satisfactionLabel  = new TextButton(String.valueOf(buildingManager.satscore.score) + "%", skin);

        detailedBuildingCounter = new TextTooltip(buildingManager.getBuildingTypeCounts(), skin);
        detailedBuildingCounter.getContainer().getActor().setFontScale(0.75f);
        detailedBuildingCounter.getContainer().getActor().setAlignment(Align.center);
        buildingCounterLabel.addListener(detailedBuildingCounter);

        pauseOverlay = new Button(skin, "pauseOverlay");
        pauseOverlay.setVisible(false);
        pauseButton = new Button(skin, "pauseToggle");
        pauseButton.addListener(new ClickListener() {
            public void clicked(InputEvent e, float x, float y) {
                if (timer.isUserStopped()) {
                    timer.userStartTime();
                    pauseOverlay.setVisible(false);
                } else {
                    timer.userStopTime();
                    pauseOverlay.setVisible(true);

                }
            }
        });

        settingsButton = new Button(skin, "settingsIcon");
        settingsButton.addListener(new ClickListener() {
            public void clicked(InputEvent e, float x, float y) {
                timer.systemStopTime(); // pause Time while in settings
                game.setScreen(new SettingsScreen(game, game.getScreen()));
            }
        });

        //////////////// BUILDINGS

        accommodationButton = new Button(skin, "bedIcon");
        accommodationButton.addListener(new ClickListener() {
            public void clicked(InputEvent e, float x, float y) {
                // Deal with clicking later
                buildingManager.setBuildingState(BuildingState.BUILDING);
                buildingManager.chooseLocationOfBuilding(0);
            }
        });

        recreationalBuilding1 = new Button(skin, "sportIcon");
        recreationalBuilding1.addListener(new ClickListener() {
            public void clicked(InputEvent e, float x, float y) {
                // Deal with clicking later
                buildingManager.setBuildingState(BuildingState.BUILDING);
                buildingManager.chooseLocationOfBuilding(3);
            }
        });

        recreationalBuilding2 = new Button(skin, "sportIcon");
        recreationalBuilding2.addListener(new ClickListener() {
            public void clicked(InputEvent e, float x, float y) {
                // Deal with clicking later
                buildingManager.setBuildingState(BuildingState.BUILDING);
                buildingManager.chooseLocationOfBuilding(4);
            }
        });

        courseBuilding = new Button(skin, "bookIcon");
        courseBuilding.addListener(new ClickListener() {
            public void clicked(InputEvent e, float x, float y) {
                // Deal with clicking later
                buildingManager.setBuildingState(BuildingState.BUILDING);
                buildingManager.chooseLocationOfBuilding(2);
            }
        });

        cafateriaBuilding = new Button(skin, "foodIcon");
        cafateriaBuilding.addListener(new ClickListener() {
            public void clicked(InputEvent e, float x, float y) {
                // Deal with clicking later
                buildingManager.setBuildingState(BuildingState.BUILDING);
                buildingManager.chooseLocationOfBuilding(1);
            }
        });

        timerLabel.addListener(new ClickListener() {
            public void clicked(InputEvent e, float x, float y) {
                game.setScreen(new GameOverScreen(game,buildingManager.satscore.score));
            }
        });

        detailedBuildingCounter.setInstant(true);
        // Create the main table
        table = new Table(skin);
        table.setFillParent(true);
        table.setDebug(false);
        table.setTouchable(Touchable.enabled);
        stage.addActor(table);

        Table topRow = new Table(skin);
        topRow.add(timerLabel).top().left().width(Value.percentWidth(0.3f, table))
            .height(Value.percentWidth(0.072f, table));
        topRow.add(buildingCounterLabel).expandX().top().left().width(Value.percentWidth(0.1f, table))
            .height(Value.percentWidth(0.072f, table));
        topRow.add().colspan(5).expandX().top().left();
        topRow.add(pauseButton).expandX().top().right().spaceRight(10)
            .width(Value.percentWidth(0.05f, table))
            .height(Value.percentWidth(0.05f, table));
        topRow.add(settingsButton).top().right()
            .width(Value.percentWidth(0.05f, table))
            .height(Value.percentWidth(0.05f, table));
        table.add(topRow).colspan(5).expandX().expandY().top().padTop(10);
        topRow.add(satisfactionLabel).expandX().top().right().width(Value.percentWidth(0.1f, table))
            .height(Value.percentWidth(0.072f, table));

        table.row();


        //achivements

        achievementTable = new Table();

        displayAchievement = new Label("placeholder", skin,"Achievement");
        displayAchievementDesc = new Label("placeholder", skin,"Achievement");


        achievementTable.setSize(700, 500);
        achievementTable.setDebug(true);
        achievementTable.setPosition(0,stage.getHeight() - 700);
  
        
        achievementTable.add(displayAchievement).top().width(700).height(100);
        displayAchievement.setAlignment(Align.center);
        achievementTable.row();
        achievementTable.add(displayAchievementDesc).top().width(700).height(200);
        displayAchievementDesc.setAlignment(Align.center);





        stage.addActor(achievementTable);
        achievementTable.setVisible(false);


// Bottom row: evenly distribute building icons
        Table bottomRow = new Table(skin);
        bottomRow.add(accommodationButton).grow().width(Value.percentWidth(0.1f, table))
            .height(Value.percentWidth(0.1f, table));
        bottomRow.add(cafateriaBuilding).grow().width(Value.percentWidth(0.1f, table))
            .height(Value.percentWidth(0.1f, table));
        bottomRow.add(courseBuilding).grow().width(Value.percentWidth(0.1f, table))
            .height(Value.percentWidth(0.1f, table));
        bottomRow.add(recreationalBuilding1).grow().width(Value.percentWidth(0.1f, table))
            .height(Value.percentWidth(0.1f, table));
        bottomRow.add(recreationalBuilding2).grow().width(Value.percentWidth(0.1f, table))
            .height(Value.percentWidth(0.1f, table));

// Add bottom row to the main table
        table.add(bottomRow).colspan(5).expandX().expandY().bottom().padBottom(10);

// Enable debug lines for layout visualization during development
        table.debug();







        TextButton upgradeButton = new TextButton("UPGRADE", skin);
        upgradeButton.addListener(new ClickListener(){
            public void clicked(InputEvent e, float x, float y){
                System.out.println(selectedBuilding.getMapPos());
                selectedBuilding.upgrade();
            }
        });

        TextButton moveButton = new TextButton("MOVE", skin);
        moveButton.addListener(new ClickListener(){
            public void clicked(InputEvent e, float x, float y){
                buildingManager.setBuildingState(BuildingState.MOVING);
                buildingManager.setSelectedBuilding(selectedBuilding);
            }
        });

        TextButton deleteButton = new TextButton("DELETE", skin);
        deleteButton.addListener(new ClickListener(){
            public void clicked(InputEvent e, float x, float y){
                buildingManager.setBuildingState(BuildingState.DELETING);
                buildingManager.setSelectedBuilding(selectedBuilding);
            }
        });



        popUpTable = new Table(skin);
        stage.addActor(popUpTable);
        popUpTable.setVisible(false);
        popUpTable.setFillParent(true);
        popUpTable.setDebug(false);
        popUpTable.setTouchable(Touchable.enabled);

        popUpTable.add(upgradeButton).expandY().bottom().left().width(Value.percentWidth(0.1f, popUpTable))
            .height(Value.percentWidth(0.1f, popUpTable));
        popUpTable.add(moveButton).expandY().bottom().left().width(Value.percentWidth(0.1f, popUpTable))
            .height(Value.percentWidth(0.1f, popUpTable));
        popUpTable.add(deleteButton).expandY().bottom().left().width(Value.percentWidth(0.1f, popUpTable))
            .height(Value.percentWidth(0.1f, popUpTable));



        timer.initialiseTimerValues();
        timer.userStartTime();
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        timer.systemStartTime();
        Soundtrack.play();
    }

    @Override
    public void render(float delta) {
        handleInput();
        camera.update();
        buildingManager.setCamera(camera);
        buildingManager.handleInput();
        buildingManager.update();
        renderer.setView(camera);

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        renderer.render();
        buildingManager.render();
        float timeLeft = timer.updateTime(delta);
        float elapsedTime = timer.getTimeElapsed(delta);

        if ((elapsedTime > (Constants.ONE_MONTH * 3)) && eventcount == 0) {
            if (world.WorldEvent(buildingManager.getPlacedBuildings())){
                eventcount++;
            }
        }

        if ((elapsedTime > Constants.ONE_MONTH * 14 ) && eventcount < 3)
        {
            if(world.ChooseEvent() == 1)
            {
                // do the visuals for a positive event

                // event logic
            }
            else
            {
                // negative event

                // event logic

            }
            eventcount++;
        }
        if (timeLeft < 1) {

            game.setScreen(new GameOverScreen(game,buildingManager.satscore.score));

        }

        detailedBuildingCounter.getContainer().getActor()
            .setText(buildingManager.getBuildingTypeCounts());
        buildingCounterLabel.setText(Integer.toString(buildingManager.getBuildingCount()));
        satisfactionLabel.setText(String.valueOf(buildingManager.satscore.score) + "%");
        timerLabel.setText(timer.output());
        stage.act();
        stage.draw();

        //achievement check

        achievements.updateData(buildingManager.getPlacedBuildings(), buildingManager.satscore.score);
        if(!achievementTable.isVisible()) {
            displayAchievement.setText(achievements.checkall());
            if (displayAchievement.getText().toString() != "") {
                displayAchievementDesc.setText(achievements.getAchievementDesc(displayAchievement.getText().toString()));
                System.out.print(achievements.getAchievementDesc(displayAchievement.getText().toString()));
                displayAchievement.setText("Achieved: " + displayAchievement.getText());
                displayAchievementDesc.setScaleX(displayAchievement.getScaleX());;

                achievementTable.setVisible(true);
                timestamp = timeLeft - 8;
            }
        }
        else if (timeLeft <= timestamp) {
            achievementTable.setVisible(false);
            displayAchievement.setText("");
            timestamp = -1;

        }

    }

    /**
     * Handles the user's mouse input, allowing dragging of the map.
     */
    private void handleMouseInput() {
        Vector3 touchPoint = new Vector3();
        mouseDown = Gdx.input.isButtonPressed(Input.Buttons.LEFT);

        if (Gdx.input.justTouched()) {
            camera.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));

            for (Building building: buildingManager.getPlacedBuildings()){
                int buildingX = building.getMapPos().getColumn();
                int buildingY = building.getMapPos().getRow();

                if (building.getSprite().getBoundingRectangle().setPosition(buildingX, buildingY).contains(touchPoint.x, touchPoint.y)){
                    System.out.println("MOUSE: " + touchPoint);
                    System.out.println(building.getSprite().getBoundingRectangle().setPosition(buildingX, buildingY));
                    popUpTable.setVisible(true);
                    table.setVisible(false);
                    selectedBuilding = building;
                    break;
                }
                else{
                    table.setVisible(true);
                    popUpTable.setVisible(false);

                }
            }
        }

        if (!dragging && mouseDown) {
            dragging = true;
            oldMouseX = Gdx.input.getX();
            oldMouseY = Gdx.input.getY();
        } else if (!mouseDown) {
            dragging = false;
        }

        if (dragging) {
            float currentX = Gdx.input.getX();
            float currentY = Gdx.input.getY();
            Vector2 translate = new Vector2(-(currentX - oldMouseX), currentY - oldMouseY);
            translate.scl(Constants.DEFAULT_MOUSE_SENSITIVITY
                * GamePreferences.getMouseSensitivity() * camera.zoom);
            camera.translate(translate);
            oldMouseX = currentX;
            oldMouseY = currentY;
        }
    }

    /**
     * Handle user's keyboard inputs, allowing movement and zooming of the map.
     */
    private void handledKeyboardInput() {
        if (Gdx.input.isKeyPressed(GamePreferences.getKeyboardBindingZoomOut())) {
            camera.zoom += Constants.DEFAULT_KEYBOARD_SENSITIVITY
                * GamePreferences.getKeyboardSensitivity();
        }
        if (Gdx.input.isKeyPressed(GamePreferences.getKeyboardBindingZoomIn())) {
            camera.zoom -= Constants.DEFAULT_KEYBOARD_SENSITIVITY
                * GamePreferences.getKeyboardSensitivity();
        }
        if (Gdx.input.isKeyPressed(GamePreferences.getKeyboardBindingLeft())) {
            camera.translate(-1 * GamePreferences.getKeyboardSensitivity(), 0, 0);
        }
        if (Gdx.input.isKeyPressed(GamePreferences.getKeyboardBindingRight())) {
            camera.translate(1 * GamePreferences.getKeyboardSensitivity(), 0, 0);
        }
        if (Gdx.input.isKeyPressed(GamePreferences.getKeyboardBindingDown())) {
            camera.translate(0, -1 * GamePreferences.getKeyboardSensitivity(), 0);
        }
        if (Gdx.input.isKeyPressed(GamePreferences.getKeyboardBindingUp())) {
            camera.translate(0, 1 * GamePreferences.getKeyboardSensitivity(), 0);
        }
    }

    /**
     * Collective method for processing user input.
     */
    private void handleInput() {
        handleMouseInput();
        handledKeyboardInput();

        // keep the map in the viewport
        // https://libgdx.com/wiki/graphics/2d/orthographic-camera
        camera.zoom = MathUtils.clamp(camera.zoom, 0.1f, 100 / camera.viewportWidth);

        float effectiveViewPortWidth = camera.viewportWidth * camera.zoom;
        float effectiveViewPortHeight = camera.viewportHeight * camera.zoom;

        camera.position.x = MathUtils.clamp(camera.position.x, effectiveViewPortWidth / 2f,
            128 - effectiveViewPortWidth / 2f);
        camera.position.y = MathUtils.clamp(camera.position.y, effectiveViewPortHeight / 2f,
            72 - effectiveViewPortHeight / 2f);
    }

    @Override
    public void resize(int width, int height) {
        if (width == 0 || height == 0) {
            return;
        }
        //TODO: replace 1000 with constant
        timerLabel.getStyle().font.getData().setScale(width / 2000f);
        buildingCounterLabel.getStyle().font.getData().setScale(width / 2000f);

        camera.viewportWidth = MathUtils.floor(width / 32f);
        camera.viewportHeight = camera.viewportWidth * height / width;
        camera.update();
        stage.getViewport().update(width, height, true);

    }

    @Override
    public void pause() {
        // Invoked when your application is paused.
        timer.systemStopTime();
    }

    @Override
    public void resume() {
        // Invoked when your application is resumed after pause.
        timer.systemStartTime();
    }

    @Override
    public void hide() {
        // This method is called when another screen replaces this one.
    }

    @Override
    public void dispose() {
        map.dispose();
        stage.dispose();
        skin.dispose();
        renderer.dispose();
    }

}
