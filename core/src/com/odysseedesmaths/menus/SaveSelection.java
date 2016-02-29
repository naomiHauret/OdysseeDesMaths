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

public class SaveSelection implements Screen {

    private OdysseeDesMaths game;

    private static final int WIDTH = 800;
    private static final int HEIGHT = 480;
    private Viewport viewport;

    private static final int SPACE_BETWEEN_SAVES = 20;
    private static final int SAVE_PAD = 10;

    private static final BitmapFont TITLE;
    private static final BitmapFont TEXT;

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

    public SaveSelection(OdysseeDesMaths game) {
        this.game = game;

        viewport = new StretchViewport(WIDTH, HEIGHT);
        stage = new Stage(viewport);
        Gdx.input.setInputProcessor(stage);

        table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        skin = new Skin();
        skin.addRegions(Assets.getManager().get(Assets.UI_TEST, TextureAtlas.class));
        skin.add("background", Assets.getManager().get(Assets.MAIN_MENU_BACKGROUND, Texture.class));
        skin.add("title", new LabelStyle(TITLE, null));
        skin.add("text", new LabelStyle(TEXT, null));
        skin.add("hero", Assets.getManager().get(Assets.HERO, Texture.class));
        skin.add("cross", Assets.getManager().get(Assets.CROSS), Texture.class);
        skin.add("plus", Assets.getManager().get(Assets.PLUS), Texture.class);

        ButtonStyle buttonStyle = new ButtonStyle();
        buttonStyle.up = skin.getDrawable("default-round");
        buttonStyle.down = skin.getDrawable("default-round-down");
        skin.add("saveButton", buttonStyle);

        buttonStyle = new ButtonStyle();
        buttonStyle.up = skin.getDrawable("cross");
        skin.add("deleteButton", buttonStyle);

        gameTitle = new Label("L'Odyssée des Maths", skin, "title");

        Label saveName;
        Image hero;
        Label saveCompletion;
        Image newGamePlus;

        save1 = new Button(skin, "saveButton");
        save1.pad(SAVE_PAD);
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
        save2.pad(SAVE_PAD);
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
        save3.pad(SAVE_PAD);
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

        table.setBackground(skin.getDrawable("background"));
        table.padTop(HEIGHT / 10);
        table.add(gameTitle).top().colspan(3).padBottom(HEIGHT / 7);
        table.row().width(WIDTH / 4).height(HEIGHT * 2 / 5);
        table.add(save1).fill();
        table.add(save2).fill().padLeft(SPACE_BETWEEN_SAVES).padRight(SPACE_BETWEEN_SAVES);
        table.add(save3).fill();
        table.row().top().expandY();
        table.add(deleteSave1);
        table.add(deleteSave2);
        table.add(deleteSave3);

        deleteSave1.setVisible(!game.getSavesManager().getSave1().isEmpty());
        deleteSave2.setVisible(!game.getSavesManager().getSave2().isEmpty());
        deleteSave3.setVisible(!game.getSavesManager().getSave3().isEmpty());

        InputEcouteur listener = new InputEcouteur();
        save1.addListener(listener);
        save2.addListener(listener);
        save3.addListener(listener);
        deleteSave1.addListener(listener);
        deleteSave2.addListener(listener);
        deleteSave3.addListener(listener);
    }

    @Override
    public void show() {

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

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        stage.dispose();
        skin.dispose();
    }

    private class InputEcouteur extends InputListener {
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
            }
        }
    }

    static {
        FreeTypeFontGenerator generator;
        FreeTypeFontParameter parameter;

        generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/PressStart2P.ttf"));

        parameter = new FreeTypeFontParameter();
        parameter.size = HEIGHT / 15;
        parameter.color = Color.WHITE;
        TITLE = generator.generateFont(parameter);

        parameter = new FreeTypeFontParameter();
        parameter.size = HEIGHT / 20;
        parameter.color = Color.WHITE;
        TEXT = generator.generateFont(parameter);
    }
}
