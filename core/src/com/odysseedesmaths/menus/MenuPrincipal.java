package com.odysseedesmaths.menus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.odysseedesmaths.Assets;
import com.odysseedesmaths.Musique;
import com.odysseedesmaths.OdysseeDesMaths;
import com.odysseedesmaths.minigames.accrobranche.Accrobranche;
import com.odysseedesmaths.minigames.arriveeremarquable.ArriveeRemarquable;

public class MenuPrincipal implements Screen {
    private TextButton nouveauJeu, continuer, quitter;
    private TextButton.TextButtonStyle txtButtonStyle=null;
    private Label text;
    private Table tableau;
    private Stage stage = null;
    private Label.LabelStyle textStyle;
    private OdysseeDesMaths jeu;
    private BitmapFont menuFont = null, fontButton = null;
    private FreeTypeFontGenerator ftfg = null;
    private FreeTypeFontGenerator.FreeTypeFontParameter ftfp = null;


    public MenuPrincipal(OdysseeDesMaths jeu) {
        this.jeu = jeu;
        this.stage = new Stage();
        this.tableau = new Table();

        //propriétés relatives à la police
        this.ftfp = new FreeTypeFontGenerator.FreeTypeFontParameter();
        this.menuFont = new BitmapFont();
        this.fontButton = new BitmapFont();

        this.txtButtonStyle = new TextButton.TextButtonStyle();
        this.textStyle = new Label.LabelStyle();

        this.createUI();
    }

    public void createUI(){
        //font parameter
        ftfp.size = Gdx.graphics.getHeight()/ 15; //the size can be changed later
        ftfp.color = new Color(255f,255f,255f,1);
        ftfg = new FreeTypeFontGenerator(Gdx.files.internal("fonts/PressStart2P.ttf"));
        menuFont = ftfg.generateFont(ftfp);
        ftfp.size = Gdx.graphics.getHeight()/ 20;
        fontButton =  ftfg.generateFont(ftfp);

        //button style
        txtButtonStyle.font = fontButton;
        textStyle.font = menuFont;

        //texts and buttons
        text = new Label("L'Odyssée des Maths",textStyle);
        nouveauJeu = new TextButton("Nouvelle partie",txtButtonStyle);
        continuer = new TextButton("Reprendre",txtButtonStyle);
        quitter = new TextButton("Quitter",txtButtonStyle);
        setListener(new MenuListener());

        //table disposition
        tableau.setFillParent(true);
        tableau.background(new SpriteDrawable(new Sprite(Assets.getManager().get(Assets.MAIN_MENU_BACKGROUND, Texture.class))));

        //le titre prends un tiers
        tableau.add(text).pad(10);
        tableau.getCell(text).expand();

        //le reste pour les trois autres boutons
        tableau.row();
        tableau.add(continuer);
        tableau.getCell(continuer).expand();
        tableau.row();
        tableau.add(nouveauJeu);
        tableau.getCell(nouveauJeu).expand();
        tableau.row();
        tableau.add(quitter);
        tableau.getCell(quitter).expand();

        stage.addActor(tableau);
    }

    public void setListener(EventListener listener) {
        nouveauJeu.addListener(listener);
        continuer.addListener(listener);
        quitter.addListener(listener);
    }

    //crée une nouvelle partie
    public void newGame(OdysseeDesMaths jeu){
        this.jeu.setScreen(new SaveSelection(jeu));
    }

    //lance la partie en cours
    public void launchGame(OdysseeDesMaths jeu){
        this.jeu.setScreen(new Accrobranche(jeu));

    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);

        Musique.setCurrent(Assets.MENU_MUSIC);
        Musique.play();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
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

    public class MenuListener extends InputListener{
        @Override
        public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
            Actor source = event.getTarget();

            if (source == nouveauJeu.getLabel()) {
                newGame(jeu);
            } else if (source == continuer.getLabel()) {
                launchGame(jeu);
            } else if (source == quitter.getLabel()) {
                Gdx.app.exit();
            }

            return true;
        }
    }
}
