package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Elixir implements Potion{
    /*
    The strongest potion in the game.
    heal player's HP and increase the speed as well.
     */
    private final int HEALING = 30; // temp value
    private final int SPEED_UP = 10; // temp value
    private final String NAME = "Elixir";
    Texture img;

    public Elixir(Assignment2 game){

        this.img = new Texture("level_design_tools_and_tile_set/png/Elixir.png");
    }

    @Override
    public int getHealingPoint(){
        // return the int to heal player's HP
        return this.HEALING;
    }

    @Override
    public int getSpeedPoint(){
        // return the int to increase Player's speed
        return this.SPEED_UP;
    }

    @Override
    public  String getName(){
        return this.NAME;
    }
}
