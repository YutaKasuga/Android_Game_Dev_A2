package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;

public class gameInputProcessor implements InputProcessor {
    @Override
    public boolean keyDown(int keycode) {
        Gdx.app.log("gameInputProcessor: ","keyDown");
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        Gdx.app.log("gameInputProcessor: ","keyUp");
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        Gdx.app.log("gameInputProcessor: ","keyTyped");
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        Gdx.app.log("gameInputProcessor: ","touchDown");
        Gdx.app.log("gameInputProcessor: ", String.valueOf(button));
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        Gdx.app.log("gameInputProcessor: ","touchUp");
        return false;
    }

    @Override
    public boolean touchCancelled(int screenX, int screenY, int pointer, int button) {
        Gdx.app.log("gameInputProcessor: ","touchCancelled");
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        //Gdx.app.log("gameInputProcessor: ","touchDragged");
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        Gdx.app.log("gameInputProcessor: ","mouseMoved");
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        Gdx.app.log("gameInputProcessor: ","scrolled");
        return false;
    }
}
