package com.odysseedesmaths.menus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton.ImageButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class Menu implements Screen {
    private ImageButton musique;
    private ImageButton son;

    public Menu() {
        Skin skinMusique = new Skin();
        skinMusique.add("image", Gdx.files.internal("music64.png"));
        ImageButtonStyle bsMusique = new ImageButton.ImageButtonStyle();
        bsMusique.imageChecked = skinMusique.getDrawable("image");
    }

    @Override
    public void show() {
        // TODO
    }

    @Override
    public void render(float delta) {
        // TODO
    }

    @Override
    public void resize(int width, int height) {
        // TODO
    }

    @Override
    public void pause() {
        // TODO
    }

    @Override
    public void resume() {
        // TODO
    }

    @Override
    public void hide() {
        // TODO
    }

    @Override
    public void dispose() {
        // TODO
    }
}