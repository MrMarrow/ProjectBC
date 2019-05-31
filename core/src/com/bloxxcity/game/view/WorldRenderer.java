package com.bloxxcity.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.bloxxcity.game.CameraManager;
import com.bloxxcity.game.MainGame;
import com.bloxxcity.game.controller.FontFactory;
import com.bloxxcity.game.model.BuildWorld;
import com.bloxxcity.game.model.People;

import java.util.Locale;

import static com.bloxxcity.game.ConstantValue.BLOCK_SIZE;
import static com.bloxxcity.game.ConstantValue.BUIlDING_SITE_SIZE;
import static com.bloxxcity.game.ConstantValue.CAMERA_HEIGHT;
import static com.bloxxcity.game.ConstantValue.CAMERA_WIDTH;
import static com.bloxxcity.game.ConstantValue.RATIO_TEXT_HOUSE_CURRENT_NUMBER_POSITION_X;
import static com.bloxxcity.game.ConstantValue.RATIO_TEXT_HOUSE_CURRENT_NUMBER_POSITION_Y;
import static com.bloxxcity.game.ConstantValue.RATIO_TEXT_HOUSE_MAX_NUMBER_POSITION_X;
import static com.bloxxcity.game.ConstantValue.RATIO_TEXT_HOUSE_MAX_NUMBER_POSITION_Y;
import static com.bloxxcity.game.ConstantValue.RATIO_TEXT_PEOPLE_NAME_POSITION_X;
import static com.bloxxcity.game.ConstantValue.RATIO_TEXT_PEOPLE_NAME_POSITION_Y;
import static com.bloxxcity.game.ConstantValue.RATIO_TEXT_PEOPLE_NUMBER_POSITION_X;
import static com.bloxxcity.game.ConstantValue.RATIO_TEXT_PEOPLE_NUMBER_POSITION_Y;


public class WorldRenderer {

    private Box2DDebugRenderer renderer;
    private BuildWorld world;
    private OrthographicCamera cam;
    private SpriteBatch spriteBatch;

    private Texture textureBlock;
    private Texture textureCrane;
    private Texture textureCable;
    private Texture textureRoof;
    private Texture textureGround;
    private Texture textureBuildSite;
    private Texture topBarTexture;
    private Sprite topBarSprite;
    private Sprite backgroundSprite1;
    private Sprite backgroundSprite2;
    private Texture backgroundTexture;

    private BitmapFont font;
    private CameraManager cameraManager;
    private float ppuX;    // pixels per unit on the X axis
    private float ppuY;    // pixels per unit on the Y axis
    private float height = Gdx.graphics.getHeight();
    private float width = Gdx.graphics.getWidth();
    private MainGame game;

    private Locale ruLocale;
    private FontFactory fontFactory;
    private float heightChange = 0;

    WorldRenderer(MainGame game, BuildWorld world, float widthC, float heightC, boolean debug, CameraManager cameraManager, People people) {
        // Initialize FontFactory
        fontFactory = new FontFactory(25, Color.BLACK);
        // Initialize locales
        ruLocale = new Locale("ru", "RU");

        this.game = game;
        renderer = new Box2DDebugRenderer();
        this.world = world;//задаём мир для отрисовки

        spriteBatch = game.getSpriteBatch();

        loadTextures();//загружаем текстуры
        topBarSprite = new Sprite(topBarTexture);
        topBarSprite.setSize(width, height * 0.125f);
        topBarSprite.setPosition(0f, height * 0.875f);

        backgroundSprite1 = new Sprite(backgroundTexture);
        backgroundSprite2 = new Sprite(backgroundTexture);
        backgroundSprite1.setSize(width, height * 3);
        backgroundSprite2.setSize(width + 1.2f, height * 3);
        backgroundSprite1.setPosition(0, height * 0.191f);
        backgroundSprite2.setPosition(width, height * 0.191f);

        ppuX = (float) Gdx.graphics.getWidth() / CAMERA_WIDTH;
        ppuY = (float) Gdx.graphics.getHeight() / CAMERA_HEIGHT;
        this.cam = new OrthographicCamera(widthC, heightC); //определяем камеру
        SetCamera(widthC / 2f, heightC / 2f);//задаём положение камере
        this.cameraManager = cameraManager;//задаём значение флага камеры
        font = new BitmapFont();
        font.setColor(Color.BLACK);
    }

