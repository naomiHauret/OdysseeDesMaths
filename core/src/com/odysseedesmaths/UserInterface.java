package com.odysseedesmaths;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Button.ButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.odysseedesmaths.arriveeremarquable.ArriveeGame;

public class UserInterface extends Stage {

    private final int HERO_HP_SIZE = 48;
    private final int TIMER_SIZE = 48;
    private final int PAUSE_SIZE = 64;
    private final int PAD_ARROW_SIZE = 64;

    private Table table;
    private Skin skin;

    // Points de vie du héros
    private int heroHpMax;
    private Table heroHpGroup;
    private Image heroHp;

    // Timer
    public Timer timer;
    private Label timerLabel;

    // Pause
    public Button pause;

    // Pad directionnel
    private Table padGroup;
    public Button padLeft;
    public Button padRight;
    public Button padUp;
    public Button padDown;

    // Items
    private Table itemGroup;

    public UserInterface(int heroHpAmount, int timerAmount, boolean usePad, boolean useItems) {
        super();

        // Initialisation du tableau principal
        table = new Table();
        table.setFillParent(true);
        addActor(table);

        // Initialisation du skin
        TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("ui/ui.atlas"));
        skin = new Skin();
        skin.addRegions(atlas);

        // Ajout des composants
        table.pad(10);
        if (heroHpAmount > 0) {
            addHeroHp(heroHpAmount);
        }
        if (timerAmount > 0) {
            addTimer(timerAmount);
        }
        addPause();
        table.row();
        if (usePad) {
            addPad();
        }
        if (useItems) {
            addItems();
        }
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
    private void addHeroHp(int heroHpAmount) {
        heroHpMax = heroHpAmount;

        // Ajout des ressources nécessaires dans le skin
        skin.add("heroHp", new TextureRegionDrawable(skin.getRegion("coeur")));
        skin.add("heroEmptyHp", new TextureRegionDrawable(skin.getRegion("coeurVide")));

        // Création et ajout des pv
        heroHpGroup = new Table();
        heroHpGroup.addAction(new Action() {
            public boolean act(float delta) {
                setHeroHp(ArriveeGame.get().hero.getPdv());        // A changer (utilise Arrivee)
                return false;
            }
        });

        // Ajout des pv au tableau principal
        table.add(heroHpGroup).size(heroHpMax * (HERO_HP_SIZE + 10), HERO_HP_SIZE).top().left().expand();
    }

    // Met à jour les points de vie du héros
    private void setHeroHp(int hpAmount) {
        // Suppression des anciens pv
        heroHpGroup.clearChildren();

        // Création et ajout des pv pleins
        for (int i=0; i < hpAmount; i++) {
            heroHp = new Image(skin.get("heroHp", TextureRegionDrawable.class));
            heroHpGroup.add(heroHp).padRight(10);
        }

        // Création et ajout des pv vides
        for (int i=0; i < heroHpMax - hpAmount; i++) {
            heroHp = new Image(skin.get("heroEmptyHp", TextureRegionDrawable.class));
            heroHpGroup.add(heroHp).padRight(10);
        }
    }

    // Ajoute un timer
    private void addTimer(int timerAmount) {
        // Initialisation du timer avec la limite de temps du mini-jeu
        timer = new Timer(timerAmount);

        // Ajout des ressources nécessaires dans le skin
        skin.add("timer", new LabelStyle(new BitmapFont(Gdx.files.internal("ui/timer.fnt")), Color.WHITE));     // Color ?

        // Création du timer
        timerLabel = new Label(timer.toString(), skin, "timer");
        timerLabel.addAction(new Action() {
            public boolean act(float delta) {
                timerLabel.setText(timer.toString());
                return false;
            }
        });

        // Ajout du timer au tableau principal
        table.add(timerLabel).size(125, TIMER_SIZE).top().right().expand();
    }

    // Ajoute un bouton pause
    private void addPause() {
        // Ajout des ressources nécessaires dans le skin
        ButtonStyle pauseStyle = new ButtonStyle();
        pauseStyle.up = new TextureRegionDrawable(skin.getRegion("pause"));
        pauseStyle.down = new TextureRegionDrawable(skin.getRegion("pauseTap"));
        skin.add("pause", pauseStyle);

        // Création du bouton pause
        pause = new Button(skin, "pause");

        // Ajout du bouton pause au tableau principal
        table.add(pause).size(PAUSE_SIZE, PAUSE_SIZE).top().right();
    }

    // Ajoute un pad directionnel
    private void addPad() {
        // Ajout des ressources nécessaires dans le skin
        ButtonStyle padStyle;

        padStyle = new ButtonStyle();
        padStyle.up = new TextureRegionDrawable(skin.getRegion("flecheGauche"));
        padStyle.down = new TextureRegionDrawable(skin.getRegion("flecheGaucheTap"));
        skin.add("padLeft", padStyle);

        padStyle = new ButtonStyle();
        padStyle.up = new TextureRegionDrawable(skin.getRegion("flecheDroite"));
        padStyle.down = new TextureRegionDrawable(skin.getRegion("flecheDroiteTap"));
        skin.add("padRight", padStyle);

        padStyle = new ButtonStyle();
        padStyle.up = new TextureRegionDrawable(skin.getRegion("flecheHaut"));
        padStyle.down = new TextureRegionDrawable(skin.getRegion("flecheHautTap"));
        skin.add("padUp", padStyle);

        padStyle = new ButtonStyle();
        padStyle.up = new TextureRegionDrawable(skin.getRegion("flecheBas"));
        padStyle.down = new TextureRegionDrawable(skin.getRegion("flecheBasTap"));
        skin.add("padDown", padStyle);

        // Création des flèches
        padLeft = new Button(skin, "padLeft");
        padRight = new Button(skin, "padRight");
        padUp = new Button(skin, "padUp");
        padDown = new Button(skin, "padDown");

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
    private void addItems() {

    }
}
