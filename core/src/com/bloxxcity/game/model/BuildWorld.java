package com.bloxxcity.game.model;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJoint;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;
import com.badlogic.gdx.physics.box2d.joints.WeldJoint;
import com.badlogic.gdx.physics.box2d.joints.WeldJointDef;
import com.bloxxcity.game.controller.MyContactListener;

import static com.bloxxcity.game.ConstantValue.BUIlDING_SITE_SIZE;
import static com.bloxxcity.game.ConstantValue.MOTORSPEED_OF_CABLE;
import static java.lang.Math.cos;
import static java.lang.Math.sin;

public class BuildWorld {

    private World world;
    private Crane crane;
    private Block block;
    private Cable cable;
    private Body buildingSite;
    private House house;
    private RevoluteJoint revoluteJointCC;
    private WeldJoint weldJointCB;
    private Vector2 coordOfJointCC = new Vector2(24f, 65f);
    private Vector2 buldingSitePosition = new Vector2(24f, 15f);

    private boolean flagOfBlock = true;

    /**
     * creates a world and objects in it
     * @param countBlocks count of blocks in house
     */
    public BuildWorld(int countBlocks) {
        house = new House(countBlocks);
        world = new World(new Vector2(0, -10), true);
        world.setContactListener(new MyContactListener(world, house));
        createWorld();
    }

    /**
     * creates objects in the world
     */
    private void createWorld() {


        //creates crane and it size
        crane = new Crane(world);
        crane.getBody().setTransform(30.f, 67.f, 0);

        BodyDef defS = new BodyDef();
        defS.type = BodyType.StaticBody;//тип тела статический
        defS.position.set(0, 0);
        Body ground = world.createBody(defS);
        FixtureDef fDef = new FixtureDef();
        ChainShape chainShapeG = new ChainShape();
        chainShapeG.createChain(new Vector2[]{new Vector2(0, 15), new Vector2(48, 15)});//"землей"
        // является отрезок от начала экрана до его конца
        fDef.shape = chainShapeG;
        ground.setUserData("ground");
        ground.createFixture(fDef);//создаём тело в мире
        chainShapeG.dispose();

        buildingSite = createBox(BodyType.StaticBody, BUIlDING_SITE_SIZE, 0.3f, 0);//создаём
        buildingSite.setUserData("buildingSite");

        // строительную площадку
        buildingSite.setTransform(buldingSitePosition, 0);//задаём позицию строительной площадки

        cable = new Cable(world);//создаём кабель
        cable.getBody().setTransform(24f, 55f, 0);//задаём позицию кабелю

        block = new Block(world, 0, 10);//создаём блок
        block.getBody().setTransform(24.f, 40.f, 0);//задаём позицию блоку


        RevoluteJointDef jointDefCC = new RevoluteJointDef();
        jointDefCC.initialize(crane.getBody(), cable.getBody(),coordOfJointCC);

        jointDefCC.enableMotor = true;
        jointDefCC.motorSpeed = MOTORSPEED_OF_CABLE;
        jointDefCC.maxMotorTorque = 170f;

        jointDefCC.enableLimit = true;
        jointDefCC.lowerAngle = -0.55f;
        jointDefCC.upperAngle = 0.55f;

        revoluteJointCC = (RevoluteJoint) world.createJoint(jointDefCC);//соединяем кабель и кран

        jointCableBlock(cable, block);

    }

    private Body createBox(BodyType type, float width, float height, float density) {//функция для создания квадратного тела
        BodyDef def = new BodyDef();
        def.type = type;
        Body box = world.createBody(def);
        PolygonShape poly = new PolygonShape();
        poly.setAsBox(width, height);
        box.createFixture(poly, density);
        poly.dispose();
        return box;
    }

    public void jointCableBlock(Cable cable, Block block){
        WeldJointDef jointDefCB = new WeldJointDef();
        float hypotenuse = cable.getHeight() / 2;
        float angle = cable.getBody().getAngle();
        float x = (float) (hypotenuse * sin(angle));
        float y = (float) (hypotenuse * cos(angle));
        Vector2 coordinate = cable.getPosition();
        Vector2 newCoordinate = new Vector2(coordinate.x + x,
                coordinate.y - y);

        jointDefCB.initialize(block.getBody(), cable.getBody(), newCoordinate);
        weldJointCB = (WeldJoint) world.createJoint(jointDefCB);// соединяем кабель и блок
    }

    public World getWorld() {
        return world;
    }

    public void dispose() {
        world.dispose();
    }

    public Cable getCable() {
        return cable;
    }

    public Block getBlock() {
        return block;
    }

    public Crane getCrane() {
        return crane;
    }

    public Body getBuildingSite() {
        return buildingSite;
    }

    public House getHouse() {
        return house;
    }

    public RevoluteJoint getRevoluteJointCC() {
        return revoluteJointCC;
    }

    public WeldJoint getWeldJointCB() {
        return weldJointCB;
    }

    public boolean isFlagOfBlock() {
        return flagOfBlock;
    }

    public void setFlagOfBlock(boolean flagOfBlock) {
        this.flagOfBlock = flagOfBlock;
    }

    public Vector2 getCoordOfJointCC() {
        return coordOfJointCC;
    }

    public void setBlock(Block block) {
        this.block = block;
    }
}
