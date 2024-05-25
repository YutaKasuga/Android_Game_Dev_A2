package com.mygdx.game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Enemy {
    Vector2 position;
    Texture texture;

    public Enemy(float x, float y, String texturePath) {
        this.position = new Vector2(x, y);
        this.texture = new Texture(texturePath);
    }

    public void render(SpriteBatch batch) {
        batch.begin();
        batch.draw(texture, position.x, position.y);
        batch.end();
    }

    public void dispose() {
        texture.dispose();
    }
}
