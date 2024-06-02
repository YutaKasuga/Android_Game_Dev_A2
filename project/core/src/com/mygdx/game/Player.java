package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

import java.util.ArrayList;

public class Player implements CollidableObject{
    /*
     Class for the player.
     */

    Assignment2 game;

    /* Section for textures */
    Texture idleSheet;
    Texture dyingSheet;
    Texture runningSheet;
    Texture HPTexture; // if we need to set it as texture
    Texture righthandTexture;
    Texture lefthandTexture;
    Texture boneShieldTexture;
    Texture woodShieldTexture;
    Texture boneSwordTexture;
    Texture woodSwordTexture;
    Texture boneBowTexture;
    Texture woodBowTexture;
    Texture boneArrowTexture;
    Texture woodArrowTexture;
    Texture boneArrowShotTexture;
    Texture woodArrowShotTexture;


    /* section for animation */
    private TextureRegion[] runningFrames;
    private TextureRegion[] idleFrames;
    private TextureRegion[] dyingFrames;
    private TextureRegion[] swordFrames; // only for sword animation.

    private Animation idleAnimation;
    private Animation runningAnimation;
    private Animation dyingAnimation;
    private TextureRegion currentFrame;
    private float stateTime = 0.0f;
    private float frameDuration = 0.2f; // to customize animation speed
    private static final int IDLE_FRAME_COLS = 4;
    private static final int IDLE_FRAME_ROWS = 1;
    private static final int RUNNING_FRAME_COLS = 6;
    private static final int RUNNING_FRAME_ROWS = 1;
    private float frame; // for animation
    private static final float  HAND_OFFSET_X = 22;
    private static final float  HAND_OFFSET_Y = 6;

    /* section for player's parameter and items */
    private int maxHP; // max HP for the player
    private int currentHP; // HP when player in the field
    private float ySpeed;    // Player's moving speed for y axis
    private float attackPower; // Player's attack power;
    private float maxSpeed; // the limit speed for the player, when it use SpeedPotion
    private float xSpeed;   // player's moving speed for x axis
    private float xPos; // player's X position
    private float yPos; // player's Y position

    Weapon myWeapon; // Player's weapon I need to activate after implement Weapon class
    private ArrayList<Potion> potions; // To store several potion.will be change the design
    private ArrayList<String> materials; // To store materials
    private int money;
    public static enum State {IDLE,  DEATH, JUMPING, RUNNING, FALLING, ATTACKING} // Player's status variation
    private State playerStatus; // current player's state
    private Rectangle boundingBox; // Player's bounding box
    private boolean isKilled;

    private boolean xDirection; // true = moving right, false = moving left. (should be affect only status is running)
    private boolean yDirection;// true = moving up, false = moving down. (should be affect only status is jumping)

    /* section for the moving limitation */

    private float ground = 150; // for handling ground (temp solution. need to re-consider it after tilemap has created)
    private float ceiling = Gdx.graphics.getHeight() - 50; // for handling ceiling of the screen (temp solution. need to re-consider it after tilemap has created)

    /* Section for Box2D (handling gravity) */
    //private Body body;

