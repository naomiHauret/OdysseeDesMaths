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

public class MenuWin extends Stage {

    private static final int WIDTH = 800;
    private static final int HEIGHT = 480;
    private static final int SPACE_BETWEEN_BUTTONS = HEIGHT / 15;
    private static final BitmapFont WIN;
    private static final BitmapFont BUTTON;

    private Table table;
    private Skin skin;

    private Label title;
    private TextButton continuer;
    private TextButton returnMainMenu;

    public MenuWin() {
        super(new StretchViewport(WIDTH, HEIGHT));

        table = new Table();
        table.setFillParent(true);
        table.setY(HEIGHT / 35);
        addActor(table);

        skin = new Skin();
        skin.addRegions(Assets.getManager().get(Assets.UI_MAIN, TextureAtlas.class));
        skin.addRegions(Assets.getManager().get(Assets.UI_ORANGE, TextureAtlas.class));
        skin.add("gagner", WIN);
        skin.add("button", BUTTON);

        LabelStyle titleStyle = new LabelStyle(skin.getFont("gagner"), null);
        skin.add("title", titleStyle);

        TextButtonStyle buttonStyle = new TextButtonStyle();
        buttonStyle.font = skin.getFont("button");
        buttonStyle.up = skin.getDrawable("button");
        buttonStyle.down = skin.getDrawable("button_pressed");
        skin.add("button", buttonStyle);

        title = new Label("GAGNER", skin, "title");
        continuer = new TextButton("Continuer", skin, "button");
        returnMainMenu = new TextButton("Quitter", skin, "button");

        table.add(title).padBottom(SPACE_BETWEEN_BUTTONS);
        table.row();
        table.add(retry).size(256, 64).padBottom(SPACE_BETWEEN_BUTTONS);
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
        Musique.setCurrent(Assets.WIN_MUSIC);
        Musique.play();
    }

    @Override
    public void dispose() {
        super.dispose();
        skin.dispose();
    }

    public void setListener(InputListener listener) {
        retry.addListener(listener);
        returnMainMenu.addListener(listener);
    }

    static {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Assets.PRESS_START_2P);
        FreeTypeFontParameter parameter = new FreeTypeFontParameter();
        parameter.size = HEIGHT / 9;
        parameter.color = Color.RED;
        parameter.borderWidth = 5;
        parameter.borderColor = Color.WHITE;
        WIN = generator.generateFont(parameter);

        parameter = new FreeTypeFontParameter();
        parameter.size = HEIGHT / 22;
        parameter.color = Color.WHITE;
        BUTTON = generator.generateFont(parameter);
    }
}