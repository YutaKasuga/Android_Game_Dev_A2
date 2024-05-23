package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

import java.util.ArrayList;

public class Player implements CollidableObject{

    Assignment2 game;
    /* Section for textures */
    Texture[] idleTexture = new Texture[4]; // need to modify
    Texture[] dyingTexture = new Texture[6]; // need to modify
    Texture[] runningTexture = new Texture[6]; // need to modify
    Texture HPTexture; // need to set the texture

    /* section for player's parameter and items */
    private int maxHP; // max HP for the player
    private int currentHP; // HP when player in the field
    private float ySpeed;    // Player's moving speed for y axis
    private float attackPower; // Player's attack power;
    private float xSpeed;   // player's moving speed for x axis
    private float xPos; // player's X position
    private float yPos; // player's Y position

    //Weapon myWeapon // Player's weapon I need to activate after implement Weapon class
    private ArrayList<String> potions; // To store several potion.will be change the design
    private ArrayList<String> materials; // To store materials
    private int money;
    public static enum State {IDLE,  DEATH, JUMPING, RUNNING, FALLING, ATTACKING} // Player's status variation
    private State playerStatus; // current player's state
    private Rectangle boundingBox; // Player's bounding box
    private boolean isKilled;

    private boolean direction; // true = moving right, false = moving left. (should be affect only status is running)

    /* section for the playing */
    private float frame; // for animation
    private float ground = 100; // for handling ground (temp solution. need to re-consider it after tilemap has created)
    private float ceiling = Gdx.graphics.getHeight() -50; // for handling ceiling of the screen (temp solution. need to re-consider it after tilemap has created)

    // for box2d's body
    private Body player;

    public Player(Assignment2 game){

        /*Need to find how to set the texture with single png file*/
        //this.idleTexture = new Texture("Heroes/Knight/Idle/Idle-Sheet.png");
        //this.runningTexture = new Texture("Heroes/Knight/Run/Run-Sheet.png");
        //this.dyingTexture = new Texture("Heroes/Knight/Death/Death-Sheet.png");

        this.game = game;
        //myWeapon = null;
        this.potions = new ArrayList<String>(); // can stock potions as String
        this.materials  = new ArrayList<String>(); // can stock materials as String
        this.maxHP = 100; // temp value
        this.currentHP = maxHP;
        this.money = 0;
        this.xPos = 0;
        this.attackPower = 0; // should be set by weapon
        this.yPos = 100; // need to set specific value
        this.xSpeed = 50; // temp value
        this.ySpeed = 10; // temp value
        this.isKilled = false;
        this.playerStatus = State.IDLE;
        this.boundingBox = new Rectangle( xPos + 30, yPos +30, 19, 29); // need to set x and y. width and height is from measured value in photo app.
        this.frame = 0;
        this.direction = true;

        // need to set box2D
        BodyDef playerDef = new BodyDef();
        playerDef.type = BodyType.DynamicBody;
        playerDef.position.set(this.xPos,this.yPos);
        // need to set shape?
        /*
         sample code from prac 10

        CircleShape ballShape = new CircleShape();
		ballShape.setRadius(5.0f);

		FixtureDef ballFixture = new FixtureDef();
		ballFixture.shape = ballShape;
		ballFixture.density = 1.0f; // Give the ball a density so it has mass
		ballFixture.friction = 0.0f; // Ignore friction
		ballFixture.restitution = 0.95f; // Allow the ball to bounce

		ball1 = world.createBody(ballDef);
		ball1.createFixture(ballFixture);
         */

    }

    public void render(SpriteBatch batch){
        switch (this.playerStatus){
            case IDLE:
                //batch.draw(this.idleTexture, this.xPos,this.yPos);
                break;
            case RUNNING:
                //batch.draw(this.runningTexture,this.xPos,this.yPos);
                break;
            case DEATH:
                //batch.draw(this.dyingTexture,this.xPos,this.yPos);
                break;
            // due to no assets for below status, need to consider how to draw the batch.
            case ATTACKING:
            case JUMPING:
            case FALLING:
                break;
        }
    }
    
