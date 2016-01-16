package com.odysseedesmaths;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Button.ButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class UserInterface {

    private static UserInterface ui = null;

    private Stage stage;
    private Table table;

    private Skin skin;

    private Table pad;
    private Button padLeft;
    private Button padRight;
    private Button padUp;
    private Button padDown;

    private UserInterface() {
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("ui/ui.atlas"));
        skin = new Skin();
        skin.addRegions(atlas);

        addPad();
    }

    public static void create() {
        ui = new UserInterface();
    }

    public static UserInterface get() {
        if (ui == null) {
            create();
        }
        return ui;
    }

    public static void render() {
        if (ui != null) {
            ui.stage.act(Gdx.graphics.getDeltaTime());
            ui.stage.draw();
        }
    }

    public static void dispose() {
        if (ui != null) {
            ui.stage.dispose();
            ui.skin.dispose();
        }
    }

    private void addPad() {
        TextureRegion arrow, arrowFull;
        ButtonStyle style;

        // Création flèche de gauche
        arrow = skin.getRegion("flecheGauche");
        arrowFull = skin.getRegion("flecheGauchePleine");
        style = new ButtonStyle();
        style.up = new TextureRegionDrawable(arrow);
        style.down = new TextureRegionDrawable(arrowFull);
        padLeft = new Button(style);

        // Création flèche de droite
        arrow = skin.getRegion("flecheDroite");
        arrowFull = skin.getRegion("flecheDroitePleine");
        style = new ButtonStyle();
        style.up = new TextureRegionDrawable(arrow);
        style.down = new TextureRegionDrawable(arrowFull);
        padRight = new Button(style);

        // Création flèche du haut
        arrow = skin.getRegion("flecheHaut");
        arrowFull = skin.getRegion("flecheHautPleine");
        style = new ButtonStyle();
        style.up = new TextureRegionDrawable(arrow);
        style.down = new TextureRegionDrawable(arrowFull);
        padUp = new Button(style);

        // Création flèche du bas
        arrow = skin.getRegion("flecheBas");
        arrowFull = skin.getRegion("flecheBasPleine");
        style = new ButtonStyle();
        style.up = new TextureRegionDrawable(arrow);
        style.down = new TextureRegionDrawable(arrowFull);
        padDown = new Button(style);

        // Ajout des flèches au pad
        pad = new Table();
        pad.add(padUp).colspan(2);
        pad.row();
        pad.add(padLeft).padRight(64);
        pad.add(padRight);
        pad.row();
        pad.add(padDown).colspan(2);

        // Ajout du pad au tableau principal
        table.add(pad).size(192, 192).padLeft(10).padBottom(10).bottom().left().expand();
    }
}
