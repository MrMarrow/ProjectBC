package com.bloxxcity.game.view;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.bloxxcity.game.ConstantValue;
import com.bloxxcity.game.MainGame;
import com.bloxxcity.game.controller.FontFactory;

import java.util.Locale;

import static com.bloxxcity.game.ConstantValue.RATIO_BUTTON_HOUSE_HEIGHT;
import static com.bloxxcity.game.ConstantValue.RATIO_BUTTON_HOUSE_WIDTH;
import static com.bloxxcity.game.ConstantValue.RATIO_POSITION_HOUSE1_X;
import static com.bloxxcity.game.ConstantValue.RATIO_POSITION_HOUSE1_Y;
import static com.bloxxcity.game.ConstantValue.RATIO_POSITION_HOUSE2_X;
import static com.bloxxcity.game.ConstantValue.RATIO_POSITION_HOUSE2_Y;
import static com.bloxxcity.game.ConstantValue.RATIO_POSITION_HOUSE3_X;
import static com.bloxxcity.game.ConstantValue.RATIO_POSITION_HOUSE3_Y;
import static com.bloxxcity.game.ConstantValue.RATIO_POSITION_TEXT_CHOICE_X;
import static com.bloxxcity.game.ConstantValue.RATIO_POSITION_TEXT_CHOICE_Y;
import static com.bloxxcity.game.ConstantValue.RATIO_TEXT_CHOICE_WIDTH;
import static com.bloxxcity.game.ConstantValue.RATIO_TEXT_CHOICE_HEIGHT;

public class HouseChoiceScreen implements Screen {

    private SpriteBatch spriteBatch;
    private OrthographicCamera camera;

    private Texture backgroundTexture;
    private Texture textChoiceTexture;
    private Texture buttonHouse1Texture;
    private Texture buttonHouse2Texture;
    private Texture buttonHouse3Texture;

    private Sprite backgroundSprite1;
    private Sprite backgroundSprite2;
    private Sprite textChoiceSprite;
    private Sprite buttonHouse1Sprite;
    private Sprite buttonHouse2Sprite;
    private Sprite buttonHouse3Sprite;

    private float height = Gdx.graphics.getHeight();
    private float width = Gdx.graphics.getWidth();

    private MainGame game;
    private Vector3 temp = new Vector3();

    private Locale ruLocale;
    private FontFactory fontFactory;

    public HouseChoiceScreen(MainGame game) {
        this.game = game;
        camera = new OrthographicCamera(getWidth(), getHeight());
        camera.setToOrtho(false);

        // Initialize FontFactory
        fontFactory = new FontFactory(70, Color.CORAL);
        // Initialize locales
        ruLocale = new Locale("ru", "RU");

        spriteBatch = game.getSpriteBatch();

        loadTexture();
        spriteInitialization();

        //setSize(textChoiceSprite, RATIO_TEXT_CHOICE_WIDTH, RATIO_TEXT_CHOICE_HEIGHT);
        setSize(buttonHouse1Sprite, RATIO_BUTTON_HOUSE_WIDTH, RATIO_BUTTON_HOUSE_HEIGHT);
        setSize(buttonHouse2Sprite, RATIO_BUTTON_HOUSE_WIDTH, RATIO_BUTTON_HOUSE_HEIGHT);
        setSize(buttonHouse3Sprite, RATIO_BUTTON_HOUSE_WIDTH, RATIO_BUTTON_HOUSE_HEIGHT);
        backgroundSprite1.setSize(width, height);
        backgroundSprite2.setSize(width + 1.2f, height);

        //setPosition(textChoiceSprite, RATIO_POSITION_TEXT_CHOICE_X, RATIO_POSITION_TEXT_CHOICE_Y);
        setPosition(buttonHouse1Sprite, RATIO_POSITION_HOUSE1_X, RATIO_POSITION_HOUSE1_Y);
        setPosition(buttonHouse2Sprite, RATIO_POSITION_HOUSE2_X, RATIO_POSITION_HOUSE2_Y);
        setPosition(buttonHouse3Sprite, RATIO_POSITION_HOUSE3_X, RATIO_POSITION_HOUSE3_Y);
        backgroundSprite1.setPosition(0, height * 0.171f);
        backgroundSprite2.setPosition(width, height * 0.171f);

    }

