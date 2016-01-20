package com.odysseedesmaths.arriveeremarquable;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.odysseedesmaths.Timer;
import com.odysseedesmaths.UserInterface;
import com.odysseedesmaths.arriveeremarquable.entities.signes.Add;
import com.odysseedesmaths.arriveeremarquable.entities.signes.Div;
import com.odysseedesmaths.arriveeremarquable.entities.signes.Mult;
import com.odysseedesmaths.arriveeremarquable.entities.signes.Signe;
import com.odysseedesmaths.arriveeremarquable.entities.signes.Soust;
import com.odysseedesmaths.arriveeremarquable.map.Case;

import java.util.HashMap;
import java.util.Map;

public class ForetScreen implements Screen {

    private UserInterface ui;

    private Sprite herosSprite;
    private Map<String, Sprite> signesSprite;
    private Sprite bouclierSprite;

    private OrthographicCamera camera;

    public ForetScreen() {
        herosSprite = new Sprite(ArriveeGame.get().graphics.get("heros"));
        signesSprite = new HashMap<String, Sprite>();
        signesSprite.put("egal", new Sprite(ArriveeGame.get().graphics.get("signeEgal")));
        signesSprite.put("add", new Sprite(ArriveeGame.get().graphics.get("signeAdd")));
        signesSprite.put("soust", new Sprite(ArriveeGame.get().graphics.get("signeSoust")));
        signesSprite.put("mult", new Sprite(ArriveeGame.get().graphics.get("signeMult")));
        signesSprite.put("div", new Sprite(ArriveeGame.get().graphics.get("signeDiv")));
        bouclierSprite = new Sprite(ArriveeGame.get().graphics.get("bouclier"));

        ui = new UserInterface(ArriveeGame.get().heros.PDV_MAX, ArriveeGame.LIMITE_TEMPS * Timer.ONE_MINUTE, true, true);
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
        ui.timer.start();
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
            bouclierSprite.setPosition(herosSprite.getX() - 32, herosSprite.getY() - 32);
            bouclierSprite.draw(ArriveeGame.get().batch);
        }

        for (Signe s : ArriveeGame.get().signes) {
            Sprite signe;
            if (s instanceof Add) signe = signesSprite.get("add");
            else if (s instanceof Soust) signe = signesSprite.get("soust");
            else if (s instanceof Mult) signe = signesSprite.get("mult");
            else if (s instanceof Div) signe = signesSprite.get("div");
            else signe = signesSprite.get("egal");
            signe.setPosition(s.getCase().i * 64, s.getCase().j * 64);
            signe.draw(ArriveeGame.get().batch);
        }

        ArriveeGame.get().batch.end();

        // Centrage de la caméra sur le héros
        // S'il y a du blanc c'est normal c'est le hors map
        camera.position.set(herosSprite.getX() + herosSprite.getWidth()/2, herosSprite.getY() + herosSprite.getHeight()/2, 0);

        // Interface utilisateur
        ui.render();

        camera.update();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {
        ui.timer.stop();
    }

    @Override
    public void resume() {
        ui.timer.start();
    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        ui.dispose();
    }

    public static boolean isVisible(Case c) {
        boolean resW, resH;
        int width = Gdx.graphics.getWidth();
        int height = Gdx.graphics.getHeight();
        Case cHeros = ArriveeGame.get().heros.getCase();
        resW = Math.abs(c.i - cHeros.i) * 64 < width/2;
        resH = Math.abs(c.j - cHeros.j) * 64 < height/2;
        return resW && resH;
    }

    public static boolean isInHeroSight(Case c) {
        Case cHeros = ArriveeGame.get().heros.getCase();
        Case[][] cases = ArriveeGame.get().terrain.getCases();
        //TODO: A refaire avec un rectangle
        if (cHeros.i == c.i) {
            int i = c.i;
            for (int j = Math.min(cHeros.j, c.j); j < Math.max(cHeros.j, c.j); j++) {
                if (cases[i][j].isObstacle()) return false;
            }
        } else if (cHeros.j == c.j) {
            int j = c.j;
            for (int i = Math.min(cHeros.i, c.i); i < Math.max(cHeros.i, c.i); i++) {
                if (cases[i][j].isObstacle()) return false;
            }
        } else {
            return false;
        }

        return true;
    }

    private class InputEcouteur extends InputListener {

        @Override
        public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
            Actor source = event.getTarget();

            if (source == ui.padUp) {
                ArriveeGame.get().heros.moveUp();
            } else if (source == ui.padRight) {
                ArriveeGame.get().heros.moveRight();
            } else if (source == ui.padDown) {
                ArriveeGame.get().heros.moveDown();
            } else if (source == ui.padLeft) {
                ArriveeGame.get().heros.moveLeft();
            }

            ArriveeGame.get().playTurn();

            return true;
        }
    }
}
