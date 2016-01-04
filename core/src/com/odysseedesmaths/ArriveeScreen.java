package com.odysseedesmaths;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

public class ArriveeScreen implements Screen {
    final OdysseeGame game;

    private Rectangle heros;

    private Texture herosImage;
    private Texture signeImage;
    private Texture cheminImage;
    private Texture obstacleImage;
    private OrthographicCamera camera;

    public ArriveeScreen(OdysseeGame game) {
        this.game = game;

        // Images
        herosImage = new Texture(Gdx.files.internal("heros64.png"));
        cheminImage = new Texture(Gdx.files.internal("chemin.png"));
        obstacleImage = new Texture(Gdx.files.internal("arbre.png"));
        signeImage = new Texture(Gdx.files.internal("signe.png"));

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
        Gdx.gl.glClearColor(255, 255, 255, 1);
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
