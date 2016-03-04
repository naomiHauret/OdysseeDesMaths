package com.odysseedesmaths.menus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.odysseedesmaths.Assets;
import com.odysseedesmaths.Musique;

public class MenuGameOver extends Menu {

    private static final int SPACE_BETWEEN_BUTTONS = Gdx.graphics.getHeight() / 15;

    private Table table;
    private Skin skin;

    private Label title;
    private TextButton retry;
    private TextButton returnMainMenu;

    public MenuGameOver() {
        table = new Table();
        table.setFillParent(true);
        table.setY(Gdx.graphics.getHeight() / 35);
        addActor(table);

        skin = new Skin();
        skin.addRegions(Assets.getManager().get(Assets.UI_MAIN, TextureAtlas.class));
        skin.addRegions(Assets.getManager().get(Assets.UI_RED, TextureAtlas.class));

        LabelStyle titleStyle = new LabelStyle(Assets.GAME_OVER, null);
        skin.add("title", titleStyle);

        TextButtonStyle buttonStyle = new TextButtonStyle();
        buttonStyle.font = Assets.BUTTON;
        buttonStyle.up = skin.getDrawable("button");
        buttonStyle.down = skin.getDrawable("button_pressed");
        skin.add("button", buttonStyle);

        title = new Label("GAME OVER", skin, "title");
        retry = new TextButton("Recommencer", skin, "button");
        returnMainMenu = new TextButton("Quitter", skin, "button");

        table.add(title).padBottom(SPACE_BETWEEN_BUTTONS);
        table.row();
        table.add(retry).padBottom(SPACE_BETWEEN_BUTTONS);
        table.row();
        table.add(returnMainMenu).width(retry.getWidth());
    }

    public TextButton getRetry() {
        return retry;
    }

    public TextButton getReturnMainMenu() {
        return returnMainMenu;
    }

    public void render() {
        act(Gdx.graphics.getDeltaTime());
        draw();
    }

    public void playMusic(){
        Musique.setCurrent(Assets.GAME_OVER_MUSIC);
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
}