    //public Player(Assignment2 game, World world){
    public Player(Assignment2 game){
        this.game = game;

        /* setting player's texture*/
        this.idleSheet = new Texture("Heroes/Knight/Idle/Idle-Sheet.png");
        this.runningSheet = new Texture("Heroes/Knight/Run/Run-Sheet.png");
        this.dyingSheet = new Texture("Heroes/Knight/Death/Death-Sheet.png");

        this.lefthandTexture = new Texture("Weapons/Hands/leftHand.png");
        this.righthandTexture = new Texture("Weapons/Hands/rightHand.png");

        /* setting weapons' texture for player*/
        //
        this.boneShieldTexture = new Texture("Weapons/Bone/BoneShield.png");
        this.woodShieldTexture = new Texture("Weapons/Wood/WoodShield.png");

        this.boneSwordTexture = new Texture("Weapons/Bone/BoneSword.png");
        this.woodSwordTexture = new Texture("Weapons/Wood/WoodSword.png");

        this.boneArrowTexture = new Texture("Weapons/Bone/BoneArrow1.png");
        this.boneBowTexture = new Texture("Weapons/Bone/BoneBow.png");
        this.boneArrowShotTexture = new Texture("Weapons/Bone/BoneArrow2.png");

        this.woodArrowTexture = new Texture("Weapons/Wood/WoodArrow1.png");
        this.woodBowTexture = new Texture("Weapons/Wood/WoodBow.png");
        this.woodArrowShotTexture = new Texture("Weapons/Wood/WoodArrow2.png");

        /* setting animation for State.IDLE */
        TextureRegion[][] tempIdle = TextureRegion.split(idleSheet, idleSheet.getWidth() / IDLE_FRAME_COLS, idleSheet.getHeight() / IDLE_FRAME_ROWS);
        idleFrames = new TextureRegion[IDLE_FRAME_COLS * IDLE_FRAME_ROWS];
        int indexIdle = 0;
        for (int i = 0; i < IDLE_FRAME_ROWS; i++) {
            for (int j = 0; j < IDLE_FRAME_COLS; j++) {
                idleFrames[indexIdle++] = tempIdle[i][j];
            }
        }
        idleAnimation = new Animation(frameDuration,idleFrames);

        /* setting animation for State.RUNNING*/
        TextureRegion[][] tempRunning = TextureRegion.split(runningSheet, runningSheet.getWidth() / RUNNING_FRAME_COLS, runningSheet.getHeight() / RUNNING_FRAME_ROWS);
        runningFrames = new TextureRegion[RUNNING_FRAME_COLS * RUNNING_FRAME_ROWS];
        int indexRunning = 0;
        for (int i = 0; i < RUNNING_FRAME_ROWS; i++) {
            for (int j = 0; j < RUNNING_FRAME_COLS; j++) {
                runningFrames[indexRunning++] = tempRunning[i][j];
            }
        }
        runningAnimation = new Animation(frameDuration,runningFrames);

        /* setting animation for State.DYING*/
        TextureRegion[][] tempDying = TextureRegion.split(dyingSheet, dyingSheet.getWidth() / RUNNING_FRAME_COLS, dyingSheet.getHeight() / RUNNING_FRAME_ROWS);
        dyingFrames = new TextureRegion[RUNNING_FRAME_COLS * RUNNING_FRAME_ROWS];
        int indexDying = 0;
        for (int i = 0; i < RUNNING_FRAME_ROWS; i++) {
            for (int j = 0; j < RUNNING_FRAME_COLS; j++) {
                dyingFrames[indexDying++] = tempDying[i][j];
            }
        }
        dyingAnimation = new Animation(frameDuration,dyingFrames);

        /* Set player's status as initialising*/
        myWeapon = new BoneBow(this.game); // currently set the initial weapon as wood bow
        this.potions = new ArrayList<Potion>(); // can stock potions as String
        this.materials  = new ArrayList<String>(); // can stock materials as String
        this.maxHP = 100; // temp value
        this.currentHP = maxHP;
        this.money = 0;
        this.xPos = 50;
        this.attackPower = myWeapon.getPower(); // the power will be depends on the current weapon.
        this.yPos = ground; // temp value
        this.maxSpeed = 30; // temp value
        this.xSpeed = 10; // temp value
        this.ySpeed = 10; // temp value
        this.isKilled = false;
        this.playerStatus = State.IDLE;
        this.boundingBox = new Rectangle( xPos, yPos, 19, 29);
        this.frame = 0;
        this.xDirection = false;
        this.yDirection = false;

        /* set box2D to the texture*/
        /* commented out due to having bug to handle gravity
        BodyDef playerDef = new BodyDef();
        playerDef.type = BodyType.DynamicBody;
        playerDef.position.set(this.xPos,this.yPos); // set the player to initial position
        this.body = world.createBody(playerDef);

        PolygonShape textureShape = new PolygonShape();
        textureShape.setAsBox(19.0f, 29.0f); // as bounding box size

        FixtureDef playerFixture = new FixtureDef();
        playerFixture.shape = textureShape; // allocate the shape as bounding box
        playerFixture.density = 50.0f; // Set a density to allocate gravity
        playerFixture.friction = 0.0f; // Ignore friction
        playerFixture.restitution = 0.0f; // ignore the bounce when player hit the ground
        body.createFixture(playerFixture);
        textureShape.dispose();
        */

    }

