package com.bloxxcity.game.model;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

public class House {

    private Array<Fixture> blocks;
    private int currentBlock;
    private int maxBlocks;
    private People people;
    private boolean flagBloxx;

    /**
     *  creates an array of block with counter and maximum number
     *  creates flag for block on crane
     * @param N - max possible number of Blocks
     */
    public House(int N){
        blocks = new Array<Fixture>();
        this.currentBlock = 0;
        this.maxBlocks = N;
        this.people = new People();
        flagBloxx = false;
    }

    /**
     * increments current number
     */
    public void addBlock(Fixture block, House house){
        currentBlock++;
        people.setPeople(house, block);
        flagBloxx = true;
        getBlocks().add(block);
    }

    public int getCurrentBlock() {
        return currentBlock;
    }

    public void setCurrentBlock(int currentBlock) {
        this.currentBlock = currentBlock;
    }

    public int getMaxBlocks() {
        return maxBlocks;
    }

    public void setMaxBlocks(int maxBlocks) {
        this.maxBlocks = maxBlocks;
    }

    public Array<Fixture> getBlocks() {
        return blocks;
    }

    public People getPeople() {
        return people;
    }

    public boolean isFlagBloxx() {
        return flagBloxx;
    }

    public void setFlagBloxx(boolean flagBloxx) {
        this.flagBloxx = flagBloxx;
    }
}
