package com.odysseedesmaths.scenes;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class Scene0 extends Scene {

    Texture background;

    public Scene0 () {
        background = new Texture(Gdx.files.internal("scenes/scene0.png"));
        // Je ne me sers pas de la classe Assets tant qu'elle n'est pas rangée ;)
    }

    @Override
    public Texture getBackground() {
        return background;
    }

    @Override
    public void aventure() {

    }
}
