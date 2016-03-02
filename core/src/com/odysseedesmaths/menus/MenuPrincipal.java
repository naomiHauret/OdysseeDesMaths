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
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Button.ButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.odysseedesmaths.Assets;
import com.odysseedesmaths.Musique;
import com.odysseedesmaths.OdysseeDesMaths;

public class MenuPrincipal implements Screen {
    private OdysseeDesMaths jeu;

    private static final int WIDTH = 800;
    private static final int HEIGHT = 480;
    private Viewport viewport;

    private Stage stage;
    private Table tableau;
    private Skin skin;

    private TextButton play;
    private TextButtonStyle playButtonStyle;

    private Button music;
    private ButtonStyle musicButtonStyle;
    private Button sounds;
    private ButtonStyle soundsButtonStyle;

    private Label gameTitle;
    private LabelStyle gameTitleStyle;

    private BitmapFont menuFont = null, fontButton = null;
    private FreeTypeFontGenerator ftfg = null;
    private FreeTypeFontParameter ftfp = null;

    public MenuPrincipal(OdysseeDesMaths jeu) {
        this.jeu = jeu;

        this.viewport = new StretchViewport(WIDTH, HEIGHT);
        this.stage = new Stage(viewport);
        this.tableau = new Table();
        this.skin = new Skin();
        this.skin.addRegions(Assets.getManager().get(Assets.UI_MAIN, TextureAtlas.class));
        this.skin.addRegions(Assets.getManager().get(Assets.UI_GREY, TextureAtlas.class));
        this.skin.add("background", Assets.getManager().get(Assets.MAIN_MENU_BACKGROUND, Texture.class));

        //propriétés relatives à la police
        this.ftfp = new FreeTypeFontParameter();
        this.menuFont = new BitmapFont();
        this.fontButton = new BitmapFont();

        this.playButtonStyle = new TextButtonStyle();
        this.gameTitleStyle = new LabelStyle();
        this.musicButtonStyle = new ButtonStyle();
        this.soundsButtonStyle = new ButtonStyle();

        this.createUI();
    }

    public void createUI() {
        //font parameter
        ftfp.size = HEIGHT / 9; //the size can be changed later
        ftfg = new FreeTypeFontGenerator(Gdx.files.internal("fonts/kenpixel_blocks.ttf"));
        menuFont = ftfg.generateFont(ftfp);

        ftfp.size = HEIGHT / 15;
        ftfp.color = new Color(255f,255f,255f,1);
        ftfg = new FreeTypeFontGenerator(Gdx.files.internal("fonts/PressStart2P.ttf"));
        fontButton =  ftfg.generateFont(ftfp);

        //buttons styles
        playButtonStyle.font = fontButton;
        playButtonStyle.up = skin.getDrawable("button");
        playButtonStyle.down = skin.getDrawable("button_pressed");
        gameTitleStyle.font = menuFont;
        musicButtonStyle.up = skin.getDrawable("music_on");
        soundsButtonStyle.up = skin.getDrawable("sounds_on");

        //texts and buttons
        gameTitle = new Label("L'Odyssée des Maths", gameTitleStyle);
        play = new TextButton("JOUER", playButtonStyle);
        music = new Button(musicButtonStyle);
        sounds = new Button(soundsButtonStyle);
        setListener(new MenuListener());

        //table disposition
        tableau.setFillParent(true);
        tableau.setBackground(skin.getDrawable("background"));

        tableau.padTop(HEIGHT / 3);
        tableau.add(gameTitle).colspan(2).bottom().padBottom(HEIGHT / 12);
        tableau.row();
        tableau.add(play).colspan(2).top().expand();
        tableau.row();
        tableau.add(music);
        tableau.add(sounds).left().expandX();

        stage.addActor(tableau);
    }

    public void setListener(EventListener listener) {
        play.addListener(listener);
        music.addListener(listener);
        sounds.addListener(listener);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);

        Musique.setCurrent(Assets.MENU_MUSIC);
        Musique.play();
    }

    @Override
    public void render(float delta) {
        if (gameTitle.getY() == HEIGHT * 12/13 - gameTitle.getHeight()) {
            this.jeu.setScreen(new SaveSelection(jeu));
        }

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
        stage.draw();
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
        stage.dispose();
        ftfg.dispose();
        fontButton.dispose();
        menuFont.dispose();
    }

    private void play(){
        AlphaAction alphaAction = new AlphaAction();
        alphaAction.setAlpha(0);
        alphaAction.setDuration(0.5f);
        play.addAction(alphaAction);
        play.setTouchable(Touchable.disabled);

        MoveToAction moveToAction = new MoveToAction();
        moveToAction.setPosition(gameTitle.getX(), HEIGHT * 12 / 13 - gameTitle.getHeight());
        moveToAction.setDuration(2);
        gameTitle.addAction(moveToAction);
    }

    private class MenuListener extends InputListener{
        @Override
        public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
            Actor source = event.getTarget();

            if ((source == play) || (source == play.getLabel())) {
                play();
            } else if (source == music) {
                if (Musique.isPlaying()) {
                    Musique.pause();
                    musicButtonStyle.up = skin.getDrawable("music_off");
                } else {
                    Musique.play();
                    musicButtonStyle.up = skin.getDrawable("music_on");
                }
            } else if (source == sounds) {
                if (Musique.isPlaying()) {
                    Musique.pause();
                    soundsButtonStyle.up = skin.getDrawable("sounds_off");
                } else {
                    Musique.play();
                    soundsButtonStyle.up = skin.getDrawable("sounds_on");
                }
            }

            return true;
        }
    }
}