    public void render(SpriteBatch batch){
        stateTime += Gdx.graphics.getDeltaTime();

        switch (this.playerStatus){
            case IDLE:

                currentFrame = (TextureRegion)(idleAnimation.getKeyFrame(stateTime, true));
                batch.draw(currentFrame,this.xPos, this.yPos);
                break;

            case RUNNING:
            case JUMPING:
            case FALLING:
                // due to no assets for jump and fall status, set the texture as running.
                currentFrame = (TextureRegion)(runningAnimation.getKeyFrame(stateTime, true));
                batch.draw(currentFrame,this.xPos-19, this.yPos);

                break;
            case DEATH:
                currentFrame = (TextureRegion)(dyingAnimation.getKeyFrame(stateTime, true));
                batch.draw(currentFrame,this.xPos, this.yPos);
                break;

            // need to set asset
            case ATTACKING:
                break;
        }
        // offset for hand and weapon
        float leftHandX = this.xPos + HAND_OFFSET_X ;
        float rightHandX = this.xPos + HAND_OFFSET_X -19;
        float handY = this.yPos + HAND_OFFSET_Y;
        if (this.playerStatus != State.DEATH && this.playerStatus != State.ATTACKING){
            // draw hand and weapon assets
            if (this.myWeapon.getName().equals("WoodSword")){
                batch.draw(woodSwordTexture, rightHandX-1, handY-3);
                batch.draw(righthandTexture, rightHandX, handY);
                batch.draw(woodShieldTexture, leftHandX, handY-3);

            } else if (this.myWeapon.getName().equals("WoodBow")){
                batch.draw(woodBowTexture, leftHandX-2, handY-9);
                batch.draw(woodArrowTexture, rightHandX, handY);
                batch.draw(lefthandTexture, leftHandX, handY);
                batch.draw(righthandTexture, rightHandX, handY);

            } else if (this.myWeapon.getName().equals("BoneBow")){
                batch.draw(boneBowTexture, leftHandX-2, handY-9);
                batch.draw(boneArrowTexture, rightHandX, handY);
                batch.draw(lefthandTexture, leftHandX, handY);
                batch.draw(righthandTexture, rightHandX, handY);

            } else {
                // case for Bone sword
                batch.draw(boneSwordTexture, rightHandX-1, handY-3);
                batch.draw(righthandTexture, rightHandX, handY);
                batch.draw(boneShieldTexture, leftHandX, handY-3);
            }
        }
        //batch.draw(currentFrame, body.getPosition().x, body.getPosition().y); // for rendering box2D version
    }
    
