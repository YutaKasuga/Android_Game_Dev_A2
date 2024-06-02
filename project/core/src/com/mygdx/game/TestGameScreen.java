package com.mygdx.game;
/*This class is mainly for testing Box2D for enemy or player class. Not For actual Game screen*/

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import java.util.ArrayList;

public class TestGameScreen implements Screen{
    Assignment2 game;
    Texture background;
    Player player;
    OrthographicCamera camera;
    Viewport viewport;
    ShapeRenderer shapeRenderer;
    SpriteBatch batch;
    private Skin skin;
    private Stage stage;
    private boolean isPaused;
    private boolean isJumpPressed;
    private boolean isMoveRightPressed;
    private boolean isMoveLeftPressed;
    private boolean isButtonDisabled;
    private float frame;
    // for setting screen size the device
    private final float screenWidth = Gdx.graphics.getWidth();
    private final float screenHeight = Gdx.graphics.getHeight();
    private final float screenRatio = screenWidth / screenHeight;

    // For setting background texture size larger than actual screen size to adjust devices
    private static final float WORLD_WIDTH = (float)(Gdx.graphics.getWidth() * 1.05);;
    private static final float WORLD_HEIGHT = (float)(Gdx.graphics.getHeight() * 1.05);;

    //Control Button class ----------------------- Hoi
    private final ControlButton playerButton;
    private final AttackButton attackButton;

    //Attack Command class ----------------------- Hoi
    private final AttackCommand attackCommand;

    /*Section for Box2D (Gravity handling)*/
    /* commented out due to having bug
    private World world;
    private Box2DDebugRenderer debugRenderer;
    public static final float BOX_STEP = 1 / 60f; // 60fps
    public static final int BOX_VELOCITY_ITERATIONS = 6;
    public static final int BOX_POSITION_ITERATIONS = 2;
    public static final float GRAVITY = -10.0f;
    */

    public TestGameScreen(Assignment2 game, boolean isButtonDisabled) {
        this.game = game;
        this.isButtonDisabled = isButtonDisabled;


        //Control Button class ----------------------- Hoi
        this.playerButton = new ControlButton();
        this.attackButton = new AttackButton();

        //Attack command class ----------------------- Hoi
        String[] commandArr = {"up","right","down","down","down","up"};
        this.attackCommand = new AttackCommand(commandArr);
        this.attackCommand.displayLists();

    }

    public void create(){
        //Gdx.app.log("GameScreen: ","game screen has created");

        /* create world which accept gravity from box2D*/
        /* commented out due to having bug with box2D
        this.world = new World(new Vector2(0 ,GRAVITY),true);
        debugRenderer = new Box2DDebugRenderer();

        // create ground as a static box
        PolygonShape groundShape = new PolygonShape();
        groundShape.setAsBox(screenWidth, 1.0f);

        BodyDef groundDef = new BodyDef();
        groundDef.position.set(new Vector2(0,10));
        Body ground = world.createBody(groundDef);
        ground.createFixture(groundShape,0.0f);

         */

        this.batch = new SpriteBatch();
        this.shapeRenderer = new ShapeRenderer();

        skin = new Skin(Gdx.files.internal("gui/uiskin.json"));
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        // handling images for this screen
        this.background = new Texture("Social/test_2.png");

        // setting camera for the screen
        this.camera = new OrthographicCamera();
        this.camera.setToOrtho(false, background.getHeight() * screenRatio, background.getHeight());
        this.viewport = new FitViewport(screenWidth * screenRatio, screenHeight * screenRatio);

        //setButton(this.isButtonDisabled);
        //this.player = new Player(game, this.world); // with box2D version. (having bug)
        this.player = new Player(game);
        this.isPaused = false;
        this.isJumpPressed = false;
        this.isMoveRightPressed = false;
        this.isMoveLeftPressed = false;
        this.frame = 0;
        //Gdx.app.log("Player: ","X = "+ this.player.getPosition().x+ "Y = "+this.player.getPosition().y);
        //Gdx.app.log("BoundingBox: ","X = "+ this.player.getBoundingBox().x+ "Y = "+this.player.getBoundingBox().y);

    }

    public void render(float f){
        //this.world.step(BOX_STEP, BOX_VELOCITY_ITERATIONS, BOX_POSITION_ITERATIONS);
        ScreenUtils.clear(0,0,0,1);

        this.batch.begin();

        this.playerButton.render(this.batch, this.stage);
        this.attackButton.render(this.batch, this.stage);

        /* draw the background */
        batch.draw(this.background, 0,0,WORLD_WIDTH,WORLD_HEIGHT);

        this.update();
        this.player.update();
        this.input();
        this.player.render(this.batch);


        stage.draw();
        this.batch.end();
        // display area of bounding box for player where has hit
        /*
        this.shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        this.shapeRenderer.setColor(Color.BLUE);

        if (this.player.getBoundingBox() != null){
            this.shapeRenderer.rect(this.player.getBoundingBox().x+25, this.player.getBoundingBox().y,
                    this.player.getBoundingBox().width, this.player.getBoundingBox().height);
        }
        */
        this.shapeRenderer.end();
    }
    public void update(){
        float dt = Gdx.graphics.getDeltaTime();
        this.frame += 20 * dt;
        if (this.frame > 60){
            this.frame = 0;
        }
        this.camera.update();
    }

