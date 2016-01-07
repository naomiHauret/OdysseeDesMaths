package com.odysseedesmaths;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

public class ArriveeScreen implements Screen {
    private ArriveeGame game;

    private Sprite heros;
    private Array<Rectangle> cases;
    private Sprite arrow;

    private OrthographicCamera camera;

    public ArriveeScreen(ArriveeGame game) {
        this.game = game;

        // Images

        // Sons

        // Musique

        // Camera
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);

        // Physiques
        heros = new Sprite(game.graphics.get("heros"));
        heros.setPosition(800/2 - heros.getWidth()/2, 480/2 - heros.getHeight()/2);
    }

    @Override
    public void show() {
        game.playMusic("musicTest");
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();

        game.getTerrain().getRenderer().setView(camera);
        game.getTerrain().getRenderer().render();

        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        heros.draw(game.batch);
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

    }
}
