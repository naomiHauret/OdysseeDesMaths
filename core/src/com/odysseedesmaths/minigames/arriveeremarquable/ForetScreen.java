package com.odysseedesmaths.minigames.arriveeremarquable;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.odysseedesmaths.Assets;
import com.odysseedesmaths.Musique;
import com.odysseedesmaths.Timer;
import com.odysseedesmaths.minigames.MiniGameUI;
import com.odysseedesmaths.minigames.arriveeremarquable.entities.Entity;
import com.odysseedesmaths.minigames.arriveeremarquable.entities.Hero;
import com.odysseedesmaths.minigames.arriveeremarquable.entities.ennemies.Enemy;
import com.odysseedesmaths.minigames.arriveeremarquable.entities.ennemies.Greed;
import com.odysseedesmaths.minigames.arriveeremarquable.entities.ennemies.Smart;
import com.odysseedesmaths.minigames.arriveeremarquable.entities.ennemies.Sticky;
import com.odysseedesmaths.minigames.arriveeremarquable.entities.ennemies.SuperSmart;
import com.odysseedesmaths.minigames.arriveeremarquable.entities.items.Item;
import com.odysseedesmaths.minigames.arriveeremarquable.entities.items.Shield;
import com.odysseedesmaths.minigames.arriveeremarquable.map.Case;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ForetScreen implements Screen {

    public static final int WIDTH = 800;
    public static final int HEIGHT = 480;
    public static final int DELTA = 300;
    public static final int CELL_SIZE = 64;

    private Viewport viewport;
    private OrthographicCamera camera;
    private Batch batch;

    private MiniGameUI ui;

    private Sprite heroSprite;
    private Map<Entity, Sprite> entitiesSprites;
    private Map<Class<? extends Item>, Sprite> buffsSprites;
    private Sprite hordeSprite;

    public ForetScreen() {
        camera = new OrthographicCamera();
        viewport = new StretchViewport(WIDTH, HEIGHT, camera);
        batch = new SpriteBatch();

        // Sprites
        heroSprite = new Sprite(Assets.getManager().get(Assets.HERO, Texture.class));
        heroSprite.setPosition(ArriveeRemarquable.get().hero.getCase().i * CELL_SIZE, ArriveeRemarquable.get().hero.getCase().j * CELL_SIZE);

        entitiesSprites = new HashMap<Entity, Sprite>();

        buffsSprites = new HashMap<Class<? extends Item>, Sprite>();
        buffsSprites.put(Shield.class, new Sprite(Assets.getManager().get(Assets.ARR_BUFF_SHIELD, Texture.class)));

        hordeSprite = new Sprite(Assets.getManager().get(Assets.ARR_HORDE, Texture.class));

        ui = new MiniGameUI(Hero.PDV_MAX, ArriveeRemarquable.TIME_LIMIT * Timer.ONE_MINUTE, true);
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
        Musique.setCurrent(Assets.ARCADE);
        Musique.play();
        ui.getTimer().start();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    @Override
    public void render(float delta) {
        // Effaçage du précédent affichage
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Affichage du terrain
        ArriveeRemarquable.get().terrain.renderer.setView(camera);
        ArriveeRemarquable.get().terrain.renderer.render();

        // Affichage des entités
        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        for (Enemy enemy : ArriveeRemarquable.get().enemies) {
            Sprite enemySprite = entitiesSprites.get(enemy);
            if (enemySprite == null) {
                enemySprite = getNewSpriteFor(enemy);
                entitiesSprites.put(enemy, enemySprite);
            }
            updatePos(enemySprite, enemy);
            enemySprite.draw(batch);
        }

        Iterator<Enemy> deadIterator = ArriveeRemarquable.get().deadpool.iterator();
        while (deadIterator.hasNext()) {
            Enemy deadEnemy = deadIterator.next();
            Sprite deadSprite = entitiesSprites.get(deadEnemy);
            if (updatePos(deadSprite, deadEnemy)) {
                deadIterator.remove();
                entitiesSprites.remove(deadEnemy);
            }
            deadSprite.draw(batch);
        }

        for (Item item : ArriveeRemarquable.get().items) {
            Sprite itemSprite = entitiesSprites.get(item);
            if (itemSprite == null) {
                itemSprite = getNewSpriteFor(item);
                entitiesSprites.put(item, itemSprite);
            }
            updatePos(itemSprite, item);
            itemSprite.draw(batch);
        }

        // Affichage du héros
        updatePos(heroSprite, ArriveeRemarquable.get().hero);
        heroSprite.draw(batch);

        if (ArriveeRemarquable.get().activeItems.get(Shield.class) != null) {
            buffsSprites.get(Shield.class).setPosition(heroSprite.getX(), heroSprite.getY());
            buffsSprites.get(Shield.class).draw(batch);
        }

        // Affichage de la horde
        if (ArriveeRemarquable.get().horde.getFront() >= 0) {
            for (int i = 0; i <= ArriveeRemarquable.get().horde.getFront(); i++) {
                for (int j = 0; j < ArriveeRemarquable.get().terrain.getHeight(); j++) {
                    hordeSprite.setPosition(i * CELL_SIZE, j * CELL_SIZE);
                    hordeSprite.draw(batch);
                }
            }
        }

        batch.end();

        // Positionnement de la caméra (sur le héros ou sur les bords)
        float posX, posY, minX, minY, maxX, maxY;
        posX = heroSprite.getX() + heroSprite.getWidth()/2;
        posY = heroSprite.getY() + heroSprite.getHeight()/2;
        minX = WIDTH/2f;
        minY = HEIGHT/2f;
        maxX = ArriveeRemarquable.get().terrain.getWidth() * CELL_SIZE - WIDTH/2f;
        maxY = ArriveeRemarquable.get().terrain.getHeight() * CELL_SIZE - HEIGHT/2f;
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
    public void pause() {
        ui.getTimer().stop();
    }

    @Override
    public void resume() {
        ui.getTimer().start();
    }

    @Override
    public void hide() {
        Musique.pause();
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
        String asset;

        if (aEntity instanceof Enemy) {
            if (aEntity instanceof Sticky) asset = Assets.ARR_S_EGAL;
            else if (aEntity instanceof Smart) asset = Assets.ARR_S_ADD;
            else if (aEntity instanceof Greed) asset = Assets.ARR_S_MULT;
            else if (aEntity instanceof SuperSmart) asset = Assets.ARR_S_DIV;
            else asset = Assets.ARR_S_SOUST;
        } else {// c'est un item, pas d'appel de cette méthode sur le héros
            if (aEntity instanceof Shield) asset = Assets.ARR_SHIELD;
            else asset = Assets.HEART;
        }

        Sprite sprite = new Sprite(Assets.getManager().get(asset, Texture.class));
        sprite.setPosition(aEntity.getCase().i * CELL_SIZE, aEntity.getCase().j * CELL_SIZE);

        return sprite;
    }

    private class InputEcouteur extends InputListener {

        @Override
        public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
            Actor source = event.getTarget();

            if (source == ui.getPadUp()) {
                ArriveeRemarquable.get().hero.moveUp();
            } else if (source == ui.getPadRight()) {
                ArriveeRemarquable.get().hero.moveRight();
            } else if (source == ui.getPadDown()) {
                ArriveeRemarquable.get().hero.moveDown();
            } else if (source == ui.getPadLeft()) {
                ArriveeRemarquable.get().hero.moveLeft();
            }

            ArriveeRemarquable.get().playTurn();

            return true;
        }
    }


    /**********
     * STATIC *
     **********/

    public static boolean isVisible(Case c) {
        boolean resW, resH;
        Case cHeros = ArriveeRemarquable.get().hero.getCase();
        resW = Math.abs(c.i - cHeros.i) * CELL_SIZE < WIDTH/2;
        resH = Math.abs(c.j - cHeros.j) * CELL_SIZE < HEIGHT/2;
        return resW && resH;
    }

    public static boolean isInHeroSight(Case c) {
        Case cHeros = ArriveeRemarquable.get().hero.getCase();
        Case[][] cases = ArriveeRemarquable.get().terrain.getCases();
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
