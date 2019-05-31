package com.bloxxcity.game.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.bloxxcity.game.model.BuildWorld;
import com.bloxxcity.game.CameraManager;
import com.bloxxcity.game.model.Block;
import com.bloxxcity.game.model.BuildWorld;
import com.bloxxcity.game.model.House;

import static com.bloxxcity.game.ConstantValue.BLOCK_SIZE;
import static com.bloxxcity.game.ConstantValue.BUIlDING_SITE_SIZE;
import static com.bloxxcity.game.ConstantValue.MOTORSPEED_OF_CABLE;
import static java.lang.Math.abs;
import static java.lang.Math.cos;
import static java.lang.Math.sin;

public class WorldController {


    private BuildWorld world;
    private final CameraManager cameraManager;
    private boolean flagChekBlock;


    /**
     * creates a controller of the world
     * @param world         - created world
     * @param cameraManager - flag to shift camera position
     */
    public WorldController(BuildWorld world, CameraManager cameraManager) {
        this.world = world;
        this.cameraManager = cameraManager;
    }

    public void update(float delta) {
        moveCrane();
        float currNumberOfBlock = world.getHouse().getCurrentBlock();
        float maxNumberOfBlock = world.getHouse().getMaxBlocks();
        if (currNumberOfBlock < maxNumberOfBlock) {
            if (Gdx.input.justTouched()) {
                if (world.isFlagOfBlock()) {
                    world.getWorld().destroyJoint(world.getWeldJointCB());
                    world.getBlock().getBody().resetMassData();
                    world.getBlock().getBody().setGravityScale(15f);
                    world.setFlagOfBlock(false);
                    world.getHouse().setFlagBloxx(false);
                }
            }

            if (!world.isFlagOfBlock()) {
                checkBlock();
                if (world.getHouse().isFlagBloxx() || flagChekBlock) {
                    createBlockOnCable();
                }
            }
        }
    }
//            if (currNumberOfBlock > maxNumberOfBlock){
//                world.getWorld().destroyBody(world.getCable().getBody());
//                world.getWorld().destroyBody(world.getCrane().getBody());
//            }
//
//
//        if (flag) {//блок прикреплён к тросу
//            moveCrane();//создаём маятниковое даижение
//            if (Gdx.input.justTouched()) {//если было касание к экрану
//                flag = false;
//                Vector2 pos = world.getBlock().getBody().getPosition();//узнаем позицию блока
//                float angle = world.getBlock().getBody().getAngle();//узнаём его угол наклона
//                block = new Block(world.getWorld(), k);//создём заново
//                block.getBody().setTransform(pos, angle);//перемещаем его в нужную позицию и под нужным углом
//            }
//        } else if (k <= N) {//пока не достигли последнего блока
//            if (world.getCable().getBody().getPosition().x < 24.1f &&
//                    world.getCable().getBody().getPosition().x > 23.9f && !flagBloxx) {//если трос находится посередине
//                block = new Block(world.getWorld() , k);//создаём блок
//                block.getBody().setTransform(24, world.getCrane().getPosition().y - 27, 0);//перемещаём блок
//                WeldJointDef jointDefCB = new WeldJointDef();
//                jointDefCB.initialize(block.getBody(), world.getCable().getBody(), new Vector2(24, world.getCrane().getPosition().y - 22));
//                world.getWorld().createJoint(jointDefCB);//скрепляем блок с кабелем
//                world.getBlock().setBody(block.getBody());//делаем созданый блок текущим
//                flag = true;//даём добро на сбрасывание блока
//                flagBloxx = true;//меняем значение флага проверки
//            }
//            if (flagBloxx && world.getBlock().getPosition().y - BLOCK_SIZE <= 15.4f
//                    && world.getBlock().getPosition().x < 32
//                    && world.getBlock().getPosition().x > 16) {//если первый блок упал на строительную площадку
//                WeldJointDef jointDefBB = new WeldJointDef();
//                jointDefBB.initialize(world.getBuildingSite(), world.getBlock().getBody(),
//                        new Vector2(block.getPosition().x, block.getPosition().y - BLOCK_SIZE));
//                world.getWorld().createJoint(jointDefBB);//скрепляем блок со строительной площадкой
//                world.getBlocks().add(block);//добавляем блок в массив
//                flagBloxx = false;//меняем значение флага проверки
//                people.setPeople(world);//увеличиваем количество жителей
//                k++;//увеличиваем число этажей
//            } else if (flagBloxx && world.getBlock().getPosition().y - BLOCK_SIZE <= 15.4f
//                    && (world.getBlock().getPosition().x > 32
//                    || world.getBlock().getPosition().x < 16)) {
//                world.getWorld().destroyBody(world.getBlock().getBody());////если первый блок упал за пределы строительной площадки
//                flagBloxx = false;
//            } else if (flagBloxx && world.getBlocks().size != 0 &&
//                    world.getBlock().getPosition().y - BLOCK_SIZE <= world.getBlocks().peek().getPosition().y + BLOCK_SIZE + 0.05f
//                    && world.getBlock().getPosition().x < world.getBlocks().peek().getPosition().x + BLOCK_SIZE
//                    && world.getBlock().getPosition().x > world.getBlocks().peek().getPosition().x - BLOCK_SIZE) {
//                //если блок упал на предыдущий блок
//                WeldJointDef jointDefBB = new WeldJointDef();
//                jointDefBB.initialize(world.getBlocks().peek().getBody(), world.getBlock().getBody(),
//                        new Vector2(world.getBlock().getPosition().x, world.getBlock().getPosition().y - BLOCK_SIZE));
//                world.getWorld().createJoint(jointDefBB);//скрепляем блок со строительной площадкой
//                people.setPeople(world);//увеличиваем количество жителей
//                world.getBlocks().add(block);//добавляем блок в массив
//                flagBloxx = false;//меняем значение флага проверки
//                world.getBlock().setBody(null);
//
//                if (k < N) {//пока не достигли крыши
//                    world.getCrane().getBody().setTransform(world.getCrane().getPosition().x,
//                            world.getCrane().getPosition().y + BLOCK_SIZE * 2, 0);//сдвигаем кран наверх
//                    cameraManager.setCameraFlag(true);//меняем флаг для камеры
//                }
//                k++;//увеличиваем число этаже
//            } else if (flagBloxx && world.getBlocks().size != 0 &&
//                    world.getBlock().getPosition().y - BLOCK_SIZE <= world.getBlocks().peek().getPosition().y + BLOCK_SIZE
//                    && (world.getBlock().getPosition().x > world.getBlocks().peek().getPosition().x + BLOCK_SIZE
//                    || world.getBlock().getPosition().x < world.getBlocks().peek().getPosition().x - BLOCK_SIZE)) {
//                //если не упал на предыдущий блок
//                world.getWorld().destroyBody(world.getBlock().getBody());
//                world.getBlock().setBody(null);
//                flagBloxx = false;
//            }
//        }

//    }

