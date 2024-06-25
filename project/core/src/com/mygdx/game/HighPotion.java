package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;

public class HighPotion implements Potion{
    /*
    The potion for healing HP .
    heal player's HP.
    But no effect for increasing speed for player
     */
    Assignment2 game;
    private final int HEALING = 10; // temp value
    private final int SPEED_UP = 0;
    private final String NAME = "HighPotion";
    Texture img;

    public HighPotion(Assignment2 game){
        this.game = game;
        this.img =new Texture("level_design_tools_and_tile_set/png/HighPotion.png");
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
    public String getName(){
        // return NAME
        return this.NAME;
    }
}
