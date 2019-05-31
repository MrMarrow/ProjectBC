package com.bloxxcity.game.model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Polygon;

public abstract class GameObject {

    protected Polygon bounds;
    protected Sprite object;
    public GameObject(Texture texture, float x, float y, float width, float height){
        bounds = new Polygon(new float[]{0f, 0f, width, 0f, 0f, height, width, height});
        object = new Sprite(texture);
        object.setSize(width, height);

    }

    public void draw (SpriteBatch batch){

        object.draw(batch);
    }

    public Polygon getBounds() {
        return bounds;
    }
}
