package com.mygdx.game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

enum EnemyState {
    ALIVE,
    DEAD
}

public class Enemy {
    Vector2 position;
    Texture texture;
    float speed;

    public Enemy(float x, float y, String texturePath) {
        this.position = new Vector2(x, y);
        this.texture = new Texture(texturePath);
        this.speed = Config.ENEMY_INITIAL_MOVEMENT_SPEED;
    }

    public void render(SpriteBatch batch) {
        batch.begin();
        batch.draw(texture, position.x, position.y);
        batch.end();
    }

    public void dispose() {
        texture.dispose();
    }

    public void update() {
        position.x += speed;
    }

    public boolean checkCollision(Vector2 playerPosition, float playerSize) {
        float distance = position.dst(playerPosition);
        return distance < playerSize + (float) texture.getWidth() / 2;
    }
}
