package com.odysseedesmaths.menus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.AlphaAction;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.odysseedesmaths.Assets;
import com.odysseedesmaths.Musique;
import com.odysseedesmaths.OdysseeDesMaths;

import java.lang.Character;

public class MenuPrincipal implements Screen {

    private static final int WIDTH = 800;
    private static final int HEIGHT = 480;
    private static final double ENCRYPTION_CHANCE = 0.1;
    private static final float ENCRYPTION_DURATION = 0.25f;
    private enum State {NORMAL, TRANSITION}

    private OdysseeDesMaths jeu;
    private State currentState;

    private Viewport viewport;
    private Stage stage;
    private Table tableau;
    private Skin skin;

    private Label gameTitle;
    private LabelStyle gameTitleStyle;

    private TextButton play;
    private TextButtonStyle playButtonStyle;

    private AudioButtons audioButtons;

    private BitmapFont menuFont = null, fontButton = null;
    private FreeTypeFontGenerator ftfg = null;
    private FreeTypeFontParameter ftfp = null;

    public MenuPrincipal(OdysseeDesMaths jeu) {
        this.jeu = jeu;

        this.currentState = State.NORMAL;
        this.viewport = new StretchViewport(WIDTH, HEIGHT);
        this.stage = new Stage(viewport);
        this.tableau = new Table();
        this.skin = new Skin();
        this.skin.addRegions(Assets.getManager().get(Assets.UI_MAIN, TextureAtlas.class));
        this.skin.addRegions(Assets.getManager().get(Assets.UI_ORANGE, TextureAtlas.class));
        this.skin.add("background", Assets.getManager().get(Assets.MAIN_MENU_BACKGROUND, Texture.class));

        //propriétés relatives à la police
        this.ftfp = new FreeTypeFontParameter();
        this.menuFont = new BitmapFont();
        this.fontButton = new BitmapFont();

        this.playButtonStyle = new TextButtonStyle();
        this.gameTitleStyle = new LabelStyle();
        this.audioButtons = new AudioButtons();

        this.createUI();
    }

    public void createUI() {
        //font parameter
        ftfp.size = HEIGHT / 9; //the size can be changed later
        ftfg = new FreeTypeFontGenerator(Assets.KENPIXEL_BLOCKS);
        menuFont = ftfg.generateFont(ftfp);

        ftfp.size = HEIGHT / 15;
        ftfp.color = new Color(255f,255f,255f,1);
        ftfg = new FreeTypeFontGenerator(Assets.PRESS_START_2P);
        fontButton =  ftfg.generateFont(ftfp);

        //buttons styles
        playButtonStyle.font = fontButton;
        playButtonStyle.up = skin.getDrawable("button");
        playButtonStyle.down = skin.getDrawable("button_pressed");
        gameTitleStyle.font = menuFont;

        //texts and buttons
        gameTitle = new Label("L'Odyssée des Maths", gameTitleStyle);
        play = new TextButton("JOUER", playButtonStyle);
        setListener(new MenuListener());

        //table disposition
        tableau.setFillParent(true);
        tableau.setBackground(skin.getDrawable("background"));

        tableau.padTop(HEIGHT / 3);
        tableau.add(gameTitle).colspan(2).bottom().padBottom(HEIGHT / 12);
        tableau.row();
        tableau.add(play).size(200, 64).colspan(2).top().expand();
        tableau.row();
        tableau.add(audioButtons).left();

        stage.addActor(tableau);
    }

    public void setListener(EventListener listener) {
        play.addListener(listener);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);

        Musique.setCurrent(Assets.MENU_MUSIC);
        Musique.play();
    }

    @Override
    public void render(float delta) {
        switch (currentState) {
            case NORMAL:
                if (Math.random() < ENCRYPTION_CHANCE) encryptGameTitle();
                break;
            case TRANSITION:
                if (gameTitle.getY() == HEIGHT * 12/13 - gameTitle.getHeight()) {
                    this.jeu.setScreen(new SaveSelection(jeu));
                }
                break;
            default:
                // Erreur état du jeu
                break;
        }

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    @Override
    public void pause() {
        // cette méthode n'est pas nécessaire ici
    }

    @Override
    public void resume() {
        // cette méthode n'est pas nécessaire ici
    }

    @Override
    public void hide() {
        // cette méthode n'est pas néessaire ici
    }

    @Override
    public void dispose() {
        stage.dispose();
        ftfg.dispose();
        fontButton.dispose();
        menuFont.dispose();
    }

    private void play() {
        AlphaAction alphaAction = new AlphaAction();
        alphaAction.setAlpha(0);
        alphaAction.setDuration(0.5f);
        play.addAction(alphaAction);
        play.setTouchable(Touchable.disabled);

        MoveToAction moveToAction = new MoveToAction();
        moveToAction.setPosition(gameTitle.getX(), HEIGHT * 12 / 13 - gameTitle.getHeight());
        moveToAction.setDuration(2);
        gameTitle.addAction(moveToAction);

        Timer.instance().clear();
        gameTitle.setText("L'Odyssée des Maths");
        currentState = State.TRANSITION;
    }

    private void encryptGameTitle() {
        char oldCharTmp;
        int indexTmp;
        int number = (int)(Math.random() * 10);

        do {
            indexTmp = (int)(Math.random() * gameTitle.getText().length());
            oldCharTmp = gameTitle.getText().charAt(indexTmp);
        } while (!Character.isLetter(oldCharTmp) || oldCharTmp == 'M');

        final char oldChar = oldCharTmp;
        final int index = indexTmp;

        gameTitle.setText(gameTitle.getText().substring(0, index) + number + gameTitle.getText().substring(index + 1));
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                gameTitle.setText(gameTitle.getText().substring(0, index) + oldChar + gameTitle.getText().substring(index + 1));
            }
        }, ENCRYPTION_DURATION);
    }

    private class MenuListener extends InputListener {
        @Override
        public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
            Actor source = event.getTarget();

            if ((source == play) || (source == play.getLabel())) {
                play();
            }

            return true;
        }
    }
}