    public void update(){
        // due to having bug for Vox2D, every lines for box2D handling is commented out

        float dt = Gdx.graphics.getDeltaTime();
        //Vector2 bodyPosition = body.getPosition();
        //float velocityX = 0;
        //Gdx.app.log("Player xPos(body): ", String.valueOf(this.body.getPosition().x));
        //Gdx.app.log("Player yPos(body): ", String.valueOf(this.body.getPosition().y));
        //Gdx.app.log("Player xPos(actual): ", String.valueOf(this.xPos));
        //Gdx.app.log("Player yPos(actual): ", String.valueOf(this.yPos));
        //Gdx.app.log("Player status: ", String.valueOf(this.playerStatus));


        // if player is trying to go under the ground, prevent it and set the yPos and ySpeed to 0. Then change the state to IDLE
        if (this.yPos < ground ){
            this.yPos = ground;
            //body.setLinearVelocity(body.getLinearVelocity().x,this.yPos);
            //body.setTransform(bodyPosition.x, ground + 10, body.getAngle());
            this.playerStatus = State.IDLE;
        }

        this.frame += 10 * dt;
        if (this.frame >= 8){
            this.frame = 0;
        }

        // update player's bounding box to current position
        this.boundingBox = new Rectangle(xPos, yPos, 19, 29);

        // update the player's position depends on the status
        switch (this.playerStatus){
            case IDLE:
                break;

            case FALLING:
                // case for player is falling down

                // if player is trying to go under the ground, prevent it and set the yPos and ySpeed to 0. Then change the state to IDLE
                if (this.yPos <= ground){
                    this.yPos = ground;
                    //body.setTransform(bodyPosition.x, ground + 10, body.getAngle());
                    this.playerStatus = State.IDLE;
                } else {
                    this.yPos -= ySpeed; // I think I should handle this one by box2D and need to consider item's effect later
                }
                break;
            case JUMPING:
                // if player is about to over the ceiling, prevent is and set the state as falling
                if (this.yPos > ceiling - 50){
                    this.yPos = ceiling - 50; // temp value
                } else {
                    //body.applyLinearImpulse(new Vector2(0, ySpeed), body.getWorldCenter(),true);
                    this.yPos += ySpeed; // I think I should handle this one by box2D and need to consider item's effect later
                }
                //body.setLinearVelocity(body.getLinearVelocity().x,this.yPos);
                break;

            case RUNNING:
                // case for player is moving right or left

                if (this.xDirection){

                    // prevent the player's xPos going to outside of screen
                    if (this.xPos >= Gdx.graphics.getWidth() - 100){ // temp value
                        this.xPos = Gdx.graphics.getWidth() - 100;
                        //body.setTransform(Gdx.graphics.getWidth() - 100,bodyPosition.y, body.getAngle());
                    } else {
                        // for moving right
                        this.xPos += xSpeed;
                    }
                    //velocityX = xSpeed;
                    //body.setLinearVelocity(xSpeed,body.getLinearVelocity().y);
                } else {
                    // prevent player is going out of the screen
                    if (this.xPos < 50){ //temp value
                        this.xPos = 50;
                        //body.setTransform(50,bodyPosition.y, body.getAngle());
                    } else {
                        // for moving left
                        this.xPos -= xSpeed;
                    }
                    //body.setLinearVelocity(-xSpeed,body.getLinearVelocity().y);
                    //velocityX = -xSpeed;
                }

                // case for the player is on the air, allocate gravity. (temp solution due to having bug with box2D)
                if (this.yPos > ground){
                    this.yPos -= ySpeed;
                }

                //body.setLinearVelocity(this.xPos,body.getLinearVelocity().y);
                break;
            case ATTACKING:
                // for attacking, need to wait until weapon has created. after attacking change the state depends on yPos

                if (this.yPos > ground){
                    // case for player is still in the air, change the state as falling
                    this.playerStatus = State.FALLING;
                } else {
                   // case for player is on the ground
                   this.playerStatus = State.IDLE;
                }

                break;
            case DEATH:
                this.isKilled = true;
                if (this.yPos > ground){
                    // in case of player died in the air, reduce yPos due to having gravity
                    this.yPos -= ySpeed;
                }
                //body.setLinearVelocity(body.getLinearVelocity().x,this.yPos);
                break;

        }

    }


    /*
    Set weapon when player get new one.
     */
    public void setWeapon(Weapon weapon){
        this.myWeapon = weapon;
    }

    /*
    Heal player's HP when player use potion
     */
    public void healHP(Potion potion){
        this.currentHP += potion.getHealingPoint();
        if (this.currentHP > this.maxHP){
            this.currentHP = this.maxHP;
        }
    }

    /*
    Increase the player speed when player use specific potion
    If the player's speed is same as max value, won't increase.
     */
    public void speedUp(Potion potion){
        this.xSpeed += potion.getSpeedPoint();
        this.ySpeed += potion.getSpeedPoint();
        if (this.xSpeed > this.maxSpeed){
            this.xSpeed = this.maxSpeed;
        }
        if (this.ySpeed > this.maxSpeed){
            this.ySpeed = this.maxSpeed;
        }
    }

