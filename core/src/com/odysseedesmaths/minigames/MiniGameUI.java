package com.odysseedesmaths.minigames;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.ColorAction;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Button.ButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton.ImageButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.odysseedesmaths.Assets;
import com.odysseedesmaths.Timer;
import com.odysseedesmaths.minigames.arriveeremarquable.entities.Hero;

import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

/**
 * Classe gérant l'interface utilisateur d'un mini-jeu.
 */
public class MiniGameUI extends Stage implements Observer {

    private static final int WIDTH = 800;
    private static final int HEIGHT = 480;
    private static final int PAD_CENTER = 64;
    private static final BitmapFont TIMER;
    private static final BitmapFont ITEM_COUNTER;
    public enum PAD_TYPE {FULL, HORIZONTAL};

    private Table table;
    private Skin skin;

    private Table north;
    private Table west;
    private Container center;
    private Table east;
    private Table south;

    private boolean useButtonA = false;
    private boolean useButtonB = false;

    private Container<Actor> heroHpContainer;
    private Table heroHpGroup;
    private Image[] heroHps;

    private Container<Actor> bossHpContainer;

    private Container<Actor> timerContainer;
    private Label timerLabel;
    private ColorAction timerColorAction;

    private Container<Actor> pauseContainer;
    private ImageButton pause;

    private Container<Actor> oxygenContainer;

    private Container<Actor> padContainer;
    private Table padGroup;
    private Button padLeft;
    private Button padRight;
    private Button padUp;
    private Button padDown;
    private PAD_TYPE padType;

    private Container<Actor> itemsContainer;
    private Table itemsGroup;
    private Image itemImage;
    private Label itemCounter;
    private Map<Sprite, Integer> activeItems;

    private Container<Actor> buttonAContainer;
    private Button buttonA;
    private Container<Actor> buttonBContainer;
    private Button buttonB;

    /**
     * Initialise une nouvelle interface avec les ressources nécessaires.
     */
    public MiniGameUI() {
        super(new StretchViewport(WIDTH, HEIGHT));

        table = new Table();
        table.setFillParent(true);
        addActor(table);

        skin = new Skin();
        skin.addRegions(Assets.getManager().get(Assets.UI_MAIN, TextureAtlas.class));
        skin.addRegions(Assets.getManager().get(Assets.UI_ORANGE, TextureAtlas.class));
        skin.add("heart", Assets.getManager().get(Assets.HEART, Texture.class));
        skin.add("heart_empty", Assets.getManager().get(Assets.HEART_EMPTY, Texture.class));
        skin.add("timer", TIMER);
        skin.add("itemCounter", ITEM_COUNTER);

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

        heroHpContainer = new Container<>();
        bossHpContainer = new Container<>();
        timerContainer = new Container<>();
        pauseContainer = new Container<>();
        oxygenContainer = new Container<>();
        padContainer = new Container<>();
        itemsContainer = new Container<>();
        buttonAContainer = new Container<>();
        buttonBContainer = new Container<>();

        north.add(heroHpContainer).top().left();
        north.add(bossHpContainer).top().expandX();
        north.add(timerContainer).right();
        north.add(pauseContainer).top();
        west.add(oxygenContainer).top().left().expandY();
        south.add(padContainer).bottom().left();
        south.add(itemsContainer).bottom().expandX();
        south.add(buttonAContainer).bottom().right();
        south.add(buttonBContainer).bottom().right();

        addPause();
    }

    /*
      mise en action des boutons

      @return bouton concerné par l'action
    */
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

