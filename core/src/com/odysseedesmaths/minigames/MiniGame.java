package com.odysseedesmaths.minigames;

import com.badlogic.gdx.Screen;
import com.odysseedesmaths.OdysseeDesMaths;
import com.odysseedesmaths.Timer;

public abstract class MiniGame implements Screen {

    protected OdysseeDesMaths game;
    protected Screen currentScreen;

    protected String regles; //voir si on garde un String
    protected Timer timer;

    public MiniGame(OdysseeDesMaths game) {
        this.game = game;
    }

    public void setScreen(Screen screen) {
        if (currentScreen != null) currentScreen.dispose();
        currentScreen = screen;
    }

    @Override
    public void show() {
        currentScreen.show();
    }

    @Override
    public void render(float delta) {
        currentScreen.render(delta);
    }

    @Override
    public void resize(int width, int height) {
        currentScreen.resize(width, height);
    }

    @Override
    public void pause() {
        currentScreen.pause();
    }

    @Override
    public void resume() {
        currentScreen.resume();
    }

    @Override
    public void hide() {
        currentScreen.hide();
    }

    @Override
    public void dispose() {
        currentScreen.dispose();
    }

    public abstract void gameOver();
}
