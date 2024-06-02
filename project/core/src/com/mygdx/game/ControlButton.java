package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;

public class ControlButton implements Disposable
{
    private Table table;
    private Texture button;
    public boolean leftPressed, rightPressed;
    private Vector2 position;



    public ControlButton()
    {
       //this.button = new Texture("arrow.png");

        Image left = new Image(new Texture("LeftArrow.png"));
        Image right = new Image(new Texture("RightArrow.png"));

        this.table = new Table();


        right.setSize(100, 100);
        right.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                rightPressed = true;
                Gdx.app.log("Controller: ","right clicked");
                return true;
            }
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button){
                rightPressed = false;
            }
        });


        left.setSize(100, 100);
        left.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                leftPressed = true;
                Gdx.app.log("Controller: ","left clicked");
                return true;
            }
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button){
                leftPressed = false;
            }
        });

        table.left().bottom();
        table.add(left).size(left.getWidth(), left.getHeight());//set size of each element, here is taking the size from the button
        table.add();
        table.add(right).size(right.getWidth(), right.getHeight());//set size of each element, here is taking the size from the button
    }

    public void render(SpriteBatch batch, Stage stage){
        //batch.begin();
        stage.addActor(table);
        //batch.end();
    }



    @Override
    public void dispose() {

    }
}
