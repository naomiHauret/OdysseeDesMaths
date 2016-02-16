package com.odysseedesmaths.menus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
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

    private Table table;
    private Skin skin;

    private Label title;
    private TextButton retry;
    private TextButton returnMainMenu;

    public MenuGameOver() {
        table = new Table();
        table.setFillParent(true);
        table.setY(Gdx.graphics.getHeight() / 12);
        addActor(table);

        skin = new Skin();
        skin.addRegions(Assets.getManager().get(Assets.UI_TEST, TextureAtlas.class));

        LabelStyle titleStyle = new LabelStyle(Assets.GAME_OVER, Color.WHITE);
        skin.add("title", titleStyle);

        TextButtonStyle buttonStyle = new TextButtonStyle();
        buttonStyle.font = Assets.BUTTON;
        buttonStyle.up = skin.getDrawable("default-round");
        buttonStyle.down = skin.getDrawable("default-round-down");
        skin.add("button", buttonStyle);

        title = new Label("GAME OVER", skin, "title");
        retry = new TextButton("Reessayer", skin, "button");
        returnMainMenu = new TextButton("Quitter", skin, "button");

        table.add(title).colspan(2).padBottom(Gdx.graphics.getHeight() / 20);
        table.row();
        table.add(retry).padRight(Gdx.graphics.getWidth() / 10);
        table.add(returnMainMenu);
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
