package com.odysseedesmaths.menus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.odysseedesmaths.Assets;
import com.odysseedesmaths.Musique;

/*
    Classe d'affichage à la fin lorsque le joueur gagne à un mini-jeu
*/

public class MenuGagner extends Stage {

    private static final int WIDTH = 800;
    private static final int HEIGHT = 480;
    private static final int SPACE_BETWEEN_BUTTONS = HEIGHT / 15;
    private static final BitmapFont GAGNER;
    private static final BitmapFont BUTTON;

    private Table table;
    private Skin skin;

    private Label title;
    private TextButton continuer;
    private TextButton returnMainMenu;

    public MenuGagner() {
        super(new StretchViewport(WIDTH, HEIGHT));

        table = new Table();
        table.setFillParent(true);
        addActor(table);

        skin = new Skin();
        skin.addRegions(Assets.getManager().get(Assets.UI_MAIN, TextureAtlas.class));
        skin.addRegions(Assets.getManager().get(Assets.UI_ORANGE, TextureAtlas.class));
        skin.add("gagner", GAGNER);
        skin.add("button", BUTTON);

        LabelStyle titleStyle = new LabelStyle(skin.getFont("gagner"), null);
        skin.add("title", titleStyle);

        TextButtonStyle buttonStyle = new TextButtonStyle();
        buttonStyle.font = skin.getFont("button");
        buttonStyle.up = skin.getDrawable("button");
        buttonStyle.down = skin.getDrawable("button_pressed");
        skin.add("button", buttonStyle);

        title = new Label("GAGNE!", skin, "title");
        continuer = new TextButton("Continuer", skin, "button");
        returnMainMenu = new TextButton("Quitter", skin, "button");

        table.setBackground(skin.getDrawable("dark_background"));
        table.add(title).padBottom(SPACE_BETWEEN_BUTTONS);
        table.row();
        table.add(continuer).size(256, 64).padBottom(SPACE_BETWEEN_BUTTONS);
        table.row();
        table.add(returnMainMenu).size(256, 64);
    }

    public TextButton getContinuer() {
        return continuer;
    }

    public TextButton getReturnMainMenu() {
        return returnMainMenu;
    }

    public void render() {
        act(Gdx.graphics.getDeltaTime());
        draw();
    }

    public void playMusic(){
        //Musique.setCurrent(Assets.GAGNER_MUSIC);        // trouver la musique !!!!
        Musique.play();
    }

    @Override
    public void dispose() {
        super.dispose();
        skin.dispose();
    }

    public void setListener(InputListener listener) {
        continuer.addListener(listener);
        returnMainMenu.addListener(listener);
    }

    static {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Assets.PRESS_START_2P);
        FreeTypeFontParameter parameter = new FreeTypeFontParameter();
        parameter.size = HEIGHT / 9;
        parameter.color = Color.FOREST;
        parameter.borderWidth = 5;
        parameter.borderColor = Color.WHITE;
        GAGNER = generator.generateFont(parameter);

        parameter = new FreeTypeFontParameter();
        parameter.size = HEIGHT / 22;
        parameter.color = Color.WHITE;
        BUTTON = generator.generateFont(parameter);
    }
}
