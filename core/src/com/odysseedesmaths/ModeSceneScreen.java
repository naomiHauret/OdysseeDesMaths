package com.odysseedesmaths;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton.ImageButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.odysseedesmaths.menus.MenuPauseScene;
import com.odysseedesmaths.menus.MenuPrincipal;
import com.odysseedesmaths.scenes.Scene;
import com.odysseedesmaths.scenes.Scene0;
import com.odysseedesmaths.scenes.Scene1;
import com.odysseedesmaths.scenes.Scene2;
/*
        Classe type de l'affichage écran de la scène
 */

public class ModeSceneScreen implements Screen {

    private static final int WIDTH = 800;
    private static final int HEIGHT = 480;

    private OdysseeDesMaths jeu;

    public enum State {RUNNING, PAUSED}
    protected State currentState;

    private Scene sceneActive;
    private Scene0 scene0 = null;
    private Scene1 scene1 = null;
    private Scene2 scene2 = null;

    private Stage stage;
    private Viewport viewport;
    private InputMultiplexer multiplexer;

    private Table table;
    private Skin skin;

    private ImageButton pause;
    private MenuPauseScene menuPause;

    public ModeSceneScreen(OdysseeDesMaths game) {
        jeu = game;
        Scene.updateMss(this);
        if (game.getSavesManager().getCurrentSave().isLevel3Finished()) {
            sceneActive = getScene2();
        } else if (game.getSavesManager().getCurrentSave().isLevel2Finished()) {
            sceneActive = getScene2();
        } else if (game.getSavesManager().getCurrentSave().isLevel1Finished()) {
            sceneActive = getScene2();
        } else if (game.getSavesManager().getCurrentSave().isPrologueFinished()){
            sceneActive = getScene1();
        } else {
            sceneActive = getScene0();
        }

        setState(State.RUNNING);
        createUI();
    }

    public void createUI() {
        viewport = new StretchViewport(WIDTH, HEIGHT);
        stage = new Stage(viewport);
        table = new Table();
        skin = new Skin();
        skin.addRegions(Assets.getManager().get(Assets.UI_MAIN, TextureAtlas.class));
        skin.addRegions(Assets.getManager().get(Assets.UI_ORANGE, TextureAtlas.class));

        // Creation du bouton de pause
        ImageButtonStyle pauseStyle = new ImageButtonStyle();
        pauseStyle.up = skin.getDrawable("button");
        pauseStyle.imageUp = skin.getDrawable("pause");
        pauseStyle.down = skin.getDrawable("button_pressed");
        skin.add("pause", pauseStyle);

        pause = new ImageButton(skin, "pause");
        pause.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                pauseScene();
            }
        });

        menuPause = new MenuPauseScene();
        menuPause.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                Actor source = event.getTarget();

                if (source == menuPause.getRetourJeu().getLabel()) {
                    returnToScene();
                } else if (source == menuPause.getInventaire().getLabel()) {
                    // TODO
                } else if (source == menuPause.getQuitter().getLabel()) {
                    jeu.setScreen(new MenuPrincipal(jeu));
                }
            }
        });

        // Création de la table
        table.setFillParent(true);
        table.pad(10);
        table.add(pause).expand().right().top().size(64, 64);
        updateBackground();

        stage.addActor(table);

        multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(stage);
        multiplexer.addProcessor(new InputAdapter() {
            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                return true;
            }

            @Override
            public boolean touchUp(int screenX, int screenY, int pointer, int button) {
                sceneActive.aventure();
                return true;
            }
        });
        Gdx.input.setInputProcessor(multiplexer);
    }

    @Override
    public void show() {
        // cette méthode n'est pas nécessaire ici
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.draw();

        switch (getState()) {
            case RUNNING:
                break;
            case PAUSED:
                menuPause.draw();
                break;
            default:
                // Erreur état du jeu
                break;
        }
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    @Override
    public void pause() {
        // cette méthode n'est pas nécessaire ici
    }

    @Override
    public void resume() {
        // cette méthode n'est pas nécessaire ici
    }

    @Override
    public void hide() {
        // cette méthode n'est pas nécessaire ici
    }

    @Override
    public void dispose() {
        // cette méthode n'est pas nécessaire ici
    }

    public void returnToScene() {
        Gdx.input.setInputProcessor(multiplexer);
        setState(State.RUNNING);
    }

    public void pauseScene() {
        Gdx.input.setInputProcessor(menuPause);
        setState(State.PAUSED);
    }

    /*
      permet le changement de scène durant l'avancement du jeu
    */
    public void switchScene(Scene s) {
        sceneActive = s;
        updateBackground();
    }

    public void updateBackground() {
        table.background(new SpriteDrawable(new Sprite(sceneActive.getBackground())));
    }

    public Scene0 getScene0() {
        if (scene0 == null) {
            scene0 = new Scene0();
        }
        return scene0;
    }

    public Scene1 getScene1() {
        if (scene1 == null) {
            scene1 = new Scene1();
        }
        return scene1;
    }

    public Scene2 getScene2() {
        if (scene2 == null) {
            scene2 = new Scene2();
        }
        return scene2;
    }

    public OdysseeDesMaths getJeu() {
        return jeu;
    }

    public State getState() {
        return currentState;
    }

    public void setState(State newState) { currentState = newState; }
}
