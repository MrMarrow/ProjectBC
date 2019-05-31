package com.bloxxcity.game.controller;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;
import com.badlogic.gdx.physics.box2d.joints.WeldJointDef;
import com.bloxxcity.game.model.House;

import static com.bloxxcity.game.ConstantValue.BLOCK_SIZE;
import static com.bloxxcity.game.ConstantValue.BUIlDING_SITE_SIZE;
import static java.lang.Math.abs;

public class MyContactListener implements ContactListener {
    private World world;
    private House house;

    public MyContactListener(World world, House house){
        super();
        this.world = world;
        this.house = house;
    }

    @Override
    public void beginContact(Contact contact) {
        Fixture currBlock;
        Fixture prevBlock;
        if (contact.getFixtureA().getFriction() == 0.3f){
            currBlock = contact.getFixtureA();
            prevBlock = contact.getFixtureB();
        } else {
            currBlock = contact.getFixtureB();
            prevBlock = contact.getFixtureA();
        }
        float size;

        if (prevBlock.getBody().getUserData().equals("buildingSite")) {
            size = BUIlDING_SITE_SIZE;
            prevBlock.setFriction(0);
        } else if (prevBlock.getBody().getUserData().equals("block")) {
            size = BLOCK_SIZE - 1;
        } else {
            return;
        }

        if (house.getBlocks().indexOf(currBlock, false) == -1) {
            if (abs(abs(currBlock.getBody().getAngle()) - abs(prevBlock.getBody().getAngle())) < 0.09 &&
                    abs(currBlock.getBody().getPosition().x - prevBlock.getBody().getPosition().x)
                            < size ) {
                currBlock.setFriction(0);
                currBlock.getBody().setLinearVelocity(0, 0);
                contact.setFriction(0);
                contact.setTangentSpeed(0);
                currBlock.getBody().setGravityScale(1);
                world.step(1f / 60f, 2, 3);
                WeldJointDef jointDef = new WeldJointDef();
                Vector2 coordinate = new Vector2(currBlock.getBody().getPosition().x,
                        currBlock.getBody().getPosition().y - BLOCK_SIZE);
                jointDef.initialize(prevBlock.getBody(), currBlock.getBody(), coordinate);
                jointDef.collideConnected = true;
                world.createJoint(jointDef);
                house.addBlock(currBlock, house);
            }
        }

    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureA();

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