    public void update(){

        float dt = Gdx.graphics.getDeltaTime();

        // if player is trying to go under the ground, prevent it and set the yPos and ySpeed to 0. Then change the state to IDLE
        if (yPos < ground){
            this.yPos = ground + 10;
            this.ySpeed = 0;
            this.playerStatus = State.IDLE;
        }

        this.frame += 10 * dt;
        if (this.frame >= 8){
            this.frame = 0;
        }

        // update player's boundingbox to current position
        this.boundingBox = new Rectangle(xPos + 30, yPos +30, 19, 29); // need to set the x and y's width and height

        // update the player's position depends on the status
        switch (this.playerStatus){
            case IDLE:

                break;

            case FALLING:
                // case for player is falling down
                this.yPos -= ySpeed; // I think I should handle this one by box2D and need to consider item's effect later
                // if player is trying to go under the ground, prevent it and set the yPos and ySpeed to 0. Then change the state to IDLE
                if (yPos <= ground){
                    this.yPos = ground + 10;
                    this.playerStatus = State.IDLE;
                }
                break;
            case JUMPING:
                // case for player is jumping
                this.yPos += ySpeed; // I think I should handle this one by box2D and need to consider item's effect later

                // if player is about to over the ceiling, prevent is and set the state as falling
                if (yPos >= ceiling){
                    this.yPos = ceiling - 50; // temp value
                    this.playerStatus = State.FALLING;
                }
                break;

            case RUNNING:
                // case for player is moving right or left
                // do I need to handle these section with joystick?

                if (this.direction){
                    // for moving right
                    this.xPos += xSpeed;
                    // prevent the player's xPos
                    if (this.xPos >= Gdx.graphics.getWidth() - 100){ // temp value
                        this.xPos = Gdx.graphics.getWidth() - 100;
                    }
                } else{
                    // for moving left
                    this.xPos -= xSpeed;
                    // prevent player is trying to go out of the screen
                    if (this.xPos <= 50){ //temp value
                        this.xPos = 50;
                    }
                }

                break;
            case ATTACKING:
                // for attacking, need to wait until weapon has created. after attacking change the state depends on yPos

                // case for player is still in the air
                if (this.yPos > ground){
                    this.playerStatus = State.FALLING;
                } else {
                   // case for player is on the ground
                   this.playerStatus = State.IDLE;
                }

                break;
            case DEATH:
                this.isKilled = true;
                // in case of player died in the air, reduce yPos due to having gravity
                if (this.yPos > ground){
                    this.yPos -= ySpeed;
                }

                break;

        }
    }


    /*
    Set weapon when player get new one.
     */
    /*
    public void setWeapon(Weapon weapon){
        this.myweapon = weapon;
    }
    */

    /*
    Heal player's HP when player use potion
     */
    public void healHP(){
        this.currentHP += 10;   // temp value.
        if (this.currentHP > this.maxHP){
            this.currentHP = this.maxHP;
        }

    }

    /*
    Increase the player speed when player use specific potion
     */
    public void speedUp(){
        this.ySpeed += 10;  // temp value.
        this.xSpeed += 10;  // temp value.
    }

    /*
    Calculate current HP when player get damage.
    If the HP will be 0, change the player status as DEATH.
     */
    public void damage(){
        this.currentHP -= 10;
        if (this.currentHP <= 0){
            this.playerStatus = State.DEATH;
            this.isKilled = true;
        }
    }

    /*
    return current position as Vector2d
     */
    public Vector2 getPosition(){
        return new Vector2(this.xPos, this.yPos);
    }

    /*
    get current player's status
     */
    public State getPlayerStatus(){
        return playerStatus;
    }

    /*
    Set new player status.
     */
    public void setPlayerStatus(State newState){
        this.playerStatus = newState;
    }

    /*
     Get potions list
     */
    public ArrayList<String> getPotions(){
        return this.potions;
    }

    /*
    Set new potion in the list
     */
    public void setPotion(String potionName){
        this.potions.add(potionName);
    }

    /*
    Use potion?
    Do we need?
     */
    public void usePotion(String potionName){
        for (String i : this.potions){
            if (potionName.equals(i)){
                potions.remove(i);
                // need to set condition to use potion

            }
        }
        // need to set error handling in case of there is no specific potion in the arraylist
    }

    /*
     Get material list
     */
    public ArrayList<String> getMaterials(){
        return this.materials;
    }

    /*
    set new material in the materials
     */
    public void setMaterials(String materialName){
        this.materials.add(materialName);
    }

    /*
    Use material for crafting?
    Do we need?
    */
    public void useMaterial(String materialName){
        for (String i : this.materials){
            if (materialName.equals(i)){
                materials.remove(i);
                // need to set condition to craft new item?

            }
        }
        // need to set error handling in case of there is no specific potion in the arraylist
    }

    public void dispose(){

    }


    @Override
    public Rectangle getBoundingBox() {
        return this.boundingBox;
    }

    @Override
    public void handleCollision(){
        this.damage();
    }
}
