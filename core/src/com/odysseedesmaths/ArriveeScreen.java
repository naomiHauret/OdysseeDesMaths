package com.odysseedesmaths;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.odysseedesmaths.metier.Terrain;

public class ArriveeScreen implements Screen {
    final OdysseeGame game;

    private Sprite heros;
    private Array<Rectangle> cases;
    private Sprite arrow;

    private Texture herosImage;
    private Texture signeImage;
    private Texture arrowImage;
    private OrthographicCamera camera;

    public ArriveeScreen(OdysseeGame game) {
        this.game = game;

        // Images
        herosImage = new Texture(Gdx.files.internal("heros64.png"));
        signeImage = new Texture(Gdx.files.internal("signe64.png"));
        arrowImage = new Texture(Gdx.files.internal("arrow64.png"));
        arrow = new Sprite(arrowImage);

        // Sons


        // Musique


        // Camera
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);

        // Physiques
        heros = new Sprite(herosImage);
        heros.setPosition(800/2 - 64/2, 480/2 - 64/2);
    }


    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();

        game.getTerrain().renderer.setView(camera);
        game.getTerrain().renderer.render();

        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        heros.draw(game.batch);
        arrow.draw(game.batch); // UTILISER SCENE2D
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
