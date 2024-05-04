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
import com.badlogic.gdx.graphics.g2d.BitmapFont;

public class MenuScreen implements Screen {
    Assignment2 game;
    private SpriteBatch batch;
    private Skin skin;
    private Stage stage;
    private Texture title; // if we create png file for title
    private Texture background; // for background
    private boolean isButtonDisabled; // to disable buttons when user playing other screen

    public MenuScreen(Assignment2 game, boolean isButtonDisabled){
        this.game = game;
        this.isButtonDisabled = isButtonDisabled;
    }

    public void create(){
        this.batch = new SpriteBatch();
        this.stage = new Stage();
        this.skin = new Skin(Gdx.files.internal("gui/uiskin.json")); // need path after we found the skin
        // set the background and title
        this.background = new Texture(""); // need to set the file
        this.title = new Texture(""); // need to set the file
        this.isButtonDisabled = false;
    }

    public void update(){
        float dt = Gdx.graphics.getDeltaTime();
    }

    public void render(float f){
        this.update();
        Gdx.gl.glClearColor(1,1,1,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        this.batch.begin();

        //this.batch.draw();
        //stage.draw();
        this.batch.end();
    }

    @Override
    public void dispose(){

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

    }
    @Override
    public void hide(){

    }

    public void toGameScreen(){
        // change the screen to the GameScreen

    }
    public void setButtons(boolean isButtonDisabled){
        // set buttons, text, and title on the screen

    }
}
