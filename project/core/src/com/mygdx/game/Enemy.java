package com.mygdx.game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

enum EnemyState {
    ALIVE,
    DEAD
}

public class Enemy {
    private final Vector2 position;
    private final Texture texture;
    private final float speed;
    private final EnemyState state;
    private Texture spritesheet;
    private Animation<TextureRegion> walkAnimation;
    private float stateTime;

    public Enemy(float x, float y, String texturePath) {
        this.position = new Vector2(x, y);
        this.texture = new Texture(texturePath);
        this.speed = Config.ENEMY_INITIAL_MOVEMENT_SPEED;
        this.state = EnemyState.ALIVE;
        this.spritesheet = new Texture(Gdx.files.internal("/assets/Enemy/Orc/Run/Run-Sheet.png"));


        int frameCols = 6;
        int frameRows = 1;
        TextureRegion[][] tmp = TextureRegion.split(spritesheet,
                spritesheet.getWidth() / frameCols,
                spritesheet.getHeight() / frameRows);

        TextureRegion[] walkFrames = new TextureRegion[frameCols * frameRows];
        int index = 0;
        for (int i = 0; i < frameRows; i++) {
            for (int j = 0; j < frameCols; j++) {
                walkFrames[index++] = tmp[i][j];
            }
        }

        this.walkAnimation = new Animation<>(0.1f, walkFrames);
        this.stateTime = 0f;
    }

    public void render(SpriteBatch batch) {
        TextureRegion currentFrame = walkAnimation.getKeyFrame(stateTime, true);
        batch.begin();
        batch.draw(texture, position.x, position.y);
        batch.end();
    }

    public void dispose() {
        texture.dispose();
        spritesheet.dispose();
    }

    public void update(float deltaTime) {
        if (state == EnemyState.ALIVE) {
            position.x += speed;
        }
        position.x += speed * deltaTime;
    }

    public boolean checkCollision(Vector2 playerPosition, float playerSize) {
        float distance = position.dst(playerPosition);
        return distance < playerSize + (float) texture.getWidth() / 2;
    }
}
