package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;

public class WoodBow implements Weapon{

    /*
        Class for the lowest level of the Bow.
        The power is lower than sword class.
        but, able to attack enemy which are far. (like a gun in assignment 1)

        parameter:
            power -> damage for the enemy
            speed -> speed for the allow.
     */
    Assignment2 game;

    /* Section for the weapon parameter */
    private final int POWER = 10; // temp value
    private final int SPEED = 10; // temp value
    private final String NAME = "WoodBow";

    /* Section for the weapon's texture */
    Texture imgBow;
    Texture imgArrow;

    public WoodBow(Assignment2 game){
        this.game = game;
        //this.imgBow = new Texture(""); // need to set the image in the Weapons/Wood/Wood.png
        //this.imgArrow = new Texture("");
    }

    @Override
    public int getPower(){
        // return the power of the weapon
        return this.POWER;
    }

    public int getSPEED(){
        // return the speed for the arrow
        return this.SPEED;
    }
    @Override
    public String getName(){
        // return the name
        return this.NAME;
    }

}
