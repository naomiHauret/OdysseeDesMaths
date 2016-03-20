package com.odysseedesmaths.dialogs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.odysseedesmaths.Assets;
import com.odysseedesmaths.OdysseeDesMaths;
import com.odysseedesmaths.util.DisplayTextAction;

public abstract class DialogScreen implements Screen {

    private static final int MAX_CHARS = 6;

    protected static final int WIDTH = 560;
    protected static final int HEIGHT = 340;
    private static final int DIALOG_HEIGHT = 125;
    private static final int BUTTON_HEIGHT = 50;

    protected static final BitmapFont FONT_16;
    protected static final BitmapFont FONT_15;
    protected static final BitmapFont FONT_14;
    protected static final BitmapFont FONT_13;
    protected static final BitmapFont FONT_12;
    protected static final BitmapFont FONT_11;
    static {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/PressStart2P.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 16;
        FONT_16 = generator.generateFont(parameter);
        parameter.size = 15;
        FONT_15 = generator.generateFont(parameter);
        parameter.size = 14;
        FONT_14 = generator.generateFont(parameter);
        parameter.size = 13;
        FONT_13 = generator.generateFont(parameter);
        parameter.size = 12;
        FONT_12 = generator.generateFont(parameter);
        parameter.size = 11;
        FONT_11 = generator.generateFont(parameter);
    }

    protected OdysseeDesMaths game;
    private Viewport viewport;

    private Drawable backgroundImage;

    protected Skin skin;
    private Stage stage;
    private Table mainTable;

    private HorizontalGroup leftCharGroup;
    private HorizontalGroup rightCharGroup;
    private Image[] charsImages;

    private Image middleImage;

    protected Table dialogTable;

    protected HorizontalGroup buttonsGroup;
    protected TextButton.TextButtonStyle buttonStyle;

    protected DisplayTextAction displayAction;
    protected static final int DISPLAY_SPEED = 14;

    //TODO: ajouter les SFX
    public DialogScreen(OdysseeDesMaths game) {
        this.game = game;

        viewport = new StretchViewport(WIDTH, HEIGHT);
        stage = new Stage(viewport);
        Gdx.input.setInputProcessor(stage);

        skin = new Skin();
        skin.addRegions(Assets.getManager().get(Assets.UI_SCROLL, TextureAtlas.class));
        skin.addRegions(Assets.getManager().get(Assets.UI_ORANGE, TextureAtlas.class));

        mainTable = new Table();
        mainTable.setFillParent(true);
        stage.addActor(mainTable);

        leftCharGroup = new HorizontalGroup();
        rightCharGroup = new HorizontalGroup();
        leftCharGroup.space(5);
        rightCharGroup.space(5);
        charsImages = new Image[MAX_CHARS];
        for (int i=0; i<MAX_CHARS; i++) {
            charsImages[i] = new Image();
            if (i < MAX_CHARS/2) {
                leftCharGroup.addActor(charsImages[i]);
            } else {
                rightCharGroup.addActor(charsImages[i]);
            }
        }

        middleImage = new Image();
        dialogTable = new Table();
        dialogTable.background(skin.getDrawable("scroll_background"));

        buttonsGroup = new HorizontalGroup();
        buttonsGroup.space(10);

        buttonStyle = new TextButton.TextButtonStyle();
        buttonStyle.up = skin.getDrawable("button");
        buttonStyle.down = skin.getDrawable("button_pressed");
        buttonStyle.font = FONT_11;
        buttonStyle.fontColor = Color.BLACK;

        buildGUI();

        displayAction = new DisplayTextAction();

        //stage.setDebugAll(true);
    }

    public void buildGUI() {
        mainTable.pad(50);

        // Ajout des personnages et de l'image entre eux
        mainTable.add(leftCharGroup).top().left().align(Align.bottom).padLeft(10);
        mainTable.add(middleImage).top().colspan(2).padBottom(20).expandX();
        mainTable.add(rightCharGroup).top().right().align(Align.bottom).padRight(10);
        mainTable.row();

        // Ajout du dialogue
        dialogTable.pad(10);
        mainTable.add(dialogTable).colspan(4).fill().expandX().height(DIALOG_HEIGHT);
        mainTable.row();

        // Ajout des boutons
        mainTable.add(buttonsGroup).colspan(4).padTop(10).height(BUTTON_HEIGHT);
    }

    @Override
    public void show() {
        // cette méthode n'est pas néessaire ici
    }

    @Override
    public void render(float delta) {
        // Effaçage du précédent affichage
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    @Override
    public void pause() {
        // cette méthode n'est pas néessaire ici
    }

    @Override
    public void resume() {
        // cette méthode n'est pas nécessaire ici
    }

    @Override
    public void hide() {
        // cette méthode n'est pas nécessaire ici
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    public void setBackgroundImage(String assetPath) {
        backgroundImage = new SpriteDrawable(new Sprite(Assets.getManager().get(assetPath, Texture.class)));
        mainTable.background(backgroundImage);
    }

    public void setChar(String assetPath, int position) {
        TextureRegion tr = new TextureRegion(Assets.getManager().get(assetPath, Texture.class));
        // Pour les personnages à droites il faut retourner la texture horizontalement
        if (position >= MAX_CHARS/2) {
            tr.flip(true, false);
        }
        charsImages[position].setDrawable(new TextureRegionDrawable(tr));
    }

    public void clearCharAt(int position) {
        charsImages[position].setDrawable(null);
    }

    public void setMiddleImage(String assetPath) {
        middleImage.setDrawable(new SpriteDrawable(new Sprite(Assets.getManager().get(assetPath, Texture.class))));
    }

    public void clearMiddleImage() {
        middleImage.setDrawable(null);
    }

    // On pourrait si besoin rajouter ici des attitudes pour les persos (rire, énerver...)
    public static String getAssetForChar(String charName) {
        String asset = null;
        switch (charName) {
            case "hero": asset = Assets.ICON_HERO; break;
            case "pyles": asset = Assets.ICON_PYLES; break;
            case "audib": asset = Assets.ICON_AUDIB; break;
            case "viktor": asset = Assets.ICON_VIKTOR; break;
            case "phythagore": asset = Assets.ICON_PYTHAGORE; break;
        }
        return asset;
    }

    public static String getAssetFor(String name) {
        String asset = null;
        switch (name) {
            case "menu principal": asset = Assets.MAIN_MENU_BACKGROUND; break;
        }
        return asset;
    }

    public String format(String text) {
        return text.replace("%h", game.getSavesManager().getCurrentSave().getName());
    }
}
