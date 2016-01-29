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
import com.odysseedesmaths.arriveeremarquable.entities.items.Item;
import com.odysseedesmaths.arriveeremarquable.entities.items.Shield;
import com.odysseedesmaths.arriveeremarquable.map.Case;

import java.util.HashMap;
import java.util.Iterator;
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
        heroSprite = new Sprite(ArriveeGame.get().getGraphics().get("hero"));
        heroSprite.setPosition(ArriveeGame.get().hero.getCase().i * CELL_SIZE, ArriveeGame.get().hero.getCase().j * CELL_SIZE);

        entitiesSprites = new HashMap<Entity, Sprite>();

        buffsSprites = new HashMap<Class<? extends Item>, Sprite>();
        buffsSprites.put(Shield.class, new Sprite(ArriveeGame.get().getGraphics().get("buffShield")));

        hordeSprite = new Sprite(ArriveeGame.get().getGraphics().get("horde"));

        ui = new UserInterface(Hero.PDV_MAX, ArriveeGame.TIME_LIMIT * Timer.ONE_MINUTE, true);
        Gdx.input.setInputProcessor(ui);
        InputEcouteur ecouteur = new InputEcouteur();
        ui.getPadUp().addListener(ecouteur);
        ui.getPadRight().addListener(ecouteur);
        ui.getPadDown().addListener(ecouteur);
        ui.getPadLeft().addListener(ecouteur);

        // Camera
        camera = new OrthographicCamera();
        viewport = new StretchViewport(WIDTH, HEIGHT, camera);
    }

    @Override
    public void show() {
        ArriveeGame.get().playMusic("musicTest");
        ui.getTimer().start();
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

        for (Enemy enemy : ArriveeGame.get().enemies) {
            Sprite enemySprite = entitiesSprites.get(enemy);
            if (enemySprite == null) {
                enemySprite = getNewSpriteFor(enemy);
                entitiesSprites.put(enemy, enemySprite);
            }
            updatePos(enemySprite, enemy);
            enemySprite.draw(ArriveeGame.get().batch);
        }

        Iterator<Enemy> deadIterator = ArriveeGame.get().deadpool.iterator();
        while (deadIterator.hasNext()) {
            Enemy deadEnemy = deadIterator.next();
            Sprite deadSprite = entitiesSprites.get(deadEnemy);
            if (updatePos(deadSprite, deadEnemy)) {
                deadIterator.remove();
                entitiesSprites.remove(deadEnemy);
            }
            deadSprite.draw(ArriveeGame.get().batch);
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

        // Affichage du héros
        updatePos(heroSprite, ArriveeGame.get().hero);
        heroSprite.draw(ArriveeGame.get().batch);

        if (ArriveeGame.get().activeItems.get(Shield.class) != null) {
            buffsSprites.get(Shield.class).setPosition(heroSprite.getX(), heroSprite.getY());
            buffsSprites.get(Shield.class).draw(ArriveeGame.get().batch);
        }

        // Affichage de la horde
        if (ArriveeGame.get().horde.getFront() >= 0) {
            for (int i = 0; i <= ArriveeGame.get().horde.getFront(); i++) {
                for (int j = 0; j < ArriveeGame.get().terrain.getHeight(); j++) {
                    hordeSprite.setPosition(i * CELL_SIZE, j * CELL_SIZE);
                    hordeSprite.draw(ArriveeGame.get().batch);
                }
            }
        }

        ArriveeGame.get().batch.end();

        // Positionnement de la caméra (sur le héros ou sur les bords)
        float posX, posY, minX, minY, maxX, maxY;
        posX = heroSprite.getX() + heroSprite.getWidth()/2;
        posY = heroSprite.getY() + heroSprite.getHeight()/2;
        minX = (float)WIDTH/2;
        minY = (float)HEIGHT/2;
        maxX = ArriveeGame.get().terrain.getWidth() * CELL_SIZE - WIDTH/2;
        maxY = ArriveeGame.get().terrain.getHeight() * CELL_SIZE - HEIGHT/2;
        if (posX < minX) posX = minX;
        else if (posX > maxX) posX = maxX;
        if (posY < minY) posY = minY;
        else if (posY > maxY) posY = maxY;
        camera.position.set(posX, posY, 0);

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
        ui.getTimer().stop();
    }

    @Override
    public void resume() {
        ui.getTimer().start();
    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        ui.dispose();
    }

    private boolean updatePos(Sprite aSprite, Entity aEntity) {
        float targetX, targetY, newX, newY;

        targetX = aEntity.getCase().i * CELL_SIZE;
        if (targetX > aSprite.getX()) {
            newX = Math.min(aSprite.getX() + DELTA * Gdx.graphics.getDeltaTime(), targetX);
        } else {
            newX = Math.max(aSprite.getX() - DELTA * Gdx.graphics.getDeltaTime(), targetX);
        }

        targetY = aEntity.getCase().j * CELL_SIZE;
        if (targetY > aSprite.getY()) {
            newY = Math.min(aSprite.getY() + DELTA * Gdx.graphics.getDeltaTime(), targetY);
        } else {
            newY = Math.max(aSprite.getY() - DELTA * Gdx.graphics.getDeltaTime(), targetY);
        }

        aSprite.setPosition(newX, newY);
        return (newX == targetX) && (newY == targetY);
    }

    // Temporaire
    private Sprite getNewSpriteFor(Entity aEntity) {
        Sprite sprite;

        if (aEntity instanceof Enemy) {
            if (aEntity instanceof Sticky) sprite = new Sprite(ArriveeGame.get().getGraphics().get("signeEgal"));
            else if (aEntity instanceof Smart) sprite = new Sprite(ArriveeGame.get().getGraphics().get("signeAdd"));
            else if (aEntity instanceof Greed) sprite = new Sprite(ArriveeGame.get().getGraphics().get("signeMult"));
            else if (aEntity instanceof SuperSmart) sprite = new Sprite(ArriveeGame.get().getGraphics().get("signeDiv"));
            else sprite = new Sprite(ArriveeGame.get().getGraphics().get("signeSoust"));
        } else {// c'est un item, pas d'appel de cette méthode sur le héros
            if (aEntity instanceof Shield) sprite = new Sprite(ArriveeGame.get().getGraphics().get("shield"));
            else sprite = new Sprite(ArriveeGame.get().getGraphics().get("heart"));
        }

        sprite.setPosition(aEntity.getCase().i * CELL_SIZE, aEntity.getCase().j * CELL_SIZE);

        return sprite;
    }

    private class InputEcouteur extends InputListener {

        @Override
        public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
            Actor source = event.getTarget();

            if (source == ui.getPadUp()) {
                ArriveeGame.get().hero.moveUp();
            } else if (source == ui.getPadRight()) {
                ArriveeGame.get().hero.moveRight();
            } else if (source == ui.getPadDown()) {
                ArriveeGame.get().hero.moveDown();
            } else if (source == ui.getPadLeft()) {
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
        Case cHeros = ArriveeGame.get().hero.getCase();
        resW = Math.abs(c.i - cHeros.i) * CELL_SIZE < WIDTH/2;
        resH = Math.abs(c.j - cHeros.j) * CELL_SIZE < HEIGHT/2;
        return resW && resH;
    }

    public static boolean isInHeroSight(Case c) {
        Case cHeros = ArriveeGame.get().hero.getCase();
        Case[][] cases = ArriveeGame.get().terrain.getCases();
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
