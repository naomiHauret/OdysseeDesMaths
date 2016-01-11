package com.odysseedesmaths.arriveeremarquable;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class ArriveeScreen implements Screen {
    private ArriveeGame game;

    private Sprite heros;

    private OrthographicCamera camera;

    public ArriveeScreen(ArriveeGame g) {
        game = g;

        heros = new Sprite(game.graphics.get("heros"));

        // Camera
        camera = new OrthographicCamera();
        camera.setToOrtho(false);
    }

    @Override
    public void show() {
        game.playMusic("musicTest");
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Affichage du terrain
        game.getTerrain().getRenderer().setView(camera);
        game.getTerrain().getRenderer().render();

        // Positionnement des entités
        heros.setPosition(game.getHeros().getCase().i * 64, game.getHeros().getCase().j * 64);

        // Affichage des entités
        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        heros.draw(game.batch);
        game.batch.end();

        // Centrage de la caméra sur le héros
        camera.position.set(heros.getX() + heros.getWidth()/2, heros.getY() + heros.getHeight()/2, 0);

        camera.update();
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
