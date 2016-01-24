package com.odysseedesmaths.arriveeremarquable;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.odysseedesmaths.Timer;
import com.odysseedesmaths.UserInterface;
import com.odysseedesmaths.arriveeremarquable.entities.ennemies.Greed;
import com.odysseedesmaths.arriveeremarquable.entities.ennemies.SuperSmart;
import com.odysseedesmaths.arriveeremarquable.entities.ennemies.Sticky;
import com.odysseedesmaths.arriveeremarquable.entities.ennemies.Enemy;
import com.odysseedesmaths.arriveeremarquable.entities.ennemies.Smart;
import com.odysseedesmaths.arriveeremarquable.entities.ennemies.Lost;
import com.odysseedesmaths.arriveeremarquable.entities.items.Heart;
import com.odysseedesmaths.arriveeremarquable.entities.items.Item;
import com.odysseedesmaths.arriveeremarquable.entities.items.Shield;
import com.odysseedesmaths.arriveeremarquable.map.Case;

import java.util.HashMap;
import java.util.Map;

public class ForetScreen implements Screen {

    private Viewport viewport;
    private OrthographicCamera camera;

    private UserInterface ui;

    private Sprite herosSprite;
    private Sprite hordeSprite;
    private Map<Class<? extends Enemy>, Sprite> enemiesSprite;
    private Map<Class<? extends Item>, Sprite> itemsSprite;

    public ForetScreen() {
        herosSprite = new Sprite(ArriveeGame.get().graphics.get("hero"));
        hordeSprite = new Sprite(ArriveeGame.get().graphics.get("horde"));

        enemiesSprite = new HashMap<Class<? extends Enemy>, Sprite>();
        enemiesSprite.put(Sticky.class, new Sprite(ArriveeGame.get().graphics.get("signeEgal")));
        enemiesSprite.put(Greed.class, new Sprite(ArriveeGame.get().graphics.get("signeAdd")));
        enemiesSprite.put(Lost.class, new Sprite(ArriveeGame.get().graphics.get("signeSoust")));
        enemiesSprite.put(Smart.class, new Sprite(ArriveeGame.get().graphics.get("signeMult")));
        enemiesSprite.put(SuperSmart.class, new Sprite(ArriveeGame.get().graphics.get("signeDiv")));

        itemsSprite = new HashMap<Class<? extends Item>, Sprite>();
        itemsSprite.put(Shield.class, new Sprite(ArriveeGame.get().graphics.get("bouclier")));
        itemsSprite.put(Heart.class, new Sprite(ArriveeGame.get().graphics.get("heart")));

        ui = new UserInterface(ArriveeGame.get().hero.PDV_MAX, ArriveeGame.LIMITE_TEMPS * Timer.ONE_MINUTE, true, true);
        Gdx.input.setInputProcessor(ui);
        InputEcouteur ecouteur = new InputEcouteur();
        ui.padUp.addListener(ecouteur);
        ui.padRight.addListener(ecouteur);
        ui.padDown.addListener(ecouteur);
        ui.padLeft.addListener(ecouteur);

        // Camera
        camera = new OrthographicCamera();
        viewport = new StretchViewport(WIDTH, HEIGHT, camera);
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

        herosSprite.setPosition(ArriveeGame.get().hero.getCase().i * 64, ArriveeGame.get().hero.getCase().j * 64);
        herosSprite.draw(ArriveeGame.get().batch);

        if (ArriveeGame.get().activeItems.get(Shield.class) != null) {
            itemsSprite.get(Shield.class).setPosition(herosSprite.getX() - 32, herosSprite.getY() - 32);
            itemsSprite.get(Shield.class).draw(ArriveeGame.get().batch);
        }

        for (Enemy e : ArriveeGame.get().enemies) {
            Sprite enemy = enemiesSprite.get(e.getClass());
            enemy.setPosition(e.getCase().i * 64, e.getCase().j * 64);
            enemy.draw(ArriveeGame.get().batch);
        }

        for (Item it : ArriveeGame.get().items) {
            Sprite item = itemsSprite.get(it.getClass());
            item.setPosition(it.getCase().i * 64, it.getCase().j * 64);
            item.draw(ArriveeGame.get().batch);
        }

        // Affichage de la horde
        if (ArriveeGame.get().horde.getFront() >= 0) {
            for (int i = 0; i <= ArriveeGame.get().horde.getFront(); i++) {
                for (int j = 0; j < ArriveeGame.get().terrain.getHeight(); j++) {
                    hordeSprite.setPosition(i * 64, j * 64);
                    hordeSprite.draw(ArriveeGame.get().batch);
                }
            }
        }

        ArriveeGame.get().batch.end();

        // Centrage de la caméra sur le héros
        camera.position.set(herosSprite.getX() + herosSprite.getWidth()/2, herosSprite.getY() + herosSprite.getHeight()/2, 0);

        // Interface utilisateur par dessus le reste
        ui.render();

        camera.update();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
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

    private class InputEcouteur extends InputListener {

        @Override
        public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
            Actor source = event.getTarget();

            if (source == ui.padUp) {
                ArriveeGame.get().hero.moveUp();
            } else if (source == ui.padRight) {
                ArriveeGame.get().hero.moveRight();
            } else if (source == ui.padDown) {
                ArriveeGame.get().hero.moveDown();
            } else if (source == ui.padLeft) {
                ArriveeGame.get().hero.moveLeft();
            }

            ArriveeGame.get().playTurn();

            return true;
        }
    }


    /**********
     * STATIC *
     **********/

    public static final int WIDTH = 800;
    public static final int HEIGHT = 480;

    public static boolean isVisible(Case c) {
        boolean resW, resH;
        int width = Gdx.graphics.getWidth();
        int height = Gdx.graphics.getHeight();
        Case cHeros = ArriveeGame.get().hero.getCase();
        resW = Math.abs(c.i - cHeros.i) * 64 < width/2;
        resH = Math.abs(c.j - cHeros.j) * 64 < height/2;
        return resW && resH;
    }

    public static boolean isInHeroSight(Case c) {
        Case cHeros = ArriveeGame.get().hero.getCase();
        Case[][] cases = ArriveeGame.get().terrain.getCases();
        //TODO: A refaire avec un rectangle (?)
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
}
