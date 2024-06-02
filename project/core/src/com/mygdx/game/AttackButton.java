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

public class AttackButton
{
    private Table table;
    private Texture button;
    public boolean upAttack, downAttack, leftAttack, rightAttack;
    private Vector2 position;

    private void clickButton(Image button){
        InputEvent inputEvent = new InputEvent();
        inputEvent.setRelatedActor(button);

        inputEvent.setType(InputEvent.Type.touchDown);
        button.fire(inputEvent);

        inputEvent.setType(InputEvent.Type.touchUp);
        button.fire(inputEvent);
    }

    public AttackButton()
    {
        Image up = new Image(new Texture("AttackUpArrow.png"));
        Image down = new Image(new Texture("AttackDownArrow.png"));
        Image left = new Image(new Texture("AttackLeftArrow.png"));
        Image right = new Image(new Texture("AttackRightArrow.png"));

        this.table = new Table();

        up.setSize(100, 100);
        up.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                upAttack = true;
                Gdx.app.log("Controller: ","up attack clicked");
                //upAttack = false;
                return true;
            }
//            @Override
//            public void touchUp(InputEvent event, float x, float y, int pointer, int button){
//                upAttack = false;
//            }
        });

        down.setSize(100, 100);
        down.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                downAttack = true;
                Gdx.app.log("Controller: ","down attack clicked");
                return true;
            }
        });

        left.setSize(100, 100);
        left.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                leftAttack = true;
                Gdx.app.log("Controller: ","left attack clicked");
                return true;
            }
        });

        right.setSize(100, 100);
        right.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                rightAttack = true;
                Gdx.app.log("Controller: ","rightAttack attack clicked");
                return true;
            }
        });





        this.table.bottom().right();
        this.table.setFillParent(true);

        this.table.add();
        this.table.add(up).size(up.getWidth(), up.getHeight());
        this.table.add();

        this.table.row().pad(5, 5, 5, 5);
        this.table.add(left).size(left.getWidth(), left.getHeight());
        this.table.add(down).size(down.getWidth(), down.getHeight());
        this.table.add(right).size(right.getWidth(), right.getHeight());
    }

    public void render(SpriteBatch batch, Stage stage){
        //batch.begin();
        stage.addActor(this.table);
        //batch.end();
    }




}
