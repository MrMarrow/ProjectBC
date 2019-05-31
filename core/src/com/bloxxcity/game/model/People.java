package com.bloxxcity.game.model;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;

import static com.bloxxcity.game.ConstantValue.BLOCK_SIZE;
import static java.lang.Math.abs;

public class People {

    private int people;


    public int getPeople() {
        return people;
    }


    /**
     * increase quantity of people after the block fall
     * @param house
     * @param block
     */
    public void setPeople(House house, Fixture block) {
        if (house.getBlocks().size == 0) {
            people += 10;
        } else {
            float currX = block.getBody().getPosition().x;
            float prevX = house.getBlocks().peek().getBody().getPosition().x;
            if (currX < prevX){
                people += 10 * (abs((currX + BLOCK_SIZE) - prevX) / BLOCK_SIZE);
            } else{
                people += 10 * (abs((currX - BLOCK_SIZE) - prevX) / BLOCK_SIZE);
            }
        }
    }
}
