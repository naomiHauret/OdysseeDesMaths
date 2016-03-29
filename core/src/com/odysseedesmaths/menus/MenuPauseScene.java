package com.odysseedesmaths.menus;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.odysseedesmaths.Assets;

/*
        Classe type de l'affichage du menu pause durant une scène
 */

public class MenuPauseScene extends Menu {
    private static final int WIDTH = 800;
    private static final int HEIGHT = 480;
    private static final int SPACE_BETWEEN_BUTTONS = HEIGHT / 15;

    private TextButton retourJeu, inventaire, quitter;
    private TextButton.TextButtonStyle txtButtonStyle=null;
    private AudioButtons audioButtons;

    public MenuPauseScene() {
        super(22, new Color(255f, 255f, 255f, 1), "fonts/PressStart2P.ttf", new StretchViewport(WIDTH, HEIGHT));

        Skin skin = new Skin();
        skin.addRegions(Assets.getManager().get(Assets.UI_MAIN, TextureAtlas.class));
        skin.addRegions(Assets.getManager().get(Assets.UI_ORANGE, TextureAtlas.class));

        txtButtonStyle = new TextButton.TextButtonStyle();

        txtButtonStyle.font = font;
        txtButtonStyle.up = skin.getDrawable("button");
        txtButtonStyle.down = skin.getDrawable("button_pressed");

        retourJeu = new TextButton("Retour",txtButtonStyle);
        inventaire = new TextButton("Inventaire",txtButtonStyle);
        quitter = new TextButton("Quitter",txtButtonStyle);
        audioButtons = new AudioButtons();

        Table tableau = new Table();
        tableau.setFillParent(true);
        tableau.setBackground(skin.getDrawable("dark_background"));
        tableau.pad(10);
        tableau.add(retourJeu).size(256, 64).padBottom(SPACE_BETWEEN_BUTTONS).bottom().expand();
        tableau.row();
        tableau.add(inventaire).size(256, 64).padBottom(SPACE_BETWEEN_BUTTONS);
        tableau.row();
        tableau.add(quitter).size(256, 64);
        tableau.row();
        tableau.add(audioButtons).bottom().left().expand();

        addActor(tableau);
    }

    public TextButton getRetourJeu() {
        return retourJeu;
    }

    public TextButton getInventaire() {
        return inventaire;
    }

    public TextButton getQuitter() {
        return quitter;
    }

    public void setListener(EventListener listener) {
        retourJeu.addListener(listener);
        inventaire.addListener(listener);
        quitter.addListener(listener);
    }

    @Override
    public void dispose() {
        super.dispose();
    }
}
