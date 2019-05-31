package com.bloxxcity.game.model;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class Crane{
    private Body box;
    private Fixture cranePhysicsFixture;
    private float height = 2;
    private float width = 20;


    /**
     * creates crane
     * @param world - the world where creates crane
     */


    Crane(World world) {
        BodyDef defS = new BodyDef();
        defS.type = BodyDef.BodyType.StaticBody;
        box = world.createBody(defS);
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width, height);
        box.setUserData("crane");
        cranePhysicsFixture = box.createFixture(shape,0);
        shape.dispose();
    }



    public float getHeight() {
        return height;
    }

    public float getWidth() {
        return width;
    }

    public void setBody(Body box) {
        this.box = box;
    }

    public Vector2 getPosition(){ return box.getPosition(); }

    public Body getBody(){
        return box;
    }

    public Fixture getCranePhysicsFixture() {
        return cranePhysicsFixture;
    }
}
