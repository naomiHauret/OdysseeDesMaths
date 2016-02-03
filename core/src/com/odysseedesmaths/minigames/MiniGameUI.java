package com.odysseedesmaths.minigames;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Button.ButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.odysseedesmaths.Assets;
import com.odysseedesmaths.Timer;
import com.odysseedesmaths.minigames.arriveeremarquable.entities.Hero;

import java.util.Map;

/**
 * Classe gérant l'interface utilisateur d'un mini-jeu.
 */
//TODO: Redimensionner l'UI pour toutes les tailles d'écran
public class MiniGameUI extends Stage {

    private static final int PAD_ARROW_SIZE = 64;

    private Table table;
    private Skin skin;

    private boolean useHeroHp = false;
    private boolean useTimer = false;
    private boolean usePad = false;
    private boolean useItems = false;

    private Table heroHpGroup;
    private Image heroHp;

    private Label timer;

    private Button pause;

    private Table padGroup;
    private Button padLeft;
    private Button padRight;
    private Button padUp;
    private Button padDown;

    private HorizontalGroup itemsGroup;
    private Image itemImage;
    private Label itemCounter;

    /**
     * Initialise une nouvelle interface avec les ressources nécessaires.
     */
    public MiniGameUI() {
        super();

        table = new Table();
        table.setFillParent(true);
        addActor(table);

        skin = new Skin();
        skin.addRegions(Assets.getManager().get(Assets.UI_ATLAS, TextureAtlas.class));
        skin.add("timer", new LabelStyle(Assets.TIMER, Color.BLACK));
    }

    public Button getPause() {
        return pause;
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

    @Override
    public void dispose() {
        super.dispose();
        skin.dispose();
    }

    /**
     * Construit l'interface suivant les composants utilisés.
     */
    public void build() {
        table.pad(10);
        if (useHeroHp) {
            table.add(heroHpGroup).top().left().expand();
        }
        if (useTimer) {
            table.add(timer).padTop(10).padRight(25).top().right().expand();
        }
        addPause();
        table.row();
        if (usePad) {
            table.add(padGroup).bottom().left();
        }
        if (useItems) {
            table.add(itemsGroup).bottom();
        }
    }

    /**
     * Ajoute des points de vie pour le héros à l'interface.
     *
     * @param aHero Le Hero dont les points de vie sont à afficher
     */
    public void addHeroHp(final Hero aHero) {
        useHeroHp = true;
        heroHpGroup = new Table();
        heroHpGroup.addAction(new Action() {
            @Override
            public boolean act(float delta) {
                heroHpGroup.clearChildren();

                for (int i = 0; i < aHero.getPdv(); i++) {
                    heroHp = new Image(skin.getDrawable("coeur"));
                    heroHpGroup.add(heroHp).padRight(10);
                }

                for (int i = 0; i < Hero.PDV_MAX - aHero.getPdv(); i++) {
                    heroHp = new Image(skin.getDrawable("coeurVide"));
                    heroHpGroup.add(heroHp).padRight(10);
                }

                return false;
            }
        });
    }

    /**
     * Ajoute un timer à l'interface.
     *
     * @param aTimer Le Timer à utiliser
     */
    public void addTimer(final Timer aTimer) {
        useTimer = true;
        timer = new Label(aTimer.toString(), skin, "timer");
        timer.addAction(new Action() {
            @Override
            public boolean act(float delta) {
                timer.setText(aTimer.toString());
                return false;
            }
        });
    }

    /**
     * Ajoute un bouton pause à l'interface.
     */
    private void addPause() {
        ButtonStyle pauseStyle = new ButtonStyle();
        pauseStyle.up = skin.getDrawable("pause");
        pauseStyle.down = skin.getDrawable("pauseTap");
        skin.add("pause", pauseStyle);

        pause = new Button(skin, "pause");

        table.add(pause).top().right();
    }

    /**
     * Ajoute un pad directionnel à l'interface, composé de 4 boutons (flèches) vers les 4
     * principales directions : haut, bas, droite et gauche.
     */
    public void addPad() {
        usePad = true;
        ButtonStyle padStyle;

        padStyle = new ButtonStyle();
        padStyle.up = skin.getDrawable("flecheGauche");
        padStyle.down = skin.getDrawable("flecheGaucheTap");
        skin.add("padLeft", padStyle);

        padStyle = new ButtonStyle();
        padStyle.up = skin.getDrawable("flecheDroite");
        padStyle.down = skin.getDrawable("flecheDroiteTap");
        skin.add("padRight", padStyle);

        padStyle = new ButtonStyle();
        padStyle.up = skin.getDrawable("flecheHaut");
        padStyle.down = skin.getDrawable("flecheHautTap");
        skin.add("padUp", padStyle);

        padStyle = new ButtonStyle();
        padStyle.up = skin.getDrawable("flecheBas");
        padStyle.down = skin.getDrawable("flecheBasTap");
        skin.add("padDown", padStyle);

        padLeft = new Button(skin, "padLeft");
        padRight = new Button(skin, "padRight");
        padUp = new Button(skin, "padUp");
        padDown = new Button(skin, "padDown");

        padGroup = new Table();
        padGroup.add(padUp).colspan(2);
        padGroup.row();
        padGroup.add(padLeft).padRight(PAD_ARROW_SIZE);     // padRight (padding) : espace au centre du pad
        padGroup.add(padRight);
        padGroup.row();
        padGroup.add(padDown).colspan(2);
    }

    /**
     * Ajoute des items actifs à l'interface.
     *
     * @param itemsSprites Les items possibles et leur Sprite.
     * @param itemsActives Les items actifs et leur durée restante.
     * @param <T>          Classe utilisée pour gérer les items.
     */
    public <T> void addItems(final Map<T, Sprite> itemsSprites, final Map<T, Integer> itemsActives) {
        useItems = true;
        itemsGroup = new HorizontalGroup();
        itemsGroup.addAction(new Action() {
            @Override
            public boolean act(float delta) {
                itemsGroup.clearChildren();

                for (Map.Entry<T, Integer> entry : itemsActives.entrySet()) {
                    itemImage = new Image(itemsSprites.get(entry.getKey()));
                    itemsGroup.addActor(itemImage);
                    itemCounter = new Label(entry.getValue().toString(), skin, "timer");
                    itemsGroup.addActor(itemCounter);
                }

                return false;
            }
        });
    }

    /**
     * Associe l'InputListener spécifié aux composants pouvant recevoir un input.
     *
     * @param aListener L'InputListener à associer
     */
    public void setListener(InputListener aListener) {
        if (usePad) {
            padLeft.addListener(aListener);
            padRight.addListener(aListener);
            padUp.addListener(aListener);
            padDown.addListener(aListener);
        }
        pause.addListener(aListener);
    }
}