    public MainGame getGame() {
        return game;
    }

    public BuildWorld getWorld() {
        return world;
    }

    private void loadTextures() {//load texture
        topBarTexture = new Texture(Gdx.files.internal("topBar.png"));
        backgroundTexture = new Texture(Gdx.files.internal("backgroundGameScreen.png"));
        textureBlock = new Texture(Gdx.files.internal("orange_room.png"));
        textureCrane = new Texture(Gdx.files.internal("crane.png"));
        textureCable = new Texture(Gdx.files.internal("hook.png"));
        textureRoof = new Texture(Gdx.files.internal("roof.png"));
        textureGround = new Texture(Gdx.files.internal("ground.png"));
        textureBuildSite = new Texture(Gdx.files.internal("build_site.png"));
    }

    private void SetCamera(float x, float y) {//задание положения камеры
        this.cam.position.set(x, y, 0);
        this.cam.update();
    }

    public void dispose() {
        world.dispose();
    }

    public void render(float delta) {

        Gdx.gl.glClearColor(0.545f, 0.8627f, 1f, 1);
        //Gdx.gl.glClearColor(1f, 1f, 1f, 1);
        // Gdx.gl.glClearColor(0f, 0f, 0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        //renderer.render(world.getWorld(), cam.combined);

        //
        if (cameraManager.isCameraFlag()) {
            SetCamera(cam.position.x, cam.position.y + 10f);
            heightChange += 10f;
            System.out.println(heightChange);
            cameraManager.setCameraFlag(false);
        }

        spriteBatch.begin();//начало отрисовки
        if (backgroundSprite1.getX() < -width) {
            backgroundSprite1.setPosition(width, height * 0.391f - heightChange * ppuY);
        } else {
            backgroundSprite1.setPosition(backgroundSprite1.getX() - 0.2f,
                    height * 0.391f - heightChange * ppuY);

        }
        if (backgroundSprite2.getX() < -width) {
            backgroundSprite2.setPosition(width, height * 0.391f - heightChange * ppuY);
        } else {
            backgroundSprite2.setPosition(backgroundSprite2.getX() - 0.2f,
                    height * 0.391f - heightChange * ppuY);

        }

        backgroundSprite1.draw(spriteBatch);
        backgroundSprite2.draw(spriteBatch);

        topBarSprite.draw(spriteBatch);
        drawFont("Жителей:", RATIO_TEXT_PEOPLE_NAME_POSITION_X, RATIO_TEXT_PEOPLE_NAME_POSITION_Y);
        drawFont(String.valueOf(world.getHouse().getPeople().getPeople()),
                RATIO_TEXT_PEOPLE_NUMBER_POSITION_X,
                RATIO_TEXT_PEOPLE_NUMBER_POSITION_Y);
        drawFont(String.valueOf(world.getHouse().getMaxBlocks()),
                RATIO_TEXT_HOUSE_CURRENT_NUMBER_POSITION_X,
                RATIO_TEXT_HOUSE_CURRENT_NUMBER_POSITION_Y);
        drawFont(String.valueOf(world.getHouse().getCurrentBlock()),
                RATIO_TEXT_HOUSE_MAX_NUMBER_POSITION_X,
                RATIO_TEXT_HOUSE_MAX_NUMBER_POSITION_Y);

        drawGround();//отрисовывем землю
        drawBuildSite();//отрисовываем стартовую площадку
        drawCable();//отрисовка кабеля
        drawCrane();//отрисовка крана
        drawBlock();//отрисовка текущего блока
        drawBlocks();//отрисовка массива блоков
        spriteBatch.end();//конец отрисовки
        world.getWorld().step(delta, 4, 4);//делает шаг времени

    }

    public void drawFont(String text, float posX, float posY) {
        fontFactory.getFont(ruLocale).draw(spriteBatch, text, width * posX, height * posY);
    }

    /**
     * draws start platform
     */
    private void drawBuildSite() {
        spriteBatch.draw(textureBuildSite,
                (world.getBuildingSite().getPosition().x - BUIlDING_SITE_SIZE) * ppuX,
                (world.getBuildingSite().getPosition().y - 0.6f - heightChange) * ppuY,
                18 * ppuX, 0.9f * ppuY);

    }

