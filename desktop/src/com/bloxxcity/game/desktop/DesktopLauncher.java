package com.bloxxcity.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.bloxxcity.game.MainGame;

import static com.bloxxcity.game.ConstantValue.HEIGHT;
import static com.bloxxcity.game.ConstantValue.WIDTH;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Bloxx city";
		config.width = WIDTH;
		config.height = HEIGHT;
		new LwjglApplication(new MainGame(), config);
	}
}
