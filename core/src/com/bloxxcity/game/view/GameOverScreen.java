package com.bloxxcity.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.bloxxcity.game.MainGame;
import com.bloxxcity.game.controller.FontFactory;

import java.util.Locale;

import static com.bloxxcity.game.ConstantValue.RATIO_BUTTON_EXIT_POSITION_X;
import static com.bloxxcity.game.ConstantValue.RATIO_BUTTON_EXIT_POSITION_Y;
import static com.bloxxcity.game.ConstantValue.RATIO_BUTTON_HEIGHT;
import static com.bloxxcity.game.ConstantValue.RATIO_BUTTON_TRY_AGAIN_POSITION_X;
import static com.bloxxcity.game.ConstantValue.RATIO_BUTTON_TRY_AGAIN_POSITION_Y;
import static com.bloxxcity.game.ConstantValue.RATIO_BUTTON_WIDTH;
import static com.bloxxcity.game.ConstantValue.RATIO_RESULT_DIALOG_HEIGHT;
import static com.bloxxcity.game.ConstantValue.RATIO_RESULT_DIALOG_POSITION_X;
import static com.bloxxcity.game.ConstantValue.RATIO_RESULT_DIALOG_POSITION_Y;
import static com.bloxxcity.game.ConstantValue.RATIO_RESULT_DIALOG_WIDTH;
import static com.bloxxcity.game.ConstantValue.RATIO_TEXT_BLOCKS_POSITION_X;
import static com.bloxxcity.game.ConstantValue.RATIO_TEXT_BLOCKS_POSITION_Y;
import static com.bloxxcity.game.ConstantValue.RATIO_TEXT_PEOPLE_POSITION_X;
import static com.bloxxcity.game.ConstantValue.RATIO_TEXT_PEOPLE_POSITION_Y;

public class GameOverScreen /*implements Screen*/ {

    private SpriteBatch spriteBatch;
    private OrthographicCamera camera;

    private Texture resultDialogTexture;
    private Texture buttonExitTexture;
    private Texture buttonTryAgainTexture;

    private Sprite resultDialogSprite;
    private Sprite buttonExitSprite;
    private Sprite buttonTryAgainSprite;

    private float height = Gdx.graphics.getHeight();
    private float width = Gdx.graphics.getWidth();

    private MainGame game;
    private GameScreen gameScreen;
    private Vector3 temp = new Vector3();

    private Locale ruLocale;
    private FontFactory fontFactory;

    public GameOverScreen(GameScreen gameScreen, MainGame game){
        this.gameScreen = gameScreen;
        this.game = game;
        camera = new OrthographicCamera(getWidth(), getHeight());
        camera.setToOrtho(false);

        // Initialize FontFactory
        fontFactory = new FontFactory(30, Color.WHITE);
        // Initialize locales
        ruLocale = new Locale("ru", "RU");
        spriteBatch = game.getSpriteBatch();

        loadTexture();
        spriteInitialization();

        setSize(resultDialogSprite, RATIO_RESULT_DIALOG_WIDTH, RATIO_RESULT_DIALOG_HEIGHT);
        setSize(buttonExitSprite, RATIO_BUTTON_WIDTH, RATIO_BUTTON_HEIGHT);
        setSize(buttonTryAgainSprite, RATIO_BUTTON_WIDTH, RATIO_BUTTON_HEIGHT);

        setPosition(resultDialogSprite, RATIO_RESULT_DIALOG_POSITION_X, RATIO_RESULT_DIALOG_POSITION_Y);
        setPosition(buttonExitSprite, RATIO_BUTTON_EXIT_POSITION_X, RATIO_BUTTON_EXIT_POSITION_Y);
        setPosition(buttonTryAgainSprite, RATIO_BUTTON_TRY_AGAIN_POSITION_X, RATIO_BUTTON_TRY_AGAIN_POSITION_Y);
    }

    private void spriteInitialization() {
        resultDialogSprite = new Sprite(resultDialogTexture);
        buttonTryAgainSprite = new Sprite(buttonTryAgainTexture);
        buttonExitSprite = new Sprite(buttonExitTexture);
    }

    private void loadTexture() {
        resultDialogTexture = new Texture(Gdx.files.internal("resultPanel.psd"));
        buttonExitTexture = new Texture(Gdx.files.internal("buttonExitGame.psd"));
        buttonTryAgainTexture = new Texture(Gdx.files.internal("buttonTryAgain.psd"));
    }

    void render(float delta) {

        //отрисовка игровых объектов
        spriteBatch.begin();
        resultDialogSprite.draw(spriteBatch);
        buttonExitSprite.draw(spriteBatch);
        buttonTryAgainSprite.draw(spriteBatch);
        fontFactory.getFont(ruLocale).
                draw(spriteBatch, "Этажей: " + gameScreen.getWorld().getHouse().getCurrentBlock(),
                getWidth() * RATIO_TEXT_BLOCKS_POSITION_X,
                getHeight() * RATIO_TEXT_BLOCKS_POSITION_Y);
        fontFactory.getFont(ruLocale).
                draw(spriteBatch, "Жителей: " + gameScreen.getWorld().getHouse().getPeople().getPeople(),
                getWidth() * RATIO_TEXT_PEOPLE_POSITION_X,
                getHeight() * RATIO_TEXT_PEOPLE_POSITION_Y);

        handleTouch();

        spriteBatch.end();
    }

    private void handleTouch() {
        if (Gdx.input.justTouched()) {
            // get touch coordinate
            temp.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(temp);
            float touchX = temp.x;
            float touchY = temp.y;

            // check buttons
            if (checkTouch(touchX, touchY, buttonTryAgainSprite)) {
                //try again
                game.setScreen(new HouseChoiceScreen(game));
            } else if (checkTouch(touchX, touchY, buttonExitSprite)) {
                //exit
                Gdx.app.exit();
            }
        }
    }

    private boolean checkTouch(float touchX, float touchY, Sprite sprite) {
        return (touchX >= sprite.getX()) &&
                touchX <= (sprite.getX() + sprite.getWidth()) &&
                (touchY >= sprite.getY()) &&
                touchY <= (sprite.getY() + sprite.getHeight());
    }

    public void dispose() {
        resultDialogTexture.dispose();
        buttonTryAgainTexture.dispose();
        buttonExitTexture.dispose();
    }

    private void setPosition(Sprite sprite, float ratioX, float ratioY) {
        sprite.setPosition(getWidth() * ratioX, getHeight() * ratioY);
    }

    private void setSize(Sprite sprite, float ratioWidth, float ratioHeight) {
        sprite.setSize(getWidth() * ratioWidth, getHeight() * ratioHeight);
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }
}
