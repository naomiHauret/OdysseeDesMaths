package com.odysseedesmaths;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Widget;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;

public class UserInterface {

    private static UserInterface ui = null;

    private Stage stage;
    private Table table;

    private Skin skin;

    private WidgetGroup pad;
    private Widget padRight;
    private Widget padLeft;
    private Widget padUp;
    private Widget padDown;

    private UserInterface() {
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        skin = new Skin();

        initPad();
        table.add(pad);
    }

    public static void create() {
        ui = new UserInterface();
    }

    public static UserInterface get() {
        if (ui == null) {
            create();
        }
        return ui;
    }

    public static void render() {
        if (ui != null) {
            ui.stage.act(Gdx.graphics.getDeltaTime());
            ui.stage.draw();
        }
    }

    public static void dispose() {
        if (ui != null) {
            ui.stage.dispose();
            ui.skin.dispose();
        }
    }

    private void initPad() {
        padLeft = new Widget();
        padRight = new Widget();
        padUp = new Widget();
        padDown = new Widget();
        pad = new WidgetGroup(padLeft, padRight, padUp, padDown);
    }
}
