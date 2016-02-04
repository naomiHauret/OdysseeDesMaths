package com.odysseedesmaths.menus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.odysseedesmaths.Assets;

public class MenuGameOver extends Stage {

    private Table table;
    private Skin skin;

    private Label title;
    private TextButton retry;
    private TextButton returnMainMenu;

    public MenuGameOver() {
        table = new Table();
        table.setFillParent(true);
        addActor(table);

        skin = new Skin();
        LabelStyle titleStyle = new LabelStyle(Assets.PIXEL, Color.WHITE);
        skin.add("title", titleStyle);
        TextButtonStyle buttonStyle = new TextButtonStyle();
        buttonStyle.font = Assets.PIXEL;
        skin.add("button", buttonStyle);

        title = new Label("GAME OVER", skin, "title");
        retry = new TextButton("Réessayer", skin, "button");
        returnMainMenu = new TextButton("Quitter", skin, "button");

        table.add(title).colspan(2).padBottom(Gdx.graphics.getWidth() / 5);
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