    /*
    Calculate current HP when player get damage.
    If the HP will be 0, change the player status as DEATH.
     */
    public void damage(){
        this.currentHP -= 10; // temp value
        if (this.currentHP <= 0){
            this.playerStatus = State.DEATH;
            this.isKilled = true;
        }
    }

    /*
    return current position as Vector2d
     */
    public Vector2 getPosition(){
        return new Vector2(this.xPos,this.yPos);
    }

    /*

     */

    public void jump(){
        this.playerStatus = State.JUMPING;
        // if player is about to over the ceiling, prevent is and set the state as falling
        if (this.yPos > ceiling - 50){
            this.yPos = ceiling - 50; // temp value
        } else {
            //body.applyLinearImpulse(new Vector2(0, ySpeed), body.getWorldCenter(),true);
            this.yPos += ySpeed; // I think I should handle this one by box2D and need to consider item's effect later
        }
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
    public ArrayList<Potion> getPotions(){
        return this.potions;
    }

    /*
    Set new potion in the list
     */
    public void setPotion(Potion potion){
        this.potions.add(potion);
    }

    /*
        Use potion from the potion's list
        when it used, the potion will be removed from the list,
        then player's status will be updated.

        Parameter:
            -> potionName -> HighPotion, SpeedPotion or Elixir as a string
     */
    public void usePotion(String potionName){
        boolean isUsed = false;
        for (Potion p : this.potions){
            if (potionName.equals(p.getName()) && !isUsed){
                potions.remove(p);
                healHP(p);
                speedUp(p);
                isUsed = true;
            }
        }
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
    Use material for crafting new Item.(potion or weapon)
    when player craft the new item, materials will be removed from the this.materials.
    Then if the item is potion, it will be added to the this.potions,
    or if the item is Weapon, change the current weapon to new weapon.

    parameter:
        -> material1, material2 -> material name as string. the requirement items are in Craft.java
        -> itemType -> item name to craft. (potion or weapon as string)
    */
    public void craftItem(String material1, String material2, String itemType){
        boolean isCrafted = false;
        for (String i : this.materials){
            for (String j: this.materials){
                if (material1.equals(i) && material2.equals(j) && itemType.equals("potion") && !isCrafted){
                    // section for making new potion
                    materials.remove(i);
                    materials.remove(j);
                    Potion newPotion = Craft.createPotion(i, j);
                    setPotion(newPotion);
                    isCrafted = true;
                } else if (material1.equals(i) && material2.equals(j) && itemType.equals("weapon") && !isCrafted){
                    // section for making new Weapon
                    materials.remove(i);
                    materials.remove(j);
                    this.myWeapon = Craft.createWeapon(i, j);
                    this.attackPower = this.myWeapon.getPower();
                    isCrafted = true;
                }
            }

        }

    }

    /*
    test function
    return direction
     */
    public boolean getXDirection(){
        return this.xDirection;
    }

    public void setXDirection(boolean bool){
        this.xDirection = bool;
    }

    public boolean getYDirection(){
        return this.yDirection;
    }

    public void setYDirection(boolean bool){
        this.yDirection = bool;
    }


    public void dispose(){
        this.lefthandTexture.dispose();
        this.righthandTexture.dispose();
        this.boneShieldTexture.dispose();
        this.woodShieldTexture.dispose();
        this.boneSwordTexture.dispose();
        this.woodSwordTexture.dispose();
        this.boneArrowTexture.dispose();
        this.boneBowTexture.dispose();
        this.boneArrowShotTexture.dispose();
        this.woodArrowTexture.dispose();
        this.woodBowTexture.dispose();
        this.woodArrowShotTexture.dispose();
        this.idleSheet.dispose();
        this.runningSheet.dispose();
        this.dyingSheet.dispose();
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
