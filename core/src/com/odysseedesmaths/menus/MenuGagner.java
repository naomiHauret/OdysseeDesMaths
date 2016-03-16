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

    public MenuGameOver() {
        super(new StretchViewport(WIDTH, HEIGHT));

        table = new Table();
        table.setFillParent(true);
        table.setY(HEIGHT / 35);
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

        title = new Label("GAGNER", skin, "title");
        continuer = new TextButton("Continuer", skin, "button");
        returnMainMenu = new TextButton("Quitter", skin, "button");

        table.add(title).padBottom(SPACE_BETWEEN_BUTTONS);
        table.row();
<<<<<<< HEAD:core/src/com/odysseedesmaths/menus/MenuGagner.java
        table.add(continuer).size(256, 64).padBottom(SPACE_BETWEEN_BUTTONS);
=======
        //table.add(retry).size(256, 64).padBottom(SPACE_BETWEEN_BUTTONS);
>>>>>>> 3619d6d01c9afbc29228ed74b5f7121d3f67eb41:core/src/com/odysseedesmaths/menus/MenuWin.java
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
<<<<<<< HEAD:core/src/com/odysseedesmaths/menus/MenuGagner.java
        Musique.setCurrent(Assets.GAGNER_MUSIC);        // trouver la musique !!!!
=======
       // Musique.setCurrent(Assets.WIN_MUSIC);
>>>>>>> 3619d6d01c9afbc29228ed74b5f7121d3f67eb41:core/src/com/odysseedesmaths/menus/MenuWin.java
        Musique.play();
    }

    @Override
    public void dispose() {
        super.dispose();
        skin.dispose();
    }

    public void setListener(InputListener listener) {
<<<<<<< HEAD:core/src/com/odysseedesmaths/menus/MenuGagner.java
        continuer.addListener(listener);
=======
        //retry.addListener(listener);
>>>>>>> 3619d6d01c9afbc29228ed74b5f7121d3f67eb41:core/src/com/odysseedesmaths/menus/MenuWin.java
        returnMainMenu.addListener(listener);
    }

    static {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Assets.PRESS_START_2P);
        FreeTypeFontParameter parameter = new FreeTypeFontParameter();
        parameter.size = HEIGHT / 9;
        parameter.color = Color.RED;
        parameter.borderWidth = 5;
        parameter.borderColor = Color.WHITE;
        GAGNER = generator.generateFont(parameter);

        parameter = new FreeTypeFontParameter();
        parameter.size = HEIGHT / 22;
        parameter.color = Color.WHITE;
        BUTTON = generator.generateFont(parameter);
    }
}