    @Override
    public void show() {

    }

    private void setPosition(Sprite sprite, float ratioX, float ratioY) {
        sprite.setPosition(getWidth() * ratioX, getHeight() * ratioY);
    }

    private void setSize(Sprite sprite, float ratioWidth, float ratioHeight) {
        sprite.setSize(getWidth() * ratioWidth, getHeight() * ratioHeight);
    }

    private void spriteInitialization() {
        backgroundSprite1 = new Sprite(backgroundTexture);
        backgroundSprite2 = new Sprite(backgroundTexture);
        textChoiceSprite = new Sprite(textChoiceTexture);
        buttonHouse1Sprite = new Sprite(buttonHouse1Texture);
        buttonHouse2Sprite = new Sprite(buttonHouse2Texture);
        buttonHouse3Sprite = new Sprite(buttonHouse3Texture);
    }

    private void loadTexture() {
        backgroundTexture = new Texture(Gdx.files.internal("background.png"));
        textChoiceTexture = new Texture(Gdx.files.internal("textChoiceHouse.png"));
        buttonHouse1Texture = new Texture(Gdx.files.internal("houseButton1.psd"));
        buttonHouse2Texture = new Texture(Gdx.files.internal("houseButton2.psd"));
        buttonHouse3Texture = new Texture(Gdx.files.internal("houseButton3.psd"));
    }

    @Override
    public void render(float delta) {

        // clear screen
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        spriteBatch.setProjectionMatrix(camera.combined);// устанавливаем в экземпляр spritebatch вид с камеры (области просмотра)

        //отрисовка игровых объектов
        spriteBatch.begin();
        if (backgroundSprite1.getX() < -width) {
            backgroundSprite1.setPosition(width, height * 0.171f);
        } else {
            backgroundSprite1.setPosition(backgroundSprite1.getX() - 0.2f, 0);

        }
        if (backgroundSprite2.getX() < -width) {
            backgroundSprite2.setPosition(width, height * 0.171f);
        } else {
            backgroundSprite2.setPosition(backgroundSprite2.getX() - 0.2f, 0);
        }

        backgroundSprite1.draw(spriteBatch);
        backgroundSprite2.draw(spriteBatch);

        //textChoiceSprite.draw(spriteBatch);
        buttonHouse1Sprite.draw(spriteBatch);
        buttonHouse2Sprite.draw(spriteBatch);
        buttonHouse3Sprite.draw(spriteBatch);

        fontFactory.getFont(ruLocale).draw(spriteBatch, "Выбери дом",
                width * RATIO_POSITION_TEXT_CHOICE_X,
                height * RATIO_POSITION_TEXT_CHOICE_Y);

        handleTouch();

        spriteBatch.end();
    }

    private void handleTouch() {
        if (Gdx.input.justTouched()) {
            // Получаем координаты касания и устанавливаем эти значения в временный вектор
            temp.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            // получаем координаты касания относительно области просмотра нашей камеры
            camera.unproject(temp);
            float touchX = temp.x;
            float touchY = temp.y;
            // check buttons
            if (checkTouch(touchX, touchY, buttonHouse1Sprite)) {
                game.setScreen(new GameScreen(game, 10));
            } else if (checkTouch(touchX, touchY, buttonHouse2Sprite)) {
                game.setScreen(new GameScreen(game, 20));
            } else if (checkTouch(touchX, touchY, buttonHouse3Sprite)) {
                game.setScreen(new GameScreen(game, 30));
            }
        }
    }

    private boolean checkTouch(float touchX, float touchY, Sprite sprite) {
        return (touchX >= sprite.getX()) &&
                touchX <= (sprite.getX() + sprite.getWidth()) &&
                (touchY >= sprite.getY()) &&
                touchY <= (sprite.getY() + sprite.getHeight());
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
        textChoiceTexture.dispose();
        buttonHouse1Texture.dispose();
        buttonHouse2Texture.dispose();
        buttonHouse3Texture.dispose();
        backgroundTexture.dispose();
//        spriteBatch.dispose();
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }


}
