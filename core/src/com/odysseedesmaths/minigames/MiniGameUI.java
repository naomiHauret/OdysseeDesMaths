package com.odysseedesmaths.minigames;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.ColorAction;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Button.ButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
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
import java.util.Observable;
import java.util.Observer;

/**
 * Classe gérant l'interface utilisateur d'un mini-jeu.
 */
// TODO: Redimensionner l'UI pour toutes les tailles d'écran
public class MiniGameUI extends Stage implements Observer {

    private static final int PAD_ARROW_SIZE = 64;

    private Table table;
    private Skin skin;

    private Table north;
    private Table west;
    private Container center;
    private Table east;
    private Table south;

    private boolean usePad = false;

    private Container<Actor> heroHpContainer;
    private Table heroHpGroup;
    private Image heroHp;

    private Container<Actor> bossHpContainer;

    private Container<Actor> timerContainer;
    private Label timer;
    private ColorAction timerColorAction;

    private Container<Actor> pauseContainer;
    private Button pause;

    private Container<Actor> oxygenContainer;

    private Container<Actor> padContainer;
    private Table padGroup;
    private Button padLeft;
    private Button padRight;
    private Button padUp;
    private Button padDown;

    private Container<Actor> itemsContainer;
    private HorizontalGroup itemsGroup;
    private Image itemImage;
    private Label itemCounter;

    private Container<Actor> actionsContainer;

    /**
     * Initialise une nouvelle interface avec les ressources nécessaires.
     */
    public MiniGameUI() {
        table = new Table();
        table.setFillParent(true);
        addActor(table);

        skin = new Skin();
        skin.addRegions(Assets.getManager().get(Assets.UI, TextureAtlas.class));

        north = new Table();
        west = new Table();
        center = new Container();
        east = new Table();
        south = new Table();

        table.pad(10);
        table.add(north).colspan(3).fill();
        table.row();
        table.add(west).fill();
        table.add(center).expand();
        table.add(east).fill();
        table.row();
        table.add(south).colspan(3).fill();

        heroHpContainer = new Container<Actor>();
        bossHpContainer = new Container<Actor>();
        timerContainer = new Container<Actor>();
        pauseContainer = new Container<Actor>();
        oxygenContainer = new Container<Actor>();
        padContainer = new Container<Actor>();
        itemsContainer = new Container<Actor>();
        actionsContainer = new Container<Actor>();

        north.add(heroHpContainer).top().left();
        north.add(bossHpContainer).top().expandX();
        north.add(timerContainer).top().right().padTop(Gdx.graphics.getHeight() / 50).padRight(Gdx.graphics.getHeight() / 50);
        north.add(pauseContainer).top();
        west.add(oxygenContainer).top().left().expandY();
        south.add(padContainer).bottom().left();
        south.add(itemsContainer).bottom().expandX();
        south.add(actionsContainer).bottom().right();

        addPause();
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
     * Ajoute des points de vie pour le héros à l'interface.
     *
     * @param aHero Le Hero dont les points de vie sont à afficher
     */
    public void addHeroHp(final Hero aHero) {
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

        heroHpContainer.setActor(heroHpGroup);
    }

    /**
     * Ajoute un timer à l'interface.
     *
     * @param aTimer Le Timer à utiliser
     */
    public void addTimer(final Timer aTimer) {
        skin.add("timer", new LabelStyle(Assets.TIMER, Color.WHITE));

        aTimer.addObserver(this);
        timerColorAction = new ColorAction();
        timerColorAction.setEndColor(Color.WHITE);
        timerColorAction.setDuration(0.5f);

        timer = new Label(aTimer.toString(), skin, "timer");
        timer.addAction(new Action() {
            @Override
            public boolean act(float delta) {
                timer.setText(aTimer.toString());
                return false;
            }
        });

        timerContainer.setActor(timer);
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

        padContainer.setActor(padGroup);
    }

    /**
     * Ajoute des items actifs à l'interface.
     *
     * @param activeItems Map contenant les sprites des items ainsi que leur compteur.
     */
    public void addItems(final Map<Sprite, Integer> activeItems) {
        skin.add("itemCounter", new LabelStyle(Assets.ITEM_COUNTER, Color.BLACK));

        itemsGroup = new HorizontalGroup();
        itemsGroup.addAction(new Action() {
            @Override
            public boolean act(float delta) {
                itemsGroup.clearChildren();

                for (Map.Entry<Sprite, Integer> entry : activeItems.entrySet()) {
                    itemImage = new Image(entry.getKey());
                    itemsGroup.addActor(itemImage);
                    itemCounter = new Label(entry.getValue().toString(), skin, "itemCounter");
                    itemsGroup.addActor(itemCounter);
                }

                return false;
            }
        });

        itemsContainer.setActor(itemsGroup);
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

    /**
     * Ajoute un bouton pause à l'interface.
     */
    private void addPause() {
        ButtonStyle pauseStyle = new ButtonStyle();
        pauseStyle.up = skin.getDrawable("pause");
        pauseStyle.down = skin.getDrawable("pauseTap");
        skin.add("pause", pauseStyle);

        pause = new Button(skin, "pause");

        pauseContainer.setActor(pause);
    }

    @Override
    public void update(Observable o, Object arg) {
        Integer secondsLeft = (Integer)arg;

        if (secondsLeft <= 20) {
            timer.setColor(Color.RED);
            if (secondsLeft > 0) {
                timerColorAction.reset();
                timer.addAction(timerColorAction);
            }
        }
    }
}
