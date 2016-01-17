package com.odysseedesmaths.arriveeremarquable;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.odysseedesmaths.UserInterface;
import com.odysseedesmaths.arriveeremarquable.entities.signes.Signe;

public class ArriveeScreen implements Screen {

    private UserInterface ui;

    private Sprite herosSprite;
    private Sprite signeSprite;
    private Sprite bouclierSprite;

    private OrthographicCamera camera;

    public ArriveeScreen() {
        herosSprite = new Sprite(ArriveeGame.get().graphics.get("heros"));
        signeSprite = new Sprite(ArriveeGame.get().graphics.get("signe"));
        bouclierSprite = new Sprite(ArriveeGame.get().graphics.get("bouclier"));

        ui = new UserInterface();
        Gdx.input.setInputProcessor(ui);
        InputEcouteur ecouteur = new InputEcouteur();
        ui.padUp.addListener(ecouteur);
        ui.padRight.addListener(ecouteur);
        ui.padDown.addListener(ecouteur);
        ui.padLeft.addListener(ecouteur);

        // Camera
        camera = new OrthographicCamera();
        camera.setToOrtho(false);
    }

    @Override
    public void show() {
        ArriveeGame.get().playMusic("musicTest");
    }

    @Override
    public void render(float delta) {
        // Effaçage du précédent affichage
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Affichage du terrain
        ArriveeGame.get().terrain.renderer.setView(camera);
        ArriveeGame.get().terrain.renderer.render();

        // Affichage des entités
        ArriveeGame.get().batch.setProjectionMatrix(camera.combined);
        ArriveeGame.get().batch.begin();

        herosSprite.setPosition(ArriveeGame.get().heros.getCase().i * 64, ArriveeGame.get().heros.getCase().j * 64);
        herosSprite.draw(ArriveeGame.get().batch);

        if (ArriveeGame.get().activeItems.get("pi") != null) {
            bouclierSprite.setPosition(herosSprite.getX()-64, herosSprite.getY()-64);
            bouclierSprite.draw(ArriveeGame.get().batch);
        }

        for (Signe s : ArriveeGame.get().signes) {
            signeSprite.setPosition(s.getCase().i * 64, s.getCase().j * 64);
            signeSprite.draw(ArriveeGame.get().batch);
        }

        ArriveeGame.get().batch.end();

        // Centrage de la caméra sur le héros
        // S'il y a du blanc c'est normal c'est le hors map
        camera.position.set(herosSprite.getX() + herosSprite.getWidth() / 2, herosSprite.getY() + herosSprite.getHeight()/2, 0);

        // Interface utilisateur
        ui.render();

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
        ui.dispose();
    }


    private class InputEcouteur extends InputListener {

        @Override
        public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
            Actor source = event.getTarget();

            if (source == ui.padUp) {
                ArriveeGame.get().heros.move(0,1);
            } else if (source == ui.padRight) {
                ArriveeGame.get().heros.move(1,0);
            } else if (source == ui.padDown) {
                ArriveeGame.get().heros.move(0,-1);
            } else if (source == ui.padLeft) {
                ArriveeGame.get().heros.move(-1,0);
            }

            ArriveeGame.get().playTurn();

            return true;
        }
    }
}