    /**
     * draws ground under start platform
     */
    private void drawGround() {
        if (world.getHouse().getCurrentBlock() <= world.getHouse().getMaxBlocks()) {
            spriteBatch.draw(textureGround, 0,
                    (0 - heightChange) * ppuY,
                    48 * ppuX, 15 * ppuY);
        }
    }

    private void drawCable() {
        if (world.getCable().getBody() != null) {
            Sprite sprite = new Sprite(textureCable);
            sprite.setSize(world.getCable().getWidth() * 6 * ppuX, (world.getCable().getHeight() * 2) * ppuY);
            sprite.setRotation(world.getCable().getBody().getAngle() * (180 / (float) Math.PI));

            sprite.setPosition((world.getCable().getPosition().x - world.getCable().getWidth()) * ppuX,
                    (world.getCable().getPosition().y - world.getCable().getHeight() - heightChange) * ppuY);
            sprite.draw(spriteBatch);
        }

    }

    /**
     * draws blocks o house
     */
    private void drawBlocks() {//отрисовка массива блоков
        Sprite sprite = new Sprite(textureBlock);
        sprite.setSize(BLOCK_SIZE * 2 * ppuX, BLOCK_SIZE * 2 * ppuY);
        for (Fixture block : world.getHouse().getBlocks()) {
            //draw roof
            if (block.getBody().getUserData().equals("roof")) {
                sprite = new Sprite(textureRoof);
                sprite.setSize((BLOCK_SIZE * 2 + 2) * ppuX, (BLOCK_SIZE * 2) * ppuY);
                sprite.setPosition((block.getBody().getPosition().x - BLOCK_SIZE - 1) * ppuX,
                        (block.getBody().getPosition().y - BLOCK_SIZE - heightChange) * ppuY);
            } else {
                //draw other blocks
                sprite.setPosition((block.getBody().getPosition().x - BLOCK_SIZE) * ppuX,
                        (block.getBody().getPosition().y - BLOCK_SIZE - heightChange) * ppuY);
            }
            sprite.draw(spriteBatch);
        }
    }

    /**
     * draws block on cable
     */
    private void drawBlock() {//отрисовка текущего блока
        if (world.getBlock() != null && !world.getHouse().isFlagBloxx()) {
            Sprite sprite = new Sprite(textureBlock);
            sprite.setSize(BLOCK_SIZE * 2 * ppuX, BLOCK_SIZE * 2 * ppuY);
            sprite.setRotation(world.getBlock().getBody().getAngle() * (180 / (float) Math.PI));
            if (world.getHouse().getCurrentBlock() < world.getHouse().getMaxBlocks() - 1) {//когда в массиве от 1 до 9 блоков
                sprite.setPosition((world.getBlock().getPosition().x - BLOCK_SIZE) * ppuX,
                        (world.getBlock().getPosition().y - BLOCK_SIZE - heightChange) * ppuY);
                sprite.draw(spriteBatch);

            } else {
                sprite = new Sprite(textureRoof);
                sprite.setSize((BLOCK_SIZE * 2 + 2) * ppuX, (BLOCK_SIZE * 2) * ppuY);
                sprite.setPosition((world.getBlock().getPosition().x - BLOCK_SIZE - 1) * ppuX,
                        (world.getBlock().getPosition().y - BLOCK_SIZE - heightChange) * ppuY);
                sprite.draw(spriteBatch);
            }
        }
    }

    private void drawCrane() {
        if (world.getCrane().getBody() != null) {
            if (world.getHouse().getCurrentBlock() < world.getHouse().getMaxBlocks()) {
                spriteBatch.draw(textureCrane, (world.getCrane().getPosition().x - world.getCrane().getWidth()) * ppuX,
                        (world.getCrane().getPosition().y - world.getCrane().getHeight() - heightChange) * ppuY,
                        world.getCrane().getWidth() * 2 * ppuX, world.getCrane().getHeight() * 2f * ppuY);
            } else {
                spriteBatch.draw(textureCrane, (world.getCrane().getPosition().x - world.getCrane().getWidth()) * ppuX,
                        (world.getCrane().getPosition().y - world.getCrane().getHeight() - heightChange) * ppuY,
                        world.getCrane().getWidth() * 2 * ppuX, world.getCrane().getHeight() * 2f * ppuY);
            }
        }
    }
}
