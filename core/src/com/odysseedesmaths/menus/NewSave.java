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
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldStyle;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.odysseedesmaths.Assets;
import com.odysseedesmaths.OdysseeDesMaths;

public class NewSave implements Screen {
    private OdysseeDesMaths game;

    private static final int WIDTH = 800;
    private static final int HEIGHT = 480;
    private Viewport viewport;

    private static final BitmapFont TITLE;
    private static final BitmapFont TEXT;

    private Stage stage;
    private Table table;
    private Skin skin;

    private Label gameTitle;
    private Label saveNameLabel;
    private TextField saveNameField;
    private TextButton submit;

    public NewSave(OdysseeDesMaths game) {
        this.game = game;

        viewport = new StretchViewport(WIDTH, HEIGHT);
        stage = new Stage(viewport);
        Gdx.input.setInputProcessor(stage);

        table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        skin = new Skin();
        skin.addRegions(Assets.getManager().get(Assets.UI_MAIN, TextureAtlas.class));
        skin.addRegions(Assets.getManager().get(Assets.UI_GREY, TextureAtlas.class));
        skin.add("background", Assets.getManager().get(Assets.MAIN_MENU_BACKGROUND, Texture.class));
        skin.add("title", new Label.LabelStyle(TITLE, null));
        skin.add("text", new Label.LabelStyle(TEXT, null));

        TextButtonStyle textButtonStyle = new TextButtonStyle();
        textButtonStyle.font = TEXT;
        textButtonStyle.up = skin.getDrawable("button");
        textButtonStyle.down = skin.getDrawable("button_pressed");
        skin.add("textButton", textButtonStyle);

        TextFieldStyle textFieldStyle = new TextFieldStyle();
        textFieldStyle.font = TEXT;
        textFieldStyle.fontColor = Color.WHITE;
        textFieldStyle.background = skin.getDrawable("field");
        skin.add("textField", textFieldStyle);

        gameTitle = new Label("L'Odyssée des Maths", skin, "title");
        saveNameLabel = new Label("Nom:", skin, "text");
        saveNameField = new TextField("", skin, "textField");
        saveNameField.setMaxLength(7);
        submit = new TextButton("C'est parti !", skin, "textButton");

        table.setBackground(skin.getDrawable("background"));
        table.padTop(HEIGHT / 13);
        table.add(gameTitle).top().colspan(2).padBottom(HEIGHT / 7);
        table.row().padBottom(HEIGHT / 10);
        table.add(saveNameLabel).right().padRight(10);
        table.add(saveNameField).left().width(WIDTH / 4).height(HEIGHT / 10);
        table.row();
        table.add(submit).top().colspan(2).expandY();

        NewSaveListener listener = new NewSaveListener();
        submit.addListener(listener);
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

    private class NewSaveListener extends InputListener {
        @Override
        public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
            return true;
        }

        @Override
        public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
            Actor source = event.getTarget();

            if (source == submit.getLabel()) {
                if (saveNameField.getText().equals("")) {
                    // TODO
                } else {
                    game.getSavesManager().getCurrentSave().setName(saveNameField.getText());
                    game.startGame();
                }
            }
        }
    }

    static {
        FreeTypeFontGenerator generator;
        FreeTypeFontParameter parameter;

        generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/kenpixel_blocks.ttf"));

        parameter = new FreeTypeFontParameter();
        parameter.size = HEIGHT / 9;
        TITLE = generator.generateFont(parameter);

        generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/PressStart2P.ttf"));

        parameter = new FreeTypeFontParameter();
        parameter.size = HEIGHT / 20;
        parameter.color = Color.WHITE;
        TEXT = generator.generateFont(parameter);
    }
}
