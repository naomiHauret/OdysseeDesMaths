package com.odysseedesmaths.menus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Button.ButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.odysseedesmaths.Assets;
import com.odysseedesmaths.OdysseeDesMaths;

/*
  classe de mise en place de sélection de sauvegarde
*/

public class SaveSelection implements Screen {

    private static final int WIDTH = 800;
    private static final int HEIGHT = 480;
    private static final int SPACE_BETWEEN_SAVES = 20;
    private static final int BUTTON_PAD = 20;
    private static final BitmapFont TITLE;
    private static final BitmapFont TEXT;

    private OdysseeDesMaths game;

    private Viewport viewport;
    private Stage stage;
    private Table table;
    private Skin skin;

    private Label gameTitle;

    private Button save1;
    private Button deleteSave1;
    private Button save2;
    private Button deleteSave2;
    private Button save3;
    private Button deleteSave3;

    private AudioButtons audioButtons;
    private Button returnButton;

    public SaveSelection(OdysseeDesMaths game) {
        this.game = game;

        viewport = new StretchViewport(WIDTH, HEIGHT);
        stage = new Stage(viewport);
        Gdx.input.setInputProcessor(stage);

        table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        skin = new Skin();
        skin.addRegions(Assets.getManager().get(Assets.UI_MAIN, TextureAtlas.class));
        skin.addRegions(Assets.getManager().get(Assets.UI_ORANGE, TextureAtlas.class));
        skin.add("background", Assets.getManager().get(Assets.MAIN_MENU_BACKGROUND, Texture.class));
        skin.add("title", new LabelStyle(TITLE, null));
        skin.add("text", new LabelStyle(TEXT, null));
        skin.add("hero", Assets.getManager().get(Assets.HERO, Texture.class));

        ButtonStyle buttonStyle = new ButtonStyle();
        buttonStyle.up = skin.getDrawable("button");
        buttonStyle.down = skin.getDrawable("button_pressed");
        skin.add("saveButton", buttonStyle);

        buttonStyle = new ButtonStyle();
        buttonStyle.up = skin.getDrawable("cross");
        skin.add("deleteButton", buttonStyle);

        ButtonStyle returnButtonStyle = new ButtonStyle();
        returnButtonStyle.up = skin.getDrawable("return");
        skin.add("returnButton", returnButtonStyle);

        gameTitle = new Label("L'Odyssée des Maths", skin, "title");

        Label saveName;
        Image hero;
        Label saveCompletion;
        Image newGamePlus;

        save1 = new Button(skin, "saveButton");
        save1.pad(BUTTON_PAD);
        if (game.getSavesManager().getSave1().isEmpty()) {
            newGamePlus = new Image(skin.getDrawable("plus"));
            save1.add(newGamePlus);
        } else {
            saveName = new Label(game.getSavesManager().getSave1().getName(), skin, "text");
            hero = new Image(skin.getDrawable("hero"));
            saveCompletion = new Label(game.getSavesManager().getSave1().getCompletion()+"%", skin, "text");
            save1.add(saveName).top();
            save1.row();
            save1.add(hero).expand();
            save1.row();
            save1.add(saveCompletion).bottom();
        }

        save2 = new Button(skin, "saveButton");
        save2.pad(BUTTON_PAD);
        if (game.getSavesManager().getSave2().isEmpty()) {
            newGamePlus = new Image(skin.getDrawable("plus"));
            save2.add(newGamePlus);
        } else {
            saveName = new Label(game.getSavesManager().getSave2().getName(), skin, "text");
            hero = new Image(skin.getDrawable("hero"));
            saveCompletion = new Label(game.getSavesManager().getSave2().getCompletion()+"%", skin, "text");
            save2.add(saveName).top();
            save2.row();
            save2.add(hero).expand();
            save2.row();
            save2.add(saveCompletion).bottom();
        }

        save3 = new Button(skin, "saveButton");
        save3.pad(BUTTON_PAD);
        if (game.getSavesManager().getSave3().isEmpty()) {
            newGamePlus = new Image(skin.getDrawable("plus"));
            save3.add(newGamePlus);
        } else {
            saveName = new Label(game.getSavesManager().getSave3().getName(), skin, "text");
            hero = new Image(skin.getDrawable("hero"));
            saveCompletion = new Label(game.getSavesManager().getSave3().getCompletion()+"%", skin, "text");
            save3.add(saveName).top();
            save3.row();
            save3.add(hero).expand();
            save3.row();
            save3.add(saveCompletion).bottom();
        }

        deleteSave1 = new Button(skin, "deleteButton");
        deleteSave2 = new Button(skin, "deleteButton");
        deleteSave3 = new Button(skin, "deleteButton");
        audioButtons = new AudioButtons();
        returnButton = new Button(skin, "returnButton");

        table.setBackground(skin.getDrawable("background"));
        table.padTop(HEIGHT / 13);
        table.add(gameTitle).colspan(3).top().padBottom(HEIGHT / 9).expandX();
        table.row().width(WIDTH / 4).height(HEIGHT * 2 / 5);
        table.add(save1).fill().padLeft(40);
        table.add(save2).fill().padLeft(SPACE_BETWEEN_SAVES).padRight(SPACE_BETWEEN_SAVES);
        table.add(save3).fill().padRight(40);
        table.row().top().expandY();
        table.add(deleteSave1).padLeft(40);
        table.add(deleteSave2);
        table.add(deleteSave3).padRight(40);
        table.row();
        table.add(audioButtons).left();
        table.add(returnButton).colspan(2).right();

        deleteSave1.setVisible(!game.getSavesManager().getSave1().isEmpty());
        deleteSave2.setVisible(!game.getSavesManager().getSave2().isEmpty());
        deleteSave3.setVisible(!game.getSavesManager().getSave3().isEmpty());

        SaveSelectionListener listener = new SaveSelectionListener();
        save1.addListener(listener);
        save2.addListener(listener);
        save3.addListener(listener);
        deleteSave1.addListener(listener);
        deleteSave2.addListener(listener);
        deleteSave3.addListener(listener);
        returnButton.addListener(listener);
    }

