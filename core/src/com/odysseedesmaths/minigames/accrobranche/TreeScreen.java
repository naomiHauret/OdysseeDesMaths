package com.odysseedesmaths.minigames.accrobranche;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.utils.Box2DBuild;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.odysseedesmaths.Assets;
import com.odysseedesmaths.Musique;
import com.odysseedesmaths.menus.MenuGagner;
import com.odysseedesmaths.menus.MenuGameOver;
import com.odysseedesmaths.menus.MenuPause;
import com.odysseedesmaths.minigames.MiniGame;
import com.odysseedesmaths.minigames.MiniGameUI;

public class TreeScreen implements Screen {

    public static final int WIDTH = 800;
    public static final int HEIGHT = 480;
    public static final float PPM = 100;

    //Box2D Collision Bits
    public static final short NOTHING_BIT = 0;
    public static final short GROUND_BIT = 1;
    public static final short HERO_BIT = 2;
    public static final short HERO_HEAD_BIT = 4;

    // Actual ground
    public enum Ground { STANDARD, ACCROCHANT, TRAMPOLINE, GLISSANT };

    private final Accrobranche minigame;

    private SpriteBatch batch;
    public TextureAtlas atlas;

    private OrthographicCamera cam;
    private Viewport port;

    // Menus
    private MiniGameUI ui;
    private MenuPause menuPause;
    private MenuGameOver menuGameOver;
    private MenuGagner menuGagner;

    // Tiled map variables
    private TmxMapLoader mapLoader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;
    public float width_map;

    // Box2d variables
    private World world;
    //private Box2DBuild b2dr;
    private Box2DDebugRenderer b2dr;

    // Sprite
    private Hero hero;
    private boolean moveLeft = false;
    private boolean moveRight = false;

    private Ground groundCourant;

    public TreeScreen(final Accrobranche minigame) {

        this.minigame = minigame;

        batch = new SpriteBatch();

        cam = new OrthographicCamera();
        port = new FitViewport(WIDTH / PPM / 1.5f, HEIGHT / PPM / 1.5f, cam);

        mapLoader = new TmxMapLoader();
        map = mapLoader.load("accrobranche/map.tmx");
        renderer = new OrthogonalTiledMapRenderer(map, 1 / PPM);
        width_map = (int)map.getProperties().get("width") / PPM;

        // Elements relatifs à Box2d
        world = new World(new Vector2(0, -10), true);
        //b2dr = new Box2DBuild();
        b2dr = new Box2DDebugRenderer();

        BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();
        Body body;

        // Standard
        for(MapObject object : map.getLayers().get(3).getObjects().getByType(RectangleMapObject.class)) {
            RectangleMapObject rectMO = ((RectangleMapObject) object);
            rectMO.setVisible(false);
            Rectangle rect = rectMO.getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth() / 2) / PPM, (rect.getY() + rect.getHeight() / 2) / PPM);

            body = world.createBody(bdef);

