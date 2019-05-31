package com.bloxxcity.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.bloxxcity.game.view.GameScreen;
import com.bloxxcity.game.view.HouseChoiceScreen;
import com.bloxxcity.game.view.MenuScreen;

public class MainGame extends Game {

    protected SpriteBatch spriteBatch;

	@Override
	public void create() {
	    spriteBatch = new SpriteBatch();
		Screen menuScreen = new MenuScreen(this);
		setScreen(menuScreen);
	}

	@Override
	public void dispose(){

	}

    public SpriteBatch getSpriteBatch() {
        return spriteBatch;
    }
}