    @Override
    public void show() {
        // cette méthode n'est pas nécessaire ici
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    @Override
    public void pause() {
        // cette méthode n'est pas nécessaire ici
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
        skin.dispose();
    }

    private class SaveSelectionListener extends InputListener {
        @Override
        public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
            return true;
        }

        @Override
        public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
            Actor source = event.getTarget();

            if (source == save1) {
                game.getSavesManager().setCurrentSave(game.getSavesManager().getSave1());
                game.startGame();
            } else if (source == save2) {
                game.getSavesManager().setCurrentSave(game.getSavesManager().getSave2());
                game.startGame();
            } else if (source == save3) {
                game.getSavesManager().setCurrentSave(game.getSavesManager().getSave3());
                game.startGame();
            } else if (source == deleteSave1) {
                game.getSavesManager().getSave1().reset();
                game.setScreen(new SaveSelection(game));
            } else if (source == deleteSave2) {
                game.getSavesManager().getSave2().reset();
                game.setScreen(new SaveSelection(game));
            } else if (source == deleteSave3) {
                game.getSavesManager().getSave3().reset();
                game.setScreen(new SaveSelection(game));
            } else if (source == returnButton) {
                game.setScreen(new MenuPrincipal(game));
            }
        }
    }

    static {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Assets.KENPIXEL_BLOCKS);
        FreeTypeFontParameter parameter = new FreeTypeFontParameter();
        parameter.size = HEIGHT / 9;
        TITLE = generator.generateFont(parameter);

        generator = new FreeTypeFontGenerator(Assets.PRESS_START_2P);
        parameter = new FreeTypeFontParameter();
        parameter.size = HEIGHT / 20;
        parameter.color = Color.WHITE;
        TEXT = generator.generateFont(parameter);
    }
}