    /**
     * creates pendulum motion
     */
    private void moveCrane() {
        float cableAngele = world.getCable().getBody().getAngle();
        if (cableAngele < -0.5) {
            world.getRevoluteJointCC().setMotorSpeed(MOTORSPEED_OF_CABLE);
        } else if (cableAngele > 0.5) {
            world.getRevoluteJointCC().setMotorSpeed(-MOTORSPEED_OF_CABLE);
        }
    }

    /**
     *
     */
    private void checkBlock() {
        if (world.getBlock() != null) {
            float blockX = world.getBlock().getPosition().x;
            float blockY = world.getBlock().getPosition().y;
            if (world.getHouse().getCurrentBlock() == 0 &&
                    abs(blockX - world.getBuildingSite().getPosition().x)
                            > BUIlDING_SITE_SIZE
                    && blockY < world.getBuildingSite().getPosition().y + world.getCable().getHeight()) {
                flagChekBlock = true;
                world.getWorld().destroyBody(world.getBlock().getBody());
                world.setBlock(null);
            } else if (world.getHouse().getCurrentBlock() > 0) {
                Vector2 prevBlockPosition = world.getHouse().getBlocks().peek().getBody().getPosition();
                if (abs(blockX - prevBlockPosition.x) > BLOCK_SIZE
                        && blockY < prevBlockPosition.y) {
                    flagChekBlock = true;
                    world.getWorld().destroyBody(world.getBlock().getBody());
                    world.setBlock(null);
                } else
                    flagChekBlock = false;
            }
        }
    }

    /**
     * creates new block on cable
     */
    private void createBlockOnCable() {
        float angle = world.getCable().getBody().getAngle();
        if (abs(angle) >= 0.55) {
            if (world.getHouse().isFlagBloxx()) {
                moveCraneUp();
                cameraManager.setCameraFlag(true);
            }
            float hypotenuse = world.getCable().getHeight() / 2 + BLOCK_SIZE * 2;
            float x = (float) (hypotenuse * sin(angle));
            float y = (float) (hypotenuse * cos(angle));
            Vector2 blockCoord = new Vector2(world.getCable().getPosition().x + x,
                    world.getCable().getPosition().y - y);
            Block newBlock = new Block(world.getWorld(), world.getHouse().getCurrentBlock(),
                    world.getHouse().getMaxBlocks());
            newBlock.getBody().setTransform(blockCoord, angle);
            world.setBlock(newBlock);
            world.jointCableBlock(world.getCable(), world.getBlock());
            world.setFlagOfBlock(true);
            world.getHouse().setFlagBloxx(false);
        }
    }

    private void moveCraneUp() {
        float x = world.getCrane().getPosition().x;
        float y = world.getCrane().getPosition().y;
        world.getCrane().getBody().setTransform(x, y + BLOCK_SIZE * 2f, 0);
    }

}
