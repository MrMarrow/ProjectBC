package com.bloxxcity.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.bloxxcity.game.MainGame;

public class MenuScreen implements Screen {

    private SpriteBatch spriteBatch;
    private OrthographicCamera camera;

    private Texture startButtonTexture;
    private Texture exitButtonTexture;
    private Texture backgroundTexture1;
    private Texture backgroundTexture2;
    private Sprite startButtonSprite;
    private Sprite exitButtonSprite;
    private Sprite backGroundSprite1;
    private Sprite backGroundSprite11;
    private Sprite backGroundSprite2;

    private static float BUTTON_RESIZE_FACTOR = 800f; // задаём относительный размер

    private MainGame game;
    private Vector3 temp = new Vector3();

    private float height = Gdx.graphics.getHeight();
    private float width = Gdx.graphics.getWidth();

    private void loadTexture() {
        startButtonTexture = new Texture(Gdx.files.internal("buttonStartGame.psd"));
        exitButtonTexture = new Texture(Gdx.files.internal("buttonExitGame.psd"));
        backgroundTexture1 = new Texture(Gdx.files.internal("background.png"));
        backgroundTexture2 = new Texture(Gdx.files.internal("groundBackground.png"));
    }

    public MenuScreen(MainGame game) {
        this.game = game;


        camera = new OrthographicCamera(width, height);
        camera.setToOrtho(false);

        spriteBatch = game.getSpriteBatch();


        loadTexture();
        startButtonSprite = new Sprite(startButtonTexture);
        exitButtonSprite = new Sprite(exitButtonTexture);
        backGroundSprite1 = new Sprite(backgroundTexture1);
        backGroundSprite11 = new Sprite(backgroundTexture1);
        backGroundSprite2 = new Sprite(backgroundTexture2);

        startButtonSprite.setSize(startButtonSprite.getWidth() * (width / BUTTON_RESIZE_FACTOR),
                startButtonSprite.getHeight() * (width / BUTTON_RESIZE_FACTOR));
        exitButtonSprite.setSize(exitButtonSprite.getWidth() * (width / BUTTON_RESIZE_FACTOR),
                exitButtonSprite.getHeight() * (width / BUTTON_RESIZE_FACTOR));
        backGroundSprite1.setSize(width, height);
        backGroundSprite11.setSize(width + 1.2f, height);
        backGroundSprite2.setSize(width, height * 0.171f);
        startButtonSprite.setPosition(width * 0.21f,
                height * 0.575f);
        exitButtonSprite.setPosition(width * 0.21f,
                height * 0.375f);
        backGroundSprite1.setPosition(0, height * 0.171f);
        backGroundSprite11.setPosition(width, height * 0.171f);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        spriteBatch.setProjectionMatrix(camera.combined);
        if (backGroundSprite1.getX() < -width) {
            backGroundSprite1.setPosition(width, height * 0.171f);
        } else {
            backGroundSprite1.setPosition(backGroundSprite1.getX() - 0.2f, height * 0.171f);

        }
        if (backGroundSprite11.getX() < -width) {
            backGroundSprite11.setPosition(width, height * 0.171f);
        } else {
            backGroundSprite11.setPosition(backGroundSprite11.getX() - 0.2f, height * 0.171f);

        }
        spriteBatch.begin();
        backGroundSprite1.draw(spriteBatch);
        backGroundSprite11.draw(spriteBatch);
        backGroundSprite2.draw(spriteBatch);
        startButtonSprite.draw(spriteBatch);
        exitButtonSprite.draw(spriteBatch);

        handleTouch();

        spriteBatch.end();
    }

    @Override
    public void resize(int width, int height) {

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
        startButtonTexture.dispose();
        exitButtonTexture.dispose();
    }

    /**
     * Checks touching the screen
     */
    private void handleTouch() {
        if (Gdx.input.justTouched()) {
            // get coordinates
            temp.set(Gdx.input.getX(), Gdx.input.getY(), 0);

            camera.unproject(temp);
            float touchX = temp.x;
            float touchY = temp.y;
            // Start game
            if ((touchX >= startButtonSprite.getX()) &&
                    touchX <= (startButtonSprite.getX() + startButtonSprite.getWidth()) &&
                    (touchY >= startButtonSprite.getY()) &&
                    touchY <= (startButtonSprite.getY() + startButtonSprite.getHeight())) {
                game.setScreen(new HouseChoiceScreen(game));
            }
            // Exit the game
            else if ((touchX >= exitButtonSprite.getX()) &&
                    touchX <= (exitButtonSprite.getX() + exitButtonSprite.getWidth()) &&
                    (touchY >= exitButtonSprite.getY()) &&
                    touchY <= (exitButtonSprite.getY() + exitButtonSprite.getHeight())) {
                Gdx.app.exit();
            }
        }
    }

}
