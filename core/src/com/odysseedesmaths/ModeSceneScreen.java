package com.odysseedesmaths;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton.ImageButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
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

    private OdysseeDesMaths jeu;

    public enum State {RUNNING, PAUSED}
    protected State currentState;

    private Scene sceneActive;
    private Scene0 scene0 = null;
    private Scene1 scene1 = null;
    private Scene2 scene2 = null;

    private Stage stage;

    private Table table;
    private Skin skin;

    private Container<Actor> pauseContainer;
    private ImageButton pause;
    private MenuPauseScene menuPause;

    public ModeSceneScreen(OdysseeDesMaths game) {
        jeu = game;
        Scene.updateMss(this);
        sceneActive = getScene1(); // selectionner la bonne plus tard dans le fichier de sauvegarde


        setState(State.RUNNING);
        createUI();

    }

    public void createUI() {
        stage = new Stage();
        table = new Table();
        skin = new Skin();
        skin.addRegions(Assets.getManager().get(Assets.UI_MAIN, TextureAtlas.class));
        skin.addRegions(Assets.getManager().get(Assets.UI_ORANGE, TextureAtlas.class));

        // Creation du bouton de pause
        ImageButtonStyle pauseStyle = new ImageButtonStyle();
        pauseStyle.up = skin.getDrawable("pause");
        pauseStyle.imageUp = skin.getDrawable("button");
        pauseStyle.imageDown = skin.getDrawable("button_pressed");
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
        table.background(new SpriteDrawable(new Sprite(sceneActive.getBackground())));
        table.add(pause).expand().right().top();

        stage.addActor(table);
        Gdx.input.setInputProcessor(stage);
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
        // cette méthode n'est pas nécessaire ici
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
        Gdx.input.setInputProcessor(stage);
        setState(State.RUNNING);
    }

    public void pauseScene() {
        Gdx.input.setInputProcessor(menuPause);
        setState(State.PAUSED);
    }

    public void switchScene(Scene s) {
        sceneActive = s;
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