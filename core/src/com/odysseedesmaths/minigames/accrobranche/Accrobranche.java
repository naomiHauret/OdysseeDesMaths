package com.odysseedesmaths.minigames.accrobranche;

import com.odysseedesmaths.OdysseeDesMaths;
import com.odysseedesmaths.Timer;
import com.odysseedesmaths.minigames.MiniGame;
import com.odysseedesmaths.minigames.accrobranche.entities.Hero;

public class Accrobranche extends MiniGame {

    public static final int TIME_LIMIT = 3;

    public Hero hero;
    public Terrain terrain;
    public Timer timer;

    public Accrobranche(OdysseeDesMaths game) {
        super(game);
        init();
        setScreen(new TreeScreen(this));
    }

    public void init() {
        terrain = new Terrain();
        hero = new Hero(this);
        timer = new Timer(TIME_LIMIT * Timer.ONE_MINUTE);
        setState(State.RUNNING);
    }

    public void pauseGame() {
        setState(State.PAUSED);
        timer.stop();
    }

    public void returnToGame() {
        setState(State.RUNNING);
        timer.start();
    }

    public void restartGame() {
        getGame().setScreen(new Accrobranche(getGame()));
    }

    public void gameOver() {
        setState(State.GAME_OVER);
        timer.stop();
        ((TreeScreen)currentScreen).gameOver();
    }
}
