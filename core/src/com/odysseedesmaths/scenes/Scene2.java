package com.odysseedesmaths.scenes;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class Scene2 extends Scene {

    Texture background;

    public Scene2 () {
        background = new Texture(Gdx.files.internal("scenes/scene2.png"));
    }

    @Override
    public Texture getBackground() {
        return background;
    }

    @Override
    public void aventure() {

    }
}
