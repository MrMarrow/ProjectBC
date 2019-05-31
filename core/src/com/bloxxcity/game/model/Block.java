package com.bloxxcity.game.model;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.MassData;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

import static com.bloxxcity.game.ConstantValue.BLOCK_SIZE;


public class Block {
    private Body boxBlock;
    private Fixture blockPhysicsFixture;
    private float size = BLOCK_SIZE;

    public Block(World world, int N, int maxBlock) {
        BodyDef defD = new BodyDef();
        defD.type = BodyDef.BodyType.DynamicBody;
        boxBlock = world.createBody(defD);
        PolygonShape shape = new PolygonShape();
        if (N == maxBlock - 1) {
            shape.set(new Vector2[]{new Vector2(-6, -5), new Vector2(0, 5), new Vector2(6, -5)});
            boxBlock.setUserData("roof");
        }else {
            shape.setAsBox(size, size);
            boxBlock.setUserData("block");
        }
        MassData massData = new MassData();
        massData.mass = 1f;
        massData.I = 1;


        blockPhysicsFixture = boxBlock.createFixture(shape,2);
        blockPhysicsFixture.getBody().setMassData(massData);
        blockPhysicsFixture.setFriction(0.3f);
        blockPhysicsFixture.setDensity(1000);
        shape.dispose();
    }

    public float getSize() {
        return size;
    }

    public Vector2 getPosition(){ return boxBlock.getPosition(); }

    public Body getBody() {
        return boxBlock;
    }

    public void setBody(Body box) {
        this.boxBlock = box;
    }

    public Fixture getBlockPhysicsFixture() {
        return blockPhysicsFixture;
    }
}
