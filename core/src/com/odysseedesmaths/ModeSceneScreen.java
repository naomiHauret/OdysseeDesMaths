package com.odysseedesmaths;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
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

    private Stage stage;

    private Table table;
    private Skin skin;

    private Container<Actor> pauseContainer;
    private Button pause;

    public ModeSceneScreen(OdysseeDesMaths game) {
        jeu = game;
        Scene.updateMss(this);
        sceneActive = getScene1(); // selectionner la bonne plus tard dans le fichier de sauvegarde

        createUI();

    }

    public void createUI() {
        stage = new Stage();
        table = new Table();
        skin = new Skin();
        skin.addRegions(Assets.getManager().get(Assets.UI, TextureAtlas.class));

        // Creation du bouton de pause
        Button.ButtonStyle pauseStyle = new Button.ButtonStyle();
        pauseStyle.up = skin.getDrawable("pause");
        pauseStyle.down = skin.getDrawable("pauseTap");
        skin.add("pause", pauseStyle);

        pause = new Button(skin, "pause");

        // Création de la table
        table.setFillParent(true);
        table.background(new SpriteDrawable(new Sprite(sceneActive.getBackground())));
        table.add(pause).expand().right().top();

        stage.addActor(table);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.draw();
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