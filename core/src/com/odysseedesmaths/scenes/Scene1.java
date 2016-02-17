package com.odysseedesmaths.scenes;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.odysseedesmaths.minigames.arriveeremarquable.ArriveeRemarquable;

public class Scene1 extends Scene {

    Texture background;

    public Scene1 () {
        background = new Texture(Gdx.files.internal("scenes/scene1.png"));
    }

    @Override
    public Texture getBackground() {
        return background;
    }

    @Override
    public void aventure() {
        getMss().getJeu().setScreen(new ArriveeRemarquable(getMss().getJeu()));
    }
}
