package com.odysseedesmaths;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/*
 * Squelette de test... à modifier sans modération !
 */
public class DialogScreen implements Screen {

    private OdysseeDesMaths game;
    private static final int WIDTH = 800;
    private static final int HEIGHT = 480;
    private Viewport viewport;

    private BitmapFont font;
    private SpriteBatch batch;

    private Stage stage;
    private Skin skin;
    private Table table;

    private Image backgroundImage;
    private Image char1;
    private Image char2;
    private Image middleImage;

    private Image dialogBackground;
    private Label text;
    private Button back;
    private Button next;

    private boolean afficher;

    public DialogScreen(OdysseeDesMaths game) {
        viewport = new StretchViewport(WIDTH, HEIGHT);
        stage = new Stage(viewport);
        batch = new SpriteBatch();

        this.game = game;

        TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("test/uiskin.atlas"));
        skin = new Skin();
        skin.addRegions(atlas);

        table = new Table();
        table.setFillParent(true);
        stage.addActor(table);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        // Effaçage du précédent affichage
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        //font.draw(batch,consigne,10,200);
        batch.end();
        stage.act(Gdx.graphics.getDeltaTime());
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
}