    public Button getButtonA() {
        return buttonA;
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
        heroHps = new Image[Hero.PDV_MAX];
        for (int i = 0; i < heroHps.length; i++) {
            heroHps[i] = new Image(skin.getDrawable("heart"));
        }

        heroHpGroup = new Table();
        heroHpGroup.defaults().space(10);
        heroHpGroup.addAction(new Action() {
            @Override
            public boolean act(float delta) {
                heroHpGroup.clearChildren();
                String sprite;

                for (int i = 0; i < heroHps.length; i++) {
                    sprite = i < aHero.getPdv() ? "heart" : "heart_empty";
                    heroHps[i].setDrawable(skin.getDrawable(sprite));
                    heroHpGroup.add(heroHps[i]);
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
        LabelStyle timerStyle = new LabelStyle();
        timerStyle.font = skin.getFont("timer");
        timerStyle.background = skin.getDrawable("frame_left");
        skin.add("timer", timerStyle);

        aTimer.addObserver(this);
        timerColorAction = new ColorAction();
        timerColorAction.setEndColor(Color.WHITE);
        timerColorAction.setDuration(0.5f);
        timerLabel = new Label(aTimer.toString(), skin, "timer");

        timerContainer.setActor(timerLabel);
        timerContainer.height(48);
    }

    /**
     * Ajoute un pad directionnel à l'interface, composé de 4 boutons (flèches) vers les 4
     * principales directions : haut, bas, droite et gauche.
     */
    public void addPad(PAD_TYPE type) {
        padType = type;
        ButtonStyle padStyle;

        padStyle = new ButtonStyle();
        padStyle.up = skin.getDrawable("arrow_left");
        padStyle.down = skin.getDrawable("arrow_left_pressed");
        skin.add("padLeft", padStyle);

        padStyle = new ButtonStyle();
        padStyle.up = skin.getDrawable("arrow_right");
        padStyle.down = skin.getDrawable("arrow_right_pressed");
        skin.add("padRight", padStyle);

        padLeft = new Button(skin, "padLeft");
        padRight = new Button(skin, "padRight");

        if (padType == PAD_TYPE.FULL) {
            padStyle = new ButtonStyle();
            padStyle.up = skin.getDrawable("arrow_up");
            padStyle.down = skin.getDrawable("arrow_up_pressed");
            skin.add("padUp", padStyle);

            padStyle = new ButtonStyle();
            padStyle.up = skin.getDrawable("arrow_down");
            padStyle.down = skin.getDrawable("arrow_down_pressed");
            skin.add("padDown", padStyle);

            padUp = new Button(skin, "padUp");
            padDown = new Button(skin, "padDown");
        }

        padGroup = new Table();
        if (padType == PAD_TYPE.FULL) {
            padGroup.add(padUp).colspan(2);
            padGroup.row();
            padGroup.add(padLeft).padRight(PAD_CENTER);
            padGroup.add(padRight);
            padGroup.row();
            padGroup.add(padDown).colspan(2);
        } else {
            padGroup.add(padLeft).padRight(PAD_CENTER / 2);
            padGroup.add(padRight);
        }


        padContainer.setActor(padGroup);
    }

    /**
     * Ajoute des items actifs à l'interface.
     */
    public void addItems() {
        skin.add("itemCounter", new LabelStyle(skin.getFont("itemCounter"), null));

        activeItems = new HashMap<Sprite, Integer>();
        itemsGroup = new Table();
        itemsGroup.addAction(new Action() {
            @Override
            public boolean act(float delta) {
                itemsGroup.clearChildren();

                for (Map.Entry<Sprite, Integer> entry : activeItems.entrySet()) {
                    itemImage = new Image(entry.getKey());
                    itemsGroup.add(itemImage).padRight(Gdx.graphics.getWidth() / 50);
                    itemCounter = new Label(entry.getValue().toString(), skin, "itemCounter");
                    itemsGroup.add(itemCounter).padRight(Gdx.graphics.getWidth() / 15);
                }

                return false;
            }
        });

        itemsContainer.setActor(itemsGroup);
    }

    /**
     * Ajoute ou met à jour un item actif.
     *
     * @param itemSprite    Le sprite correspondant
     * @param itemCounter   La durée restante
     */
    public void addActiveItem(Sprite itemSprite, Integer itemCounter) {
        activeItems.put(itemSprite, itemCounter);
    }

    /**
     * Vide la liste des items actifs.
     */
    public void resetActiveItems() {
        activeItems.clear();
    }

    /**
     * Ajoute un bouton d'action "A" à l'interface.
     */
    public void addButtonA() {
        useButtonA = true;
        ButtonStyle buttonStyle = new ButtonStyle();
        buttonStyle.up = skin.getDrawable("button");
        buttonStyle.down = skin.getDrawable("button_pressed");
        skin.add("buttonA", buttonStyle);

        buttonA = new Button(skin, "buttonA");

        buttonAContainer.setActor(buttonA);
        buttonAContainer.size(64, 64);
    }

    /**
     * Ajoute un bouton d'action "B" à l'interface.
     */
    public void addButtonB() {
        useButtonB = true;

        ButtonStyle buttonStyle = new ButtonStyle();
        buttonStyle.up = skin.getDrawable("button");
        buttonStyle.down = skin.getDrawable("button_pressed");
        skin.add("buttonB", buttonStyle);

        buttonB = new Button(skin, "buttonB");

        buttonBContainer.setActor(buttonB);
        buttonBContainer.size(64, 64).padLeft(20);
    }

    /**
     * Associe l'InputListener spécifié aux composants pouvant recevoir un input.
     *
     * @param aListener L'InputListener à associer
     */
    public void setListener(InputListener aListener) {
        if (padContainer.hasChildren()) {
            padLeft.addListener(aListener);
            padRight.addListener(aListener);
            if (padType == PAD_TYPE.FULL) {
                padUp.addListener(aListener);
                padDown.addListener(aListener);
            }
        }
        if (useButtonA) buttonA.addListener(aListener);
        if (useButtonB) buttonB.addListener(aListener);
        pause.addListener(aListener);
    }

    /**
     * Ajoute un bouton pause à l'interface.
     */
    private void addPause() {
        ImageButtonStyle pauseStyle = new ImageButtonStyle();
        pauseStyle.up = skin.getDrawable("button");
        pauseStyle.imageUp = skin.getDrawable("pause");
        pauseStyle.down = skin.getDrawable("button_pressed");
        skin.add("pause", pauseStyle);

        pause = new ImageButton(skin, "pause");

        pauseContainer.setActor(pause);
        pauseContainer.size(64, 64);
    }

    @Override
    public void update(Observable o, Object arg) {
        Timer timer = (Timer)o;

        timerLabel.setText(timer.toString());
        if (timer.getSecondsLeft() <= 20) {
            timerLabel.setColor(Color.RED);
            timerColorAction.reset();
            timerLabel.addAction(timerColorAction);
        }
    }

    static {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Assets.PRESS_START_2P);
        FreeTypeFontParameter parameter = new FreeTypeFontParameter();
        parameter.size = HEIGHT / 20;
        parameter.color = Color.WHITE;
        TIMER = generator.generateFont(parameter);
        ITEM_COUNTER = generator.generateFont(parameter);
    }
}
