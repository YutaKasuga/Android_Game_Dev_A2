package com.mygdx.game;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.Game;

import java.util.ArrayList;
import java.util.Random;

public class Assignment2 extends Game implements ApplicationListener {
	SpriteBatch batch;
	public static MenuScreen menuScreen;
	static Random random = new Random();
	Enemy enemy;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		menuScreen = new MenuScreen(this, false);

		// set the screen to menuScreen
		setScreen(menuScreen);
		// Create TEST enemy
		enemy = new Enemy(100, 100, "enemy.png");
	}

	@Override
	public void render () {
		float deltaTime = Gdx.graphics.getDeltaTime();
		super.render();
		enemy.update(deltaTime);
		enemy.render(batch);
	}
	
	@Override
	public void dispose () {
		super.dispose();
		enemy.dispose();
		batch.dispose();
	}

	@Override
	public void resize(int width, int height){
		super.resize(width, height);
	}

	@Override
	public void pause(){
		super.pause();
	}

	@Override
	public void resume(){
		super.resume();
	}
}
