package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class MenuScreen implements Screen {
    Assignment2 game;
    // Map and rendering
    private SpriteBatch batch;
    private Skin skin;
    private Stage stage;
    OrthographicCamera camera;
    Viewport viewport;

    // UI textures
    private Texture title; // if we create png file for title
    private Texture background; // for background <- currently temporary png is used

    // To disable buttons when user is on other screen
    private boolean isButtonDisabled;

    // for setting screen size the device
    float screenWidth = Gdx.graphics.getWidth();
    float screenHeight = Gdx.graphics.getHeight();

    // For setting background texture size larger than actual screen size to adjust devices
    private static final float WORLD_WIDTH = (float)(Gdx.graphics.getWidth() * 1.05);;
    private static final float WORLD_HEIGHT = (float)(Gdx.graphics.getHeight() * 1.05);;

    public MenuScreen(Assignment2 game, boolean isButtonDisabled){
        this.game = game;
        this.isButtonDisabled = isButtonDisabled;
    }

    public void create(){

        this.batch = new SpriteBatch();
        this.stage = new Stage();
        this.skin = new Skin(Gdx.files.internal("gui/uiskin.json")); // need path after we found the skin <- current file is came from prac3.

        // set the background and title
        this.background = new Texture("Repeated.png"); // need to set actual data <- current one is temporary setting
        this.title = new Texture("Tittle.png"); // need to set the file, if we have <- current one is temporary setting

        // Set camera
        camera = new OrthographicCamera();
        viewport = new FitViewport(WORLD_WIDTH,WORLD_HEIGHT,camera);

    }

    public void update(){
        // If we want to move the background texture, need to set here.
        float dt = Gdx.graphics.getDeltaTime();
    }

    public void render(float f){
        this.update();

        // Clear the screen before drawing the textures.
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        batch.begin();

        // set background, title, and buttons <- note: background and title is temporary solution. need to set actual data
        batch.draw(this.background, 0,0,WORLD_WIDTH,WORLD_HEIGHT);
        batch.draw(this.title, screenWidth/ 2 - 600, screenHeight / 2);
        this.setButtons();

        stage.draw();
        batch.end();
    }

    @Override
    public void dispose(){
        this.background.dispose();

    }

    @Override
    public void resize(int width, int height){

    }

    @Override
    public void pause(){

    }

    @Override
    public void resume(){

    }
    @Override
    public void show(){
        create();
    }
    @Override
    public void hide(){

    }

    public void toGameScreen(){
        // change the screen to the GameScreen, then set the buttons as disabled on this screen
        this.isButtonDisabled = true;
        //game.setScreen();

    }
    public void setButtons(){
        // set buttons, text, and title on the screen.
        // if the user is on other screen, disable every buttons events.

        // create and set button for starting new Game
        final TextButton newGameButton = new TextButton("Start new game", skin, "default");
        newGameButton.setWidth(600f);
        newGameButton.setHeight(400f);
        newGameButton.setPosition(screenWidth/ 3 - 300f, screenHeight / 3 - 300f);
        stage.addActor(newGameButton);

        // create and set button for exit the game
        final TextButton exitGameButton = new TextButton("Exit", skin, "default");
        exitGameButton.setWidth(600f);
        exitGameButton.setHeight(400f);
        exitGameButton.setPosition(screenWidth/ 3 + 600f, screenHeight / 3 - 300f);
        stage.addActor(exitGameButton);

        //

        // activate buttons to be able to input (click)
        Gdx.input.setInputProcessor(stage);

        // Set actions for the event that moving to other screen when user click the button.
        if (!this.isButtonDisabled){
            newGameButton.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y){
                    // moving to game screen
                    //toGameScreen();
                    Gdx.app.log("MenuScreen: ", "newGameButton has clicked");
                }
            });
            exitGameButton.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y){
                    // finish the application.
                    //System.exit(-1);
                    Gdx.app.log("MenuScreen: ", "exitGameButton has clicked");
                }
            });
        }
    }
}
