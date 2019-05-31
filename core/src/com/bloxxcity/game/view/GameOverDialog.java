package com.bloxxcity.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

public class GameOverDialog {

    //dialog game over
    private Dialog dlgGameOver;
    private Skin skin;
    private Stage stage;
    private TextButton btnExit;
    private TextButton btnTryAgain;
    private GameScreen gameScreen;

    public GameOverDialog(final GameScreen gameScreen) {
        //create the game over dialog
        this.gameScreen = gameScreen;
        skin = new Skin(Gdx.files.internal("endGameSkin.json"));
        dlgGameOver = new Dialog(" ",skin);
        stage = new Stage();
        btnExit = new TextButton("Выйти", skin);
        btnTryAgain = new TextButton("Try Again", skin);
        dlgGameOver.text("Game over!");
        dlgGameOver.text("Этажей:" + gameScreen.getWorld().getHouse().getCurrentBlock());
        dlgGameOver.text("Жителей:" + gameScreen.getWorld().getHouse().getPeople());
        dlgGameOver.button("Заново");
        dlgGameOver.button("Выйти");
        dlgGameOver.show(stage);
        btnExit.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.app.exit();
                System.out.println("Button Pressed");
            }
        });
        btnTryAgain.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                gameScreen.getGame().setScreen(new HouseChoiceScreen(gameScreen.getGame()));
                System.out.println("Button Pressed");
            }
        });
        Gdx.input.setInputProcessor(stage);

    }

    public Stage getStage() {
        return stage;
    }

}
