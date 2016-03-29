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

    private TextButton retourJeu, inventaire, quitter;
    private TextButton.TextButtonStyle txtButtonStyle=null;

    public MenuPauseScene() {
        super(10, new Color(255f, 255f, 255f, 1), "fonts/PressStart2P.ttf", new StretchViewport(WIDTH, HEIGHT));

        Skin skin = new Skin();
        skin.addRegions(Assets.getManager().get(Assets.UI_MAIN, TextureAtlas.class));
        skin.addRegions(Assets.getManager().get(Assets.UI_ORANGE, TextureAtlas.class));

        txtButtonStyle = new TextButton.TextButtonStyle();

        txtButtonStyle.font = font;
        txtButtonStyle.up = skin.getDrawable("button");
        txtButtonStyle.down = skin.getDrawable("button_pressed");

        retourJeu = new TextButton("Retour",txtButtonStyle);
        inventaire = new TextButton("Recommencer",txtButtonStyle);
        quitter = new TextButton("Quitter",txtButtonStyle);

        Table tableau = new Table();
        tableau.setFillParent(true);
        //tableau.row().height(Gdx.graphics.getHeight() / 3);
        tableau.add(retourJeu).width(WIDTH / 3).pad(10);
        tableau.getCell(retourJeu).expand();
        tableau.row();
        tableau.add(inventaire).width(WIDTH / 3).pad(10);
        tableau.getCell(inventaire).expand();
        tableau.row();
        tableau.add(quitter).width(WIDTH / 3).pad(10);
        tableau.getCell(quitter).expand();

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
