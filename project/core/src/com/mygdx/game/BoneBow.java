package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;

public class BoneBow implements Weapon{

    /*
        Class for the next level of the Bow.
        Power and speed are increased from WoodBow.

        The power is lower than upper level of Sword class.
        but, able to attack enemy which are far. (like a gun in assignment 1)

        parameter:
            power -> damage for the enemy
            speed -> speed for the allow.

     */
    Assignment2 game;

    /* Section for the weapon parameter */
    private final int POWER = 20;
    private final int SPEED = 20;
    private final String NAME = "BoneBow";

    public BoneBow(Assignment2 game){
        this.game = game;
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
        return this.NAME;
    }
}
