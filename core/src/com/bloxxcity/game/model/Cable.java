package com.bloxxcity.game.model;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class Cable {
    private Body box;
    private Fixture cranePhysicsFixture;
    private float height = 10f;
    private float width = 0.1f;
    public Cable (World world) {
        BodyDef defD = new BodyDef();
        defD.type = BodyDef.BodyType.DynamicBody;
        box = world.createBody(defD);
        PolygonShape shape = new PolygonShape();
        Vector2 vector2 = new Vector2(0f, 0f);

        shape.setAsBox(width, height);
        box.setUserData("cable");
        cranePhysicsFixture = box.createFixture(shape,1);
        shape.dispose();
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public void setBody(Body box) {
        this.box = box;
    }

    public Vector2 getPosition(){ return box.getPosition(); }

    public Body getBody(){
        return box;
    }
}
