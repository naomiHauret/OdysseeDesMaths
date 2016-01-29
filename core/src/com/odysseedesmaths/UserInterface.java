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

/**
 * Classe gérant l'interface utilisateur d'un mini-jeu.
 */
public class UserInterface extends Stage {

    private static final int PAD_ARROW_SIZE = 64;

    private Table table;
    private Skin skin;

    // Points de vie du héros
    private int heroHpMax;
    private Table heroHpGroup;
    private Image heroHp;

    // Timer
    private Timer timer;
    private Label timerLabel;

    // Bouton pause
    private Button pause;

    // Pad directionnel
    private Table padGroup;
    private Button padLeft;
    private Button padRight;
    private Button padUp;
    private Button padDown;

    /**
     * Initialise une nouvelle interface. Les composants de l'interface dépendent des paramètres
     * spécifiés.
     *
     * @param aHeroHpAmount Le nombre de points de vie du héros à afficher. 0 si aucun affichage.
     * @param aTimerAmount  Le temps initial du timer du mini-jeu à afficher. 0 si aucun timer.
     * @param usePad        Vrai si l'interface doit afficher un pad directionnel, faux sinon.
     */
    public UserInterface(int aHeroHpAmount, int aTimerAmount, boolean usePad) {
        super();

        table = new Table();
        table.setFillParent(true);
        addActor(table);

        TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("ui/ui.atlas"));
        skin = new Skin();
        skin.addRegions(atlas);

        // Ajout des composants
        table.pad(10);
        if (aHeroHpAmount > 0) {
            addHeroHp(aHeroHpAmount);
        }
        if (aTimerAmount > 0) {
            addTimer(aTimerAmount);
        }
        addPause();
        table.row();
        if (usePad) {
            addPad();
        }
    }

    public Timer getTimer() {
        return timer;
    }

    public Button getPadLeft() {
        return padLeft;
    }

    public Button getPadRight() {
        return padRight;
    }

    public Button getPadUp() {
        return padUp;
    }

    public Button getPadDown() {
        return padDown;
    }

    public void render() {
        act(Gdx.graphics.getDeltaTime());
        draw();
    }

    public void dispose() {
        super.dispose();
        skin.dispose();
    }

    /**
     * Ajoute des points de vie pour le héros à l'interface.
     * Position : En haut à gauche de l'écran.
     *
     * @param aHeroHpAmount Le nombre maximum de points de vie
     */
    private void addHeroHp(int aHeroHpAmount) {
        heroHpMax = aHeroHpAmount;

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

        table.add(heroHpGroup).top().left().expand();
    }

    /**
     * Met à jour le nombre de points de vie du héros.
     *
     * @param aHpAmount Le nouveau nombre de points de vie
     */
    private void setHeroHp(int aHpAmount) {
        heroHpGroup.clearChildren();

        // Création et ajout des pv pleins
        for (int i = 0; i < aHpAmount; i++) {
            heroHp = new Image(skin.get("heroHp", TextureRegionDrawable.class));
            heroHpGroup.add(heroHp).padRight(10);
        }

        // Création et ajout des pv vides
        for (int i = 0; i < heroHpMax - aHpAmount; i++) {
            heroHp = new Image(skin.get("heroEmptyHp", TextureRegionDrawable.class));
            heroHpGroup.add(heroHp).padRight(10);
        }
    }

    /**
     * Ajoute un timer à l'interface, initialisé avec le temps initial spécifié.
     * Position : En haut à droite de l'écran.
     *
     * @param aTimerAmount Le temps initial du timer
     */
    private void addTimer(int aTimerAmount) {
        timer = new Timer(aTimerAmount);

        // Ajout des ressources nécessaires dans le skin
        skin.add("timer", new LabelStyle(new BitmapFont(Gdx.files.internal("ui/timer.fnt")), Color.WHITE));

        // Création du timer label
        timerLabel = new Label(timer.toString(), skin, "timer");
        timerLabel.addAction(new Action() {
            public boolean act(float delta) {
                timerLabel.setText(timer.toString());
                return false;
            }
        });

        table.add(timerLabel).padTop(10).padRight(25).top().right().expand();
    }

    /**
     * Ajoute un bouton pause à l'interface.
     * Position : En haut à droite de l'écran.
     */
    private void addPause() {
        // Ajout des ressources nécessaires dans le skin
        ButtonStyle pauseStyle = new ButtonStyle();
        pauseStyle.up = new TextureRegionDrawable(skin.getRegion("pause"));
        pauseStyle.down = new TextureRegionDrawable(skin.getRegion("pauseTap"));
        skin.add("pause", pauseStyle);

        // Création du bouton pause
        pause = new Button(skin, "pause");

        table.add(pause).top().right();
    }

    /**
     * Ajoute un pad directionnel à l'interface, composé de 4 boutons (flèches) vers les 4
     * principales directions : haut, bas, droite et gauche.
     * Position : En bas à gauche de l'écran.
     */
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

        table.add(padGroup).bottom().left().expand();
    }
}
