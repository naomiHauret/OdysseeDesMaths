package com.odysseedesmaths;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

/**
 * Created by Allan on 03/01/2016.
 */
public class ArriveeScreen implements Screen {
    final OdysseeGame game;

    private Rectangle heros;

    private Texture herosImage;
    private Texture signe;
    private Texture chemin;
    private Texture obstacle;
    private OrthographicCamera camera;

    public ArriveeScreen(OdysseeGame game) {
        this.game = game;

        // Images
        herosImage = new Texture(Gdx.files.internal("heros.png"));
        chemin = new Texture(Gdx.files.internal("chemin.png"));
        obstacle = new Texture(Gdx.files.internal("arbre.png"));

        // Sons


        // Musique


        // Camera
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);

        // Physiques
        heros = new Rectangle();
        heros.width = 32;
        heros.height = 32;
        heros.x = 800/2 - heros.width/2;
        heros.y = 480/2 - heros.height/2;
    }


    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();

        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();
        game.batch.draw(herosImage, heros.x, heros.y);
        game.batch.end();
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
        herosImage.dispose();
    }
}
