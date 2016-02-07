package com.odysseedesmaths.minigames.arriveeremarquable;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.odysseedesmaths.Assets;
import com.odysseedesmaths.Musique;
import com.odysseedesmaths.menus.MenuGameOver;
import com.odysseedesmaths.menus.MenuPause;
import com.odysseedesmaths.minigames.MiniGame;
import com.odysseedesmaths.minigames.MiniGameUI;
import com.odysseedesmaths.minigames.arriveeremarquable.entities.Entity;
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

    private final ArriveeRemarquable minigame;
    
    private Viewport viewport;
    private OrthographicCamera camera;
    private Batch batch;

    private MiniGameUI ui;
    private MenuPause mpause;
    private MenuGameOver menuGameOver;

    private Sprite heroSprite;
    private Map<Entity, Sprite> entitiesSprites;
    private Map<Class<? extends Item>, Sprite> buffsSprites;
    private Sprite hordeSprite;

    public ForetScreen(final ArriveeRemarquable minigame) {
        this.minigame = minigame;
        
        camera = new OrthographicCamera();
        viewport = new StretchViewport(WIDTH, HEIGHT, camera);
        batch = new SpriteBatch();

        // Sprites
        heroSprite = new Sprite(Assets.getManager().get(Assets.HERO, Texture.class));
        heroSprite.setPosition(minigame.hero.getCase().i * CELL_SIZE, minigame.hero.getCase().j * CELL_SIZE);

        entitiesSprites = new HashMap<Entity, Sprite>();

        buffsSprites = new HashMap<Class<? extends Item>, Sprite>();
        buffsSprites.put(Shield.class, new Sprite(Assets.getManager().get(Assets.ARR_BUFF_SHIELD, Texture.class)));

        hordeSprite = new Sprite(Assets.getManager().get(Assets.ARR_HORDE, Texture.class));

        ui = new MiniGameUI();
        ui.addHeroHp(minigame.hero);
        ui.addTimer(minigame.timer);
        ui.addPad();
        ui.build();
        Gdx.input.setInputProcessor(ui);
        ui.setListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Actor source = event.getTarget();

                if (source == ui.getPadUp()) {
                    minigame.hero.moveUp();
                } else if (source == ui.getPadRight()) {
                    minigame.hero.moveRight();
                } else if (source == ui.getPadDown()) {
                    minigame.hero.moveDown();
                } else if (source == ui.getPadLeft()) {
                    minigame.hero.moveLeft();
                } else if (source == ui.getPause()) {
                    minigame.pauseGame();
                    Gdx.input.setInputProcessor(mpause);
                    return true;
                }

                minigame.playTurn();

                return true;
            }
        });

        mpause = new MenuPause();
        mpause.setListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Actor source = event.getTarget();

                if (source == mpause.getRetourJeu().getLabel()) {
                    minigame.returnToGame();
                    Gdx.input.setInputProcessor(ui);
                } else if (source == mpause.getRecommencer().getLabel()) {
                    minigame.restartGame();
                }

                return true;
            }
        });

        menuGameOver = new MenuGameOver();
        menuGameOver.setListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Actor source = event.getTarget();

                if (source == menuGameOver.getRetry().getLabel()) {
                    minigame.restartGame();
                } else if (source == menuGameOver.getReturnMainMenu().getLabel()) {
                    // TODO
                }

                return true;
            }
        });
    }

    @Override
    public void show() {
        Musique.setCurrent(Assets.ARCADE);
        Musique.play();
        minigame.timer.start();
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
        minigame.terrain.renderer.setView(camera);
        minigame.terrain.renderer.render();

        // Affichage des entités
        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        for (Enemy enemy : minigame.enemies) {
            Sprite enemySprite = entitiesSprites.get(enemy);
            if (enemySprite == null) {
                enemySprite = getNewSpriteFor(enemy);
                entitiesSprites.put(enemy, enemySprite);
            }
            updatePos(enemySprite, enemy);
            enemySprite.draw(batch);
        }

        Iterator<Enemy> deadIterator = minigame.deadpool.iterator();
        while (deadIterator.hasNext()) {
            Enemy deadEnemy = deadIterator.next();
            Sprite deadSprite = entitiesSprites.get(deadEnemy);
            if (updatePos(deadSprite, deadEnemy)) {
                deadIterator.remove();
                entitiesSprites.remove(deadEnemy);
            }
            deadSprite.draw(batch);
        }

        for (Item item : minigame.items) {
            Sprite itemSprite = entitiesSprites.get(item);
            if (itemSprite == null) {
                itemSprite = getNewSpriteFor(item);
                entitiesSprites.put(item, itemSprite);
            }
            updatePos(itemSprite, item);
            itemSprite.draw(batch);
        }

        // Affichage du héros
        updatePos(heroSprite, minigame.hero);
        heroSprite.draw(batch);

        if (minigame.activeItems.get(Shield.class) != null) {
            buffsSprites.get(Shield.class).setPosition(heroSprite.getX(), heroSprite.getY());
            buffsSprites.get(Shield.class).draw(batch);
        }

        // Affichage de la horde
        if (minigame.horde.getFront() >= 0) {
            for (int i = 0; i <= minigame.horde.getFront(); i++) {
                for (int j = 0; j < minigame.terrain.getHeight(); j++) {
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
        maxX = minigame.terrain.getWidth() * CELL_SIZE - minX;
        maxY = minigame.terrain.getHeight() * CELL_SIZE - minY;
        camera.position.set(MathUtils.clamp(posX, minX, maxX), MathUtils.clamp(posY, minY, maxY), 0);

        // Interface utilisateur par dessus le reste
        ui.render();

        switch (minigame.getState()) {
            case RUNNING:
                if (minigame.timer.isFinished()) minigame.gameOver();
                break;
            case PAUSED:
                mpause.draw();
                break;
            case GAME_OVER:
                menuGameOver.render();
                break;
            default:
                // Erreur état du jeu
                break;
        }

        camera.update();
    }

    @Override
    public void pause() {
        if (minigame.getState() == MiniGame.State.RUNNING) {
            minigame.pauseGame();
        }
    }

    @Override
    public void resume() {
        // Le jeu est en pause
    }

    @Override
    public void hide() {
        Musique.pause();
    }

    @Override
    public void dispose() {
        ui.dispose();
        mpause.dispose();
        menuGameOver.dispose();
    }

    public void gameOver() {
        Gdx.input.setInputProcessor(menuGameOver);
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
}