            shape.setAsBox(rect.getWidth() / 2 / PPM, rect.getHeight() / 2 / PPM);
            fdef.shape = shape;
            body.createFixture(fdef);
        }

        // Glissant
        for(MapObject object : map.getLayers().get(4).getObjects().getByType(RectangleMapObject.class)) {
            RectangleMapObject rectMO = ((RectangleMapObject) object);
            rectMO.setVisible(false);
            Rectangle rect = rectMO.getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth() / 2) / PPM, (rect.getY() + rect.getHeight() / 2) / PPM);

            body = world.createBody(bdef);

            shape.setAsBox(rect.getWidth() / 2 / PPM, rect.getHeight() / 2 / PPM);
            fdef.shape = shape;
            body.createFixture(fdef);
        }

        // Accrochant
        for(MapObject object : map.getLayers().get(5).getObjects().getByType(RectangleMapObject.class)) {
            RectangleMapObject rectMO = ((RectangleMapObject) object);
            rectMO.setVisible(false);
            Rectangle rect = rectMO.getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth() / 2) / PPM, (rect.getY() + rect.getHeight() / 2) / PPM);

            body = world.createBody(bdef);

            shape.setAsBox(rect.getWidth() / 2 / PPM, rect.getHeight() / 2 / PPM);
            fdef.shape = shape;
            body.createFixture(fdef);
        }

        // Hero
        hero = new Hero(world, this);

        // UI
        ui = new MiniGameUI();
        ui.addTimer(minigame.timer);
        ui.addPad(MiniGameUI.PAD_TYPE.HORIZONTAL);
        ui.addButtonA();
        Gdx.input.setInputProcessor(ui);
        ui.setListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Actor source = event.getTarget();

                // Menu
                if (source == ui.getPause()) {
                    minigame.pauseGame();
                    Gdx.input.setInputProcessor(menuPause);
                }

                // Jump
                if (source == ui.getButtonA() && !(hero.getState() == Hero.State.JUMPING || hero.getState() == Hero.State.FALLING)) {
                    hero.jump(getGround());
                }

                // Lateral movement
                if (source == ui.getPadRight()) {
                    moveRight = true;
                }
                if (source == ui.getPadLeft()) {
                    moveLeft = true;
                }

                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                Actor source = event.getTarget();

                if (source == ui.getPadRight()) {
                    moveRight = false;
                }
                if (source == ui.getPadLeft()) {
                    moveLeft = false;
                }
            }
        });

        menuPause = new MenuPause();
        menuPause.setListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                Actor source = event.getTarget();

                if (source == menuPause.getRetourJeu().getLabel()) {
                    minigame.returnToGame();
                    Gdx.input.setInputProcessor(ui);
                } else if (source == menuPause.getRecommencer().getLabel()) {
                    minigame.restartGame();
                } else if (source == menuPause.getQuitter().getLabel()) {
                    minigame.returnToMainMenu();
                }
            }
        });

        menuGameOver = new MenuGameOver();
        menuGameOver.setListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                Actor source = event.getTarget();

                if (source == menuGameOver.getRetry().getLabel()) {
                    minigame.restartGame();
                } else if (source == menuGameOver.getReturnMainMenu().getLabel()) {
                    minigame.returnToMainMenu();
                }
            }
        });

        menuGagner = new MenuGagner();
        menuGagner.setListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                Actor source = event.getTarget();

                if (source == menuGagner.getContinuer().getLabel()) {
                    // TODO
                } else if (source == menuGagner.getReturnMainMenu().getLabel()) {
                    minigame.returnToMainMenu();
                }
            }
        });

        groundCourant = Ground.STANDARD;
    }

    @Override
    public void show() {
        Musique.setCurrent("accrobranche/theme.ogg");
        Musique.play();
        minigame.timer.start();
    }

    public void update(float dt) {

        world.step(1 / 80f, 6, 2);

        // Deplacement horizontal
        if (moveRight && hero.b2body.getLinearVelocity().x <= 2)
            hero.runRight(getGround());
        if (moveLeft && hero.b2body.getLinearVelocity().x >= -2)
            hero.runLeft(getGround());

        hero.update(dt);

        // Positionnement de la caméra (sur le héros ou sur les bords)
        float posXHero, posYHero, posXCam, posYCam;
        posXHero = hero.b2body.getPosition().x;
        posYHero = hero.b2body.getPosition().y;
        posXCam = posXHero;
        posYCam = cam.position.y;

        // Reglage de la position horizontale de la camera
        if (posXHero < port.getWorldWidth() / 2){
            posXCam = port.getWorldWidth() / 2;
        } else if (posXHero > 6.3f - (port.getWorldWidth() / 2)){
            posXCam = 6.3f - (port.getWorldWidth() / 2);
        }

        // Reglage de la position verticale de la caméra
        if (hero.getState() != Hero.State.JUMPING && hero.getState() != Hero.State.FALLING) {
            posYCam = posYHero;
        }
        if (posYHero < port.getWorldHeight() / 2) {
            posYCam = port.getWorldHeight() / 2;

            if (posYHero < 0) {
                minigame.gameOver();
            }
        }

        cam.position.lerp(new Vector3(posXCam, posYCam, 0), 0.07f);
        cam.update();
        renderer.setView(cam);
    }

    @Override
    public void resize(int width, int height) {
        port.update(width, height);
    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        update(delta);
        renderer.render();

        //b2dr.render(world, cam.combined);

        batch.setProjectionMatrix(cam.combined);
        batch.begin();
        hero.draw(batch);
        batch.end();

        ui.render();

        switch (minigame.getState()) {
            case RUNNING:
                if (minigame.timer.isFinished()) minigame.gameOver();
                break;
            case PAUSED:
                menuPause.draw();
                break;
            case GAME_OVER:
                menuGameOver.render();
                break;
            default:
                System.out.println("Erreur : état du jeu inconnu");
                break;
        }
    }

    @Override
    public void pause() {
        if (minigame.getState() == MiniGame.State.RUNNING) {
            minigame.pauseGame();
            Gdx.input.setInputProcessor(menuPause);
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
        map.dispose();
        renderer.dispose();
        world.dispose();
        //b2dr.dispose();
        ui.dispose();
        menuPause.dispose();
        menuGameOver.dispose();
    }

    public void gameOver() {
        Gdx.input.setInputProcessor(menuGameOver);
        menuGameOver.playMusic();
    }

    public Ground getGround() {
        return groundCourant;
    }

    public void setGround(Ground ground) {
        groundCourant = ground;
    }

    public void win() {
        Gdx.input.setInputProcessor(menuGagner);
        menuGagner.playMusic();
    }
}