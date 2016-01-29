package com.odysseedesmaths;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.HashMap;
import java.util.Map;

public abstract class MiniJeu extends Game {

    private String regles; //voir si on garde un String
    private Timer timer;

    @Override
    public void create() {

    }

    @Override
    public void render() {
        super.render();
    }

    public void dispose() {

    }

    public abstract void gameOver();
}
