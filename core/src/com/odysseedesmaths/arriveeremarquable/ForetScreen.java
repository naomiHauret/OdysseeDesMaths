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
import com.odysseedesmaths.arriveeremarquable.entities.Entity;
import com.odysseedesmaths.arriveeremarquable.entities.Hero;
import com.odysseedesmaths.arriveeremarquable.entities.ennemies.Greed;
import com.odysseedesmaths.arriveeremarquable.entities.ennemies.SuperSmart;
import com.odysseedesmaths.arriveeremarquable.entities.ennemies.Sticky;
import com.odysseedesmaths.arriveeremarquable.entities.ennemies.Enemy;
import com.odysseedesmaths.arriveeremarquable.entities.ennemies.Smart;
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

    private Sprite heroSprite;
    private Map<Entity, Sprite> entitiesSprites;
    private Map<Class<? extends Item>, Sprite> buffsSprites;
    private Sprite hordeSprite;

    public ForetScreen() {
        heroSprite = new Sprite(ArriveeGame.get().graphics.get("hero"));
        heroSprite.setPosition(ArriveeGame.get().hero.getCase().i * CELL_SIZE, ArriveeGame.get().hero.getCase().j * CELL_SIZE);

        entitiesSprites = new HashMap<Entity, Sprite>();

        buffsSprites = new HashMap<Class<? extends Item>, Sprite>();
        buffsSprites.put(Shield.class, new Sprite(ArriveeGame.get().graphics.get("shield")));

        hordeSprite = new Sprite(ArriveeGame.get().graphics.get("horde"));

        ui = new UserInterface(Hero.PDV_MAX, ArriveeGame.LIMITE_TEMPS * Timer.ONE_MINUTE, true, true);
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

        updatePos(heroSprite, ArriveeGame.get().hero);
        heroSprite.draw(ArriveeGame.get().batch);

        if (ArriveeGame.get().activeItems.get(Shield.class) != null) {
            buffsSprites.get(Shield.class).setPosition(heroSprite.getX() - 32, heroSprite.getY() - 32);
            buffsSprites.get(Shield.class).draw(ArriveeGame.get().batch);
        }

        for (Enemy enemy : ArriveeGame.get().enemies) {
            Sprite enemySprite = entitiesSprites.get(enemy);
            if (enemySprite == null) {
                enemySprite = getNewSpriteFor(enemy);
                entitiesSprites.put(enemy, enemySprite);
            }
            updatePos(enemySprite, enemy);
            enemySprite.draw(ArriveeGame.get().batch);
        }

        for (Item item : ArriveeGame.get().items) {
            Sprite itemSprite = entitiesSprites.get(item);
            if (itemSprite == null) {
                itemSprite = getNewSpriteFor(item);
                entitiesSprites.put(item, itemSprite);
            }
            updatePos(itemSprite, item);
            itemSprite.draw(ArriveeGame.get().batch);
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
        camera.position.set(heroSprite.getX() + heroSprite.getWidth()/2, heroSprite.getY() + heroSprite.getHeight()/2, 0);

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

    private void updatePos(Sprite aSprite, Entity aEntity) {
        float targetX, targetY, newX, newY;

        targetX = aEntity.getCase().i * CELL_SIZE;
        if (targetX > aSprite.getX()) newX = Math.min(aSprite.getX() + DELTA * Gdx.graphics.getDeltaTime(), targetX);
        else if (targetX < aSprite.getX()) newX = Math.max(aSprite.getX() - DELTA * Gdx.graphics.getDeltaTime(), targetX);
        else newX = targetX;

        targetY = aEntity.getCase().j * CELL_SIZE;
        if (targetY > aSprite.getY()) newY = Math.min(aSprite.getY() + DELTA * Gdx.graphics.getDeltaTime(), targetY);
        else if (targetY < aSprite.getY()) newY = Math.max(aSprite.getY() - DELTA * Gdx.graphics.getDeltaTime(), targetY);
        else newY = targetY;

        aSprite.setPosition(newX, newY);
    }

    // Temporaire
    private Sprite getNewSpriteFor(Entity aEntity) {
        Sprite sprite;

        if (aEntity instanceof Enemy) {
            if (aEntity instanceof Sticky) sprite = new Sprite(ArriveeGame.get().graphics.get("signeEgal"));
            else if (aEntity instanceof Smart) sprite = new Sprite(ArriveeGame.get().graphics.get("signeAdd"));
            else if (aEntity instanceof Greed) sprite = new Sprite(ArriveeGame.get().graphics.get("signeMult"));
            else if (aEntity instanceof SuperSmart) sprite = new Sprite(ArriveeGame.get().graphics.get("signeDiv"));
            else sprite = new Sprite(ArriveeGame.get().graphics.get("signeSoust"));
        } else {// c'est un item, pas d'appel de cette méthode sur le héros
            if (aEntity instanceof Shield) sprite = new Sprite(ArriveeGame.get().graphics.get("shield"));
            else sprite = new Sprite(ArriveeGame.get().graphics.get("heart"));
        }

        sprite.setPosition(aEntity.getCase().i * CELL_SIZE, aEntity.getCase().j * CELL_SIZE);

        return sprite;
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
    public static final int DELTA = 150;
    public static final int CELL_SIZE = 64;

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
