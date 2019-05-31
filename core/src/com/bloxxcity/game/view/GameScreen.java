package com.bloxxcity.game.view;


import com.badlogic.gdx.Screen;
import com.bloxxcity.game.CameraManager;
import com.bloxxcity.game.MainGame;
import com.bloxxcity.game.controller.WorldController;
import com.bloxxcity.game.model.BuildWorld;
import com.bloxxcity.game.model.People;

import static com.bloxxcity.game.ConstantValue.CAMERA_HEIGHT;
import static com.bloxxcity.game.ConstantValue.CAMERA_WIDTH;

public class GameScreen implements Screen {

    private GameOverDialog gameOverDialog;

    private BuildWorld world;
    private WorldRenderer renderer;
    private int width;
    private int height;
    private WorldController controller;
    private MainGame game;
    private GameOverScreen gameOverScreen;

    public GameScreen(MainGame game, int countBlocks){
        this.game = game;
        System.out.println(333);
        world = new BuildWorld(countBlocks);
        CameraManager cameraManager = new CameraManager();
        People people = new People();
        renderer = new WorldRenderer(game, world, CAMERA_WIDTH, CAMERA_HEIGHT, true, cameraManager, people);
        controller = new WorldController(world, cameraManager);
        gameOverScreen = new GameOverScreen(this, game);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        if (delta > 0.05f) {
            delta = 0.05f;
        }
        renderer.render(delta);
        if (world.getHouse().getCurrentBlock() < world.getHouse().getMaxBlocks()){
            controller.update(delta);
        } else {
            gameOverScreen.render(delta);
        }
    }

    @Override
    public void resize(int width, int height) {
        this.width = width;
        this.height = height;
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {
        gameOverScreen.dispose();
        renderer.dispose();
    }

    public BuildWorld getWorld() {
        return world;
    }

    public MainGame getGame() {
        return game;
    }

    public WorldRenderer getRenderer() {
        return renderer;
    }
}
