package com.odysseedesmaths;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Button.ButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class UserInterface extends Stage {

    private final int HERO_HP_SIZE = 48;
    private final int PAUSE_SIZE = 64;
    private final int PAD_ARROW_SIZE = 64;

    private Table table;
    private Skin skin;

    // Points de vie du héros
    private Table heroHpGroup;
    private Image heroHp;
    private Drawable heroHpDrawable;
    private Drawable heroEmptyHpDrawable;
    private int heroHpMax;

    // Pause
    public Button pause;

    // Pad directionnel
    private Table padGroup;
    public Button padLeft;
    public Button padRight;
    public Button padUp;
    public Button padDown;

    // Items

    public UserInterface(int heroHpAmount, boolean usePad, boolean useItems) {
        super();

        table = new Table();
        table.setFillParent(true);
        addActor(table);

        TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("ui.atlas"));
        skin = new Skin();
        skin.addRegions(atlas);

        table.pad(10);
        if (heroHpAmount > 0) {
            heroHpMax = heroHpAmount;
            addHeroHp();
        }
        addPause();
        table.row();
        if (usePad) addPad();
        if (useItems) addItems();
    }

    public void render() {
        act(Gdx.graphics.getDeltaTime());
        draw();
    }

    public void dispose() {
        super.dispose();
        skin.dispose();
    }

    // Ajoute des points de vie pour le héros
    public void addHeroHp() {
        TextureRegion hpRegion = skin.getRegion("coeur");
        TextureRegion emptyHpRegion = skin.getRegion("coeurVide");
        heroHpDrawable = new TextureRegionDrawable(hpRegion);
        heroEmptyHpDrawable = new TextureRegionDrawable(emptyHpRegion);

        // Création et ajout des pv
        heroHpGroup = new Table();
        setHeroHp(heroHpMax);

        // Ajout des pv au tableau principal
        table.add(heroHpGroup).size(heroHpMax * HERO_HP_SIZE + 50, HERO_HP_SIZE).top().left().expand();
    }

    // Met à jour les points de vie du héros
    public void setHeroHp(int hpAmount) {
        // Suppression des anciens pv
        heroHpGroup.clearChildren();

        // Création et ajout des pv pleins
        for (int i=0; i < hpAmount; i++) {
            heroHp = new Image(heroHpDrawable);
            heroHpGroup.add(heroHp).padRight(10);
        }

        // Création et ajout des pv vides
        for (int i=0; i < heroHpMax - hpAmount; i++) {
            heroHp = new Image(heroEmptyHpDrawable);
            heroHpGroup.add(heroHp).padRight(10);
        }
    }

    // Ajoute un bouton pause
    public void addPause() {
        TextureRegion pauseRegion = skin.getRegion("pause");
        TextureRegion pauseDownRegion = skin.getRegion("pauseTap");

        // Création du bouton pause
        ButtonStyle style = new ButtonStyle();
        style.up = new TextureRegionDrawable(pauseRegion);
        style.down = new TextureRegionDrawable(pauseDownRegion);
        pause = new Button(style);

        // Ajout du bouton pause au tableau principal
        table.add(pause).size(PAUSE_SIZE, PAUSE_SIZE).top().right().expand();
    }

    // Ajoute un pad directionnel
    private void addPad() {
        TextureRegion arrowRegion, arrowDownRegion;
        ButtonStyle style;

        // Création de la flèche de gauche
        arrowRegion = skin.getRegion("flecheGauche");
        arrowDownRegion = skin.getRegion("flecheGaucheTap");
        style = new ButtonStyle();
        style.up = new TextureRegionDrawable(arrowRegion);
        style.down = new TextureRegionDrawable(arrowDownRegion);
        padLeft = new Button(style);

        // Création de la flèche de droite
        arrowRegion = skin.getRegion("flecheDroite");
        arrowDownRegion = skin.getRegion("flecheDroiteTap");
        style = new ButtonStyle();
        style.up = new TextureRegionDrawable(arrowRegion);
        style.down = new TextureRegionDrawable(arrowDownRegion);
        padRight = new Button(style);

        // Création de la flèche du haut
        arrowRegion = skin.getRegion("flecheHaut");
        arrowDownRegion = skin.getRegion("flecheHautTap");
        style = new ButtonStyle();
        style.up = new TextureRegionDrawable(arrowRegion);
        style.down = new TextureRegionDrawable(arrowDownRegion);
        padUp = new Button(style);

        // Création de la flèche du bas
        arrowRegion = skin.getRegion("flecheBas");
        arrowDownRegion = skin.getRegion("flecheBasTap");
        style = new ButtonStyle();
        style.up = new TextureRegionDrawable(arrowRegion);
        style.down = new TextureRegionDrawable(arrowDownRegion);
        padDown = new Button(style);

        // Ajout des flèches au pad directionnel
        padGroup = new Table();
        padGroup.add(padUp).colspan(2);
        padGroup.row();
        padGroup.add(padLeft).padRight(PAD_ARROW_SIZE);     // padRight (padding) : espace au centre du pad
        padGroup.add(padRight);
        padGroup.row();
        padGroup.add(padDown).colspan(2);

        // Ajout du pad au tableau principal
        table.add(padGroup).size(3*PAD_ARROW_SIZE, 3*PAD_ARROW_SIZE).bottom().left().expand();
    }

    // Ajoute des items
    public void addItems() {

    }
}
