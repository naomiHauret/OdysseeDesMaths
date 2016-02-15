package com.odysseedesmaths;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.odysseedesmaths.scenes.Scene;
import com.odysseedesmaths.scenes.Scene0;
import com.odysseedesmaths.scenes.Scene1;
import com.odysseedesmaths.scenes.Scene2;

public class ModeSceneScreen implements Screen {

    private OdysseeDesMaths jeu;

    private Scene sceneActive;
    private Scene0 scene0 = null;
    private Scene1 scene1 = null;
    private Scene2 scene2 = null;

    private SpriteBatch batch;

    public ModeSceneScreen(OdysseeDesMaths game) {
        jeu = game;
        Scene.updateMss(this);
        sceneActive = getScene1(); // selectionner la bonne plus tard dans le fichier de sauvegarde
        batch = new SpriteBatch();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        batch.begin();
        batch.draw(sceneActive.getBackground(), 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.end();
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

    }

    @Override
    public void dispose() {

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
}
