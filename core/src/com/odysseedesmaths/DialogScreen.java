package com.odysseedesmaths;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/*
 * Squelette de test... à modifier sans modération !
 */
public class DialogScreen implements Screen {

    private DialogReader dialogReader;

    private static final int WIDTH = 560;
    private static final int HEIGHT = 340;
    private Viewport viewport;

    private OdysseeDesMaths game;
    private SpriteBatch batch;

    private Texture backgroundImage;

    private Stage stage;
    private Skin skin;
    private Table table;

    private VerticalGroup mainGroup;

    private HorizontalGroup charGroup;
    private Image char1;
    private Image char2;
    private Image middleImage;

    private Stack dialogGroup;
    private Image dialogBackground;
    private TextArea dialogText;

    private HorizontalGroup buttonGroup;
    private Button back;
    private Button next;

    private boolean afficher;
    private boolean conversationActive = false;

    public DialogScreen(OdysseeDesMaths game) {
        viewport = new StretchViewport(WIDTH, HEIGHT);
        stage = new Stage(viewport);
        this.game = game;
        batch = game.batcher;

        TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("test/uiskin.atlas"));
        skin = new Skin();
        skin.addRegions(atlas);

        mainGroup = new VerticalGroup();
        mainGroup.setFillParent(true);
        stage.addActor(mainGroup);

        buildGUI();

        //tmp
        stage.setDebugAll(true);
    }

    public void buildGUI() {
        conversationActive = true;
        mainGroup.pad(10);
        mainGroup.center();

        // Ajout des personnages et de l'image entre eux
        charGroup.addActor(char1);
        charGroup.addActor(middleImage);
        charGroup.addActor(char2);
        mainGroup.addActor(charGroup);

        // Ajout du dialogue
        dialogGroup = new Stack();
        dialogGroup.add(dialogBackground);
        dialogGroup.add(dialogText);
        mainGroup.addActor(dialogGroup);

        // Ajout des boutons
        buttonGroup = new HorizontalGroup();
        buttonGroup.addActor(back);
        buttonGroup.addActor(next);
        mainGroup.addActor(buttonGroup);
    }
/*
    @Override
    public void show(Batch batch, float parent) {
        // Visualisation des personnages et de l'image entre eux

        // Visualisation du dialogue

        // Visualisation des boutons
        if(back.isDisabled()){
            back.getStyle().disabled = Color.lightGray;
                // Le bouton se grisaille s'il n'est pas disponible
        } else if (next.isDisabled()){
            next.getStyle().disabledFontColor = Color.lightGray;
                // Idem
        }
    }*/

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        // Effaçage du précédent affichage
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.draw(backgroundImage, 0, 0);
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
        conversationActive = false;
        stage.dispose();
        skin.dispose();
        // this.clear();
    }
}
