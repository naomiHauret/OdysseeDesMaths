package com.odysseedesmaths.menus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.odysseedesmaths.Assets;

public class MenuPause extends Menu {

    private static final int SPACE_BETWEEN_BUTTONS = Gdx.graphics.getHeight() / 15;

    private TextButton retourJeu, recommencer, quitter;
    private TextButtonStyle txtButtonStyle=null;

    public MenuPause(){
        super(22, new Color(255f, 255f, 255f, 1), "fonts/PressStart2P.ttf");

        Skin skin = new Skin();
        skin.addRegions(Assets.getManager().get(Assets.UI_MAIN, TextureAtlas.class));
        skin.addRegions(Assets.getManager().get(Assets.UI_RED, TextureAtlas.class));

        txtButtonStyle = new TextButton.TextButtonStyle();

        txtButtonStyle.font = font;
        txtButtonStyle.up = skin.getDrawable("button");
        txtButtonStyle.down = skin.getDrawable("button_pressed");

        retourJeu = new TextButton("Retour",txtButtonStyle);
        recommencer = new TextButton("Recommencer",txtButtonStyle);
        quitter = new TextButton("Quitter",txtButtonStyle);

        Table tableau = new Table();
        tableau.setFillParent(true);
        tableau.add(retourJeu).padBottom(SPACE_BETWEEN_BUTTONS).width(recommencer.getWidth());
        tableau.row();
        tableau.add(recommencer).padBottom(SPACE_BETWEEN_BUTTONS);
        tableau.row();
        tableau.add(quitter).width(recommencer.getWidth());

        addActor(tableau);
    }

    public TextButton getRetourJeu() {
        return retourJeu;
    }

    public TextButton getRecommencer() {
        return recommencer;
    }

    public TextButton getQuitter() {
        return quitter;
    }

    public void setListener(EventListener listener) {
        retourJeu.addListener(listener);
        recommencer.addListener(listener);
        quitter.addListener(listener);
    }

    @Override
    public void dispose() {
        super.dispose();
    }
}
