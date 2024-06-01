package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;

public class BoneSword implements Weapon{
    /*
        Class for the upper level of the Sword.
        The power is higher than upper level's bow.
        but the only able to attack limited area (in front of player).

        parameter:
            POWER -> damage for the enemy
            ATTACK_AREA -> area for the attack. for setting the collision handling
     */
    Assignment2 game;

    /* Section for the weapon parameter */
    private final int POWER = 30; // temp value
    private final int ATTACK_AREA = 10; // temp value
    private final String NAME = "BoneSword";

    public BoneSword(Assignment2 game){
        this.game = game;
    }

    @Override
    public int getPower(){
        // return the power of the weapon
        return this.POWER;
    }

    public int getAttackArea(){
        // return the speed for the arrow
        return this.ATTACK_AREA;
    }
    @Override
    public String getName(){
        return this.NAME;
    }
}