    public void setButton(boolean isButtonDisabled){
        // set the buttons' text, size, and position then set color's which don't show in the game screen (Fly and Shoot button)
        final TextButton movingRightButton = new TextButton("Move right", skin, "default");
        final TextButton movingLeftButton = new TextButton("Move left", skin, "default"); // need to consider what I will implement later(like a ranking, setting enemy type or etc...)
        final TextButton jumpButton = new TextButton("Jump", skin, "default");
        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();






        movingRightButton.setWidth(350f);
        movingRightButton.setHeight(200f);
        //movingRightButton.setColor(1,1,1,0f); // change the button color to be transparent
        movingRightButton.setPosition(0, screenHeight -300);

        movingLeftButton.setWidth(350f);
        movingLeftButton.setHeight(200f);
        //movingLeftButton.setColor(1,1,1,0f);// change the button color to be transparent
        movingLeftButton.setPosition(screenWidth / 2 -200, screenHeight -300);

        jumpButton.setWidth(350f);
        jumpButton.setHeight(200f);
        jumpButton.setPosition(screenWidth -450, screenHeight -300);

        stage.addActor(movingRightButton);
        stage.addActor(movingLeftButton);
        stage.addActor(jumpButton);

        Gdx.input.setInputProcessor(stage);

        if (!isButtonDisabled){
            /* Only when Game Screen is launched, activate below codes.
             set the event when user with press the button */

            // currently moving right
            movingRightButton.addListener(new InputListener(){
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    isMoveRightPressed = true;
                    Gdx.app.log("move right button: ","is touch down");
                    return true;
                }
                @Override
                public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                    Gdx.app.log("move right button: ","is touch up");
                    isMoveRightPressed = false;
                }

            });
            // currently moving left
            movingLeftButton.addListener(new ClickListener(){
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    isMoveLeftPressed = true;
                    Gdx.app.log("move left button: ","is touch down");
                    return true;
                }
                @Override
                public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                    Gdx.app.log("move left button: ","is touch up");
                    isMoveLeftPressed = false;
                }

            });
            // currently handling jump or not
            jumpButton.addListener(new ClickListener(){
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    isJumpPressed = true;
                    Gdx.app.log("jump button: ","is touch down");
                    return true;
                }
                @Override
                public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                    Gdx.app.log("jump button: ","is touch up");
                    isJumpPressed = false;
                }
            });


        }
    }

    public void attackInput()
    {
        //if(this.attackButton.downAttack)
    }

    public void input(){
        // update the condition of each button in render, when Player is not dead
        if (this.player.getPlayerStatus() != Player.State.DEATH) {

            if (this.playerButton.rightPressed) {  // for right button
                // moving right
                this.player.setPlayerStatus(Player.State.RUNNING);
                this.player.setXDirection(true);

            } else if(this.playerButton.leftPressed) { // for left button
                // moving left
                this.player.setPlayerStatus(Player.State.RUNNING);
                this.player.setXDirection(false);

            } else if (this.isJumpPressed){  // for up button
                // moving up
                this.player.setPlayerStatus(Player.State.JUMPING);

            } else if (!this.isJumpPressed && this.player.getPosition().y > 150) {

                this.player.setPlayerStatus(Player.State.FALLING);
            }else {
                this.player.setPlayerStatus(Player.State.IDLE);
            }
        }

        if(this.attackButton.upAttack)
        {
            this.attackButton.upAttack = false;
            this.attackCommand.enterCommand("up");
            this.attackCommand.displayLists();
        }
        else if(this.attackButton.downAttack)
        {
            this.attackButton.downAttack = false;
            this.attackCommand.enterCommand("down");
            this.attackCommand.displayLists();
        }
        else if(this.attackButton.leftAttack)
        {
            this.attackButton.leftAttack = false;
            this.attackCommand.enterCommand("left");
            this.attackCommand.displayLists();
        }
        else if(this.attackButton.rightAttack)
        {
            this.attackButton.rightAttack = false;
            this.attackCommand.enterCommand("right");
            this.attackCommand.displayLists();
        }

            /*
            if (this.isPaused) {
                setPause();
            }
            */
    }

    @Override
    public void dispose(){
        batch.dispose();
        this.player.dispose();
        this.background.dispose();

    }
    @Override
    public void resize(int width, int height){

    }
    @Override
    public void pause(){

    }
    @Override
    public void resume(){

    }
    @Override
    public void show(){
        create();
    }

    @Override
    public void hide(){

    }

}
