package com.odysseedesmaths.minigames.accrobranche;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.odysseedesmaths.Assets;
import com.odysseedesmaths.Musique;
import com.odysseedesmaths.menus.MenuGameOver;
import com.odysseedesmaths.menus.MenuPause;
import com.odysseedesmaths.minigames.MiniGame;
import com.odysseedesmaths.minigames.MiniGameUI;

public class TreeScreen implements Screen {

    public static final int WIDTH = 800;
    public static final int HEIGHT = 480;
    public static final int DELTA = 300;
    public static final int CELL_SIZE = 64;

    private final Accrobranche minigame;

    private Viewport viewport;
    private OrthographicCamera camera;
    private Batch batch;

    private MiniGameUI ui;
    private MenuPause menuPause;
    private MenuGameOver menuGameOver;

    public TreeScreen(final Accrobranche minigame) {
        this.minigame = minigame;

        camera = new OrthographicCamera();
        viewport = new StretchViewport(WIDTH, HEIGHT, camera);
        batch = new SpriteBatch();
        ui = new MiniGameUI();

        // UI
        ui.addTimer(minigame.timer);
        ui.addPad();
        Gdx.input.setInputProcessor(ui);
        ui.setListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                Actor source = event.getTarget();
                boolean isTurnPassed = true;

                if (source == ui.getPadUp()) {
                    //minigame.hero.moveUp();
                } else if (source == ui.getPadRight()) {
                    //minigame.hero.moveRight();
                } else if (source == ui.getPadDown()) {
                    //minigame.hero.moveDown();
                } else if (source == ui.getPadLeft()) {
                    //minigame.hero.moveLeft();
                } else if (source == ui.getPause()) {
                    minigame.pauseGame();
                    Gdx.input.setInputProcessor(menuPause);
                    //isTurnPassed = false;
                }

                //if (isTurnPassed) minigame.playTurn();
            }
        });

        menuPause = new MenuPause();
        menuPause.setListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                Actor source = event.getTarget();

                if (source == menuPause.getRetourJeu().getLabel()) {
                    minigame.returnToGame();
                    Gdx.input.setInputProcessor(ui);
                } else if (source == menuPause.getRecommencer().getLabel()) {
                    minigame.restartGame();
                } else if (source == menuPause.getQuitter().getLabel()) {
                    minigame.returnToMainMenu();
                }
            }
        });

        menuGameOver = new MenuGameOver();
        menuGameOver.setListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                Actor source = event.getTarget();

                if (source == menuGameOver.getRetry().getLabel()) {
                    minigame.restartGame();
                } else if (source == menuGameOver.getReturnMainMenu().getLabel()) {
                    minigame.returnToMainMenu();
                }
            }
        });

    }

    @Override
    public void show() {
        Musique.setCurrent(Assets.ARCADE);
        Musique.play();
        minigame.timer.start();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    @Override
    public void render(float delta) {
        // Effaçage du précédent affichage
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Interface utilisateur par dessus le reste
        ui.render();

        switch (minigame.getState()) {
            case RUNNING:
                if (minigame.timer.isFinished()) minigame.gameOver();
                break;
            case PAUSED:
                menuPause.draw();
                break;
            case GAME_OVER:
                menuGameOver.render();
                break;
            default:
                // Erreur état du jeu
                break;
        }

        camera.update();
    }

    @Override
    public void pause() {
        if (minigame.getState() == MiniGame.State.RUNNING) {
            minigame.pauseGame();
            Gdx.input.setInputProcessor(menuPause);
        }
    }

    @Override
    public void resume() {
        // Le jeu est en pause
    }

    @Override
    public void hide() {
        Musique.pause();
    }

    @Override
    public void dispose() {
        ui.dispose();
        menuPause.dispose();
        menuGameOver.dispose();
    }

    public void gameOver() {
        Gdx.input.setInputProcessor(menuGameOver);
        menuGameOver.playMusic();
    }
}